package skinapp.luca.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import skinapp.luca.com.util.URLManager;
import skinapp.luca.com.vo.ProductsResponseVo;
import skinapp.luca.com.vo.SignInResponseVo;

public class GetProductProxy extends BaseProxy {

    public ProductsResponseVo run(String type) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("id", type);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getProductsURL(), formBody);

        Log.v("Content", contentString);

        ProductsResponseVo responseVo = new ProductsResponseVo();

        try {
            JSONObject jsonResponse = new JSONObject(contentString);
            responseVo.products = jsonResponse.getString("products");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return responseVo;
    }
}
