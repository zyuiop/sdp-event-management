package ch.epfl.sweng.eventmanager.ui.eventSelector;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ch.epfl.sweng.eventmanager.R;
import ch.epfl.sweng.eventmanager.viewmodel.ViewModelFactory;
import dagger.android.AndroidInjection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;

public class EventPickingActivity extends AppCompatActivity {
    private EventPickingModel model;
    public static final String SELECTED_EVENT_ID = "ch.epfl.sweng.SELECTED_EVENT_ID";
    @Inject
    ViewModelFactory factory;

    private TextView joinedHelpText;
    private TextView notJoinedHelpText;
    private RecyclerView joinedEvents;
    private RecyclerView notJoinedEvents;

    private void setupRecyclerView(RecyclerView view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        view.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                view.getContext(),
                layoutManager.getOrientation()
        );
        view.addItemDecoration(dividerItemDecoration);

        // Set an empty list adapter
        view.setAdapter(new EventListAdapter(Collections.emptyList()));
    }

    private void setupObservers() {
        this.model = ViewModelProviders.of(this, factory).get(EventPickingModel.class);
        this.model.init();
        this.model.getEventsPair().observe(this, list -> {
            if (list == null) {
                return;
            }

            notJoinedEvents.setAdapter(new EventListAdapter(list.getOtherEvents()));
            joinedEvents.setAdapter(new EventListAdapter(list.getJoinedEvents()));

            if (!list.getJoinedEvents().isEmpty()) {
                joinedHelpText.setVisibility(View.VISIBLE);
                notJoinedHelpText.setVisibility(View.VISIBLE);
            } else {
                joinedHelpText.setVisibility(View.INVISIBLE);
                notJoinedHelpText.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_picking);

        // Help text
        // Both invisible by default
        joinedHelpText = (TextView) findViewById(R.id.joined_help_text);
        joinedHelpText.setVisibility(View.INVISIBLE);

        notJoinedHelpText = (TextView) findViewById(R.id.not_joined_help_text);
        notJoinedHelpText.setVisibility(View.INVISIBLE);

        // Event lists
        notJoinedEvents = (RecyclerView) findViewById(R.id.event_list);
        joinedEvents = (RecyclerView) findViewById(R.id.joined_events_list);

        setupRecyclerView(notJoinedEvents);
        setupRecyclerView(joinedEvents);

        setupObservers();
    }
}
