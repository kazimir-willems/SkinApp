package skinapp.luca.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import skinapp.luca.com.util.URLManager;
import skinapp.luca.com.vo.SignInResponseVo;
import skinapp.luca.com.vo.SignUpResponseVo;
import skinapp.luca.com.vo.UploadQRResponseVo;

public class UploadQRProxy extends BaseProxy {

    public UploadQRResponseVo run(String deviceId, String img) throws IOException {
        UploadQRResponseVo requestVo = new UploadQRResponseVo();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("device_id", deviceId);
        formBuilder.add("img", img);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getUploadQRURL(), formBody);

        Log.v("contentString", contentString);

        UploadQRResponseVo responseVo = new Gson().fromJson(contentString, UploadQRResponseVo.class);

        return responseVo;
    }
}
