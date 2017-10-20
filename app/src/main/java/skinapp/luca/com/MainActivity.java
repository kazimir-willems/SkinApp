package skinapp.luca.com;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
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

        informDialog.show();
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
