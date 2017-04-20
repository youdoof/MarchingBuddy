package edu.truman.amb1664.marchingbuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Andrew Brogan
 * @since 3/21/17
 */
public class MainActivity extends AppCompatActivity {
    private static final String BACK_STACK_ROOT = "root_fragment";
    private MarchingDot start_dot;
    private MarchingDot end_dot;
    private int counts;

    public MarchingDot getStart_dot() {
        return start_dot;
    }

    public void setStart_dot(MarchingDot start_dot) {
        this.start_dot = start_dot;
    }

    public MarchingDot getEnd_dot() {
        return end_dot;
    }

    public void setEnd_dot(MarchingDot end_dot) {
        this.end_dot = end_dot;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }


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

    private void moveToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * @return integer representing current field type setting
     */
    public int readFieldType() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String fieldType = preferences.getString("field_type", "-1");
        return Integer.parseInt(fieldType);
    }

    /**
     * @return integer representing current hash type setting
     */
    public int readHashType() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String hashType = preferences.getString("hash_type", "-1");
        return Integer.parseInt(hashType);
    }

    /**
     * @return integer representing current side type setting
     */
    public int readSideType() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sideType = preferences.getString("side_type", "-1");
        return Integer.parseInt(sideType);
    }

    /**
     * @return integer representing current specificity setting
     */
    public int readSpecificity() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String specificity = preferences.getString("specificity", "-1");
        return Integer.parseInt(specificity);
    }

    /**
     * @param fragment to initialize the back stack start point
     */
    private void initializeBackStack(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(BACK_STACK_ROOT, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(BACK_STACK_ROOT).commit();
    }

    /**
     * @param fragment to add to back stack
     */
    private void addFragmentOnTop(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    /**
     * @param x 1 = StartFragment initialize, 2 = EndFragment, 3 = ReviewFragment, 4 = ResultFragment, 5 = EndFragment initialize
     */
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
