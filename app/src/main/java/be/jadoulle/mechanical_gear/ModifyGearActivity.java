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

import be.jadoulle.mechanical_gear.AsyncTask.GearAsyncTask;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.Entities.Gear;
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

    private ActivityResultLauncher<Void> representationLauncher = registerForActivityResult(
            new ActivityResultContracts.TakePicturePreview(),
            (Bitmap result) -> {
                //get new picture (gear representation)
                if(result != null) {
                    //save representation
                    Representation newRep = new Representation(0, Utils.bitmapToByteArray(result), this.modifyGear.getGear().getId());
                    this.modifyGear.addRepresentation(newRep);
                    this.refreshRepresentations(result);
                }
            });

    private ActivityResultLauncher<Intent> signalTypeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (ActivityResult result) -> {
                Intent data = result.getData();
                //get new signalType
                if(data.hasExtra("signalTypeName")) {
                    String signalTypeName = data.getStringExtra("signalTypeName");
                    Bitmap signalTypePicture = data.getParcelableExtra("signalTypePicture");

                    if (signalTypeName.isEmpty())
                        signalTypeName = null;

                    //save signalType
                    SignalType newSignal = new SignalType(0, signalTypeName, Utils.bitmapToByteArray(signalTypePicture), this.modifyGear.getGear().getId());
                    this.modifyGear.addSignalType(newSignal);
                    this.refreshSignalTypes(signalTypePicture);
                }
            });

    private View.OnClickListener addRepresentationListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Utils.askCameraPermission(ModifyGearActivity.this, ActivityCode.MODIFY_GEAR_ACTIVITY_CODE)) {
                try {
                    representationLauncher.launch(null);
                }
                catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private View.OnClickListener addsignalTypeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                Intent intent = new Intent(ModifyGearActivity.this, AddSignalTypeActivity.class);
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
                Gear currentGear = modifyGear.getGear();

                //get views data
                String denomination = et_denomination.getText().toString();
                String sensorType = et_sensorType.getText().toString();
                String basicWorking = et_basicWorking.getText().toString();
                String role = et_role.getText().toString();
                String nbrWire  = et_nbrWire.getText().toString();
                String tests = et_tests.getText().toString();
                String category = et_category.getText().toString();
                String composition = et_composition.getText().toString();
                String note = et_note.getText().toString();

                currentGear.setDenomination(denomination.isEmpty() ? null : denomination);
                currentGear.setSensorType(sensorType.isEmpty() ? null : sensorType);
                currentGear.setBasicWorking(basicWorking.isEmpty() ? null : basicWorking);
                currentGear.setRole(role.isEmpty() ? null : role);
                currentGear.setNbrWire(nbrWire.isEmpty() ? 0 : Byte.parseByte(nbrWire));
                currentGear.setTests(tests.isEmpty() ? null : tests);
                currentGear.setCategory(category.isEmpty() ? null : category);
                currentGear.setComposition(composition.isEmpty() ? null : composition);
                currentGear.setNote(note.isEmpty() ? null : note);

                //call async task, update gear
                new GearAsyncTask(ModifyGearActivity.this).updateGear(modifyGear);
            }
            else {
                Utils.showToast(view.getContext(), getResources().getString(R.string.gear_nbrWire_invalid_message) + "\n coucou", Toast.LENGTH_SHORT);
            }
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
        findViewById(R.id.btn_cancel).setOnClickListener(cancelListener);
        findViewById(R.id.btn_validate).setOnClickListener(validateListener);
        findViewById(R.id.btn_add_representation).setOnClickListener(addRepresentationListener);
        findViewById(R.id.btn_add_signalType).setOnClickListener(addsignalTypeListener);

        this.modifyGear = (GearWithAllObjects) getIntent().getSerializableExtra("modifyGear");
//        System.out.println("modify gear before updating : " + this.modifyGear);

        this.initViews();
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
        SignalType lastSignal = this.modifyGear.getSignalTypes().get(this.modifyGear.getSignalTypes().size() - 1);
        TextView textView = new TextView(this);
        Drawable bitmapDrawable = new BitmapDrawable(this.getResources(), lastBitmap);

        textView.setPaddingRelative(10,5,10,5);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, bitmapDrawable,null,null);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setText(lastSignal.getName());

        layout.addView(textView);
    }

    /**
     * @return true if the modifyGearActivity's form is Valid otherwise false
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

    public void confirmGearUpdating(boolean isUpdate) {
        if (isUpdate) {
            Utils.showToast(this, this.getResources().getString(R.string.gear_update_message), Toast.LENGTH_SHORT);
            Intent backIntent = new Intent();
            backIntent.putExtra("updatedGear", this.modifyGear);
            setResult(RESULT_OK, backIntent);
            finish();
        }
    }

}