package com.example.alex.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.BaseColumns;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class Complete extends ActionBarActivity {
    SQLiteDatabase db;
    ViewGroup layout;
    Button b;
    String time;
    String name;
    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LinearLayout l = new LinearLayout(this);
        setContentView(R.layout.activity_end);
        layout = (ViewGroup) findViewById(R.id.endActivity);

        db=openOrCreateDatabase("MyDB1",MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Student(name VARCHAR,time VARCHAR);");

        //RelativeLayout r = new RelativeLayout(this);
        Intent intent = getIntent();
         time = intent.getStringExtra(MainActivity.TIME); //TIME STRING
        setContentView(R.layout.activity_end);
        System.out.println(time);
        TextView text = (TextView) findViewById(R.id.textViewScore); //You got ___score

        //System.out.println(text);
        // text.setTextSize(75);
        text.setText("You took " + time + " seconds.");
        System.out.println(text.getText());

        // text.setTextColor(Color.parseColor("cyan"));
        // r.addView(text);
        b = (Button) findViewById(R.id.playMeAgain); //play again
        // b.setText("Play Again");
        b.setOnClickListener(new BackListener());
        //layout.addView(b);

        //l.addView(text);
        //l.addView(b);
        // b.setBackgroundColor(Color.parseColor("f8f8ff"));
     /*   b.setOnClickListener(



        );*/
         edit= (EditText)findViewById(R.id.editText);

    }

    public class BackListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i = new Intent(Complete.this, SettingsActivity.class);
            finish();
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_end, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void restart(MenuItem item) {
        Intent i = new Intent(this, SettingsActivity.class);
        finish();
        startActivity(i);
    }
  /*  public void recordScore(View view){
        DatabaseHandler db = new DatabaseHandler(this);
        name =edit.getText().toString();
        Score s = new Score(time,name);
        db.addScore(s);
       // Log.d("Num of Scores, ", ""+db.getScoreCount());
        System.out.println(db.getScoreCount());
    }*/
    public void recordScore(View view)
    {
        name=edit.getText().toString();
        db.execSQL("INSERT INTO  Student VALUES('"+name+"','"+time+"');");
   }
    public void showData(View view){
        String[]timeString = new String[]{time};
        Cursor c = db.query("Student",null,null,null,null,null,"time ASC");
        //Cursor c=db.rawQuery("SELECT * from Student", null);
        int count= c.getCount();
        c.moveToFirst();
        TableLayout tableLayout = new TableLayout(getApplicationContext());
        tableLayout.setVerticalScrollBarEnabled(true);
        TableRow tableRow;
        TextView textView,textView1,textView2,textView3,textView4,textView5;
        tableRow = new TableRow(getApplicationContext());
        textView=new TextView(getApplicationContext());
        textView.setText("Name");
        textView.setTextColor(Color.RED);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setPadding(20, 20, 20, 20);
        tableRow.addView(textView);
        textView4=new TextView(getApplicationContext());
        textView4.setText("Time");
        textView4.setTextColor(Color.RED);
        textView4.setTypeface(null, Typeface.BOLD);
        textView4.setPadding(20, 20, 20, 20);
        tableRow.addView(textView4);
        tableLayout.addView(tableRow);
        for (Integer j = 0; j < count; j++)
        {
            tableRow = new TableRow(getApplicationContext());
            textView1 = new TextView(getApplicationContext());
            textView1.setText(c.getString(c.getColumnIndex("name")));
            textView1.setTextColor(Color.DKGRAY);
            textView2 = new TextView(getApplicationContext());
            textView2.setText(c.getString(c.getColumnIndex("time")));
            textView2.setTextColor(Color.DKGRAY);
            textView1.setPadding(20, 20, 20, 20);
            textView2.setPadding(20, 20, 20, 20);
            tableRow.addView(textView1);
            tableRow.addView(textView2);
            tableLayout.addView(tableRow);
            c.moveToNext() ;
        }
        setContentView(tableLayout);
        db.close();

    }

}
