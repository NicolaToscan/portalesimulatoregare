package it.unitn.lpsmt2020.portalesimulatoregare.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import it.unitn.lpsmt2020.portalesimulatoregare.R;
import it.unitn.lpsmt2020.portalesimulatoregare.datasource.DataSource;
import it.unitn.lpsmt2020.portalesimulatoregare.datasource.InternalDB;
import it.unitn.lpsmt2020.portalesimulatoregare.event.SubscriptionChangedEvent;
import it.unitn.lpsmt2020.portalesimulatoregare.models.ChampionshipItem;
import it.unitn.lpsmt2020.portalesimulatoregare.ui.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 */
public class ChampionshipListFragment extends Fragment {

    private final MutableLiveData<List<ChampionshipItem>> championshipList = new MutableLiveData<List<ChampionshipItem>>();

    // TODO: Customize parameter argument names
    private static final String ARG_ONLY_SUBBED = "only-subbed";
    // TODO: Customize parameters
    private boolean onlySubbed = false;
    private RecyclerView recyclerView;
    private EventBus bus = EventBus.getDefault();


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChampionshipListFragment() {
    }

    // TODO: Customize parameter initialization
    public static ChampionshipListFragment newInstance(boolean onlySubbed) {
        ChampionshipListFragment fragment = new ChampionshipListFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_ONLY_SUBBED, onlySubbed);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);

        if (getArguments() != null) {
            this.onlySubbed = getArguments().getBoolean(ARG_ONLY_SUBBED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_championship_list, container, false);
        Context context = view.getContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.list);;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        championshipList.observe(getViewLifecycleOwner(), new Observer<List<ChampionshipItem>>() {
            @Override
            public void onChanged(@Nullable List<ChampionshipItem> chamList) {

                if (onlySubbed)
                    chamList = chamList.stream().filter(c -> c.isSubscribed()).collect(Collectors.toList());

                recyclerView.setAdapter(new ChampionshipListRecyclerViewAdapter(chamList, onlySubbed));
                ((ProgressBar) view.findViewById(R.id.listLoading)).setVisibility(View.GONE);
            }
        });
        DataSource.getSubscribedChampionship(championshipList);

        return view;
    }

    @Subscribe
    public void onUpdateSubbed(SubscriptionChangedEvent event) {
        int id = event.item.getId();

        int foundAt = -1;
        for (int i = 0; foundAt == -1 && i < recyclerView.getChildCount(); i++) {
            ChampionshipListRecyclerViewAdapter.ViewHolder vh = (ChampionshipListRecyclerViewAdapter.ViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
            if (vh.item.getId() == id)
                foundAt = i;
        }

        if (foundAt >= 0) {
            if (!onlySubbed)
                ((ChampionshipListRecyclerViewAdapter.ViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(foundAt))).checkSubStatus();
            else
                ((ChampionshipListRecyclerViewAdapter) recyclerView.getAdapter()).removeAtIndex(foundAt);
        } else if (onlySubbed) {
            ((ChampionshipListRecyclerViewAdapter) recyclerView.getAdapter()).addItem(event.item);
        }

    }
}