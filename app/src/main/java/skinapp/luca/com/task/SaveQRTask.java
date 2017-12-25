package skinapp.luca.com.task;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import skinapp.luca.com.SkinApplication;
import skinapp.luca.com.event.GetQREvent;
import skinapp.luca.com.event.SaveQREvent;
import skinapp.luca.com.proxy.GetQRProxy;
import skinapp.luca.com.ui.MainActivity;
import skinapp.luca.com.util.SharedPrefManager;
import skinapp.luca.com.vo.GetQRResponseVo;

public class SaveQRTask extends AsyncTask<String, Void, Boolean> {
    private MainActivity parent;

    public SaveQRTask(MainActivity parent) {
        this.parent = parent;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Boolean doInBackground(String... params) {
        String imageUrl = params[0];
        try {
            imageUrl = imageUrl.replaceAll("'\'", "");
            Log.v("URL", imageUrl);
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();

            File file = new File(parent.getFilesDir(), SkinApplication.QR_FILE_PATH);
            if(!file.exists()) {
                file.createNewFile();
            }

            OutputStream os = new FileOutputStream(file);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        EventBus.getDefault().post(new SaveQREvent());
    }
}