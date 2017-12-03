package skinapp.luca.com;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
/**
 * Created by Leif on 12/1/2017.
 */

public class SkinApplication extends Application {
    private static SkinApplication instance;

    public static Bitmap capturedPhoto = null;
    public static boolean bLogin = false;
    public static String loginID = "";

    /*@Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
//        MultiDex.install(this);
    }*/

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static synchronized SkinApplication getInstance() {
        return instance;
    }
}
