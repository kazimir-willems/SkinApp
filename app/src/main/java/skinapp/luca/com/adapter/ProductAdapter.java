package skinapp.luca.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import skinapp.luca.com.R;
import skinapp.luca.com.model.ProductItem;
import skinapp.luca.com.ui.RecommendationActivity;
import skinapp.luca.com.util.URLManager;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private RecommendationActivity parent;
    private List<ProductItem> items = new ArrayList<>();

    public ProductAdapter(RecommendationActivity parent) {
        this.parent = parent;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final ProductItem item = items.get(position);

        holder.tvProductName.setText(item.getName());
        holder.tvProductAdvantage.setText(item.getAdvantage());
        holder.tvComment.setText(item.getComment());

        ImageLoader.getInstance().displayImage(URLManager.getImageURL() + item.getPhotoURL(), holder.ivProduct);
    }

    public ProductItem getItem(int pos) {
        return items.get(pos);
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(ProductItem item) {
        items.add(item);
    }

    public void addItems(ArrayList<ProductItem> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        @BindView(R.id.tv_prod_name)
        TextView tvProductName;
        @BindView(R.id.tv_prod_advantage)
        TextView tvProductAdvantage;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.iv_product)
        ImageView ivProduct;

        public ProductViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
