package it.unitn.lpsmt2020.portalesimulatoregare.ui.main;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import it.unitn.lpsmt2020.portalesimulatoregare.ChampionshipDetailActivity;
import it.unitn.lpsmt2020.portalesimulatoregare.R;
import it.unitn.lpsmt2020.portalesimulatoregare.datasource.InternalDB;
import it.unitn.lpsmt2020.portalesimulatoregare.event.SubscriptionChangedEvent;
import it.unitn.lpsmt2020.portalesimulatoregare.models.ChampionshipItemLight;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ChampionshipListRecyclerViewAdapter extends RecyclerView.Adapter<ChampionshipListRecyclerViewAdapter.ViewHolder> {

    private final List<ChampionshipItemLight> mValues;
    private final boolean onlySubbed;

    public ChampionshipListRecyclerViewAdapter(List<ChampionshipItemLight> items, boolean onlySubbed) {
        mValues = items;
        this.onlySubbed = onlySubbed;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_championship, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setItem(mValues.get(position));
    }

    public void removeAtIndex(int i) {
        this.mValues.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, mValues.size());
    }

    public void addItem(ChampionshipItemLight item) {
        this.mValues.add(item);
        notifyItemInserted(this.mValues.size() - 1);
        notifyItemRangeChanged(this.mValues.size() - 1, mValues.size());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView txtChampionshipTitle;
        public final ImageView imgSubIndicator;
        public ChampionshipItemLight item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.txtChampionshipTitle = (TextView) view.findViewById(R.id.txtChampionshipTitle);
            this.imgSubIndicator = (ImageView) view.findViewById(R.id.imgSubIndicator);
            this.imgSubIndicator.setOnClickListener((View v) -> onSubClick());
            this.view.setOnClickListener((View v) -> onChampionshipClick());
        }

        public void setItem(ChampionshipItemLight item) {
            this.item = item;
            this.txtChampionshipTitle.setText(item.getName());
            setSubIcon();
        }

        private void setSubIcon() {
            Drawable d = ContextCompat.getDrawable(view.getContext(), this.item.isSubscribed() ? R.drawable.ic_baseline_flag_48 : R.drawable.ic_baseline_outlined_flag_48);
            this.imgSubIndicator.setImageDrawable(d);
        }

        private void onSubClick() {
            boolean nextStatus = !this.item.isSubscribed();


            if (nextStatus)
                InternalDB.subscribe(this.item.getId());
            else
                InternalDB.unsubscribe(this.item.getId());

            this.item.setSubscribed(nextStatus);
            setSubIcon();

            //EVENT
            EventBus bus = EventBus.getDefault();
            bus.post(new SubscriptionChangedEvent(this.item));
        }

        private void onChampionshipClick() {
            Intent openChampionship = new Intent(this.view.getContext(), ChampionshipDetailActivity.class);
            this.view.getContext().startActivity(openChampionship);
        }


        public void checkSubStatus() {
            boolean s = InternalDB.isSubscribed(this.item.getId());
            if (s != this.item.isSubscribed()) {
                this.item.setSubscribed(s);
            }
            setSubIcon();
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtChampionshipTitle.getText() + "'";
        }
    }
}