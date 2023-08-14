package com.example.admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.admin.Model.Fixture;
import com.example.admin.Model.League;
import com.example.admin.Model.Team;
import com.example.admin.adapter.LeagueAdapter;
import com.example.admin.adapter.TeamSpinnerAdapter;
import com.example.admin.helpers.Helpers;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddFixtureActivity extends AppCompatActivity {
    private Calendar calendar;
    private TextInputEditText timeField, dateField;
    private TextInputLayout timeContainer, dateContainer, leagueContaimer, homeTeamContainer, awayTeamContainer;
    private Spinner leagueSpinner, homeTeamSpinner, awayTeamSpinner;
    private Button saveBtn;
    private ArrayList<League> leagues;
    private ArrayList<Team> homeTeams;
    private ArrayList<Team> allTeams;
    private ArrayList<Team> awayTeams;
    private LeagueAdapter leagueAdapter;
    private TeamSpinnerAdapter homeTeamAdapter, awayTeamAdapter;
    private FirebaseFirestore db;

    protected void onCreate(Bundle savedInstanceState) {
        calendar = Calendar.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fixture);
        db = FirebaseFirestore.getInstance();
        ImageView backNav = findViewById(R.id.back_nav);
        backNav.setOnClickListener(e -> {
            finish();
        });
        timeField = findViewById(R.id.fixture_time_field);
        dateField = findViewById(R.id.fixture_date_field);
        timeField.setOnClickListener(v -> {
            showTimePicker();
        });
        dateField.setOnClickListener(v -> {
            showDatePickerDialog();
        });
        leagues = new ArrayList<>();
        homeTeams = new ArrayList<>();
        awayTeams = new ArrayList<>();
        allTeams=new ArrayList<>();
        leagueSpinner = findViewById(R.id.spinner_league);
        homeTeamSpinner = findViewById(R.id.spinner_home);
        awayTeamSpinner = findViewById(R.id.spinner_away);
        leagueAdapter = new LeagueAdapter(this, leagues);
        homeTeamAdapter = new TeamSpinnerAdapter(this, homeTeams);
        awayTeamAdapter = new TeamSpinnerAdapter(this, awayTeams);
        leagueSpinner.setAdapter(leagueAdapter);
        homeTeamSpinner.setAdapter(homeTeamAdapter);
        awayTeamSpinner.setAdapter(awayTeamAdapter);
        leagueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                League item = (League) parent.getItemAtPosition(position);
                reloadList(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        homeTeamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Team item = (Team) parent.getItemAtPosition(position);
                awayTeams.clear();
                awayTeams.addAll(allTeams);
                awayTeams.remove(item);
                awayTeamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        awayTeamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Team item = (Team) parent.getItemAtPosition(position);
                homeTeams.clear();
                homeTeams.addAll(allTeams);
                homeTeams.remove(item);
                homeTeamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        initList();
        saveBtn = findViewById(R.id.save_fixture);
        timeContainer=findViewById(R.id.fixture_time_container);
        dateContainer=findViewById(R.id.fixture_date_container);
        leagueContaimer=findViewById(R.id.league_select_container);
        homeTeamContainer=findViewById(R.id.home_select_container);
        awayTeamContainer=findViewById(R.id.away_select_container);

        saveBtn.setOnClickListener(v -> {
            validateAndSave();
        });
    }

    private void validateAndSave() {
        boolean isvalid = true;
        timeContainer.setError(null);
        dateContainer.setError(null);
        leagueContaimer.setError(null);
        homeTeamContainer.setError(null);
        awayTeamContainer.setError(null);

        if(leagueSpinner.getSelectedItem()==null){
            isvalid=false;
            leagueContaimer.setError("Please Select A League");
        }
        if(homeTeamSpinner.getSelectedItem()==null){
            isvalid=false;
            homeTeamContainer.setError("Please Select A Team");
        }
        if(awayTeamSpinner.getSelectedItem()==null){
            isvalid=false;
            awayTeamContainer.setError("Please Select A Team");
        }
        if(timeField.getText().toString().isEmpty()){
            isvalid=false;
            timeContainer.setError("Please Select A Time");
        }
        if(dateField.getText().toString().isEmpty()){
            isvalid=false;
            dateContainer.setError("Please Select A Date");
        }
        if(isvalid){
            Fixture fixture = Fixture.builder().
                    league((League) leagueSpinner.getSelectedItem()).
                    homeTeam((Team) homeTeamSpinner.getSelectedItem()).
                    awayTeam((Team) awayTeamSpinner.getSelectedItem()).date(formatDateTime(calendar))
            .build();
            saveToFirestore(fixture);
        }
    }

    private void saveToFirestore(Fixture fixture) {
        db.collection("fixtures").add(fixture).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Helpers.showPopupWindow(this,"Fixture Saved",2000, Color.GREEN,Color.WHITE);

            }
        });
    }


    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        updateDateText();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        updateTimeText();
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void updateDateText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());
        dateField.setText(formattedDate);
    }

    private void updateTimeText() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formattedTime = timeFormat.format(calendar.getTime());
        timeField.setText(formattedTime);
    }

    private void initList() {

        db.collection("leagues").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (!snapshot.isEmpty()) {
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        League league = documentSnapshot.toObject(League.class);
                        leagues.add(league);
                        leagueAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void reloadList(League league) {
        db.collection("teams").whereArrayContains("leagues", league).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (!snapshot.isEmpty()) {
                    homeTeams.clear();
                    awayTeams.clear();
                    allTeams.clear();
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        Team team = documentSnapshot.toObject(Team.class);
                        allTeams.add(team);
                        homeTeams.add(team);
                        awayTeams.add(team);
                        homeTeamAdapter.notifyDataSetChanged();
                        awayTeamAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private String formatDateTime(Calendar calendar) {
        SimpleDateFormat customFormat = new SimpleDateFormat("d MMM HH:mm", Locale.getDefault());
        return customFormat.format(calendar.getTime());
    }
}

