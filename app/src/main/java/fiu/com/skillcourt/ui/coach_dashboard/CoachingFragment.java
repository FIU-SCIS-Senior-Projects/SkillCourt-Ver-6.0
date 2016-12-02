package fiu.com.skillcourt.ui.coach_dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.ui.LauncherActivity;
import fiu.com.skillcourt.ui.base.BaseFragment;
import fiu.com.skillcourt.ui.dynamicsteps.DynamicStepsActivity;
import fiu.com.skillcourt.ui.new_team.NewTeam;
import fiu.com.skillcourt.ui.startgame.StartGameActivity;
import fiu.com.skillcourt.ui.team_details.TeamDetailsActivity;

public class CoachingFragment extends BaseFragment {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mTeamRef = mRootRef.child("users").child(user.getUid()).child("teams");
    List<Team> teamList = new ArrayList<>();


    public static CoachingFragment newInstance() {
        return new CoachingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_coaching, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView lv_teams = (ListView) getView().findViewById(R.id.teams_list);

        mTeamRef.addValueEventListener(
                new ValueEventListener() {
                    public void onDataChange(DataSnapshot snapshot) {
                        teamList.clear();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Team team = postSnapshot.getValue(Team.class);
                            team.setId(postSnapshot.getKey());
                            teamList.add(team);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        /*
        mTeamRef.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Team team = dataSnapshot.getValue(Team.class);
                        teamList.add(team);
                        Log.i("OJO", "add team name = " + team.getName());
                        Log.i("OJO", "add team desc = " + team.getDescription());
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
                */

        ArrayAdapter<Team> arrayAdapter = new ArrayAdapter<Team>(getContext(), android.R.layout.simple_list_item_1, teamList);

        lv_teams.setAdapter(arrayAdapter);

        lv_teams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                String selectedTeamID = teamList.get(position).getId();
                Intent intent = new Intent(getActivity(), TeamDetailsActivity.class);
                intent.putExtra("teamID",selectedTeamID);
                startActivity(intent);

            }
        });


        FloatingActionButton myFab = (FloatingActionButton) getView().findViewById(R.id.floatingActionButton3);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewTeam.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LauncherActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    static class Team {

        String id;
        String name;
        String description;

        public Team() {
        }

        public Team(String id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        public String getId(){return id;}

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString(){
            return this.getName();
        }
    }

}
