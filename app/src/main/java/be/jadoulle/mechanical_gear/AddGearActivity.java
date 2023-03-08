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
import be.jadoulle.mechanical_gear.Entities.Gear;
import be.jadoulle.mechanical_gear.Entities.Representation;
import be.jadoulle.mechanical_gear.Entities.SignalType;
import be.jadoulle.mechanical_gear.Utils.ActivityCode;
import be.jadoulle.mechanical_gear.Utils.Utils;

public class AddGearActivity extends AppCompatActivity {
    private EditText et_denomination;
    private EditText et_sensorType;
    private EditText et_basicWorking;
    private EditText et_role;
    private EditText et_nbrWire;
    private EditText et_tests;
    private EditText et_category;
    private EditText et_composition;
    private EditText et_note;
    private GearWithAllObjects newGearWithAllObjectsCreated = null;
    private ArrayList<Representation> gearRepresentations = new ArrayList<>();
    private ArrayList<SignalType> gearSignalTypes = new ArrayList<>();

    private View.OnClickListener add_representation_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO : optimise with camera application
            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intentCamera, ActivityCode.ADD_GEAR_ACTIVITY_CODE);
        }
    };

    private View.OnClickListener add_signal_type_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO : optimise
            Intent intent = new Intent(AddGearActivity.this, AddSignalTypeActivity.class);
            startActivityForResult(intent, ActivityCode.ADD_GEAR_ACTIVITY_CODE);
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
            if (isValidForm()) {
                //call async task, create a new gear
                new GearAsyncTask(AddGearActivity.this).createGear(
                        et_denomination.getText().toString(),
                        et_sensorType.getText().toString(),
                        et_basicWorking.getText().toString(),
                        et_role.getText().toString(),
                        et_nbrWire.getText().toString(),
                        et_tests.getText().toString(),
                        et_category.getText().toString(),
                        et_note.getText().toString(),
                        et_composition.getText().toString()
                );
            }
            else {
                Utils.showToast(view.getContext(), getResources().getString(R.string.gear_nbrWire_invalid_message), Toast.LENGTH_SHORT);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == ActivityCode.ADD_GEAR_ACTIVITY_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap picture = (Bitmap) bundle.get("data");

            //get new picture (gear representation)
            if(picture != null) {
                //save representation
                Representation newRep = new Representation(0, Utils.bitmapToByteArray(picture),0);
                this.gearRepresentations.add(newRep);
                this.refreshRepresentations();
            }
            //get new signalType
            else if(data.hasExtra("signalTypeName") && data.hasExtra("signalTypePicture")) {
                String signalTypeName = data.getStringExtra("signalTypeName");
                Bitmap signalTypePicture = data.getParcelableExtra("signalTypePicture");

                if (signalTypeName.isEmpty())
                    signalTypeName = null;

                //save signalType
                SignalType newSignal = new SignalType(0, signalTypeName, Utils.bitmapToByteArray(signalTypePicture),0);
                this.gearSignalTypes.add(newSignal);
                this.refreshSignalTypes();
            }
        }
    }

    /**
     * Add the last representation picture into the linear layout
     */
    private void refreshRepresentations() {
        LinearLayout layout = findViewById(R.id.ll_gear_representations);
        Representation lastRep = this.gearRepresentations.get(this.gearRepresentations.size() - 1);
        Bitmap bitmap = Utils.byteArrayToBitmap(lastRep.getPicture());
        ImageView img = new ImageView(this);
        img.setImageBitmap(bitmap);
        img.setPaddingRelative(10, 0, 0, 0);
        layout.addView(img);
    }

    /**
     * Add the last signalType picture into the linear layout
     */
    private void refreshSignalTypes() {
        LinearLayout layout = findViewById(R.id.ll_gear_signal_types);
        SignalType lastSignal = this.gearSignalTypes.get(this.gearSignalTypes.size() - 1);
        TextView textView = new TextView(this);

        Bitmap bitmap = Utils.byteArrayToBitmap(lastSignal.getPicture());
        Drawable bitmapDrawable = new BitmapDrawable(this.getResources(), bitmap);

        textView.setPaddingRelative(10,5,10,5);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, bitmapDrawable,null,null);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setText(lastSignal.getName());

        layout.addView(textView);
    }

    public void confirmGearCreation(Gear newGearCreated) {
        this.newGearWithAllObjectsCreated = new GearWithAllObjects(newGearCreated);
        this.newGearWithAllObjectsCreated.setRepresentations(this.gearRepresentations);
        this.newGearWithAllObjectsCreated.setSignalTypes(this.gearSignalTypes);

        Utils.showToast(this, this.getResources().getString(R.string.gear_creation_message), Toast.LENGTH_SHORT);
        Intent backIntent = new Intent();
        backIntent.putExtra("newGear", this.newGearWithAllObjectsCreated);
        setResult(RESULT_OK, backIntent);
        finish();
    }

    /**
     * @return true if the AddGearActivity's form is Valid otherwise false
     */
    private boolean isValidForm() {
        String wires = this.et_nbrWire.getText().toString();
        //check nbrWire
        if(!wires.isEmpty()) {
            try {
                Byte.parseByte(wires);
            }
            catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

}