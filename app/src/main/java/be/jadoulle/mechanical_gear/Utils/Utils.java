package be.jadoulle.mechanical_gear.Utils;

import android.widget.Toast;
import android.content.Context;

public final class Utils {
    private static Toast globalToast = null;

    public static void showToast(Context context, CharSequence text, int duration) {
        if(globalToast != null)
            globalToast.cancel();

        globalToast = Toast.makeText(context, text, duration);
        globalToast.show();
    }

}
