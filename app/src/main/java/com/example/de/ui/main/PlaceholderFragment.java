package com.example.de.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.de.FocusActivity;
import com.example.de.MainActivity;
import com.example.de.PersonalTodoActivity;
import com.example.de.R;
import com.example.de.TimeLineActivity;
import com.example.de.WorkTodoActivity;
import com.example.de.WriteActivity;

import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    private static String[] tables = {"time_line", "focus", "personal_todo", "work_todo"};
    private static String[][] titles = {{"今日时间线", "今日聚焦", "今日个人待办", "今日工作待办"},
                                        {"本周任务清单", "本周聚焦", "本周个人待办", "本周工作待办"},
                                        {"本月任务清单", "本月聚焦", "本月个人待办", "本月工作待办"},
                                        {"今年任务清单", "今年聚焦", "今年个人待办", "今年工作待办"}};
    private static int[] requests = {0, 1, 2, 3};
    private static String section_title = "section_title";

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
            if (requestCode == requests[i] && resultCode == RESULT_OK) {
                String s = data.getStringExtra(tables[i]);
                store(tables[i], s);
                pageViewModel.setmString(s, i);
            }
        }
    }

    private void store_title(String key, String str) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("De", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key + "_title_" + getArguments().getInt(ARG_SECTION_NUMBER), str);
        editor.commit();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        String str[] = new String[4];
        final TextView v1 = root.findViewById(R.id.time_line);
        final TextView v2 = root.findViewById(R.id.focus);
        final TextView v3 = root.findViewById(R.id.personal_todo);
        final TextView v4 = root.findViewById(R.id.work_todo);

        final int index = getArguments().getInt(ARG_SECTION_NUMBER);
        switch (index) {
            case 1:
                str[0] = "今日时间线";
                str[1] = "今日聚焦";
                str[2] = "今日个人待办";
                str[3] = "今日工作待办";
                break;
            case 2:
                str[0] = "本周任务清单";
                str[1] = "本周聚焦";
                str[2] = "本周个人待办";
                str[3] = "本周工作待办";
                break;
            case 3:
                str[0] = "本月任务清单";
                str[1] = "本月聚焦";
                str[2] = "本月个人待办";
                str[3] = "本月工作待办";
                break;
            case 4:
                str[0] = "今年任务清单";
                str[1] = "今年聚焦";
                str[2] = "今年个人待办";
                str[3] = "今年工作待办";
                break;
            default:
                break;
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("De", Context.MODE_PRIVATE);
        String stringTitle[] = new String[4];
        stringTitle[0] = sharedPreferences.getString(tables[0] + "_title_" + getArguments().getInt(ARG_SECTION_NUMBER), str[0]);
        stringTitle[1] = sharedPreferences.getString(tables[1] + "_title_" + getArguments().getInt(ARG_SECTION_NUMBER), str[1]);
        stringTitle[2] = sharedPreferences.getString(tables[2] + "_title_" + getArguments().getInt(ARG_SECTION_NUMBER), str[2]);
        stringTitle[3] = sharedPreferences.getString(tables[3] + "_title_" + getArguments().getInt(ARG_SECTION_NUMBER), str[3]);
        v1.setText(stringTitle[0]);
        v2.setText(stringTitle[1]);
        v3.setText(stringTitle[2]);
        v4.setText(stringTitle[3]);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        v1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                builder.setTitle("自定义标题");
                final EditText input = new EditText(getContext());
                input.setText(v1.getText().toString());
                builder.setView(input);
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                v1.setText(input.getText().toString());
                                store_title(tables[0], v1.getText().toString());
                            }
                        });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.create().show();
            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                builder.setTitle("自定义标题");
                final EditText input = new EditText(getContext());
                input.setText(v2.getText().toString());
                builder.setView(input);
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                v2.setText(input.getText().toString());
                                store_title(tables[1], v2.getText().toString());
                            }
                        });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.create().show();
            }
        });
        v3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                builder.setTitle("自定义标题");
                final EditText input = new EditText(getContext());
                input.setText(v3.getText().toString());
                builder.setView(input);
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                v3.setText(input.getText().toString());
                                store_title(tables[2], v3.getText().toString());
                            }
                        });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.create().show();
            }
        });
        v4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                builder.setTitle("自定义标题");
                final EditText input = new EditText(getContext());
                input.setText(v4.getText().toString());
                builder.setView(input);
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                v4.setText(input.getText().toString());
                                store_title(tables[3], v4.getText().toString());
                            }
                        });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.create().show();
            }
        });
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
                Intent intent = new Intent(getActivity(), WriteActivity.class);
                intent.putExtra(section_title, v1.getText().toString());
                intent.putExtra("section", requests[0]);
                intent.putExtra(tables[0], textView1.getText().toString());
                startActivityForResult(intent, requests[0]);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteActivity.class);
                intent.putExtra(section_title, v2.getText().toString());
                intent.putExtra("section", requests[1]);
                intent.putExtra(tables[1], textView2.getText().toString());
                startActivityForResult(intent, requests[1]);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteActivity.class);
                intent.putExtra(section_title, v3.getText().toString());
                intent.putExtra("section", requests[2]);
                intent.putExtra(tables[2], textView3.getText().toString());
                startActivityForResult(intent, requests[2]);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteActivity.class);
                intent.putExtra(section_title, v4.getText().toString());
                intent.putExtra("section", requests[3]);
                intent.putExtra(tables[3], textView4.getText().toString());
                startActivityForResult(intent, requests[3]);
            }
        });
        return root;
    }
}