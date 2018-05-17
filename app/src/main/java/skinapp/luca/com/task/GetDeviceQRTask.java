package skinapp.luca.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import skinapp.luca.com.event.GetDeviceQREvent;
import skinapp.luca.com.event.UploadQREvent;
import skinapp.luca.com.proxy.GetDeviceQRProxy;
import skinapp.luca.com.proxy.UploadQRProxy;
import skinapp.luca.com.vo.GetDeviceQRResponseVo;
import skinapp.luca.com.vo.UploadQRResponseVo;

public class GetDeviceQRTask extends AsyncTask<String, Void, GetDeviceQRResponseVo> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected GetDeviceQRResponseVo doInBackground(String... params) {
        GetDeviceQRProxy simpleProxy = new GetDeviceQRProxy();
        String devId = params[0];
        try {
            final GetDeviceQRResponseVo responseVo = simpleProxy.run(devId);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(GetDeviceQRResponseVo responseVo) {
        EventBus.getDefault().post(new GetDeviceQREvent(responseVo));
    }
}