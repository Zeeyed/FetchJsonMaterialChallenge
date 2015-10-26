package zied.curiousbat.fetchnewschallenge.Logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Zied on 26/10/2015.
 */
public class L {


    public static void m(String message){
        Log.d("ZIED", "" + message);
    }

    public static void t(Context context, String message){
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }
}
