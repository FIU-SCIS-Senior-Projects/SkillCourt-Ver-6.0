package fiu.com.skillcourt.ui.dynamicsteps;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.ui.base.BaseActivity;
import fiu.com.skillcourt.ui.dashboard.MainDashboardFragment;

public class DynamicStepsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            replaceFragment(DynamicStepsActivityFragment.newInstance(), false);
        }
        setNavigationToolbar();
    }

}
