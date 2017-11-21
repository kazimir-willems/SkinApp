package skinapp.luca.com.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuboid.cuboidcirclebutton.CuboidButton;

import skinapp.luca.com.R;
import skinapp.luca.com.consts.CommonConsts;

public class AnalyzeActivity extends AppCompatActivity {

    private ImageView imgOriginal;
    private ImageView imgConverted;

    private CuboidButton btnStart;
    private CuboidButton btnAnalyze;

    private Bitmap convertedBitmap;
    private Bitmap bm;

    private int nPixelCnt = 0;

    private int type = 0;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        type = getIntent().getIntExtra("type", 0);

        imgOriginal = (ImageView) findViewById(R.id.img_original);
        imgConverted = (ImageView) findViewById(R.id.img_converted);

        btnStart = (CuboidButton) findViewById(R.id.btn_start);
        btnAnalyze = (CuboidButton) findViewById(R.id.btn_analyze);

        bm = BitmapFactory.decodeResource(getResources(),
                R.mipmap.img_temp);

        imgOriginal.setImageBitmap(bm);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nPixelCnt = 0;

                ProcessTask task = new ProcessTask();
                task.execute(type);

                /*int redPercentage = 0;
                int totalPixel = bm.getWidth() * bm.getHeight();
                redPercentage = nPixelCnt / totalPixel * 100;

                analyze(redPercentage, 1);*/
            }
        });

        btnAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater factory = LayoutInflater.from(AnalyzeActivity.this);
                final View resultDialogView = factory.inflate(R.layout.dialog_result, null);

                TextView tvXcyyResult = resultDialogView.findViewById(R.id.tv_xcyy_result);
                TextView tvJyResult = resultDialogView.findViewById(R.id.tv_jy_result);
                TextView tvPercentage = resultDialogView.findViewById(R.id.tv_percentage);

                switch (type) {
                    case CommonConsts.OIL_ANALYSIS:
                        tvXcyyResult.setText(getResources().getStringArray(R.array.oil_analysis_result)[0]);
                        tvJyResult.setText(getResources().getStringArray(R.array.oil_analysis_result)[1]);
                        break;
                    case CommonConsts.MUSCLE_ANALYSIS:
                        tvXcyyResult.setText(getResources().getStringArray(R.array.zit_analysis_result)[0]);
                        tvJyResult.setText(getResources().getStringArray(R.array.zit_analysis_result)[1]);
                        break;
                    case CommonConsts.FIBER_ANALYSIS:
                        tvXcyyResult.setText(getResources().getStringArray(R.array.action_analysis_result)[0]);
                        tvJyResult.setText(getResources().getStringArray(R.array.action_analysis_result)[1]);
                        break;
                    case CommonConsts.FLEX_ANALYSIS:
                        tvXcyyResult.setText(getResources().getStringArray(R.array.flex_analysis_result)[0]);
                        tvJyResult.setText(getResources().getStringArray(R.array.flex_analysis_result)[1]);
                        break;
                    case CommonConsts.HAIRHOLE_ANALYSIS:
                        tvXcyyResult.setText(getResources().getStringArray(R.array.hairhole_analysis_result)[0]);
                        tvJyResult.setText(getResources().getStringArray(R.array.hairhole_analysis_result)[1]);
                        break;
                    case CommonConsts.ZIT_ANALYSIS:

                        break;
                    case CommonConsts.ACTION_ANALYSIS:

                        break;
                    case CommonConsts.WATER_ANALYSIS:
                        tvXcyyResult.setText(getResources().getStringArray(R.array.water_analysis_result)[0]);
                        tvJyResult.setText(getResources().getStringArray(R.array.water_analysis_result)[1]);
                        break;
                }

                double percentage = nPixelCnt * 100.0 / (convertedBitmap.getWidth() * convertedBitmap.getHeight());
                tvPercentage.setText(Math.round(percentage) + "%");

                final AlertDialog resultDialog = new AlertDialog.Builder(AnalyzeActivity.this).create();
                resultDialog.setView(resultDialogView);
                resultDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //your business logic
                        resultDialog.dismiss();
                    }
                });
                resultDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resultDialog.dismiss();
                    }
                });

                resultDialog.show();
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
            ret = Color.rgb(ebx, ebx, ebx);
        } else {
            nPixelCnt++;
        }

        return ret;
    }

    private int convertToGreen(int pixel) {
        int ret = Color.rgb(255, 0, 0);

        int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);

        int ebx = redValue * 0x1E;
        ebx = ebx + (greenValue * 0x3B);
        ebx = ebx + (blueValue * 0x0B);
        ebx = ebx / 0x64;

        if(ebx > 0x28 && ebx <= 0x8C) {
            ret = Color.rgb(0, 128, 0);
            nPixelCnt++;
        } else {
            ret = Color.rgb(ebx, ebx,ebx);
        }

        return ret;
    }

    private int convertToCyan(int pixel) {
        int ret = Color.rgb(255, 0, 0);

        int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);

        int ebx = redValue * 0x1E;
        ebx = ebx + (greenValue * 0x3B);
        ebx = ebx + (blueValue * 0x0B);
        ebx = ebx / 0x64;

        if(ebx >= 0x6E && ebx <= 0x87) {
            ret = Color.rgb(0, 255, 255);
            nPixelCnt++;
        } else {
            ret = Color.rgb(ebx, ebx,ebx);
        }

        return ret;
    }

    private int convertToLightBlue(int pixel) {
        int ret = Color.rgb(255, 0, 0);

        int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);

        int ebx = redValue * 0x1E;
        ebx = ebx + (greenValue * 0x3B);
        ebx = ebx + (blueValue * 0x0B);
        ebx = ebx / 0x64;

        if(ebx >= 0x82 && ebx <= 0x96) {
            ret = Color.rgb(68, 158, 234);
            nPixelCnt++;
        } else {
            ret = Color.rgb(ebx, ebx,ebx);
        }

        return ret;
    }

    private int convertToDarkBlue(int pixel) {
        int ret = Color.rgb(255, 0, 0);

        int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);

        int ebx = redValue * 0x1E;
        ebx = ebx + (greenValue * 0x3B);
        ebx = ebx + (blueValue * 0x0B);
        ebx = ebx / 0x64;

        if(ebx >= 0x9B && ebx <= 0xB4) {
            ret = Color.rgb(0, 0, 139);
            nPixelCnt++;
        } else {
            ret = Color.rgb(ebx, ebx,ebx);
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

    public class ProcessTask extends AsyncTask<Integer, Void, Boolean> {

        private int nMode = 0;

        @Override
        protected Boolean doInBackground(Integer... mode) {
            nMode = mode[0];
            convertedBitmap = bm.copy(Bitmap.Config.ARGB_8888, true);
            nPixelCnt = 0;
            for(int x = 0; x < bm.getWidth(); x++) {
                for (int y = 0; y < bm.getHeight(); y++) {
                    int pixel = bm.getPixel(x, y);

                    switch(nMode) {
                        case CommonConsts.OIL_ANALYSIS:
                            convertedBitmap.setPixel(x, y, convertToRed(pixel));
                            break;
                        case CommonConsts.MUSCLE_ANALYSIS:
                            convertedBitmap.setPixel(x, y, convertToGreen(pixel));
                            break;
                        case CommonConsts.FIBER_ANALYSIS:
                            convertedBitmap.setPixel(x, y, convertToCyan(pixel));
                            break;
                        case CommonConsts.FLEX_ANALYSIS:
                            convertedBitmap.setPixel(x, y, convertToLightBlue(pixel));
                            break;
                        case CommonConsts.HAIRHOLE_ANALYSIS:
                            convertedBitmap.setPixel(x, y, convertToDarkBlue(pixel));
                            break;
                        case CommonConsts.ZIT_ANALYSIS:
                            convertedBitmap.setPixel(x, y, convertToRed(pixel));
                            break;
                        case CommonConsts.ACTION_ANALYSIS:
                            convertedBitmap.setPixel(x, y, convertToRed(pixel));
                            break;
                        case CommonConsts.WATER_ANALYSIS:
                            convertedBitmap.setPixel(x, y, convertToDarkBlue(pixel));
                            break;
                    }

                }
                AnalyzeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imgConverted.setImageBitmap(convertedBitmap);
                    }
                });

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            return false;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Boolean result) {
            return;
        }
    }
}
