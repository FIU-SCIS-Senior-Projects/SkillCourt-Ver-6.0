package fiu.com.skillcourt.ui.dynamicsteps;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.manager.StepManager;
import fiu.com.skillcourt.ui.custom.Step;
import fiu.com.skillcourt.ui.dashboard.MainDashboardActivity;
import fiu.com.skillcourt.ui.startgame.StartGameActivity;
import pedrocarrillo.com.materialstepperlibrary.StepLayout;
import pedrocarrillo.com.materialstepperlibrary.interfaces.StepLayoutResult;

/**
 * A placeholder fragment containing a simple view.
 */
public class DynamicStepsFragment extends Fragment implements View.OnClickListener {

    private StepLayout stepLayout;
    private Button btnSave;
    protected FirebaseAuth mAuth;
    protected EditText seq_Name;
    protected FirebaseAuth.AuthStateListener mAuthListener;

    public static DynamicStepsFragment newInstance() {
        return new DynamicStepsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_dynamic_steps, container, false);
        btnSave = (Button) view.findViewById(R.id.btn_save) ;
        seq_Name = (EditText) view.findViewById(R.id.seq_name);
        btnSave.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stepLayout = (StepLayout) view.findViewById(R.id.stepLayout);

        stepLayout.setStepLayoutResult(new StepLayoutResult() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onCancel() {

            }
        });

        Step step1 = new Step(getContext());
        Step step2 = new Step(getContext());
        Step step3 = new Step(getContext());
        Step step4 = new Step(getContext());
        Step step5 = new Step(getContext());

        stepLayout.addStepView(step1);
        stepLayout.addStepView(step2);
        stepLayout.addStepView(step3);
        stepLayout.addStepView(step4);
        stepLayout.addStepView(step5);
        stepLayout.load();

    }
    @Override
    public void onClick(View v) {
        if( seq_Name.getText().toString().trim().equals("")){
            seq_Name.setError( "Sequence Name is required!" );
        }else{
            mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("sequences");
            //mAuth = FirebaseAuth.getInstance();
            SparseIntArray steps = StepManager.getInstance().Steps();
            HashMap<String, String> sequence = new HashMap<String, String>();
            FirebaseUser user = mAuth.getCurrentUser();
            DatabaseReference userRef = myRef.child(user.getUid());
            //sequence.put("name", seq_Name.getText().toString().trim());

            for (int i = 0; i < steps.size(); i++) {
                int stepNumber = steps.keyAt(i);
                int stepValue = steps.get(stepNumber);
                sequence.put(new Integer(stepNumber).toString(), new Integer(stepValue).toString());
            }
            userRef.child(seq_Name.getText().toString().trim()).setValue(sequence);
            //Intent i=new Intent(MainActivity.this, MainActivity2.class);
            Intent intent = new Intent(getActivity(), MainDashboardActivity.class);
            startActivity(intent);
        }
        }
    }

}
