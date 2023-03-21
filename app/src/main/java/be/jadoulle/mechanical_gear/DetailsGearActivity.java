package be.jadoulle.mechanical_gear;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import be.jadoulle.mechanical_gear.Entities.SignalType;
import be.jadoulle.mechanical_gear.Utils.Utils;

public class DetailsGearActivity extends AppCompatActivity {
    private GearWithAllObjects selectedGear;
    private TableLayout tableLayout;
    private int signalTypeViewIndex;

    private ActivityResultLauncher<Intent> modifyGearLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (ActivityResult result) -> {
                Intent data = result.getData();
                if (data != null && result.getResultCode() == RESULT_OK) {
                    this.selectedGear = (GearWithAllObjects) data.getSerializableExtra("updatedGear");
                    setContentView(R.layout.activity_details_gear);
                    this.initViews();
                }
            });

    private View.OnClickListener deleteGearListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //call async task, delete gear
            new GearAsyncTask(DetailsGearActivity.this).deleteGear(selectedGear.getGear());
        }
    };

    private View.OnClickListener modifyGearListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                Intent intent = new Intent(DetailsGearActivity.this, ModifyGearActivity.class);
                intent.putExtra("modifyGear", selectedGear);
                modifyGearLauncher.launch(intent);
            }
            catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_gear);

        this.initViews();
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent();
        backIntent.putExtra("updatedGear", this.selectedGear);
        setResult(RESULT_OK, backIntent);
        super.onBackPressed();
    }

    private void initViews() {
        this.tableLayout = findViewById(R.id.tl_details_gear);

        findViewById(R.id.btn_delete).setOnClickListener(deleteGearListener);
        findViewById(R.id.btn_modify).setOnClickListener(modifyGearListener);

        if (this.selectedGear == null) {
            //get selected gear from recycler view
            this.selectedGear = (GearWithAllObjects) this.getIntent().getSerializableExtra("selectedGear");
        }

        this.addDataInTableLayout();
    }

    private void addDataInTableLayout() {
        String[][] fields = this.gearFields();
        String signalTypeRes = this.getResources().getString(R.string.gear_signalType);

        for (int i = 0; i < fields.length; i++) {
            if(fields[i][0].equals(signalTypeRes)) {
                this.signalTypeViewIndex = this.tableLayout.getChildCount() - 1;
            }
            else if (fields[i][1] != null) {
                //add row if data
                this.addTableRow(fields[i][0], fields[i][1]);
            }
        }

        this.moveSignalTypeView();
        this.addRepresentations();
        this.addSignalTypes();
    }

    private void addRepresentations() {
        LinearLayout layout = findViewById(R.id.ll_gear_representations);
        for (Representation rep : this.selectedGear.getRepresentations()) {
            Bitmap bitmap = Utils.byteArrayToBitmap(rep.getPicture());
            ImageView img = new ImageView(this);
            img.setImageBitmap(bitmap);
            img.setPaddingRelative(10, 0, 0, 0);
            layout.addView(img);
        }
    }

    private void addSignalTypes() {
        LinearLayout layout = findViewById(R.id.ll_gear_signal_types);
        for (SignalType signalType : this.selectedGear.getSignalTypes()) {
            TextView textViewData = new TextView(this);

            Bitmap bitmap = Utils.byteArrayToBitmap(signalType.getPicture());
            Drawable bitmapDrawable = new BitmapDrawable(this.getResources(), bitmap);

            textViewData.setPaddingRelative(10, 5, 10, 5);
            textViewData.setCompoundDrawablesWithIntrinsicBounds(null, bitmapDrawable, null, null);
            textViewData.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textViewData.setText(signalType.getName());

            layout.addView(textViewData);
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
        this.tableLayout.addView(row, this.tableLayout.getChildCount() -1);
    }

    private void moveSignalTypeView() {
        int nbrChild = this.tableLayout.getChildCount();
        View signalTypeView = this.tableLayout.getChildAt(nbrChild -1);

        //move signal type field after "gear tests" field
        this.tableLayout.removeViewAt(nbrChild - 1);
        this.tableLayout.addView(signalTypeView, this.signalTypeViewIndex);
    }

    private String[][] gearFields() {
        Resources resources = this.getResources();
        String[] gearData = this.selectedGear.getGear().getAllDataToStringArray();
        return new String[][] {
                {resources.getString(R.string.gear_representation), null},
                {resources.getString(R.string.gear_denomination), gearData[0]},
                {resources.getString(R.string.gear_gearCategory), gearData[1]},
                {resources.getString(R.string.gear_gearSensorType), gearData[2]},
                {resources.getString(R.string.gear_basicWorking), gearData[3]},
                {resources.getString(R.string.gear_role), gearData[4]},
                {resources.getString(R.string.gear_nbrWire), gearData[5]},
                {resources.getString(R.string.gear_tests), gearData[6]},
                {resources.getString(R.string.gear_signalType), null},
                {resources.getString(R.string.gear_composition), gearData[7]},
                {resources.getString(R.string.gear_note), gearData[8]},
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
    }
}