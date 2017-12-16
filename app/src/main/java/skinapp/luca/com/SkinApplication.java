package skinapp.luca.com;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Leif on 12/1/2017.
 */

public class SkinApplication extends Application {
    private static SkinApplication instance;

    public static Bitmap capturedPhoto = null;
    public static boolean bLogin = false;
    public static String loginID = "";
    public static String seed = "";

    public static String QR_KEY = "af074e8dc858c7e8fdd27bb14152f26d";
    public static String QR_ACT = "get_qrcode";

    /*@Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
//        MultiDex.install(this);
    }*/

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static synchronized SkinApplication getInstance() {
        return instance;
    }
}
