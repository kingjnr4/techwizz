package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.FixtureAdapter;
import com.example.myapplication.model.Fixture;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home, container, false);


        List<Fixture> fixtureList = FixtureDataGenerator.generateSampleFixtures().subList(0,3);


        FixtureAdapter adapter = new FixtureAdapter(view.getContext(), fixtureList);



        RecyclerView recyclerView = view.findViewById(R.id.todays_fixture_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);





        return view;
    }



    public class FixtureDataGenerator {
        public static List<Fixture> generateSampleFixtures() {
            List<Fixture> fixtures = new ArrayList<>();

            Fixture fixture1 = new Fixture();
            fixture1.setHome("Manchester United");
            fixture1.setAway("Liverpool");
            fixture1.setDate("2023-08-15 15:00"); // Set date and time
            fixtures.add(fixture1);

            Fixture fixture2 = new Fixture();
            fixture2.setHome("Chelsea");
            fixture2.setAway("Arsenal");
            fixture2.setDate("2023-08-16 18:30"); // Set date and time
            fixtures.add(fixture2);

            Fixture fixture3 = new Fixture();
            fixture3.setHome("Tottenham Hotspur");
            fixture3.setAway("Manchester City");
            fixture3.setDate("2023-08-17 14:45"); // Set date and time
            fixtures.add(fixture3);

            Fixture fixture4 = new Fixture();
            fixture4.setHome("Leicester City");
            fixture4.setAway("Everton");
            fixture4.setDate("2023-08-18 16:30"); // Set date and time
            fixtures.add(fixture4);

            Fixture fixture5 = new Fixture();
            fixture5.setHome("West Ham United");
            fixture5.setAway("Southampton");
            fixture5.setDate("2023-08-19 12:00"); // Set date and time
            fixtures.add(fixture5);

            Fixture fixture6 = new Fixture();
            fixture6.setHome("Newcastle United");
            fixture6.setAway("Crystal Palace");
            fixture6.setDate("2023-08-19 15:30"); // Set date and time
            fixtures.add(fixture6);

            Fixture fixture7 = new Fixture();
            fixture7.setHome("Aston Villa");
            fixture7.setAway("Wolverhampton");
            fixture7.setDate("2023-08-20 14:00"); // Set date and time
            fixtures.add(fixture7);

            Fixture fixture8 = new Fixture();
            fixture8.setHome("Leeds United");
            fixture8.setAway("Burnley");
            fixture8.setDate("2023-08-20 17:30"); // Set date and time
            fixtures.add(fixture8);

            Fixture fixture9 = new Fixture();
            fixture9.setHome("Brighton & Hove Albion");
            fixture9.setAway("Norwich City");
            fixture9.setDate("2023-08-21 13:15"); // Set date and time
            fixtures.add(fixture9);

            Fixture fixture10 = new Fixture();
            fixture10.setHome("Watford");
            fixture10.setAway("Fulham");
            fixture10.setDate("2023-08-21 16:00"); // Set date and time
            fixtures.add(fixture10);

            return fixtures;
        }

        // Rest of the class remains the same...
    }


}
