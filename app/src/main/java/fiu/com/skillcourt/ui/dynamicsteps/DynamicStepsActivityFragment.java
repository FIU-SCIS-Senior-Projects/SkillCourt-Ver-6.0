package fiu.com.skillcourt.ui.dynamicsteps;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.ui.dashboard.MainDashboardFragment;
import pedrocarrillo.com.materialstepperlibrary.StepLayout;
import pedrocarrillo.com.materialstepperlibrary.interfaces.StepLayoutResult;

/**
 * A placeholder fragment containing a simple view.
 */
public class DynamicStepsActivityFragment extends Fragment {

    private StepLayout stepLayout;

    public static DynamicStepsActivityFragment newInstance() {
        return new DynamicStepsActivityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dynamic_steps, container, false);
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
}
