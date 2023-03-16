package be.jadoulle.mechanical_gear;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import be.jadoulle.mechanical_gear.AsyncTask.GearAsyncTask;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.Entities.Representation;
import be.jadoulle.mechanical_gear.Entities.SignalType;
import be.jadoulle.mechanical_gear.Utils.ActivityCode;
import be.jadoulle.mechanical_gear.Utils.Utils;

public class ModifyGearActivity extends AppCompatActivity {
    private EditText et_denomination;
    private EditText et_sensorType;
    private EditText et_basicWorking;
    private EditText et_role;
    private EditText et_nbrWire;
    private EditText et_tests;
    private EditText et_category;
    private EditText et_composition;
    private EditText et_note;
    private GearWithAllObjects modifyGear;

    private View.OnClickListener add_representation_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO : optimise with camera application
            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intentCamera, ActivityCode.MODIFY_GEAR_ACTIVITY_CODE);
        }
    };

    private View.OnClickListener add_signal_type_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO : optimise
            Intent intent = new Intent(ModifyGearActivity.this, AddSignalTypeActivity.class);
            startActivityForResult(intent, ActivityCode.MODIFY_GEAR_ACTIVITY_CODE);
        }
    };

    private View.OnClickListener cancel_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setResult(RESULT_CANCELED);
            finish();
        }
    };

    private View.OnClickListener validate_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO : not implemented
            Utils.showToast(view.getContext(), "not implemented", Toast.LENGTH_SHORT);
//            if (isValidForm()) {
//                //call async task, create a new gear
//                new GearAsyncTask(AddGearActivity.this).createGear(
//                        et_denomination.getText().toString(),
//                        et_sensorType.getText().toString(),
//                        et_basicWorking.getText().toString(),
//                        et_role.getText().toString(),
//                        et_nbrWire.getText().toString(),
//                        et_tests.getText().toString(),
//                        et_category.getText().toString(),
//                        et_note.getText().toString(),
//                        et_composition.getText().toString()
//                );
//            }
//            else {
//                Utils.showToast(view.getContext(), getResources().getString(R.string.gear_nbrWire_invalid_message), Toast.LENGTH_SHORT);
//            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //use same layout than "AddGearActivity"
        setContentView(R.layout.activity_add_gear);

        //bind views
        this.et_denomination = findViewById(R.id.et_gear_denomination);
        this.et_sensorType = findViewById(R.id.et_gear_sensorType);
        this.et_basicWorking = findViewById(R.id.et_gear_basicWorking);
        this.et_role = findViewById(R.id.et_gear_role);
        this.et_nbrWire = findViewById(R.id.et_gear_nbrWire);
        this.et_tests = findViewById(R.id.et_gear_tests);
        this.et_category = findViewById(R.id.et_gear_gearCategory);
        this.et_composition = findViewById(R.id.et_gear_composition);
        this.et_note = findViewById(R.id.et_gear_note);

        //set listeners
        findViewById(R.id.btn_cancel).setOnClickListener(cancel_listener);
        findViewById(R.id.btn_validate).setOnClickListener(validate_listener);
        findViewById(R.id.btn_add_representation).setOnClickListener(add_representation_listener);
        findViewById(R.id.btn_add_signalType).setOnClickListener(add_signal_type_listener);

        this.modifyGear = (GearWithAllObjects) getIntent().getSerializableExtra("modifyGear");

        this.initViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == ActivityCode.MODIFY_GEAR_ACTIVITY_CODE && resultCode == RESULT_OK) {
            //TODO : not implemented
        }
    }

    private void initViews() {
        TextView title = findViewById(R.id.tv_title);
        title.setText(this.getResources().getString(R.string.modify_gear_title));

        this.et_denomination.setText(this.modifyGear.getGear().getDenomination());
        this.et_sensorType.setText(this.modifyGear.getGear().getSensorType());
        this.et_basicWorking.setText(this.modifyGear.getGear().getBasicWorking());
        this.et_role.setText(this.modifyGear.getGear().getRole());
        this.et_nbrWire.setText(String.valueOf(this.modifyGear.getGear().getNbrWire()));
        this.et_tests.setText(this.modifyGear.getGear().getTests());
        this.et_category.setText(this.modifyGear.getGear().getCategory());
        this.et_composition.setText(this.modifyGear.getGear().getComposition());
        this.et_note.setText(this.modifyGear.getGear().getNote());

        LinearLayout representationsLayout = findViewById(R.id.ll_gear_representations);
        for (Representation rep : this.modifyGear.getRepresentations()) {
            Bitmap bitmap = Utils.byteArrayToBitmap(rep.getPicture());
            ImageView img = new ImageView(this);
            img.setImageBitmap(bitmap);
            img.setPaddingRelative(10, 0, 0, 0);
            representationsLayout.addView(img);
        }

        LinearLayout signalTypesLayout = findViewById(R.id.ll_gear_signal_types);
        for (SignalType signalType : this.modifyGear.getSignalTypes()) {
            TextView textViewData = new TextView(this);

            Bitmap bitmap = Utils.byteArrayToBitmap(signalType.getPicture());
            Drawable bitmapDrawable = new BitmapDrawable(this.getResources(), bitmap);

            textViewData.setPaddingRelative(10, 5, 10, 5);
            textViewData.setCompoundDrawablesWithIntrinsicBounds(null, bitmapDrawable, null, null);
            textViewData.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textViewData.setText(signalType.getName());

            signalTypesLayout.addView(textViewData);
        }
    }
}