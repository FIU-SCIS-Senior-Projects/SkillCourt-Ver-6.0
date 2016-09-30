package fiu.com.skillcourt.ui.dynamicsteps;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.ui.custom.Step;
import pedrocarrillo.com.materialstepperlibrary.StepLayout;
import pedrocarrillo.com.materialstepperlibrary.interfaces.StepLayoutResult;

/**
 * A placeholder fragment containing a simple view.
 */
public class DynamicStepsFragment extends Fragment implements View.OnClickListener {

    private StepLayout stepLayout;
    private Button btnSave;
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;

    public static DynamicStepsFragment newInstance() {
        return new DynamicStepsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dynamic_steps, container, false);
        btnSave = (Button) view.findViewById(R.id.btn_save) ;
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
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // successfulLogin();
                } else {
                    //notLoggedIn();
                }
            }
        };
    }
}
