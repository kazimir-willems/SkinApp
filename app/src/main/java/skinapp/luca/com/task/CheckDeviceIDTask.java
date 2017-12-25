package skinapp.luca.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import skinapp.luca.com.event.CheckDeviceEvent;
import skinapp.luca.com.event.SignUpEvent;
import skinapp.luca.com.proxy.CheckDeviceProxy;
import skinapp.luca.com.proxy.SignUpProxy;
import skinapp.luca.com.vo.CheckDeviceResponseVo;
import skinapp.luca.com.vo.SignUpResponseVo;

public class CheckDeviceIDTask extends AsyncTask<String, Void, CheckDeviceResponseVo> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected CheckDeviceResponseVo doInBackground(String... params) {
        CheckDeviceProxy simpleProxy = new CheckDeviceProxy();
        String deviceID = params[0];
        try {
            final CheckDeviceResponseVo responseVo = simpleProxy.run(deviceID);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(CheckDeviceResponseVo responseVo) {
        EventBus.getDefault().post(new CheckDeviceEvent(responseVo));
    }
}