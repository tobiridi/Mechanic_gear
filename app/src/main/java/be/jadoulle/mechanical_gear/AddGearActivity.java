package be.jadoulle.mechanical_gear;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import be.jadoulle.mechanical_gear.AsyncTask.GearCreateAsyncTask;
import be.jadoulle.mechanical_gear.AsyncTask.RepresentationCreateAsyncTask;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.Entities.Gear;
import be.jadoulle.mechanical_gear.Entities.Representation;
import be.jadoulle.mechanical_gear.Utils.ActivityCode;
import be.jadoulle.mechanical_gear.Utils.Utils;

public class AddGearActivity extends AppCompatActivity {
    private ArrayList<Bitmap> representationPictures = new ArrayList<>();

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
            EditText denomination = findViewById(R.id.et_gear_denomination);
            EditText sensorType = findViewById(R.id.et_gear_sensorType);
            EditText basicWorking = findViewById(R.id.et_gear_basicWorking);
            EditText role = findViewById(R.id.et_gear_role);
            EditText nbrWire = findViewById(R.id.et_gear_nbrWire);
            EditText tests = findViewById(R.id.et_gear_tests);
            EditText category = findViewById(R.id.et_gear_gearCategory);
            EditText composition = findViewById(R.id.et_gear_composition);
            EditText note = findViewById(R.id.et_gear_note);

            //call async task, create a new gear
            new GearCreateAsyncTask(AddGearActivity.this).execute(
                    denomination.getText().toString(),
                    sensorType.getText().toString(),
                    basicWorking.getText().toString(),
                    role.getText().toString(),
                    nbrWire.getText().toString(),
                    tests.getText().toString(),
                    category.getText().toString(),
                    composition.getText().toString(),
                    note.getText().toString()
            );
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gear);

        findViewById(R.id.btn_cancel).setOnClickListener(cancel_listener);
        findViewById(R.id.btn_validate).setOnClickListener(validate_listener);
        findViewById(R.id.btn_add_representation).setOnClickListener(add_representation_listener);
        findViewById(R.id.btn_add_signalType).setOnClickListener(add_signal_type_listener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == ActivityCode.ADD_GEAR_ACTIVITY_CODE && resultCode == RESULT_OK) {
            //get picture (gear representation)
            Bundle bundle = data.getExtras();
            Bitmap picture = (Bitmap) bundle.get("data");

            if(picture != null) {
                //save representation
                this.representationPictures.add(picture);
                this.refreshRepresentations();
            }

            //get new signalType
            //TODO : save signal type
            if(data.hasExtra("signalTypeName") && data.hasExtra("signalTypePicture")) {
                Bitmap signalTypePicture = data.getParcelableExtra("signalTypePicture");
                String signalTypeName = data.getStringExtra("signalTypeName");

                //test display bitmap
//                this.representationPictures.add(signalTypePicture);
//                this.refreshRepresentations();
                //TODO : i am here

                System.out.println("info recu : " + signalTypeName + signalTypePicture);
            }
        }
    }

    /**
     * Add the last representation's picture into the linear layout
     */
    private void refreshRepresentations() {
        LinearLayout layout = findViewById(R.id.ll_gear_representations);
        Bitmap bitmap = this.representationPictures.get(this.representationPictures.size() - 1);
        ImageView img = new ImageView(this);
        img.setImageBitmap(bitmap);
        img.setPaddingRelative(10, 0, 0, 0);
        layout.addView(img);
    }

    public void saveOtherGearData(GearWithAllObjects newGearWithAllObjects) {
        //the last asyncTask, call confirmGearCreation() method

        //call async task, create representation
        new RepresentationCreateAsyncTask(this, newGearWithAllObjects).execute(
                this.representationPictures.toArray(new Bitmap[0]));

        //TODO : call async task, add gear signal type

    }

    public void confirmGearCreation(GearWithAllObjects newGearWithAllObjects) {
        Utils.showToast(this, this.getResources().getString(R.string.gear_creation_message), Toast.LENGTH_SHORT);
        Intent backIntent = new Intent();
        backIntent.putExtra("newGear", newGearWithAllObjects);
        setResult(RESULT_OK, backIntent);
        finish();
    }

}