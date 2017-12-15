package skinapp.luca.com.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skinapp.luca.com.R;
import skinapp.luca.com.util.SharedPrefManager;
import skinapp.luca.com.util.StringUtil;

public class RegisterDeviceActivity extends AppCompatActivity {

    @BindView(R.id.edt_device_id)
    EditText edtDeviceID;
    @BindView(R.id.edt_confirm_device_id)
    EditText edtConfirmDeviceID;

    private String deviceID;
    private String confirmDeviceID;

    private Animation shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_device);

        ButterKnife.bind(this);

        if(!SharedPrefManager.getInstance(this).getFirstLogin()) {
            startLogoActivity();
        }

        shake = AnimationUtils.loadAnimation(RegisterDeviceActivity.this, R.anim.edittext_shake);
    }

    @OnClick(R.id.btn_register)
    void onClickRegister() {
        deviceID = edtDeviceID.getText().toString();
        confirmDeviceID = edtConfirmDeviceID.getText().toString();

        if(!checkDeviceID()) return;
        if(!checkConfirmDeviceID()) return;
        if(!checkValidDeviceID()) return;

        SharedPrefManager.getInstance(this).saveDeviceID(deviceID);
        SharedPrefManager.getInstance(this).saveFirstLogin(false);

        startLogoActivity();
    }

    private void startLogoActivity() {
        Intent intent = new Intent(RegisterDeviceActivity.this, LogoActivity.class);

        startActivity(intent);

        finish();
    }

    private boolean checkDeviceID() {
        if (StringUtil.isEmpty(deviceID)) {
            showInfoNotice(edtDeviceID);
            return false;
        }

        return true;
    }

    private boolean checkConfirmDeviceID() {
        if (StringUtil.isEmpty(confirmDeviceID)) {
            showInfoNotice(edtConfirmDeviceID);
            return false;
        }

        return true;
    }

    private boolean checkValidDeviceID() {
        if(deviceID.equals(confirmDeviceID))
            return true;
        else return false;
    }

    private void showInfoNotice(EditText target) {
        target.startAnimation(shake);
        if (target.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
