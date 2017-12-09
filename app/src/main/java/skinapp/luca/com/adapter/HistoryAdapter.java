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
import skinapp.luca.com.model.HistoryItem;
import skinapp.luca.com.model.ProductItem;
import skinapp.luca.com.ui.MainActivity;
import skinapp.luca.com.ui.RecommendationActivity;
import skinapp.luca.com.util.URLManager;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private MainActivity parent;
    private List<HistoryItem> items = new ArrayList<>();

    public HistoryAdapter(MainActivity parent) {
        this.parent = parent;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryViewHolder holder, int position) {
        final HistoryItem item = items.get(position);

        holder.tvType.setText(item.getType());
        holder.tvValue.setText(item.getValue() + "%");
        holder.tvDate.setText(item.getDate());
    }

    public HistoryItem getItem(int pos) {
        return items.get(pos);
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(HistoryItem item) {
        items.add(item);
    }

    public void addItems(ArrayList<HistoryItem> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_value)
        TextView tvValue;
        @BindView(R.id.tv_date)
        TextView tvDate;

        public HistoryViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
