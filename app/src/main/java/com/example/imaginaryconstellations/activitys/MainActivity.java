package com.example.imaginaryconstellations.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.imaginaryconstellations.R;

public class MainActivity extends AppCompatActivity {
    public static final String LOG  = "MyLog";
    private Button start,rules,exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InzializateComponents();
    }
    private void InzializateComponents()
    {
        start = findViewById(R.id.start);
        rules = findViewById(R.id.rules);
        exit = findViewById(R.id.exit);
        //
        start.setOnClickListener(createClickListener());
        rules.setOnClickListener(createClickListener());
        exit.setOnClickListener(createClickListener());
    }
    private View.OnClickListener createClickListener()
    {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.start:
                        CreateGameIntent(); break;
                    case R.id.rules:
                        CreateRuleIntent(); break;
                    case R.id.exit:
                        finishActivity(); break;
                }
            }
        };
        return onClickListener;
    }
    private void  CreateGameIntent()
    {
        startActivity(new Intent(MainActivity.this, GameStarter.class));
    }
    private void  CreateRuleIntent()
    {
        startActivity(new Intent(MainActivity.this, Rules.class));
    }
    private void finishActivity()
    {
        this.finish();
    }
}
