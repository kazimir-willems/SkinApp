package skinapp.luca.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import skinapp.luca.com.event.GetAnalysisEvent;
import skinapp.luca.com.event.SignInEvent;
import skinapp.luca.com.proxy.GetAnalysisProxy;
import skinapp.luca.com.proxy.SignInProxy;
import skinapp.luca.com.vo.GetAnalysisResponseVo;
import skinapp.luca.com.vo.SignInResponseVo;

public class GetAnalysisTask extends AsyncTask<String, Void, GetAnalysisResponseVo> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected GetAnalysisResponseVo doInBackground(String... params) {
        GetAnalysisProxy simpleProxy = new GetAnalysisProxy();
        String type = params[0];
        String value = params[1];
        String loginId = params[2];
        String deviceId = params[3];
        try {
            final GetAnalysisResponseVo responseVo = simpleProxy.run(type, value, loginId, deviceId);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(GetAnalysisResponseVo responseVo) {
        EventBus.getDefault().post(new GetAnalysisEvent(responseVo));
    }
}