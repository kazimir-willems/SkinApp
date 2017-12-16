package skinapp.luca.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import skinapp.luca.com.SkinApplication;
import skinapp.luca.com.util.URLManager;
import skinapp.luca.com.vo.GetAnalysisResponseVo;
import skinapp.luca.com.vo.SignInResponseVo;

public class GetAnalysisProxy extends BaseProxy {

    public GetAnalysisResponseVo run(String type, String value, String loginId) throws IOException {
        GetAnalysisResponseVo requestVo = new GetAnalysisResponseVo();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("type", type);
        formBuilder.add("value", value);
        formBuilder.add("loginid", loginId);
        formBuilder.add("seed", SkinApplication.seed);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getAnalysisURL(), formBody);
        Log.v("Content", contentString);

        GetAnalysisResponseVo responseVo = new GetAnalysisResponseVo();
        try {
            JSONObject jsonObject = new JSONObject(contentString);

            responseVo.success = jsonObject.getInt("success");
            responseVo.result = jsonObject.getString("result");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return responseVo;
    }
}
