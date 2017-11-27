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

import java.util.Random;

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
    private int prodRec = 0;

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

        btnAnalyze.setEnabled(false);

        Random r = new Random();
        int randomInt = r.nextInt(80 ) ;

        int seed = randomInt % 8 + 1;
        String imgName = "img_sample" + seed;
        int resourceId = this.getResources().getIdentifier(imgName, "mipmap", this.getPackageName());
        bm = BitmapFactory.decodeResource(getResources(),
                resourceId);

        imgOriginal.setImageBitmap(bm);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nPixelCnt = 0;

                ProcessTask task = new ProcessTask();
                task.execute(type);

                btnAnalyze.setEnabled(false);

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

                TextView tvPercentage = resultDialogView.findViewById(R.id.tv_percentage);
                TextView tvTitle = resultDialogView.findViewById(R.id.tv_title);
                TextView tvInformation = resultDialogView.findViewById(R.id.tv_information);
                TextView tvResult = resultDialogView.findViewById(R.id.tv_result);

                double percentage = nPixelCnt * 100.0 / (convertedBitmap.getWidth() * convertedBitmap.getHeight());
                long anaResult = analyze(Math.round(percentage), type);
                tvPercentage.setText(anaResult + "%");

                switch (type) {
                    case CommonConsts.OIL_ANALYSIS:
                        if(anaResult < 20) {
                            tvTitle.setText(R.string.str_dry_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.dry_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.dry_skin)[1]);

                            prodRec = 3;
                        } else if(anaResult >= 20 && anaResult < 40) {
                            tvTitle.setText(R.string.str_neutral_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.neutral_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.neutral_skin)[1]);

                            prodRec = 3;
                        } else if(anaResult >= 40 && anaResult < 60) {
                            tvTitle.setText(R.string.str_mixed_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.mixed_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.mixed_skin)[1]);

                            prodRec = 1;
                        } else if(anaResult >= 60 && anaResult < 80) {
                            tvTitle.setText(R.string.str_sensitive_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.sensitive_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.sensitive_skin)[1]);

                            prodRec = 2;
                        } else {
                            tvTitle.setText(R.string.str_oil_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.oil_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.oil_skin)[1]);

                            prodRec = 4;
                        }

                        break;
                    case CommonConsts.MUSCLE_ANALYSIS:
                        if(anaResult < 20) {
                            tvTitle.setText(R.string.str_aging_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.aging_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.aging_skin)[1]);

                            prodRec = 4;
                        } else if(anaResult >= 20 && anaResult < 40) {
                            tvTitle.setText(R.string.str_dry_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.dry_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.dry_skin)[1]);

                            prodRec = 1;
                        } else if(anaResult >= 40 && anaResult < 60) {
                            tvTitle.setText(R.string.str_mixed_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.mixed_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.mixed_skin)[1]);

                            prodRec = 1;
                        } else if(anaResult >= 60 && anaResult < 80) {
                            tvTitle.setText(R.string.str_sensitive_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.sensitive_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.sensitive_skin)[1]);

                            prodRec = 3;
                        } else {
                            tvTitle.setText(R.string.str_neutral_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.neutral_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.neutral_skin)[1]);

                            prodRec = 2;
                        }

                        break;
                    case CommonConsts.FIBER_ANALYSIS:
                        if(anaResult < 20) {
                            tvTitle.setText(R.string.str_dry_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.dry_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.dry_skin)[1]);

                            prodRec = 2;
                        } else if(anaResult >= 20 && anaResult < 40) {
                            tvTitle.setText(R.string.str_mixed_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.mixed_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.mixed_skin)[1]);

                            prodRec = 1;
                        } else if(anaResult >= 40 && anaResult < 60) {
                            tvTitle.setText(R.string.str_oil_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.oil_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.oil_skin)[1]);

                            prodRec = 4;
                        } else if(anaResult >= 60 && anaResult < 80) {
                            tvTitle.setText(R.string.str_sensitive_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.sensitive_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.sensitive_skin)[1]);

                            prodRec = 3;
                        } else {
                            tvTitle.setText(R.string.str_neutral_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.neutral_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.neutral_skin)[1]);

                            prodRec = 1;
                        }
                        break;
                    case CommonConsts.FLEX_ANALYSIS:
                        if(anaResult < 20) {
                            tvTitle.setText(R.string.str_aging_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.aging_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.aging_skin)[1]);

                            prodRec = 1;
                        } else if(anaResult >= 20 && anaResult < 40) {
                            tvTitle.setText(R.string.str_mixed_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.mixed_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.mixed_skin)[1]);

                            prodRec = 4;
                        } else if(anaResult >= 40 && anaResult < 60) {
                            tvTitle.setText(R.string.str_neutral_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.neutral_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.neutral_skin)[1]);

                            prodRec = 2;
                        } else if(anaResult >= 60 && anaResult < 80) {
                            tvTitle.setText(R.string.str_sensitive_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.sensitive_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.sensitive_skin)[1]);

                            prodRec = 3;
                        } else {
                            tvTitle.setText(R.string.str_oil_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.oil_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.oil_skin)[1]);

                            prodRec = 4;
                        }

                        break;
                    case CommonConsts.HAIRHOLE_ANALYSIS:
                        break;
                    case CommonConsts.ZIT_ANALYSIS:

                        break;
                    case CommonConsts.ACTION_ANALYSIS:

                        break;
                    case CommonConsts.WATER_ANALYSIS:
                        if(anaResult < 20) {
                            tvTitle.setText(R.string.str_aging_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.aging_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.aging_skin)[1]);

                            prodRec = 4;
                        } else if(anaResult >= 20 && anaResult < 40) {
                            tvTitle.setText(R.string.str_dry_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.dry_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.dry_skin)[1]);

                            prodRec = 2;
                        } else if(anaResult >= 40 && anaResult < 60) {
                            tvTitle.setText(R.string.str_mixed_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.mixed_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.mixed_skin)[1]);

                            prodRec = 2;
                        } else if(anaResult >= 60 && anaResult < 80) {
                            tvTitle.setText(R.string.str_neutral_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.neutral_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.neutral_skin)[1]);

                            prodRec = 1;
                        } else {
                            tvTitle.setText(R.string.str_sensitive_skin);
                            tvInformation.setText(getResources().getStringArray(R.array.sensitive_skin)[0]);
                            tvResult.setText(getResources().getStringArray(R.array.sensitive_skin)[1]);

                            prodRec = 3;
                        }

                        break;
                }

                final AlertDialog resultDialog = new AlertDialog.Builder(AnalyzeActivity.this).create();
                resultDialog.setView(resultDialogView);
                resultDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //your business logic
                        resultDialog.dismiss();
                    }
                });
                resultDialogView.findViewById(R.id.btn_product).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(AnalyzeActivity.this, RecommendationActivity.class);
                        intent.putExtra("type", prodRec);

                        startActivity(intent);
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
//            nPixelCnt++;
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
//            nPixelCnt++;
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

    private long analyze(long redCount, int nMode) {
        long temp = redCount;
        long ret = -1;
        switch(nMode) {
            case CommonConsts.OIL_ANALYSIS:
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
            case CommonConsts.MUSCLE_ANALYSIS:
                if(temp > 3) {
                    ret = 8;
                    break;
                }

                if(temp > 65) {
                    ret = 65;
                } else {
                    ret = temp;
                }
                break;
            case CommonConsts.FIBER_ANALYSIS:
                if(temp < 8) {
                    ret = 8;
                    break;
                }

                if(temp > 75) {
                    ret = 75;
                } else {
                    ret = temp;
                }
                break;
            case CommonConsts.FLEX_ANALYSIS:
                if(temp < 15) {
                    ret = 15;
                    break;
                }
                if(temp > 71) {
                    ret = 71;
                } else {
                    ret = temp;
                }
                break;
            case CommonConsts.WATER_ANALYSIS:
                if (temp < 0x19) {
                    ret = 0x19;
                    break;
                }
                if(temp > 86) {
                    ret = 86;
                } else {
                    ret = temp;
                }
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
            btnAnalyze.setEnabled(true);
            return;
        }
    }
}
