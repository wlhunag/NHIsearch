package com.nhisearch;

import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;


public class ListActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Detail Record");

        setContentView(R.layout.content_detail);


        TextView codeText = findViewById(R.id.code2);
        TextView enText = findViewById(R.id.en2);
        TextView chText = findViewById(R.id.ch2);
        TextView moneyText = findViewById(R.id.money2);
        TextView noteText = findViewById(R.id.note2);


        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        String en = intent.getStringExtra("en");
        String ch = intent.getStringExtra("ch");
        String money = intent.getStringExtra("money");
        String note = intent.getStringExtra("note");

        codeText.setText(code);
        enText.setText(en);
        chText.setText(ch);
        moneyText.setText(money);
        noteText.setText(note);
        Log.d("DatabaseEntry","CODE = "+code + "\n Name EN = "+en+"\nCH = " + ch);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
