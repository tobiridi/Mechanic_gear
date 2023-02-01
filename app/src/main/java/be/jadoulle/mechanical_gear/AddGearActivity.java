package be.jadoulle.mechanical_gear;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import be.jadoulle.mechanical_gear.AsyncTask.GearCreateAsyncTask;
import be.jadoulle.mechanical_gear.Utils.Utils;

public class AddGearActivity extends AppCompatActivity {

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
    }

    public void confirmGearCreation(boolean isCreate) {
        if(isCreate) {
            Utils.showToast(this, this.getResources().getString(R.string.gear_creation_message), Toast.LENGTH_SHORT);
            setResult(RESULT_OK);
            finish();
        }
        else {
            Utils.showToast(this, this.getResources().getString(R.string.gear_creation_failed_message), Toast.LENGTH_SHORT);
        }
    }

}