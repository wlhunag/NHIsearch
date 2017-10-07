package com.nhisearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 這行不知做甚麼的？
        setSupportActionBar(toolbar);

        // 下面測試 /data/data/com.test.db/databases/ 下的數據庫是否能正常工作
        final SQLiteDatabase database = DBHelper.getDatabase(this);

        // 監視搜尋列
        //Todo: 加上搜尋 icon
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String b = editable.toString().trim();
                String query = "SELECT `_id`,`code`,`en`,`ch`,`money`,`note` FROM `nhi`  WHERE `code` LIKE '%" + b + "%' or  `en` LIKE '%" + b + "%' or  `ch` LIKE '%" + b + "%' or `note` LIKE '%" + b + "%';";
                Log.d("DatabaseEntry", query);
                onquery(query, database);
            }
        });
    }

    public void onquery(String query, SQLiteDatabase database) {
        Cursor cursor = database.rawQuery(query, null);
        ListView listView = (ListView) findViewById(R.id.listview);
        final String[] from = new String[]{"code", "money", "en", "ch","note"};
        final int[] to = new int[]{R.id.code, R.id.money, R.id.en, R.id.ch, R.id.note};
        cursor.moveToFirst();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_record_view, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView codeTextView = view.findViewById(R.id.code);
                TextView enTextView = view.findViewById(R.id.en);
                TextView chTextView = view.findViewById(R.id.ch);
                TextView moneyTextView = view.findViewById(R.id.money);
                TextView noteTextView = (TextView) view.findViewById(R.id.note);

                String code = codeTextView.getText().toString();
                String en = enTextView.getText().toString();
                String money = moneyTextView.getText().toString();
                String ch = chTextView.getText().toString();
                String note = noteTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ListActivity.class);
                modify_intent.putExtra("en", en);
                modify_intent.putExtra("ch", ch);
                modify_intent.putExtra("code", code);
                modify_intent.putExtra("money", money);
                modify_intent.putExtra("note", note);

                startActivity(modify_intent);
            }
        });
    }
}
