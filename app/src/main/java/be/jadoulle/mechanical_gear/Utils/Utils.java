package be.jadoulle.mechanical_gear.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;
import android.content.Context;

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


    /** ideas:
     * method who displayed multiple or single error message in a Toast object.
     */
}
