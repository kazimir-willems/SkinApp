package skinapp.luca.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import skinapp.luca.com.SkinApplication;
import skinapp.luca.com.util.SharedPrefManager;
import skinapp.luca.com.util.URLManager;
import skinapp.luca.com.vo.GetQRResponseVo;
import skinapp.luca.com.vo.SignInResponseVo;
import skinapp.luca.com.vo.SignUpResponseVo;

public class GetQRProxy extends BaseProxy {

    public GetQRResponseVo run(String deviceID) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("key", SkinApplication.QR_KEY);
        formBuilder.add("act", SkinApplication.QR_ACT);
        formBuilder.add("sarkcode", deviceID);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getQRURL(), formBody);

        Log.v("contentString", contentString);

        GetQRResponseVo responseVo = new Gson().fromJson(contentString, GetQRResponseVo.class);

        return responseVo;
    }
}
