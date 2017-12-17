package skinapp.luca.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import skinapp.luca.com.event.GetOpenIDEvent;
import skinapp.luca.com.proxy.ConfirmOpenIDProxy;
import skinapp.luca.com.proxy.GetOpenIDProxy;
import skinapp.luca.com.vo.ConfirmOpenIDResponseVo;
import skinapp.luca.com.vo.GetOpenIDResponseVo;

public class ConfirmOpenIDTask extends AsyncTask<String, Void, ConfirmOpenIDResponseVo> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ConfirmOpenIDResponseVo doInBackground(String... params) {
        ConfirmOpenIDProxy simpleProxy = new ConfirmOpenIDProxy();
        String id = params[0];
        String openid = params[1];
        try {
            final ConfirmOpenIDResponseVo responseVo = simpleProxy.run(id, openid);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ConfirmOpenIDResponseVo responseVo) {

    }
}