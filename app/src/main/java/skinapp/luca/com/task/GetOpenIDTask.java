package skinapp.luca.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import skinapp.luca.com.event.GetOpenIDEvent;
import skinapp.luca.com.event.GetQREvent;
import skinapp.luca.com.proxy.GetOpenIDProxy;
import skinapp.luca.com.proxy.GetQRProxy;
import skinapp.luca.com.vo.GetOpenIDResponseVo;
import skinapp.luca.com.vo.GetQRResponseVo;

public class GetOpenIDTask extends AsyncTask<String, Void, GetOpenIDResponseVo> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected GetOpenIDResponseVo doInBackground(String... params) {
        GetOpenIDProxy simpleProxy = new GetOpenIDProxy();
        String deviceID = params[0];
        try {
            final GetOpenIDResponseVo responseVo = simpleProxy.run(deviceID);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(GetOpenIDResponseVo responseVo) {
        EventBus.getDefault().post(new GetOpenIDEvent(responseVo));
    }
}