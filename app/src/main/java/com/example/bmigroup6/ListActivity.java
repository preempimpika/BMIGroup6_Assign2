package com.example.bmigroup6;

import static android.provider.BaseColumns._ID;
import static com.example.bmigroup6.Constants.BMI;
import static com.example.bmigroup6.Constants.RANK;
import static com.example.bmigroup6.Constants.TABLE_NAME;
import static com.example.bmigroup6.Constants.TIME;
import static com.example.bmigroup6.Constants.WEIGHT;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {
    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    ListView listView;
    String []name;
    int[] id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dBmain= new DBmain(ListActivity.this);

        Cursor cursor = getEvents();
        showEvents(cursor);
    }

    private Cursor getEvents(){
        String[] FROM ={_ID,TIME,WEIGHT,BMI,RANK};
        String ORDER_BY = _ID+" DESC";
        SQLiteDatabase db = dBmain.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
        return cursor;
    }

    private void showEvents(Cursor cursor) {
        final ListView listView = (ListView)findViewById(R.id.listView);
        final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        while(cursor.moveToNext()) {
            map = new HashMap<String, String>();
            map.put("time", cursor.getString(1));
            map.put("weight", cursor.getString(2));
            map.put("bmi", cursor.getString(3));
            map.put("rank", cursor.getString(4));
            MyArrList.add(map);
        }
        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter( ListActivity.this, MyArrList, R.layout.activity_column,
                new String[] {"time","weight","bmi","rank"},
                new int[] {R.id.col_time, R.id.col_name, R.id.col_msg, R.id.col_amt} );
        listView.setAdapter(sAdap);
    }
}