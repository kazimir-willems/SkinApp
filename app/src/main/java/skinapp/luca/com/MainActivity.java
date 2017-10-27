package skinapp.luca.com;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 0;

    private int mYear;
    private int mMonth;
    private int mDay;

    private EditText edtBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
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

    @OnClick(R.id.btn_vip)
    void onClickBtnVip() {
        Intent intent = new Intent(MainActivity.this, VIPMainActivity.class);

        startActivity(intent);
    }

    @OnClick(R.id.btn_second)
    void onClickBtnSecond() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View informDialogView = factory.inflate(R.layout.dialog_information, null);
        edtBirthday = (EditText) informDialogView.findViewById(R.id.edt_birthday);

        final AlertDialog informDialog = new AlertDialog.Builder(this).create();
        informDialog.setView(informDialogView);
        informDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                Intent intent = new Intent(MainActivity.this, SecondMainActivity.class);

                startActivity(intent);
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

    @OnClick(R.id.btn_third)
    void onClickBtnThird() {
        Intent intent = new Intent(MainActivity.this, ThirdMainActivity.class);

        startActivity(intent);
    }

    @OnClick(R.id.btn_exit)
    void onClickBtnExit() {
        finish();
    }
}
