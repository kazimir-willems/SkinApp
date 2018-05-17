package skinapp.luca.com.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cuboid.cuboidcirclebutton.CuboidButton;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import skinapp.luca.com.R;
import skinapp.luca.com.SkinApplication;
import skinapp.luca.com.adapter.HistoryAdapter;
import skinapp.luca.com.adapter.ProductAdapter;
import skinapp.luca.com.consts.CommonConsts;
import skinapp.luca.com.event.GetDeviceQREvent;
import skinapp.luca.com.event.GetHistoryEvent;
import skinapp.luca.com.event.GetOpenIDEvent;
import skinapp.luca.com.event.GetQREvent;
import skinapp.luca.com.event.ProductEvent;
import skinapp.luca.com.event.SaveQREvent;
import skinapp.luca.com.event.SignInEvent;
import skinapp.luca.com.event.UploadQREvent;
import skinapp.luca.com.model.HistoryItem;
import skinapp.luca.com.model.ProductItem;
import skinapp.luca.com.task.ConfirmOpenIDTask;
import skinapp.luca.com.task.GetDeviceQRTask;
import skinapp.luca.com.task.GetHistoryTask;
import skinapp.luca.com.task.GetOpenIDTask;
import skinapp.luca.com.task.GetQRTask;
import skinapp.luca.com.task.SaveQRTask;
import skinapp.luca.com.task.SignInTask;
import skinapp.luca.com.task.UploadQRTask;
import skinapp.luca.com.util.SharedPrefManager;
import skinapp.luca.com.util.StringUtil;
import skinapp.luca.com.util.URLManager;
import skinapp.luca.com.vo.GetDeviceQRResponseVo;
import skinapp.luca.com.vo.GetOpenIDResponseVo;
import skinapp.luca.com.vo.GetQRResponseVo;
import skinapp.luca.com.vo.HistoryResponseVo;
import skinapp.luca.com.vo.ProductsResponseVo;
import skinapp.luca.com.vo.SignInResponseVo;
import skinapp.luca.com.vo.UploadQRResponseVo;

