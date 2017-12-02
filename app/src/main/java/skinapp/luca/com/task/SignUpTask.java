package skinapp.luca.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import skinapp.luca.com.event.SignInEvent;
import skinapp.luca.com.event.SignUpEvent;
import skinapp.luca.com.proxy.SignInProxy;
import skinapp.luca.com.proxy.SignUpProxy;
import skinapp.luca.com.vo.SignInResponseVo;
import skinapp.luca.com.vo.SignUpResponseVo;

public class SignUpTask extends AsyncTask<String, Void, SignUpResponseVo> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected SignUpResponseVo doInBackground(String... params) {
        SignUpProxy simpleProxy = new SignUpProxy();
        String loginId = params[0];
        String password = params[1];
        try {
            final SignUpResponseVo responseVo = simpleProxy.run(loginId, password);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(SignUpResponseVo responseVo) {
        EventBus.getDefault().post(new SignUpEvent(responseVo));
    }
}