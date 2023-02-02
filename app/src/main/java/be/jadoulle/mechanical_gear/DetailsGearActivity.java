package be.jadoulle.mechanical_gear;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Constraints;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.Entities.Gear;
import be.jadoulle.mechanical_gear.Utils.Utils;

public class DetailsGearActivity extends AppCompatActivity {
    private GearWithAllObjects selectedGear;
    private TableLayout tableLayout;

    private View.OnClickListener add_representation_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO : not implemented
            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intentCamera,1);
        }
    };

    private View.OnClickListener add_signalType_listener = new View.OnClickListener() {
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

        findViewById(R.id.btn_add_representation).setOnClickListener(add_representation_listener);
        findViewById(R.id.btn_add_signalType).setOnClickListener(add_signalType_listener);

        //get selected gear from recycler view
        this.selectedGear = (GearWithAllObjects) this.getIntent().getSerializableExtra("selectedGear");

        //add tableRow if data
        this.addTableRowForData();
    }

    private void addTableRowForData() {
        String[] fields = this.gearFields();
        String[] gearData = this.selectedGear.getGear().getAllDataToStringArray();

        //TODO : representation
        // gear
        // signalType (before gear.getNote())

        //TODO : add signalType array and representation array
        for (int i = 0; i < fields.length; i++) {
            if(gearData[i] != null) {
                this.addTableRow(fields[i], gearData[i]);
            }
        }
    }

    private void addTableRow(String text, String data) {
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
        textView.setText(text);

        //configure textView for data
        textViewData.setMaxWidth(500);
        textViewData.setTextAppearance(R.style.text_view);
        textViewData.setTypeface(null, Typeface.BOLD);
        textViewData.setText(data);

        row.addView(textView);
        row.addView(textViewData);
        // TODO : change when representation and signalType are ready
        this.tableLayout.addView(row);
    }

    private String[] gearFields() {
        Resources resources = this.getResources();
        //TODO : not finished, because empty implemententation for representation and signalType
        return new String[] {
//                resources.getString(R.string.gear_representation),
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
    }
}