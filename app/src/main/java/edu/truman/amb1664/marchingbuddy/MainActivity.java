package edu.truman.amb1664.marchingbuddy;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static final String BACK_STACK_ROOT = "root_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
        }

        StartFragment startFragment = new StartFragment();
        startFragment.setArguments(getIntent().getExtras());
        initializeBackStack(startFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        moveToSettings();
        return super.onOptionsItemSelected(item);
    }

    public void moveToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void initializeBackStack(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(BACK_STACK_ROOT, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(BACK_STACK_ROOT).commit();
    }

    public void addFragmentOnTop(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    public void replaceFragment(int x) {
        switch (x) {
            case 1:
                StartFragment startFragment = new StartFragment();
                initializeBackStack(startFragment);
                break;
            case 2:
                EndFragment endFragment = new EndFragment();
                addFragmentOnTop(endFragment);
                break;
            case 3:
                ReviewFragment reviewFragment = new ReviewFragment();
                addFragmentOnTop(reviewFragment);
                break;
            case 4:
                ResultFragment resultFragment = new ResultFragment();
                addFragmentOnTop(resultFragment);
                break;
            case 5:
                EndFragment endFragment1 = new EndFragment();
                initializeBackStack(endFragment1);
                break;
            default:
                break;
        }
    }
}
