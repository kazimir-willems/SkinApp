package skinapp.luca.com.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cuboid.cuboidcirclebutton.CuboidButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import skinapp.luca.com.R;
import skinapp.luca.com.SkinApplication;
import skinapp.luca.com.consts.CommonConsts;
import skinapp.luca.com.event.SignInEvent;
import skinapp.luca.com.task.SignInTask;
import skinapp.luca.com.util.StringUtil;
import skinapp.luca.com.vo.SignInResponseVo;

public class MainActivity extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 0;

    private int mYear;
    private int mMonth;
    private int mDay;

    private CuboidButton btnLogin;
    private TextView btnUserList;
    private TextView tvUserInfo;
    private EditText edtLoginID;
    private EditText edtPassword;

    private RadioButton male;
    private RadioButton female;

    private TextView btnReview;
    private TextView btnChart;

    private int selectedGender;

    private LinearLayout mainLayout;
    private LinearLayout infoLayout;
    private LinearLayout userListLayout;
    private RelativeLayout chartLayout;

    private ListView reviewList;
    private LineChartView chart;

    private Animation shake;
    private ProgressDialog progressDialog;

    private String loginID;
    private String password;

    private static final int REQUEST_SIGNUP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnLogin = (CuboidButton) findViewById(R.id.btn_login);
        btnUserList = (TextView) findViewById(R.id.btn_user_list);
        tvUserInfo = (TextView) findViewById(R.id.tv_user_info);

        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        infoLayout = (LinearLayout) findViewById(R.id.info_layout);
        userListLayout = (LinearLayout) findViewById(R.id.user_list_layout);
        chartLayout = (RelativeLayout) findViewById(R.id.chart_layout);

        chart = (LineChartView) findViewById(R.id.chart);
        reviewList = (ListView) findViewById(R.id.review_list);

        List<PointValue> values = new ArrayList<PointValue>();
        values.add(new PointValue(0, 2));
        values.add(new PointValue(1, 4));
        values.add(new PointValue(2, 3));
        values.add(new PointValue(3, 4));

        Line line = new Line(values).setColor(Color.BLUE).setCubic(false);
        line.setHasLabels(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        chart.setLineChartData(data);

        shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.edittext_shake);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));

        btnReview = (TextView) findViewById(R.id.btn_review);
        btnChart = (TextView) findViewById(R.id.btn_chart);

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnReview.setTypeface(null, Typeface.BOLD);
                btnChart.setTypeface(null, Typeface.NORMAL);
                reviewList.setVisibility(View.VISIBLE);
                chartLayout.setVisibility(View.GONE);
            }
        });

        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChart.setTypeface(null, Typeface.BOLD);
                btnReview.setTypeface(null, Typeface.NORMAL);
                reviewList.setVisibility(View.GONE);
                chartLayout.setVisibility(View.VISIBLE);
            }
        });

        SpannableString mySpannableString = new SpannableString("选择用户");
        mySpannableString.setSpan(new UnderlineSpan(), 0, mySpannableString.length(), 0);
        btnUserList.setText(mySpannableString);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View informDialogView = factory.inflate(R.layout.dialog_login, null);

                edtLoginID = (EditText) informDialogView.findViewById(R.id.edt_id);
                edtPassword = (EditText) informDialogView.findViewById(R.id.edt_password);

                final AlertDialog informDialog = new AlertDialog.Builder(MainActivity.this).create();
                informDialog.setView(informDialogView);
                informDialogView.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //your business logic
                        loginID = edtLoginID.getText().toString();
                        password = edtPassword.getText().toString();

                        if(!checkLoginID()) return;
                        if(!checkPassword()) return;

                        progressDialog.show();

                        SignInTask task = new SignInTask();
                        task.execute(loginID, password);
                    }
                });
                informDialogView.findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);

                        startActivityForResult(intent, REQUEST_SIGNUP);

                        informDialog.dismiss();
                    }
                });

                informDialog.show();
            }
        });
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
    public void onSignInEvet(SignInEvent event) {
        hideProgressDialog();
        SignInResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                btnLogin.setText(loginID);
                SkinApplication.bLogin = true;
                SkinApplication.loginID = loginID;
            } else {
                loginFailed(responseVo.error_code);
            }
        } else {
            networkError();
        }
    }

    @OnClick(R.id.btn_analyze_oil)
    void onClickBtnOil() {
        /*Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.OIL_ANALYSIS);

        startActivity(intent);*/

        Intent intent = new Intent(MainActivity.this, RecommendationActivity.class);

        intent.putExtra("type", "1");

        startActivity(intent);
    }

    @OnClick(R.id.btn_analyze_fiber)
    void onClickBtnFiber() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.FIBER_ANALYSIS);

        startActivity(intent);
    }

    @OnClick(R.id.btn_analyze_muscle)
    void onClickBtnMuscle() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.MUSCLE_ANALYSIS);

        startActivity(intent);
    }

    @OnClick(R.id.btn_analyze_flex)
    void onClickBtnFlex() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.FLEX_ANALYSIS);

        startActivity(intent);
    }

    @OnClick(R.id.btn_analyze_hairhole)
    void onClickBtnHairhole() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.HAIRHOLE_ANALYSIS);

        startActivity(intent);
    }

    @OnClick(R.id.btn_analyze_zit)
    void onClickBtnZit() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.ZIT_ANALYSIS);

        startActivity(intent);
    }

    @OnClick(R.id.btn_analyze_action)
    void onClickBtnAction() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.ACTION_ANALYSIS);

        startActivity(intent);
    }

    @OnClick(R.id.btn_analyze_water)
    void onClickBtnWater() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.WATER_ANALYSIS);

        startActivity(intent);
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
        Toast.makeText(MainActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
    }

    private void loginFailed(int errorCode) {
        switch(errorCode) {
            case 2:
                Toast.makeText(MainActivity.this, R.string.user_not_exist, Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(MainActivity.this, R.string.incorrect_password, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_SIGNUP) {
            if(resultCode == Activity.RESULT_OK){
                String loginID = data.getStringExtra("loginID");

                btnLogin.setText(loginID);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
