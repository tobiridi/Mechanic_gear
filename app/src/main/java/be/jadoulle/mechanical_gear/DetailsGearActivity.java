package be.jadoulle.mechanical_gear;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import be.jadoulle.mechanical_gear.AsyncTask.GearDeleteAsyncTask;
import be.jadoulle.mechanical_gear.AsyncTask.RepresentationCreateAsyncTask;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.Entities.Representation;
import be.jadoulle.mechanical_gear.Utils.ActivityCode;
import be.jadoulle.mechanical_gear.Utils.Utils;

public class DetailsGearActivity extends AppCompatActivity {
    private GearWithAllObjects selectedGear;
    private TableLayout tableLayout;
    private LinearLayout representationLayout;

    private View.OnClickListener delete_gear_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //call async task, delete gear
            new GearDeleteAsyncTask(DetailsGearActivity.this).execute(selectedGear.getGear());

            //TODO : move to add gear activity
            //TODO : optimise with camera application
//            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intentCamera, ActivityCode.DETAILS_GEAR_ACTIVITY_CODE);
        }
    };

    private View.OnClickListener modify_gear_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO : not implemented
            Utils.showToast(DetailsGearActivity.this, "in Progress...", Toast.LENGTH_SHORT);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_gear);

        this.tableLayout = findViewById(R.id.tl_details_gear);
        this.representationLayout = findViewById(R.id.ll_gear_representations);

        findViewById(R.id.btn_delete).setOnClickListener(delete_gear_listener);
        findViewById(R.id.btn_modify).setOnClickListener(modify_gear_listener);

        //get selected gear from recycler view
        this.selectedGear = (GearWithAllObjects) this.getIntent().getSerializableExtra("selectedGear");

        //add data of a gear
        this.addDataInLayout();
    }

    private void addDataInLayout() {
        String[] fields = this.gearFields();
        String[] gearData = this.selectedGear.getGear().getAllDataToStringArray();

        /*
         * order of table row data
         * gear's representations
         * gear info
         * gear's signalType
         */
        // TODO : signalType (before gear.getNote())

        for (int i = 0; i < fields.length; i++) {
            //gear's representations
            if(fields[i].equals(this.getResources().getString(R.string.gear_representation))) {
                if(!this.selectedGear.getRepresentations().isEmpty()) {
                    this.addPictureInTableRow(fields[i]);
                }
            }
            //gear info, fields and gear data not the same index
            else if(gearData[i-1] != null) {
                this.addTableRow(fields[i], gearData[i-1]);
            }
            //TODO : gear's signal Type
        }
    }

    private void addPictureInTableRow(String field) {
        TableRow row = null;
        TextView textView = new TextView(this);

        //configure textView
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(0,0,10,0);
        textView.setLayoutParams(params);
        textView.setTextAppearance(R.style.text_view);
        textView.setText(field);

        //add gear's representations
        if(field.equals(this.getResources().getString(R.string.gear_representation))) {
            row = findViewById(R.id.tr_gear_representations);
            row.addView(textView, 0);
            this.refreshRepresentations();
        }
        //add gear's signal Type
        else {
            //TODO : refresh signal Type
        }
    }

    private void addTableRow(String field, String data) {
        TableRow row = new TableRow(this);
        TextView textView = new TextView(this);
        TextView textViewData = new TextView(this);

        //configure tableRow
        row.setPadding(0,0,0,20);

        //configure textView
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(0,0,10,0);
        textView.setLayoutParams(params);
        textView.setTextAppearance(R.style.text_view);
        textView.setText(field);

        //configure textView for data
        textViewData.setMaxWidth(500);
        textViewData.setTextAppearance(R.style.text_view);
        textViewData.setTypeface(null, Typeface.BOLD);
        textViewData.setText(data);

        row.addView(textView);
        row.addView(textViewData);
        this.tableLayout.addView(row);
    }

    private String[] gearFields() {
        Resources resources = this.getResources();
        //TODO : not finished, because empty implemententation for signalType
        return new String[] {
                resources.getString(R.string.gear_representation),
                resources.getString(R.string.gear_denomination),
                resources.getString(R.string.gear_gearCategory),
                resources.getString(R.string.gear_gearSensorType),
                resources.getString(R.string.gear_basicWorking),
                resources.getString(R.string.gear_role),
                resources.getString(R.string.gear_nbrWire),
                resources.getString(R.string.gear_tests),
                resources.getString(R.string.gear_composition),
//                resources.getString(R.string.gear_signalType),
                resources.getString(R.string.gear_note),
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //TODO : move all to add gear activity
        if(data != null && requestCode == ActivityCode.DETAILS_GEAR_ACTIVITY_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            //get picture
            Bitmap picture = (Bitmap) bundle.get("data");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);

            //save representation
            Representation newRepresentation = new Representation(0,outputStream.toByteArray(),this.selectedGear.getGear().getId());

            //call async task
            new RepresentationCreateAsyncTask(DetailsGearActivity.this).execute(newRepresentation);

            //add picture
            if (this.selectedGear.getRepresentations().isEmpty()) {
                this.selectedGear.addRepresentation(newRepresentation);
                this.addPictureInTableRow(this.getResources().getString(R.string.gear_representation));
            }
            else {
                this.selectedGear.addRepresentation(newRepresentation);
                this.refreshRepresentations();
            }

        }
    }

    private void refreshRepresentations() {
        //TODO : move all to add gear activity
        this.representationLayout.removeAllViews();
        for (Representation rep : this.selectedGear.getRepresentations()) {
            ImageView img = new ImageView(this);
            img.setImageBitmap(BitmapFactory.decodeByteArray(rep.getPicture(),0, rep.getPicture().length));
            img.setPaddingRelative(10,0,0,0);
            this.representationLayout.addView(img);
        }
    }

    public void confirmRepresentationCreation(boolean isCreated) {
        //TODO : move all to add gear activity
        if(isCreated) {
            Utils.showToast(this, "représentation créer", Toast.LENGTH_LONG);
        }
        else {
            Utils.showToast(this, "représentation non créer", Toast.LENGTH_LONG);
        }
    }

    public void confirmGearSuppression(boolean isDelete) {
        if (isDelete) {
            Utils.showToast(this, this.getResources().getString(R.string.gear_delete_message), Toast.LENGTH_SHORT);
            //TODO : back and refresh list
            Intent backIntent = new Intent();
            backIntent.putExtra("deletedGear", this.selectedGear);
            setResult(RESULT_OK, backIntent);
            finish();
        }
        else {
            Utils.showToast(this, "gear not deleted ", Toast.LENGTH_SHORT);
        }
    }
}