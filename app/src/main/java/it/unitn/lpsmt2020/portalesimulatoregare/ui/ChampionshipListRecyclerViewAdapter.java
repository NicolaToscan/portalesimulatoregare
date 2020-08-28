package it.unitn.lpsmt2020.portalesimulatoregare.ui;

import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import it.unitn.lpsmt2020.portalesimulatoregare.R;
import it.unitn.lpsmt2020.portalesimulatoregare.datasource.InternalDB;
import it.unitn.lpsmt2020.portalesimulatoregare.event.SubscriptionChangedEvent;
import it.unitn.lpsmt2020.portalesimulatoregare.models.ChampionshipItem;
import it.unitn.lpsmt2020.portalesimulatoregare.ui.dummy.DummyContent.DummyItem;

import java.net.Inet4Address;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ChampionshipListRecyclerViewAdapter extends RecyclerView.Adapter<ChampionshipListRecyclerViewAdapter.ViewHolder> {

    private final List<ChampionshipItem> mValues;
    private final boolean onlySubbed;

    public ChampionshipListRecyclerViewAdapter(List<ChampionshipItem> items, boolean onlySubbed) {
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

    public void addItem(ChampionshipItem item) {
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
        public ChampionshipItem item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.txtChampionshipTitle = (TextView) view.findViewById(R.id.txtChampionshipTitle);
            this.imgSubIndicator = (ImageView) view.findViewById(R.id.imgSubIndicator);
            this.imgSubIndicator.setOnClickListener((View v) -> onSubClick());
        }

        public void setItem(ChampionshipItem item) {
            this.item = item;
            this.txtChampionshipTitle.setText(item.getName());
            setSubIcon();
        }

        private void setSubIcon() {
            Drawable d = ContextCompat.getDrawable(view.getContext(), this.item.isSubscribed() ? R.drawable.ic_baseline_flag_48 : R.drawable.ic_baseline_outlined_flag_48);
            this.imgSubIndicator.setImageDrawable(d);
        }

        public void onSubClick() {
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