public class MainActivity extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 0;

    private int mYear;
    private int mMonth;
    private int mDay;

    private ImageButton imgBtnLogin;
    private Button btnLogin;

    private TextView tvUserInfo;
    private EditText edtLoginID;
    private EditText edtPassword;
    private TextView tvIDLogin;
    private TextView tvWeixinLogin;

    private RadioButton male;
    private RadioButton female;

    private TextView btnReview;
    private TextView btnChart;

    private int selectedGender;

    private LinearLayout mainLayout;
    private LinearLayout infoLayout;
    private LinearLayout userListLayout;
    private RelativeLayout chartLayout;

    private RecyclerView reviewList;
    private LineChartView chart;

    private Animation shake;
    private ProgressDialog progressDialog;

    private String loginID;
    private String password;

    private static final int REQUEST_SIGNUP = 1;

    private AlertDialog informDialog;

    private String qrCodeURL;

    private ArrayList<HistoryItem> historyItems = new ArrayList<>();

    private HistoryAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ImageView ivQrCode;

    private Handler openIDHandler = new Handler();
    private Runnable openIDRunnable = new Runnable() {
        @Override
        public void run() {
            GetOpenIDTask task = new GetOpenIDTask();
            task.execute(SharedPrefManager.getInstance(MainActivity.this).getDeviceID());
        }
    };

    private boolean bRunningOpenID = false;

    @BindView(R.id.iv_photo)
    CircleImageView ivPhoto;
    @BindView(R.id.tv_openid)
    TextView tvOpenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        imgBtnLogin = (ImageButton) findViewById(R.id.img_btn_login);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvUserInfo = (TextView) findViewById(R.id.tv_user_info);

        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        infoLayout = (LinearLayout) findViewById(R.id.info_layout);
        userListLayout = (LinearLayout) findViewById(R.id.user_list_layout);
        chartLayout = (RelativeLayout) findViewById(R.id.chart_layout);

        chart = (LineChartView) findViewById(R.id.chart);
        reviewList = (RecyclerView) findViewById(R.id.review_list);

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgBtnLogin.performClick();
            }
        });

        imgBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View informDialogView = factory.inflate(R.layout.dialog_login, null);

                edtLoginID = (EditText) informDialogView.findViewById(R.id.edt_id);
                edtPassword = (EditText) informDialogView.findViewById(R.id.edt_password);

                tvIDLogin = (TextView) informDialogView.findViewById(R.id.tv_id_login);
                tvWeixinLogin = (TextView) informDialogView.findViewById(R.id.tv_weixin_login);

                ivQrCode = (ImageView) informDialogView.findViewById(R.id.iv_qrcode);
                final LinearLayout loginLayout = (LinearLayout) informDialogView.findViewById(R.id.login_layout);

                /*File imgFile = new File(getFilesDir(), SkinApplication.QR_FILE_PATH);

                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    ivQrCode.setImageBitmap(myBitmap);
                }*/

                tvIDLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tvIDLogin.setTypeface(null, Typeface.BOLD);
                        tvWeixinLogin.setTypeface(null, Typeface.NORMAL);
                        ivQrCode.setVisibility(View.GONE);
                        loginLayout.setVisibility(View.VISIBLE);
                    }
                });

                tvWeixinLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tvWeixinLogin.setTypeface(null, Typeface.BOLD);
                        tvIDLogin.setTypeface(null, Typeface.NORMAL);
                        ivQrCode.setVisibility(View.VISIBLE);
                        loginLayout.setVisibility(View.GONE);

                        progressDialog.show();
                        GetDeviceQRTask task = new GetDeviceQRTask();
                        task.execute(SharedPrefManager.getInstance(MainActivity.this).getDeviceID());

                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edtLoginID.getWindowToken(), 0);

                        openIDHandler.post(openIDRunnable);
                        bRunningOpenID = true;
                    }
                });

                informDialog = new AlertDialog.Builder(MainActivity.this).create();
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

                informDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if(bRunningOpenID) {
                            bRunningOpenID = false;
                        }
                    }
                });

                informDialog.show();
            }
        });

        reviewList.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(MainActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reviewList.setLayoutManager(mLinearLayoutManager);
        reviewList.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));

        adapter = new HistoryAdapter(MainActivity.this);
        reviewList.setAdapter(adapter);

        if(SharedPrefManager.getInstance(this).getFirstQR())
            getLoginQR();
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);

        if(SkinApplication.bLogin)
            refreshHistory();
    }

    @Override
    public void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onGetOpenIDEvent(GetOpenIDEvent event) {
        hideProgressDialog();
        GetOpenIDResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                /*if(SkinApplication.loginID.equals(responseVo.openid)) {
                    if(bRunningOpenID) {
                        openIDHandler.post(openIDRunnable);
                    }
                    return;
                } else {*/
//                    tvOpenId.setText(responseVo.openid);
//                    tvOpenId.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.GONE);
                    ivPhoto.setVisibility(View.VISIBLE);
                    SkinApplication.bLogin = true;
                    SkinApplication.loginID = responseVo.openid;
                    SkinApplication.seed = responseVo.openid + System.currentTimeMillis();

                    new DownLoadImageTask().execute(responseVo.image_url);

                    startConfirmOpenID(responseVo.id, responseVo.openid);

                    bRunningOpenID = false;

                    informDialog.dismiss();

                    infoLayout.setVisibility(View.VISIBLE);

                    refreshHistory();
//                }
            } else {
                if(responseVo.error_code == 2) {
                    if(bRunningOpenID) {
                        openIDHandler.post(openIDRunnable);
                    }
                    return;
                }
            }
        }
    }

    private void startConfirmOpenID(int id, String openid) {
        ConfirmOpenIDTask task = new ConfirmOpenIDTask();
        task.execute(String.valueOf(id), openid);
    }

    @Subscribe
    public void onSignInEvet(SignInEvent event) {
        hideProgressDialog();
        SignInResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                btnLogin.setText(loginID);

                ivPhoto.setVisibility(View.GONE);
                tvOpenId.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
                imgBtnLogin.setVisibility(View.GONE);
                SkinApplication.bLogin = true;
                SkinApplication.loginID = loginID;
                SkinApplication.seed = loginID + System.currentTimeMillis();

                informDialog.dismiss();

                infoLayout.setVisibility(View.VISIBLE);

                refreshHistory();
            } else {
                loginFailed(responseVo.error_code);
            }
        } else {
            networkError();
        }
    }

    @Subscribe
    public void onGetQREvent(GetQREvent event) {
        hideProgressDialog();
        GetQRResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.res == 1) {
                qrCodeURL = responseVo.qrcode;

                progressDialog.show();

                SaveQRTask task = new SaveQRTask(MainActivity.this);

                task.execute(qrCodeURL);
//                ImageLoader.getInstance().displayImage(responseVo.qrcode, ivQrCode);
            } else {
//                networkError();
            }
        } else {
//            networkError();
        }
    }

    @Subscribe
    public void onUploadQREvent(UploadQREvent event) {
        hideProgressDialog();
        UploadQRResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if (responseVo.success == 1) {
                SharedPrefManager.getInstance(this).saveFirstQR(false);
            }
        }
    }

    @Subscribe
    public void onGetDeviceQREvent(GetDeviceQREvent event) {
        hideProgressDialog();
        GetDeviceQRResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if (responseVo.success == 1) {
                String imgString = responseVo.img;

                Bitmap qrBitmap = StringToBitMap(imgString);
                ivQrCode.setImageBitmap(qrBitmap);
            }
        }
    }

    @Subscribe
    public void onSaveQREvent(SaveQREvent event) {
        hideProgressDialog();

        File imgFile = new File(getFilesDir(), SkinApplication.QR_FILE_PATH);

        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

        String imgText = getStringImage(myBitmap);

        UploadQRTask task = new UploadQRTask();
        task.execute(SharedPrefManager.getInstance(this).getDeviceID(), imgText);
    }

    @Subscribe
    public void onGetHistoryEvent(GetHistoryEvent event) {
        hideProgressDialog();
        HistoryResponseVo responseVo = event.getResponse();

        if (responseVo != null) {
            try {
                JSONArray historyArray = new JSONArray(responseVo.history);

                historyItems.clear();

                for(int i = 0; i < historyArray.length(); i++) {
                    JSONObject jsonHistoryItem = historyArray.getJSONObject(i);
                    String date = jsonHistoryItem.getString("date");
                    JSONArray jsonResultArray = jsonHistoryItem.getJSONArray("result");

                    for(int j = 0; j < jsonResultArray.length(); j++) {
                        JSONObject jsonResult = jsonResultArray.getJSONObject(j);

                        HistoryItem item = new HistoryItem();

                        int tempType = jsonResult.getInt("type");
                        String strType = "";
                        switch (tempType) {
                            case CommonConsts.OIL_ANALYSIS:
                                strType = "油份分析";
                                break;
                            case CommonConsts.MUSCLE_ANALYSIS:
                                strType = "肌肤铅汞值";
                                break;
                            case CommonConsts.FIBER_ANALYSIS:
                                strType = "胶原蛋白纤维";
                                break;
                            case CommonConsts.FLEX_ANALYSIS:
                                strType = "肌肤弹性";
                                break;
                            case CommonConsts.WATER_ANALYSIS:
                                strType = "水分含量";
                                break;
                        }

                        item.setType(strType);
                        item.setValue(jsonResult.getString("value"));
                        item.setDate(date);

                        historyItems.add(item);
                    }
                }

                adapter.addItems(historyItems);
                adapter.notifyDataSetChanged();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        } else {
            networkError();
        }
    }

    @OnClick(R.id.btn_analyze_oil)
    void onClickBtnOil() {
        if(!SkinApplication.bLogin) {
            askLogin();
            return;
        }
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.OIL_ANALYSIS);

        startActivity(intent);
    }

    @OnClick(R.id.iv_photo)
    void onClickPhoto() {
        btnLogin.performClick();
    }

    @OnClick(R.id.btn_analyze_fiber)
    void onClickBtnFiber() {
        if(!SkinApplication.bLogin) {
            askLogin();
            return;
        }

        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.FIBER_ANALYSIS);

        startActivity(intent);
    }

    @OnClick(R.id.btn_analyze_muscle)
    void onClickBtnMuscle() {
        if(!SkinApplication.bLogin) {
            askLogin();
            return;
        }

        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.MUSCLE_ANALYSIS);

        startActivity(intent);
    }

    @OnClick(R.id.btn_analyze_flex)
    void onClickBtnFlex() {
        if(!SkinApplication.bLogin) {
            askLogin();
            return;
        }

        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.FLEX_ANALYSIS);

        startActivity(intent);
    }

    @OnClick(R.id.btn_analyze_hairhole)
    void onClickBtnHairhole() {
        if(!SkinApplication.bLogin) {
            askLogin();
            return;
        }

        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.HAIRHOLE_ANALYSIS);

        startActivity(intent);
    }

    @OnClick(R.id.btn_analyze_zit)
    void onClickBtnZit() {
        if(!SkinApplication.bLogin) {
            askLogin();
            return;
        }

        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.ZIT_ANALYSIS);

        startActivity(intent);
    }

    @OnClick(R.id.btn_analyze_action)
    void onClickBtnAction() {
        if(!SkinApplication.bLogin) {
            askLogin();
            return;
        }

        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        intent.putExtra("type", CommonConsts.ACTION_ANALYSIS);

        startActivity(intent);
    }

    @OnClick(R.id.btn_analyze_water)
    void onClickBtnWater() {
        if(!SkinApplication.bLogin) {
            askLogin();
            return;
        }

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

    private void askLogin() {
        Toast.makeText(MainActivity.this, "请登录一下。", Toast.LENGTH_SHORT).show();
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

    private void refreshHistory() {
        progressDialog.show();

        GetHistoryTask task = new GetHistoryTask();
        task.execute(SkinApplication.loginID);
    }

    private void getLoginQR() {
        progressDialog.show();

        GetQRTask task = new GetQRTask();
        task.execute(SharedPrefManager.getInstance(this).getDeviceID());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_SIGNUP) {
            if(resultCode == Activity.RESULT_OK){
                String loginID = data.getStringExtra("loginID");

                btnLogin.setText(loginID);
                infoLayout.setVisibility(View.VISIBLE);

                refreshHistory();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        imageUrl = imageUrl.replaceAll("'\'", "");
        Log.v("URL", imageUrl);
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {

        public DownLoadImageTask(){
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            ivPhoto.setImageBitmap(result);
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
