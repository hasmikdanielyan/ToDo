package com.example.todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private com.google.android.material.tabs.TabLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();

    }

    private void initViewPager() {
        pager = findViewById(R.id.viewpager);
        tableLayout = findViewById(R.id.tabs);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());

        Fragment fragmentOne = new FragmentOne();
        Fragment fragmentTwo = new FragmentTwo();
        Fragment fragmentThree = new FragmentThree();

        adapter.addFragment(fragmentOne, "All");
        adapter.addFragment(fragmentTwo, "ToDo");
        adapter.addFragment(fragmentThree, "Done");


        pager.setAdapter(adapter);
        tableLayout.setupWithViewPager(pager);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
