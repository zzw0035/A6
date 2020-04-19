package com.example.a5;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context){
        super(context, "store.db", null, 4);
    }


    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int a, int b){
        onCreate(sqLiteDatabase);
    }


    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE asd (id INTEGER PRIMARY KEY AUTOINCREMENT, Time INT, Usd DOUBLE, Item varchar(255))");

    }





    public void Create(int time,double usd,String item){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Time", time);
        cv.put("Usd", usd);
        cv.put("Item", item);
        sqLiteDatabase.insert("asd", null, cv);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor a = db.rawQuery("SELECT * FROM asd",null);

        return a;
    }

    public Cursor getData2(String t1,String t2,String p1,String p2) {




        SQLiteDatabase db = this.getReadableDatabase();
        Cursor a = null;

        if (p1.equals("n") && p2.equals("n")){
            int gt1 = Integer.parseInt(t1);
            int gt2 = Integer.parseInt(t2);
            a = db.rawQuery("SELECT * FROM asd WHERE Time >= " + gt1 + " AND Time <= " + gt2,null);
        }

        else if (t1.equals("n") && t2.equals("n")){
            double gp1 = Double.parseDouble(p1);
            double gp2 = Double.parseDouble(p2);
            a = db.rawQuery("SELECT * FROM asd WHERE Usd >= " + gp1 + " AND Usd <= " + gp2,null);
        }

        else{
            int gt1 = Integer.parseInt(t1);
            int gt2 = Integer.parseInt(t2);
            double gp1 = Double.parseDouble(p1);
            double gp2 = Double.parseDouble(p2);
            a = db.rawQuery("SELECT * FROM asd WHERE Usd >= " + gp1 + " AND Usd <= " + gp2 + " AND Time >=" + gt1 + " AND Time <=" + gt2,null);
        }


        return a;
    }
}
