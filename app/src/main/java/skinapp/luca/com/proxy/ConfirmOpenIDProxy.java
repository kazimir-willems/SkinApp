package skinapp.luca.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import skinapp.luca.com.util.URLManager;
import skinapp.luca.com.vo.ConfirmOpenIDResponseVo;
import skinapp.luca.com.vo.GetOpenIDResponseVo;

public class ConfirmOpenIDProxy extends BaseProxy {

    public ConfirmOpenIDResponseVo run(String id, String openid) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("id", id);
        formBuilder.add("openid", openid);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getConfirmOpenIDURL(), formBody);

        Log.v("contentString", contentString);

        ConfirmOpenIDResponseVo responseVo = new Gson().fromJson(contentString, ConfirmOpenIDResponseVo.class);

        return responseVo;
    }
}
