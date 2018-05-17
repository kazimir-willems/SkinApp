package skinapp.luca.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import skinapp.luca.com.event.SignUpEvent;
import skinapp.luca.com.event.UploadQREvent;
import skinapp.luca.com.proxy.SignUpProxy;
import skinapp.luca.com.proxy.UploadQRProxy;
import skinapp.luca.com.vo.SignUpResponseVo;
import skinapp.luca.com.vo.UploadQRResponseVo;

public class UploadQRTask extends AsyncTask<String, Void, UploadQRResponseVo> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected UploadQRResponseVo doInBackground(String... params) {
        UploadQRProxy simpleProxy = new UploadQRProxy();
        String devId = params[0];
        String img = params[1];
        try {
            final UploadQRResponseVo responseVo = simpleProxy.run(devId, img);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(UploadQRResponseVo responseVo) {
        EventBus.getDefault().post(new UploadQREvent(responseVo));
    }
}