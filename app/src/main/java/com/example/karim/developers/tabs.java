package com.example.karim.developers;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class tabs extends AppCompatActivity {

    private SectionPageAdapter sectionPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        mViewPager= (ViewPager) findViewById(R.id.container);
        setupPageAdapter(mViewPager);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }
     private void setupPageAdapter(ViewPager viewPager){
         SectionPageAdapter adapter=new SectionPageAdapter(getSupportFragmentManager());
         adapter.addFragment(new frag1(),"main page");
         adapter.addFragment(new frag2(),"downloaded apps");
         adapter.addFragment(new frag2(),"my apps");
         viewPager.setAdapter(adapter);
     }


}
