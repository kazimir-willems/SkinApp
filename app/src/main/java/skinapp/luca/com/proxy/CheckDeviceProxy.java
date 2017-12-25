package skinapp.luca.com.proxy;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import skinapp.luca.com.util.URLManager;
import skinapp.luca.com.vo.CheckDeviceResponseVo;
import skinapp.luca.com.vo.SignInResponseVo;

public class CheckDeviceProxy extends BaseProxy {

    public CheckDeviceResponseVo run(String deviceID) throws IOException {
        CheckDeviceResponseVo requestVo = new CheckDeviceResponseVo();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("device_id", deviceID);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getCheckDeviceIDURL(), formBody);

        CheckDeviceResponseVo responseVo = new Gson().fromJson(contentString, CheckDeviceResponseVo.class);

        return responseVo;
    }
}
