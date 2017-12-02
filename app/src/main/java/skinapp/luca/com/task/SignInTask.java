package skinapp.luca.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import skinapp.luca.com.event.SignInEvent;
import skinapp.luca.com.proxy.SignInProxy;
import skinapp.luca.com.vo.SignInResponseVo;

public class SignInTask extends AsyncTask<String, Void, SignInResponseVo> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected SignInResponseVo doInBackground(String... params) {
        SignInProxy simpleProxy = new SignInProxy();
        String loginId = params[0];
        String password = params[1];
        try {
            final SignInResponseVo responseVo = simpleProxy.run(loginId, password);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(SignInResponseVo responseVo) {
        EventBus.getDefault().post(new SignInEvent(responseVo));
    }
}