package skinapp.luca.com.proxy;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import skinapp.luca.com.util.URLManager;
import skinapp.luca.com.vo.HistoryResponseVo;
import skinapp.luca.com.vo.ProductsResponseVo;

public class GetHistoryProxy extends BaseProxy {

    public HistoryResponseVo run(String loginid) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("loginid", loginid);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getHistoryURL(), formBody);

        Log.v("Content", contentString);

        HistoryResponseVo responseVo = new HistoryResponseVo();

        try {
            JSONObject jsonResponse = new JSONObject(contentString);
            responseVo.history = jsonResponse.getString("history");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return responseVo;
    }
}
