package com.example.a5;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    TextView text;
    TableLayout hist;
    EditText day,month,year;
    EditText usd;
    EditText item;
    Button add;
    Button sub;
    EditText t1,t2,p1,p2;
    Button search;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DatabaseHelper(this);
        text = findViewById(R.id.text);
        day = findViewById(R.id.day);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        usd = findViewById(R.id.dollar);
        item = findViewById(R.id.item);
        add = findViewById(R.id.add1);
        sub = findViewById(R.id.sub);
        hist = findViewById(R.id.tab);
        t1 = findViewById(R.id.d1);
        t2 = findViewById(R.id.d2);
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        search = findViewById(R.id.search);


        Click();
        History();
    }

    public void Click(){


        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double dollar = Double.parseDouble(usd.getText().toString());
                        int t = Integer.parseInt(year.getText().toString() + month.getText().toString() + day.getText().toString());
                        db.Create(t, dollar, item.getText().toString());

                        History();
                        c();
                    }
                }
        );

        sub.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double dollar = - 1 * Double.parseDouble(usd.getText().toString());
                        int t = Integer.parseInt(year.getText().toString() + month.getText().toString() + day.getText().toString());
                        db.Create(t, dollar, item.getText().toString());
                        History();
                        c();
                    }
                }
        );

        search.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String gt1,gt2,gp1,gp2;
                        gt1 = t1.getText().toString();
                        gt2 = t2.getText().toString();
                        gp1 = p1.getText().toString();
                        gp2 = p2.getText().toString();
                        if (gp1.equals("") && gp2.equals("") && gt2.length() > 0 && gt1.length() > 0) {
                            search(gt1,gt2,"n","n");
                        }
                        else if (gt1.equals("") && gt2.equals("") && gp2.length() > 0 && gp1.length() > 0) {
                            search("n","n",gp1,gp2);
                        }
                        else if (gt1.equals("") && gt2.equals("") && gp2.equals("") && gp1.length() > 0) {
                            gp2 = "99999999";
                            search("n","n",gp1,gp2);
                        }
                        else if (gp1.equals("") && gp2.equals("") && gt2.equals("") && gt1.length() > 0) {
                            gt2 = "99999999";
                            search(gt1,gt2,"n","n");
                        }

                        else if (gt2.length() > 0 && gt1.length() > 0 && gp2.length() > 0 && gp1.length() > 0) {
                            search(gt1,gt2,gp1,gp2);
                        }

                        else if (gt2.equals("") && gt1.length() > 0 && gp2.length() > 0 && gp1.length() > 0) {
                            gt2 = "99999999";
                            search(gt1,gt2,gp1,gp2);
                        }

                        else if (gt2.length() > 0 && gt1.length() > 0 && gp2.equals("") && gp1.length() > 0) {
                            gp2 = "99999999";
                            search(gt1,gt2,gp1,gp2);
                        }




                        c();

                    }
                }
        );



    }

    public void History(){
        Double total = 0.0;
        Cursor data = db.getData();

        if (hist.getChildCount() > 0) {
            hist.removeAllViews();

        }

        while(data.moveToNext()){
            double dollar = Double.parseDouble(data.getString(2));
            total += dollar;
            TableRow r = new TableRow(this);
            TableRow.LayoutParams cl = new TableRow.LayoutParams();
            cl.weight = 1;

            TextView D = new TextView(this);
            D.setLayoutParams(cl);
            String gTime = data.getString(1);
            String sTime = gTime.substring(4,6) + "/" + gTime.substring(6,8) + "/" + gTime.substring(0,4);
            D.setText(sTime);
            r.addView(D);

            TextView amount = new TextView(this);
            amount.setLayoutParams(cl);
            amount.setText(data.getString(2));
            r.addView(amount);

            TextView Cg = new TextView(this);
            Cg.setLayoutParams(cl);
            Cg.setText(data.getString(3));
            r.addView(Cg);

            hist.addView(r, new TableLayout.LayoutParams());

        }
        MainActivity.this.text.setText("Current Balance: $" + Double.toString(total));
    }


    public void search(String t1,String t2,String p1,String p2){
        Double total = 0.0;
        Cursor data = db.getData2(t1, t2, p1, p2);

        if (hist.getChildCount() > 0) {
            hist.removeAllViews();

        }

        while(data.moveToNext()){
            double dollar = Double.parseDouble(data.getString(2));
            total += dollar;
            TableRow r = new TableRow(this);
            TableRow.LayoutParams cl = new TableRow.LayoutParams();
            cl.weight = 1;

            TextView D = new TextView(this);
            D.setLayoutParams(cl);
            String gTime = data.getString(1);
            String sTime = gTime.substring(4,6) + "/" + gTime.substring(6,8) + "/" + gTime.substring(0,4);
            D.setText(sTime);
            r.addView(D);

            TextView amount = new TextView(this);
            amount.setLayoutParams(cl);
            amount.setText(data.getString(2));
            r.addView(amount);

            TextView Cg = new TextView(this);
            Cg.setLayoutParams(cl);
            Cg.setText(data.getString(3));
            r.addView(Cg);

            hist.addView(r, new TableLayout.LayoutParams());

        }
        MainActivity.this.text.setText("Current Balance: $" + Double.toString(total));
    }




    public void c(){
        MainActivity.this.day.setText("");
        MainActivity.this.month.setText("");
        MainActivity.this.year.setText("");
        MainActivity.this.usd.setText("");
        MainActivity.this.item.setText("");
        MainActivity.this.t1.setText("");
        MainActivity.this.t2.setText("");
        MainActivity.this.p1.setText("");
        MainActivity.this.p2.setText("");

    }

}