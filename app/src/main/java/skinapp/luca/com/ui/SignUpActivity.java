package skinapp.luca.com.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import skinapp.luca.com.event.SignInEvent;
import skinapp.luca.com.event.SignUpEvent;
import skinapp.luca.com.task.SignUpTask;
import skinapp.luca.com.util.StringUtil;
import skinapp.luca.com.vo.SignInResponseVo;
import skinapp.luca.com.vo.SignUpResponseVo;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.edt_id)
    EditText edtLoginID;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_conf_password)
    EditText edtConfPassword;

    private String loginID;
    private String password;
    private String confPassword;

    private Animation shake;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        shake = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.edittext_shake);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));

        ButterKnife.bind(this);
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
    public void onSignUpEvent(SignUpEvent event) {
        hideProgressDialog();
        SignUpResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                signupSuccess();
            } else {
                signupFailed(responseVo.error_code);
            }
        } else {
            networkError();
        }
    }

    @OnClick(R.id.btn_signup)
    void onClickSignUp() {
        loginID = edtLoginID.getText().toString();
        password = edtPassword.getText().toString();
        confPassword = edtConfPassword.getText().toString();

        if(!checkLoginID()) return;
        if(!checkPassword()) return;
        if(!checkConfPassword()) return;
        if(!checkValidPassword()) {
            Toast.makeText(SignUpActivity.this, R.string.incorrect_conf_password, Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        SignUpTask task = new SignUpTask();
        task.execute(loginID, password);
    }

    @OnClick(R.id.btn_cancel)
    void onClickCancel() {
        finish();
    }

    private boolean checkLoginID() {
        if (StringUtil.isEmpty(loginID)) {
            showInfoNotice(edtLoginID);
            return false;
        }

        return true;
    }

    private boolean checkPassword() {
        if (StringUtil.isEmpty(password)) {
            showInfoNotice(edtPassword);
            return false;
        }

        return true;
    }

    private boolean checkConfPassword() {
        if (StringUtil.isEmpty(confPassword)) {
            showInfoNotice(edtConfPassword);
            return false;
        }

        return true;
    }

    private boolean checkValidPassword() {
        if(password.equals(confPassword))
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
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void networkError() {
        Toast.makeText(SignUpActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
    }

    private void signupSuccess() {
        Intent returnIntent = new Intent();

        SkinApplication.bLogin = true;
        SkinApplication.loginID = loginID;

        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private void signupFailed(int errorCode) {
        switch(errorCode) {
            case 1:
                Toast.makeText(SignUpActivity.this, R.string.user_already_exists, Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(SignUpActivity.this, R.string.database_error, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
