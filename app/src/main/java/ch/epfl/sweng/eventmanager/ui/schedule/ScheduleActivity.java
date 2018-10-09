package ch.epfl.sweng.eventmanager.ui.schedule;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.sweng.eventmanager.R;
import ch.epfl.sweng.eventmanager.repository.data.Concert;

public class ScheduleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TimeLineAdapter timeLineAdapter;
    private List<Concert> concertsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //
        // Get the concert List for the event into concertsList
        //
        concertsList = new LinkedList<>();
        // Above is temporary

        // Temp fake for visual example
        concertsList.add(new Concert(new Date(2018, 11, 15, 23, 30),
                "David Guetta", "Electro/ Dance",
                "Incredible stage performance by famous DJ David Guetta!", 1.5));
        concertsList.add(new Concert(new Date(2018, 11, 15, 21, 15),
                "ABBA", "Rock",
                "Wow! This is the great comeback of the well-known success group!", 2));

        timeLineAdapter = new TimeLineAdapter(concertsList);
        recyclerView.setAdapter(timeLineAdapter);

    }


}

