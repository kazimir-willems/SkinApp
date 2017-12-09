package skinapp.luca.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import skinapp.luca.com.util.URLManager;
import skinapp.luca.com.vo.SignInResponseVo;
import skinapp.luca.com.vo.SignUpResponseVo;

public class SignUpProxy extends BaseProxy {

    public SignUpResponseVo run(String loginId, String password) throws IOException {
        SignInResponseVo requestVo = new SignInResponseVo();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("eid", loginId);
        formBuilder.add("password", password);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getSignUpURL(), formBody);

        Log.v("contentString", contentString);

        SignUpResponseVo responseVo = new Gson().fromJson(contentString, SignUpResponseVo.class);

        return responseVo;
    }
}
