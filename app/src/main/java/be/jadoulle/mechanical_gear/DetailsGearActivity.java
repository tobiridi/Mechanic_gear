package be.jadoulle.mechanical_gear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import be.jadoulle.mechanical_gear.AsyncTask.GearAsyncTask;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.Entities.Representation;
import be.jadoulle.mechanical_gear.Utils.Utils;

public class DetailsGearActivity extends AppCompatActivity {
    private GearWithAllObjects selectedGear;
    private TableLayout tableLayout;
    private LinearLayout representationLayout;

    private View.OnClickListener delete_gear_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //call async task, delete gear
            new GearAsyncTask(DetailsGearActivity.this).deleteGear(selectedGear.getGear());
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
        System.out.println("details of gear : " + this.selectedGear);

        //add data of a gear
        this.addDataInLayout();
    }

    private void addDataInLayout() {
        String[] fields = this.gearFields();
        String[] gearData = this.selectedGear.getGear().getAllDataToStringArray();
//        String[] signalTypeData_1 = null;
//        Bitmap[] signalTypeData_2 = null;

        /*
         * order of table row data
         * gear's representations
         * gear info
         * gear's signalType
         */
        // TODO : signalType (before gear.getNote())

        //TODO : maybe change implementation
        for (int i = 0; i < fields.length; i++) {
            //gear's representations
            if(fields[i].equals(this.getResources().getString(R.string.gear_representation))) {
                this.addRepresentationsInTableRow();
            }
            //gear info, fields and gear data don't have the same index
            else if(gearData[i-1] != null) {
                this.addTableRow(fields[i], gearData[i-1]);
            }
            //TODO : gear's signal Type
        }
    }

    private void addRepresentationsInTableRow() {
        if(!this.selectedGear.getRepresentations().isEmpty()) {
            LinearLayout layout = findViewById(R.id.ll_gear_representations);
            for(Representation rep : this.selectedGear.getRepresentations()) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(rep.getPicture(), 0, rep.getPicture().length);
                ImageView img = new ImageView(this);
                img.setImageBitmap(bitmap);
                img.setPaddingRelative(10, 0, 0, 0);
                layout.addView(img);
            }
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
        //TODO : not finished, because empty implementation for signalType
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

    public void confirmGearSuppression(boolean isDelete) {
        if (isDelete) {
            Utils.showToast(this, this.getResources().getString(R.string.gear_delete_message), Toast.LENGTH_SHORT);
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