package it.unitn.lpsmt2020.portalesimulatoregare.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import it.unitn.lpsmt2020.portalesimulatoregare.R;
import it.unitn.lpsmt2020.portalesimulatoregare.models.ChampionshipItem;
import it.unitn.lpsmt2020.portalesimulatoregare.ui.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ChampionshipListRecyclerViewAdapter extends RecyclerView.Adapter<ChampionshipListRecyclerViewAdapter.ViewHolder> {

    private final List<ChampionshipItem> mValues;

    public ChampionshipListRecyclerViewAdapter(List<ChampionshipItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_championship, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = mValues.get(position);
        holder.txtChampionshipTitle.setText(holder.item.getName());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtChampionshipTitle;
        public ChampionshipItem item;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtChampionshipTitle = (TextView) view.findViewById(R.id.txtChampionshipTitle);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtChampionshipTitle.getText() + "'";
        }
    }
}