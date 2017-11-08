package skinapp.luca.com.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cuboid.cuboidcirclebutton.CuboidButton;

import skinapp.luca.com.R;

public class AnalyzeActivity extends AppCompatActivity {

    private ImageView imgOriginal;
    private ImageView imgConverted;

    private CuboidButton btnStart;

    private int nRedCnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        imgOriginal = (ImageView) findViewById(R.id.img_original);
        imgConverted = (ImageView) findViewById(R.id.img_converted);

        btnStart = (CuboidButton) findViewById(R.id.btn_start);

        final Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.mipmap.img_temp);

        imgOriginal.setImageBitmap(bm);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bitmap convertedBitmap = bm.copy(Bitmap.Config.ARGB_8888, true);
                nRedCnt = 0;
                for(int x = 0; x < bm.getWidth(); x++) {
                    for (int y = 0; y < bm.getHeight(); y++) {
                        int pixel = bm.getPixel(x, y);

                        convertedBitmap.setPixel(x, y, convertToRed(pixel));
                    }
                }
                imgConverted.setImageBitmap(convertedBitmap);

                int redPercentage = 0;
                int totalPixel = bm.getWidth() * bm.getHeight();
                redPercentage = nRedCnt / totalPixel * 100;

                analyze(redPercentage, 1);
            }
        });
    }

    private Bitmap createInvertedBitmap(Bitmap src) {
        ColorMatrix colorMatrix_Inverted =
                new ColorMatrix(new float[] {
                        -1,  0,  0,  0, 255,
                        0, -1,  0,  0, 0,
                        0,  0, -1,  0, 0,
                        0,  0,  0,  1,   0});

        ColorFilter ColorFilter_Sepia = new ColorMatrixColorFilter(
                colorMatrix_Inverted);

        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();

        paint.setColorFilter(ColorFilter_Sepia);
        canvas.drawBitmap(src, 0, 0, paint);

        return bitmap;
    }

    private int convertToRed(int pixel) {
        int ret = Color.rgb(255, 0, 0);

        int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);

        int ebx = redValue * 0x1E;
        ebx = ebx + (greenValue * 0x3B);
        ebx = ebx + (blueValue * 0x0B);
        ebx = ebx / 0x64;

        if(ebx < 0xaa) {
            ret = Color.rgb(0, 0, 0);
        } else {
            nRedCnt++;
        }

        return ret;
    }

    private int analyze(int redCount, int nMode) {
        int temp = redCount;
        int ret = -1;
        switch(nMode) {
            case 1:
                if(temp < 3) {
                    ret = 3;
                    break;
                }

                if(temp > 0x23) {
                    ret = 0x23;
                } else {
                    ret = temp;
                }
                break;
            case 2:

                break;
            case 3:

                break;
            default:
                break;
        }

        return ret;
    }
}
