package skinapp.luca.com.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cuboid.cuboidcirclebutton.CuboidButton;

import skinapp.luca.com.R;

public class CaptureActivity extends AppCompatActivity {

    private CuboidButton btnCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        btnCapture = (CuboidButton) findViewById(R.id.btn_capture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaptureActivity.this, AnalyzeActivity.class);

                startActivity(intent);
            }
        });
    }
}
