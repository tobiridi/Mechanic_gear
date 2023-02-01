package be.jadoulle.mechanical_gear.Utils;

import android.widget.Toast;
import android.content.Context;

public final class Utils {

    public static void showToast(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

}
