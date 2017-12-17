package skinapp.luca.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import skinapp.luca.com.SkinApplication;
import skinapp.luca.com.util.URLManager;
import skinapp.luca.com.vo.GetOpenIDResponseVo;
import skinapp.luca.com.vo.GetQRResponseVo;

public class GetOpenIDProxy extends BaseProxy {

    public GetOpenIDResponseVo run(String deviceID) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("device_id", deviceID);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getOpenIDURL(), formBody);

        Log.v("contentString", contentString);

        GetOpenIDResponseVo responseVo = new Gson().fromJson(contentString, GetOpenIDResponseVo.class);

        return responseVo;
    }
}
