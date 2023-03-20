package be.jadoulle.mechanical_gear.Utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public final class Utils {
    private static Toast globalToast = null;

    /**
     * Display an unique {@link Toast} for the application,
     * the previous toast's message will be replace by the new message.
     * @param context {@link android.content.Context}
     * @param text The message displayed
     * @param duration Time displayed
     */
    public static void showToast(Context context, CharSequence text, int duration) {
        if(globalToast != null)
            globalToast.cancel();

        globalToast = Toast.makeText(context, text, duration);
        globalToast.show();
    }

    /**
     * Transform via a {@link ByteArrayOutputStream}, a {@link Bitmap} to an array of {@link Byte},
     * the quality is set to 80 and the format is PNG.<br>
     * If the parameter is null then the method returns null.
     * @param bitmap A picture
     * @return An array of {@link Byte}
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
        return stream.toByteArray();
    }

    /**
     * Transform an array of {@link Byte} to a {@link Bitmap}.<br>
     * If the parameter is null then the method returns null.
     * @param array An array of byte
     * @return A representation in {@link Bitmap} from a byte array
     */
    public static Bitmap byteArrayToBitmap(byte[] array) {
        if (array == null)
            return null;
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    /**
     * Verify camera permission, ask if not already granted.
     * Implement {@link AppCompatActivity#onRequestPermissionsResult(int, String[], int[])} in your activity to get the user response.
     * @param activity {@link AppCompatActivity}
     * @param activityCode code from {@link ActivityCode} class.
     * @return true if the permission is granted otherwise false
     */
    public static boolean askCameraPermission(AppCompatActivity activity, int activityCode) {
        /*
         * step 1 : verify if already have permission, check every time
         * step 2 : if needs, explain why to the user (UI)
         * step 3 : ask permission
         * step 4 : verify the user response
         * step 5 : if the answer is true then use permission, otherwise remove functionality who need it
         */

        if(activity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            //the permission already granted
            return true;
        }
//        else if (activity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//            //display explain
//            System.out.println("explain why using camera");
//            return true;
//        }
        else {
            //ask permission
            String[] permissions = {Manifest.permission.CAMERA};
            activity.requestPermissions(permissions, activityCode);
            return false;
        }

    }


    /** ideas:
     * method who displayed multiple or single error message in a Toast object.
     */
}
