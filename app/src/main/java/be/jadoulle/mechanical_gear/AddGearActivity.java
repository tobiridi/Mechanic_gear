package be.jadoulle.mechanical_gear;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

    private ActivityResultLauncher<Void> representationLauncher = registerForActivityResult(
            new ActivityResultContracts.TakePicturePreview(),
            (Bitmap result) -> {
                //get new picture (gear representation)
                if(result != null) {
                    //save representation
                    Representation newRep = new Representation(0, Utils.bitmapToByteArray(result),0);
                    this.gearRepresentations.add(newRep);
                    this.refreshRepresentations(result);
                }
            });

    private ActivityResultLauncher<Intent> signalTypeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (ActivityResult result) -> {
                Intent data = result.getData();
                if (data != null && result.getResultCode() == RESULT_OK) {
                    //get new signalType
                    if(data.hasExtra("signalTypeName")) {
                        String signalTypeName = data.getStringExtra("signalTypeName");
                        Bitmap signalTypePicture = data.getParcelableExtra("signalTypePicture");

                        if (signalTypeName.isEmpty())
                            signalTypeName = null;

                        //save signalType
                        SignalType newSignal = new SignalType(0, signalTypeName, Utils.bitmapToByteArray(signalTypePicture),0);
                        this.gearSignalTypes.add(newSignal);
                        this.refreshSignalTypes(signalTypePicture);
                    }
                }
            });

    private View.OnClickListener addRepresentationListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Utils.askCameraPermission(AddGearActivity.this, ActivityCode.MAIN_ACTIVITY_CODE)) {
                try {
                    representationLauncher.launch(null);
                }
                catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private View.OnClickListener addSignalTypeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                Intent intent = new Intent(AddGearActivity.this, AddSignalTypeActivity.class);
                signalTypeLauncher.launch(intent);
            }
            catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setResult(RESULT_CANCELED);
            finish();
        }
    };

    private View.OnClickListener validateListener = new View.OnClickListener() {
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
        findViewById(R.id.btn_cancel).setOnClickListener(cancelListener);
        findViewById(R.id.btn_validate).setOnClickListener(validateListener);
        findViewById(R.id.btn_add_representation).setOnClickListener(addRepresentationListener);
        findViewById(R.id.btn_add_signalType).setOnClickListener(addSignalTypeListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals(Manifest.permission.CAMERA) && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                findViewById(R.id.btn_add_representation).setClickable(false);
            }
        }
    }

    /**
     * Add the last representation picture into the linear layout
     */
    private void refreshRepresentations(Bitmap lastBitmap) {
        LinearLayout layout = findViewById(R.id.ll_gear_representations);
        ImageView img = new ImageView(this);
        img.setImageBitmap(lastBitmap);
        img.setPaddingRelative(10, 0, 0, 0);
        layout.addView(img);
    }

    /**
     * Add the last signalType picture into the linear layout
     */
    private void refreshSignalTypes(Bitmap lastBitmap) {
        LinearLayout layout = findViewById(R.id.ll_gear_signal_types);
        SignalType lastSignal = this.gearSignalTypes.get(this.gearSignalTypes.size() - 1);
        TextView textView = new TextView(this);
        Drawable bitmapDrawable = new BitmapDrawable(this.getResources(), lastBitmap);

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