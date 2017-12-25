package skinapp.luca.com.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skinapp.luca.com.R;
import skinapp.luca.com.SkinApplication;
import skinapp.luca.com.event.CheckDeviceEvent;
import skinapp.luca.com.event.GetOpenIDEvent;
import skinapp.luca.com.task.CheckDeviceIDTask;
import skinapp.luca.com.util.SharedPrefManager;
import skinapp.luca.com.util.StringUtil;
import skinapp.luca.com.vo.CheckDeviceResponseVo;
import skinapp.luca.com.vo.GetOpenIDResponseVo;

public class RegisterDeviceActivity extends AppCompatActivity {

    @BindView(R.id.edt_device_id)
    EditText edtDeviceID;
    @BindView(R.id.edt_confirm_device_id)
    EditText edtConfirmDeviceID;

    private String deviceID;
    private String confirmDeviceID;

    private Animation shake;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_device);

        ButterKnife.bind(this);

        if(!SharedPrefManager.getInstance(this).getFirstLogin()) {
            startLogoActivity();
        }

        shake = AnimationUtils.loadAnimation(RegisterDeviceActivity.this, R.anim.edittext_shake);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onCheckDeviceEvent(CheckDeviceEvent event) {
        hideProgressDialog();
        CheckDeviceResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                startLogoActivity();
            } else {
                invalidDeviceID();
            }
        }
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

        checkDevice();
    }

    private void checkDevice() {
        progressDialog.show();

        CheckDeviceIDTask task = new CheckDeviceIDTask();
        task.execute(deviceID);
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

    private void hideProgressDialog() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void invalidDeviceID() {
        Toast.makeText(RegisterDeviceActivity.this, R.string.invalid_device, Toast.LENGTH_SHORT).show();
    }

}
