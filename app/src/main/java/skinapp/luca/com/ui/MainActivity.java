package skinapp.luca.com.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cuboid.cuboidcirclebutton.CuboidButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import skinapp.luca.com.R;

public class MainActivity extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 0;

    private int mYear;
    private int mMonth;
    private int mDay;

    private EditText edtBirthday;
    private CuboidButton btnLogin;
    private TextView btnUserList;
    private TextView tvUserInfo;
    private EditText edtName;
    private EditText edtAge;

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
                final View informDialogView = factory.inflate(R.layout.dialog_information, null);
                edtBirthday = (EditText) informDialogView.findViewById(R.id.edt_birthday);
                edtName = (EditText) informDialogView.findViewById(R.id.edt_name);
                edtAge = (EditText) informDialogView.findViewById(R.id.edt_age);
                male = (RadioButton) informDialogView.findViewById(R.id.male);
                female = (RadioButton) informDialogView.findViewById(R.id.female);

                male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b) {
                            selectedGender = 0;
                        }
                    }
                });

                female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b) {
                            selectedGender = 1;
                        }
                    }
                });

                final AlertDialog informDialog = new AlertDialog.Builder(MainActivity.this).create();
                informDialog.setView(informDialogView);
                informDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //your business logic
                        btnLogin.setText(edtName.getText());
                        String userInfo = "";

                        if(selectedGender == 0)
                            userInfo = "男      ";
                        else
                            userInfo = "女      ";

                        userInfo = userInfo + String.valueOf(edtAge.getText()) + "岁";
                        tvUserInfo.setText(userInfo);
                        infoLayout.setVisibility(View.VISIBLE);
                        chartLayout.setVisibility(View.GONE);
                        informDialog.dismiss();
                    }
                });
                informDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        informDialog.dismiss();
                    }
                });

                edtBirthday.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            showDialog(DATE_DIALOG_ID);
                            return true;
                        }
                        return false;
                    }
                });

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // display the current date
                updateDisplay();

                informDialog.show();
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

    private void updateDisplay() {
        this.edtBirthday.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mYear).append("/")
                        .append(mMonth + 1).append("/")
                        .append(mDay));
    }
}
