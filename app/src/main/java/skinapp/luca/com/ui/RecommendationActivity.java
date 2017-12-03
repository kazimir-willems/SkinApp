package skinapp.luca.com.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import skinapp.luca.com.R;
import skinapp.luca.com.adapter.ProductAdapter;
import skinapp.luca.com.event.ProductEvent;
import skinapp.luca.com.model.ProductItem;
import skinapp.luca.com.vo.ProductsResponseVo;

public class RecommendationActivity extends AppCompatActivity {

    @BindView(R.id.product_list)
    RecyclerView productList;

    private ProductAdapter adapter;

    private LinearLayoutManager mLinearLayoutManager;

    private int type = 0;

    private ProgressDialog progressDialog;

    private ArrayList<ProductItem> productItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        ButterKnife.bind(this);

        productList.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(RecommendationActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productList.setLayoutManager(mLinearLayoutManager);
        productList.addItemDecoration(new DividerItemDecoration(RecommendationActivity.this, DividerItemDecoration.VERTICAL_LIST));

        adapter = new ProductAdapter(RecommendationActivity.this);
        productList.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));

        refreshItems();
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
    public void onClickHistoryEvent(ProductEvent event) {
        hideProgressDialog();
        ProductsResponseVo responseVo = event.getResponse();

        if (responseVo != null) {
            try {
                JSONArray productsArray = new JSONArray(responseVo.products);

                for(int i = 0; i < productsArray.length(); i++) {
                    JSONObject jsonProductItem = productsArray.getJSONObject(i);

                    ProductItem item = new ProductItem("", "", "", "");

                    item.setName(jsonProductItem.getString("name"));
                    item.setAdvantage(jsonProductItem.getString("advantage"));
                    item.setComment(jsonProductItem.getString("comment"));
                    item.setPhotoURL(jsonProductItem.getString("photo_url"));

                    productItems.add(item);
                }

                adapter.addItems(productItems);
                adapter.notifyDataSetChanged();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        } else {
            networkError();
        }
    }

    private void refreshItems() {

    }

    private void hideProgressDialog() {
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void networkError() {
        Toast.makeText(RecommendationActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
    }
}
