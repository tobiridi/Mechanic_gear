package be.jadoulle.mechanical_gear;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import be.jadoulle.mechanical_gear.Utils.ActivityCode;
import be.jadoulle.mechanical_gear.Utils.Utils;

public class AddSignalTypeActivity extends AppCompatActivity {
    private ImageView ivPicture;
    private EditText etName;
    private Bitmap currentBitmap;


    private View.OnClickListener add_picture_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO : optimise with camera application
            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intentCamera, ActivityCode.ADD_SIGNAL_TYPE_ACTIVITY_CODE);
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

        this.ivPicture = findViewById(R.id.iv_signal_type_picture);
        this.etName = findViewById(R.id.et_signal_type_name);

        this.ivPicture.setOnClickListener(add_picture_listener);
        findViewById(R.id.btn_cancel).setOnClickListener(cancel_listener);
        findViewById(R.id.btn_validate).setOnClickListener(validate_listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && requestCode == ActivityCode.ADD_SIGNAL_TYPE_ACTIVITY_CODE && resultCode == RESULT_OK) {
            //get picture
            Bundle bundle = data.getExtras();
            Bitmap picture = (Bitmap) bundle.get("data");

            if(picture != null) {
                this.ivPicture.setImageBitmap(picture);
                this.currentBitmap = picture;
            }
        }
    }
}