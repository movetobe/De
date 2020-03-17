package com.example.de;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WriteActivity extends AppCompatActivity {
    private EditText text;
    private Button bt;
    private Intent intent;
    private final String[] keys = {"time_line", "focus", "personal_todo", "work_todo"};
    private int section;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        intent = getIntent();
        text = findViewById(R.id.editText);

        section = intent.getIntExtra("section", 1);
        Log.e("Section", String.valueOf(section));
        TextView title = findViewById(R.id.title_each);
        title.setText(intent.getStringExtra("section_title"));
        String str = intent.getStringExtra(keys[section]);
        text.setText(str);
        text.requestFocus();
        text.setSelection(text.getText().length());
        bt = findViewById(R.id.saveButton);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(keys[section], text.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            intent = getIntent();
            text = findViewById(R.id.editText);
            intent.putExtra(keys[section], text.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
