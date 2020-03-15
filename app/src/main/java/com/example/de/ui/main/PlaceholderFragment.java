package com.example.de.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.de.FocusActivity;
import com.example.de.PersonalTodoActivity;
import com.example.de.R;
import com.example.de.TimeLineActivity;
import com.example.de.WorkTodoActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    private static String[] tables = {"time_line", "focus", "personal_todo", "work_todo"};
    private static int[] requests = {0, 1, 2, 3};

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void recover(PageViewModel p, int key) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("De", Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(tables[key] + getArguments().getInt(ARG_SECTION_NUMBER), "");
        p.setmString(string, key);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        /* get data from storage and recover them */
        for (int i = 0; i < tables.length; i++) {
            recover(pageViewModel, i);
        }
    }
    private void store(String key, String str) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("De", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key + getArguments().getInt(ARG_SECTION_NUMBER), str);
        editor.commit();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (int i = 0; i < tables.length; i++) {
            String s = data.getStringExtra(tables[i]);
            store(tables[i], s);
            pageViewModel.setmString(s, i);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        TextView v1 = root.findViewById(R.id.time_line);
        TextView v2 = root.findViewById(R.id.focus);
        TextView v3 = root.findViewById(R.id.personal_todo);
        TextView v4 = root.findViewById(R.id.work_todo);

        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        switch (index) {
            case 1:
                v1.setText("今日时间线");
                v2.setText("今日聚焦");
                v3.setText("今日个人待办");
                v4.setText("今日工作待办");
                break;
            case 2:
                v1.setText("本周任务清单");
                v2.setText("本周聚焦");
                v3.setText("本周个人待办");
                v4.setText("本周工作待办");
                break;
            case 3:
                v1.setText("本月任务清单");
                v2.setText("本月聚焦");
                v3.setText("本月个人待办");
                v4.setText("本月工作待办");
                break;
            case 4:
                v1.setText("今年任务清单");
                v2.setText("今年聚焦");
                v3.setText("今年个人待办");
                v4.setText("今年工作待办");
                break;
            default:
                break;
        }

        final TextView textView1 = root.findViewById(R.id.section_label1);
        pageViewModel.getmText(0).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView1.setText(s);
            }
        });
        final TextView textView2 = root.findViewById(R.id.section_label2);
        pageViewModel.getmText(1).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView2.setText(s);
            }
        });
        final TextView textView3 = root.findViewById(R.id.section_label3);
        pageViewModel.getmText(2).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView3.setText(s);
            }
        });
        final TextView textView4 = root.findViewById(R.id.section_label4);
        pageViewModel.getmText(3).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView4.setText(s);
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TimeLineActivity.class);
                intent.putExtra(tables[0], textView1.getText().toString());
                startActivityForResult(intent, requests[0]);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FocusActivity.class);
                intent.putExtra(tables[1], textView2.getText().toString());
                startActivityForResult(intent, requests[1]);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalTodoActivity.class);
                intent.putExtra(tables[2], textView3.getText().toString());
                startActivityForResult(intent, requests[2]);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WorkTodoActivity.class);
                intent.putExtra(tables[3], textView4.getText().toString());
                startActivityForResult(intent, requests[3]);
            }
        });
        return root;
    }
}