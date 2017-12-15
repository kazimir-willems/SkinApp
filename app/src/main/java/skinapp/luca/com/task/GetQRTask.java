package skinapp.luca.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import skinapp.luca.com.event.GetQREvent;
import skinapp.luca.com.event.SignUpEvent;
import skinapp.luca.com.proxy.GetQRProxy;
import skinapp.luca.com.proxy.SignUpProxy;
import skinapp.luca.com.vo.GetQRResponseVo;
import skinapp.luca.com.vo.SignUpResponseVo;

public class GetQRTask extends AsyncTask<String, Void, GetQRResponseVo> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected GetQRResponseVo doInBackground(String... params) {
        GetQRProxy simpleProxy = new GetQRProxy();
        String deviceID = params[0];
        try {
            final GetQRResponseVo responseVo = simpleProxy.run(deviceID);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(GetQRResponseVo responseVo) {
        EventBus.getDefault().post(new GetQREvent(responseVo));
    }
}