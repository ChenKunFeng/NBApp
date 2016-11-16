package com.gzfgeh.nbapp.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gzfgeh.nbapp.Common.Contants;
import com.gzfgeh.nbapp.Fragment.FuLiFragment;
import com.gzfgeh.nbapp.Fragment.HomeFragment;
import com.gzfgeh.nbapp.Fragment.MyFragment;
import com.gzfgeh.nbapp.R;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private ArrayList<Fragment> fragments;
    private String[] strings;
    private BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        setSwipeBackEnable(false);

        strings = getResources().getStringArray(R.array.bottomArray);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar_container);
        bottomNavigationBar.setAutoHideEnabled(true);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);

        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor(R.color.container_background);
        bottomNavigationBar.setInActiveColor(R.color.nav_gray);
        bottomNavigationBar.setActiveColor(R.color.colorPrimary);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.home, strings[0]))
                .addItem(new BottomNavigationItem(R.drawable.besttrade_a, strings[1]))
                .addItem(new BottomNavigationItem(R.drawable.consult_a, strings[2]))
                .addItem(new BottomNavigationItem(R.drawable.my_a, strings[3]));
        if(savedInstanceState == null) {
            bottomNavigationBar.setFirstSelectedPosition(0)
                    .initialise();
            setDefaultFragment();
        }else{
            bottomNavigationBar.setFirstSelectedPosition(savedInstanceState.getInt(Contants.BOTTOM_BAR_INDEX))
                    .initialise();
        }

        fragments = getFragments();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.layFrame,  HomeFragment.newInstance(strings[0]));
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance(strings[0]));
        fragments.add(FuLiFragment.newInstance(strings[1]));
        fragments.add(MyFragment.newInstance(strings[2]));
        fragments.add(MyFragment.newInstance(strings[3]));
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                if (fragment.isHidden()) {
                    ft.show(fragment);
                } else {
                    ft.add(R.id.layFrame, fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.hide(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Contants.BOTTOM_BAR_INDEX, bottomNavigationBar.getCurrentSelectedPosition());
    }
}
