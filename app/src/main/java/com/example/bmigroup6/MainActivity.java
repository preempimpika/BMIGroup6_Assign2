package com.example.bmigroup6;

import static android.provider.BaseColumns._ID;
import static com.example.bmigroup6.Constants.BMI;
import static com.example.bmigroup6.Constants.RANK;
import static com.example.bmigroup6.Constants.TABLE_NAME;
import static com.example.bmigroup6.Constants.TIME;
import static com.example.bmigroup6.Constants.WEIGHT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private  DBmain dBmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res = getResources();
        final String[] rankArray = res.getStringArray(R.array.ranktext);
        final EditText weight_input = (EditText) findViewById(R.id.weightinput);
        weight_input.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        final EditText height_input = (EditText) findViewById(R.id.heightinput);
        height_input.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        final TextView bmi_output = findViewById(R.id.bmioutput);
        final TextView rank_output = findViewById(R.id.rankoutput);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double heightmeter = 0.0;
                heightmeter = Float.parseFloat(height_input.getText().toString());
                heightmeter = heightmeter / 100;
                heightmeter *= heightmeter;
                double kilogram = 0.0;
                kilogram = Float.parseFloat(weight_input.getText().toString());
                double bmicalculator = 0.0;
                bmicalculator = kilogram / heightmeter;
                bmi_output.setText((new DecimalFormat("#,###.##").format(bmicalculator) + ""));
                Resources resources = getResources();
                if (bmicalculator < 16) {
                    rank_output.setText(rankArray[0]);
                    rank_output.setTextColor(getResources().getColor(R.color.black));
                    rank_output.setBackground(getResources().getDrawable(R.drawable.borderthinoutput3));
                } else if (bmicalculator < 17) {
                    rank_output.setText(rankArray[1]);
                    rank_output.setTextColor(getResources().getColor(R.color.black));
                    rank_output.setBackground(getResources().getDrawable(R.drawable.borderthinoutput2));
                } else if (bmicalculator < 18.5) {
                    rank_output.setText(rankArray[2]);
                    rank_output.setTextColor(getResources().getColor(R.color.black));
                    rank_output.setBackground(getResources().getDrawable(R.drawable.borderthinoutput1));
                } else if (bmicalculator < 25) {
                    rank_output.setText(rankArray[3]);
                    rank_output.setTextColor(getResources().getColor(R.color.black));
                    rank_output.setBackground(getResources().getDrawable(R.drawable.bordernormal));
                } else if (bmicalculator < 30) {
                    rank_output.setText(rankArray[4]);
                    rank_output.setTextColor(getResources().getColor(R.color.black));
                    rank_output.setBackground(getResources().getDrawable(R.drawable.borderfat));
                } else if (bmicalculator < 35) {
                    rank_output.setText(rankArray[5]);
                    rank_output.setTextColor(getResources().getColor(R.color.black));
                    rank_output.setBackground(getResources().getDrawable(R.drawable.borderoutput));
                } else if (bmicalculator < 40) {
                    rank_output.setText(rankArray[6]);
                    rank_output.setTextColor(getResources().getColor(R.color.black));
                    rank_output.setBackground(getResources().getDrawable(R.drawable.borderoutput2));
                } else if (bmicalculator > 40) {
                    rank_output.setText(rankArray[7]);
                    rank_output.setTextColor(getResources().getColor(R.color.black));
                    rank_output.setBackground(getResources().getDrawable(R.drawable.borderoutput3));

                }

                dBmain = new DBmain(MainActivity.this);
                try{
                    addEvent(weight_input,bmi_output,rank_output);
                }finally {
                    dBmain.close();
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.view_record){
            startActivity(new Intent(this, ListActivity.class));
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
    //end OnCreate
    private void addEvent(EditText weight_input, TextView bmi_output, TextView rank_output) {

        String weight = String.format("%1$s", weight_input.getText());
        String bmi = String.format("%1$s", bmi_output.getText());
        String rank = String.format("%1$s",rank_output.getText());
        SQLiteDatabase db = dBmain.getWritableDatabase();
        ContentValues values = new ContentValues();
        Date date = new Date(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.format(date);
        values.put(TIME, dateFormat.format(date));
        values.put(WEIGHT, weight);
        values.put(BMI, bmi);
        values.put(RANK, rank);
        db.insert(TABLE_NAME, null, values);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if ((newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) ||
                (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)) {
        }

        if ((newConfig.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {

        }

        float fontScale = newConfig.fontScale;
    }

}


class DecimalDigitsInputFilter implements InputFilter {
    private Pattern mPattern;

    DecimalDigitsInputFilter(int digits, int digitsAfterZero) {
        mPattern = Pattern.compile("[0-9]{0," + (digits - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) +
                "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher = mPattern.matcher(dest);
        if (!matcher.matches())
            return "";
        return null;
    }
}