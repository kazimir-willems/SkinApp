package skinapp.luca.com.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import skinapp.luca.com.R;

public class RecommendationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvContent = (TextView) findViewById(R.id.tv_content);
        ImageView ivProduct = (ImageView) findViewById(R.id.iv_prod);

//        Random r = new Random();
//        int randomInt = r.nextInt(80 ) ;

//        int type = randomInt % 4 + 1;

        int type = getIntent().getIntExtra("type", 0);

        switch(type) {
            case 1:
                tvTitle.setText(getResources().getString(R.string.prod1_title));
                tvContent.setText(getResources().getString(R.string.prod1_content));

                ivProduct.setBackground(getResources().getDrawable(R.mipmap.img_prod1));
                break;
            case 2:
                tvTitle.setText(getResources().getString(R.string.prod2_title));
                tvContent.setText(getResources().getString(R.string.prod2_content));

                ivProduct.setBackground(getResources().getDrawable(R.mipmap.img_prod2));
                break;
            case 3:
                tvTitle.setText(getResources().getString(R.string.prod3_title));
                tvContent.setText(getResources().getString(R.string.prod3_content));

                ivProduct.setBackground(getResources().getDrawable(R.mipmap.img_prod3));
                break;
            case 4:
                tvTitle.setText(getResources().getString(R.string.prod4_title));
                tvContent.setText(getResources().getString(R.string.prod4_content));

                ivProduct.setBackground(getResources().getDrawable(R.mipmap.img_prod4));
                break;
        }
    }
}
