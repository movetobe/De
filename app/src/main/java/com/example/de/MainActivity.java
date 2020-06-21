package com.example.de;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.de.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        final TextView textView = findViewById(R.id.title);
        textView.setText(new SimpleDateFormat("yyyy年MM月dd日").format(new Date(System.currentTimeMillis())));

        //指定Tab的位置
        TabLayout.Tab one = tabs.getTabAt(0);
        TabLayout.Tab two = tabs.getTabAt(1);
        TabLayout.Tab three = tabs.getTabAt(2);
        TabLayout.Tab four = tabs.getTabAt(3);

        //设置Tab的图标，假如不需要则把下面的代码删去
        one.setIcon(R.mipmap.day);
        two.setIcon(R.mipmap.week_nor);
        three.setIcon(R.mipmap.month_nor);
        four.setIcon(R.mipmap.year_nor);

        final TextView score_view = findViewById(R.id.score);

        SharedPreferences sharedPreferences = getSharedPreferences("De", Context.MODE_PRIVATE);
        String string = sharedPreferences.getString("score", "0");
        score_view.setText(String.valueOf(string));

        ImageButton plus_button = findViewById(R.id.plus);
        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score_value = Integer.valueOf(String.valueOf(score_view.getText()));
                score_value++;
                score_view.setText(String.valueOf(score_value));

                SharedPreferences sharedPreferences = getSharedPreferences("De", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("score", String.valueOf(score_value));
                editor.commit();
            }
        });
        ImageButton minus_button = findViewById(R.id.minus);
        minus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score_value = Integer.valueOf(String.valueOf(score_view.getText()));
                score_value--;
                score_view.setText(String.valueOf(score_value));

                SharedPreferences sharedPreferences = getSharedPreferences("De", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("score", String.valueOf(score_value));
                editor.commit();
            }
        });

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.mipmap.day);
                        textView.setText(new SimpleDateFormat("yyyy年MM月dd日").format(new Date(System.currentTimeMillis())));
                        break;
                    case 1:
                        tab.setIcon(R.mipmap.week);
                        textView.setText(new SimpleDateFormat("yyyy年MM月第W周").format(new Date(System.currentTimeMillis())));
                        break;
                    case 2:
                        tab.setIcon(R.mipmap.month);
                        textView.setText(new SimpleDateFormat("yyyy年MM月").format(new Date(System.currentTimeMillis())));
                        break;
                    case 3:
                        tab.setIcon(R.mipmap.year);
                        textView.setText(new SimpleDateFormat("yyyy年").format(new Date(System.currentTimeMillis())));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.mipmap.day_nor);
                        break;
                    case 1:
                        tab.setIcon(R.mipmap.week_nor);
                        break;
                    case 2:
                        tab.setIcon(R.mipmap.month_nor);
                        break;
                    case 3:
                        tab.setIcon(R.mipmap.year_nor);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}