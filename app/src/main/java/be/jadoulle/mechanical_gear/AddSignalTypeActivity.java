package be.jadoulle.mechanical_gear;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import be.jadoulle.mechanical_gear.Utils.ActivityCode;
import be.jadoulle.mechanical_gear.Utils.Utils;

public class AddSignalTypeActivity extends AppCompatActivity {
    private EditText etName;
    private Bitmap currentBitmap;

    private ActivityResultLauncher<Void> pictureLauncher = registerForActivityResult(
            new ActivityResultContracts.TakePicturePreview(),
            (Bitmap result) -> {
                if(result != null) {
                    ImageView img = findViewById(R.id.iv_signal_type_picture);
                    img.setImageBitmap(result);
                    this.currentBitmap = result;
                }
            });

    private View.OnClickListener addPictureListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Utils.askCameraPermission(AddSignalTypeActivity.this, ActivityCode.ADD_SIGNAL_TYPE_ACTIVITY_CODE)) {
                pictureLauncher.launch(null);
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
            Intent signalTypeIntent = new Intent();
            signalTypeIntent.putExtra("signalTypeName", etName.getText().toString());
            signalTypeIntent.putExtra("signalTypePicture", currentBitmap);
            setResult(RESULT_OK, signalTypeIntent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_signal_type);

        this.etName = findViewById(R.id.et_signal_type_name);

        findViewById(R.id.iv_signal_type_picture).setOnClickListener(addPictureListener);
        findViewById(R.id.btn_cancel).setOnClickListener(cancelListener);
        findViewById(R.id.btn_validate).setOnClickListener(validateListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals(Manifest.permission.CAMERA) && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                findViewById(R.id.iv_signal_type_picture).setClickable(false);
            }
        }
    }
}