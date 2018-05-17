package skinapp.luca.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import skinapp.luca.com.util.URLManager;
import skinapp.luca.com.vo.GetDeviceQRResponseVo;
import skinapp.luca.com.vo.UploadQRResponseVo;

public class GetDeviceQRProxy extends BaseProxy {

    public GetDeviceQRResponseVo run(String deviceId) throws IOException {
        GetDeviceQRResponseVo requestVo = new GetDeviceQRResponseVo();

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("device_id", deviceId);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getDeviceQRURL(), formBody);

        Log.v("contentString", contentString);

        GetDeviceQRResponseVo responseVo = new Gson().fromJson(contentString, GetDeviceQRResponseVo.class);

        return responseVo;
    }
}
