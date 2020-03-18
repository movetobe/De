package com.example.de;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WriteActivity extends AppCompatActivity {
    private EditText text;
    private TextView savebt;
    private TextView nosavebt;
    private Intent intent;
    private final String[] keys = {"time_line", "focus", "personal_todo", "work_todo"};
    private int section;
    private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        intent = getIntent();
        text = findViewById(R.id.editText);

        section = intent.getIntExtra("section", 1);

        TextView title = findViewById(R.id.title_each);
        title.setText(intent.getStringExtra("section_title"));
        str = intent.getStringExtra(keys[section]);
        text.setText(str);
        text.requestFocus();
        text.setSelection(text.getText().length());
        nosavebt = findViewById(R.id.nosaveButton);
        nosavebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder saveDialog = new AlertDialog.Builder(WriteActivity.this);
                //设置对话框标题
                saveDialog.setTitle("De");
                //设置对话框消息
                saveDialog.setMessage("是否保存修改");
                // 添加选择按钮并注册监听
                saveDialog.setPositiveButton("确定",diaListener);

                saveDialog.setNegativeButton("取消",diaListener);
                //对话框显示
                saveDialog.show();
            }
        });
        savebt = findViewById(R.id.saveButton);
        savebt.setOnClickListener(new View.OnClickListener() {
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
            AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            //设置对话框标题
            saveDialog.setTitle("De");
            //设置对话框消息
            saveDialog.setMessage("是否保存修改");
            // 添加选择按钮并注册监听
            saveDialog.setPositiveButton("确定",diaListener);

            saveDialog.setNegativeButton("取消",diaListener);
            //对话框显示
            saveDialog.show();
        }
        return false;
        //return super.onKeyDown(keyCode, event);
    }
    DialogInterface.OnClickListener diaListener=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int buttonId) {
            // TODO Auto-generated method stub
            switch (buttonId) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    intent = getIntent();
                    text = findViewById(R.id.editText);
                    intent.putExtra(keys[section], text.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "确认"按钮退出程序
                    intent = getIntent();
                    intent.putExtra(keys[section], str);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
}
