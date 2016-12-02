package fiu.com.skillcourt.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.ui.LauncherActivity;
import fiu.com.skillcourt.ui.base.BaseFragment;
import fiu.com.skillcourt.ui.coach_dashboard.CoachingDashboardActivity;
import fiu.com.skillcourt.ui.dynamicsteps.DynamicStepsActivity;
import fiu.com.skillcourt.ui.startgame.StartGameActivity;

public class MainDashboardFragment extends BaseFragment {

    Button addCoachingFeats;
    Button coachingFeats;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRoleRef = mRootRef.child("users").child(user.getUid()).child("role");

    public static MainDashboardFragment newInstance() {
        return new MainDashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_main_dashboard, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addCoachingFeats = (Button) getView().findViewById(R.id.addCoaching);
        coachingFeats = (Button) getView().findViewById(R.id.coaching_features);

        String email = user.getEmail();
        if (email != null) {
            TextView tvWelcome = (TextView) getView().findViewById(R.id.tv_welcome);
            tvWelcome.setText("Welcome " + email);
        }
        Button startCustomSteps = (Button) getView().findViewById(R.id.start_custom_steps);
        startCustomSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DynamicStepsActivity.class);
                startActivity(intent);
            }
        });
        Button startGame = (Button) getView().findViewById(R.id.start_game);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StartGameActivity.class);
                startActivity(intent);
            }
        });

        addCoachingFeats.setOnClickListener(new View.OnClickListener() {

            //ADD ALERT VIEW
            @Override
            public void onClick(View v) {

                Context context = getActivity();

                AlertDialog ad = new AlertDialog.Builder(context)
                        .create();
                ad.setCancelable(false);
                ad.setTitle("Do you want to be a coach?");
                ad.setMessage("You will have access to create teams, look for other users, etc.");
                ad.setButton(context.getString(R.string.ok_text), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        mRoleRef.setValue("coach");
                        dialog.dismiss();
                    }
                });
                ad.show();



            }
        });

        coachingFeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CoachingDashboardActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        mRoleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userRole = dataSnapshot.getValue(String.class);

                if (userRole.equals("coach")) {
                    coachingFeats.setVisibility(View.VISIBLE);
                    addCoachingFeats.setVisibility(View.INVISIBLE);
                } else {
                    coachingFeats.setVisibility(View.INVISIBLE);
                    addCoachingFeats.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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


}
