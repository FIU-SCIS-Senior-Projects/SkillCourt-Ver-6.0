package fiu.com.skillcourt.ui.dashboard;

import android.os.Bundle;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.ui.base.BaseActivity;

public class MainDashboardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            replaceFragment(MainDashboardFragment.newInstance(), false);
        }
        setToolbar();
    }


}
