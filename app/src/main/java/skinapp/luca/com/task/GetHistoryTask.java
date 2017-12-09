package skinapp.luca.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import skinapp.luca.com.event.GetHistoryEvent;
import skinapp.luca.com.event.ProductEvent;
import skinapp.luca.com.proxy.GetHistoryProxy;
import skinapp.luca.com.proxy.GetProductProxy;
import skinapp.luca.com.vo.HistoryResponseVo;
import skinapp.luca.com.vo.ProductsResponseVo;

public class GetHistoryTask extends AsyncTask<String, Void, HistoryResponseVo> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected HistoryResponseVo doInBackground(String... params) {
        GetHistoryProxy simpleProxy = new GetHistoryProxy();
        String loginid = params[0];
        try {
            final HistoryResponseVo responseVo = simpleProxy.run(loginid);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(HistoryResponseVo responseVo) {
        EventBus.getDefault().post(new GetHistoryEvent(responseVo));
    }
}