package com.example.de;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PersonalTodoActivity extends AppCompatActivity {
    private EditText text;
    private FloatingActionButton bt;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        intent = getIntent();
        text = findViewById(R.id.editText);
        String str = intent.getStringExtra("personal_todo");
        text.setText(str);
        text.requestFocus();
        text.setSelection(text.getText().length());
        bt = findViewById(R.id.saveButton);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("personal_todo", text.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


}