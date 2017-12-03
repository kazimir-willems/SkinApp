package skinapp.luca.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import skinapp.luca.com.event.ProductEvent;
import skinapp.luca.com.event.SignInEvent;
import skinapp.luca.com.proxy.GetProductProxy;
import skinapp.luca.com.proxy.SignInProxy;
import skinapp.luca.com.vo.ProductsResponseVo;
import skinapp.luca.com.vo.SignInResponseVo;

public class GetProductsTask extends AsyncTask<String, Void, ProductsResponseVo> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ProductsResponseVo doInBackground(String... params) {
        GetProductProxy simpleProxy = new GetProductProxy();
        String type = params[0];
        try {
            final ProductsResponseVo responseVo = simpleProxy.run(type);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ProductsResponseVo responseVo) {
        EventBus.getDefault().post(new ProductEvent(responseVo));
    }
}