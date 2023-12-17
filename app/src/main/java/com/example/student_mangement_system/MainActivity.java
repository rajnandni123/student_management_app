package com.example.student_mangement_system;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText txt_roll, txt_name, txt_age;
    Button btn_in, btn_up, btn_del, btn_load, btn_next, btn_prev, btn_showAll, btn_clr, btn_srch, btn_ext;
    RadioButton rb_roll, rb_name, rb_age;

    // Database
    SQLiteDatabase db;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB Part Starts
        db = openOrCreateDatabase("MyDB", Context.MODE_PRIVATE, null);
        db.execSQL("Create table if not exists MyTab(rno varchar,name varchar,age varchar)");
        // DB Part Ends

        txt_roll = findViewById(R.id.txt_roll);
        rb_roll = findViewById(R.id.rb_roll);
        txt_name = findViewById(R.id.txt_name);
        rb_name = findViewById(R.id.rb_name);
        txt_age = findViewById(R.id.txt_age);
        rb_age = findViewById(R.id.rb_age);

        btn_in = findViewById(R.id.btn_in);
        btn_up = findViewById(R.id.btn_up);
        btn_del = findViewById(R.id.btn_del);
        btn_load = findViewById(R.id.btn_load);
        btn_next = findViewById(R.id.btn_next);
        btn_prev = findViewById(R.id.btn_prev);
        btn_clr = findViewById(R.id.btn_clr);
        btn_showAll = findViewById(R.id.btn_showAll);
        btn_srch = findViewById(R.id.btn_srch);
        btn_ext = findViewById(R.id.btn_ext);

        btn_in.setOnClickListener(this);
        btn_up.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_load.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_prev.setOnClickListener(this);
        btn_clr.setOnClickListener(this);
        btn_showAll.setOnClickListener(this);
        btn_srch.setOnClickListener(this);
        btn_ext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try
        {
            if (view.findViewById(R.id.btn_clr) == btn_clr)
            {
                txt_roll.setText("");
                txt_name.setText("");
                txt_age.setText("");
                txt_roll.setEnabled(true);
                txt_roll.requestFocus();
            }
            else if (view.findViewById(R.id.btn_in) == btn_in)
            {
                if (txt_roll.getText().length() == 0 || txt_name.getText().length() == 0 || txt_age.getText().length() == 0)
                    Toast.makeText(this, "All fields are required !!!", Toast.LENGTH_SHORT).show();
                else
                {
                    String str = "insert into MyTab values('" + txt_roll.getText().toString() + "','" +
                            txt_name.getText().toString() + "','" + txt_age.getText().toString() + "')";
                    db.execSQL(str);
                    Toast.makeText(this, "Record Inserted", Toast.LENGTH_SHORT).show();
                    txt_roll.setText("");
                    txt_name.setText("");
                    txt_age.setText("");
                    txt_roll.requestFocus();
                }
            }
            else if (view.findViewById(R.id.btn_up) == btn_up)
            {
                if (txt_roll.getText().length() == 0 || txt_name.getText().length() == 0 || txt_age.getText().length() == 0)
                    Toast.makeText(this, "All fields are required !!!", Toast.LENGTH_SHORT).show();
                else
                {
                    String str = "update MyTab set name = '" + txt_name.getText().toString() + "', age = '" +
                            txt_age.getText().toString() + "' where rno = '" + txt_roll.getText().toString() + "'";

                    db.execSQL(str);
                    Toast.makeText(this, "Record Updated", Toast.LENGTH_SHORT).show();
                    txt_roll.setText("");
                    txt_name.setText("");
                    txt_age.setText("");
                    txt_roll.requestFocus();
                }
            }
            else if (view.findViewById(R.id.btn_del) == btn_del)
            {
                if (txt_roll.getText().length() == 0)
                    Toast.makeText(this, "Roll Number is required to delete record", Toast.LENGTH_SHORT).show();
                else
                {
                    String str = "delete from MyTab where rno = '" + txt_roll.getText().toString() + "'";
                    db.execSQL(str);
                    Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
                    txt_roll.setText("");
                    txt_name.setText("");
                    txt_age.setText("");
                    txt_roll.requestFocus();
                }
            }
            else if (view.findViewById(R.id.btn_load) == btn_load)
            {
                try
                {
                    c = db.rawQuery("select * from MyTab order by rno", null);
                    Toast.makeText(this, "Data Loaded", Toast.LENGTH_SHORT).show();
                    c.moveToFirst();
                    txt_roll.setText(c.getString(0));
                    txt_name.setText(c.getString(1));
                    txt_age.setText(c.getString(2));

                    txt_roll.setEnabled(false);
                }
                catch (CursorIndexOutOfBoundsException exx)
                {
                    Toast.makeText(this, "No records found. Table is Empty", Toast.LENGTH_SHORT).show();
                }
            }
            else if (view.findViewById(R.id.btn_next) == btn_next)
            {
                c.moveToNext();
                if (c.isAfterLast())
                {
                    c.moveToLast();
                    Toast.makeText(this, "U are on Last Record", Toast.LENGTH_SHORT).show();
                }
                txt_roll.setText(c.getString(0));
                txt_name.setText(c.getString(1));
                txt_age.setText(c.getString(2));
            }
            else if (view.findViewById(R.id.btn_prev) == btn_prev)
            {
                c.moveToPrevious();
                if (c.isBeforeFirst())
                {
                    c.moveToFirst();
                    Toast.makeText(getApplicationContext(), "U are on First Record", Toast.LENGTH_SHORT).show();
                }
                txt_roll.setText(c.getString(0));
                txt_name.setText(c.getString(1));
                txt_age.setText(c.getString(2));
            }
            else if (view.findViewById(R.id.btn_showAll) == btn_showAll)
            {
                String temp;
                StringBuffer sb = new StringBuffer();

                c = db.rawQuery("select * from MyTab order by rno", null);
                while (c.moveToNext())
                {
                    sb.append("\n Roll No = " + c.getString(0));
                    sb.append(" Name = " + c.getString(1));
                    sb.append(" Age = " + c.getString(2)+"\n");
                }
                temp = sb.toString();
                refresh("DB Records", temp);
            }
            else if (view.findViewById(R.id.btn_srch) == btn_srch)
            {
                int flg = 0;
                String temp;
                StringBuffer sb = new StringBuffer();

                if (rb_roll.isChecked())
                {
                    if (txt_roll.getText().length() == 0)
                        Toast.makeText(this, "Enter Roll No to Search Record", Toast.LENGTH_SHORT).show();
                    else
                    {
                        flg = 1;
                        c = db.rawQuery("select * from MyTab where rno='" + txt_roll.getText().toString() + "' order by rno", null);
                    }
                }
                else if (rb_name.isChecked())
                {
                    if (txt_name.getText().length() == 0)
                        Toast.makeText(this, "Enter Name to Search Record", Toast.LENGTH_SHORT).show();
                    else
                    {
                        flg = 1;
                        c = db.rawQuery("select * from MyTab where name like '%" + txt_name.getText().toString() + "%' order by rno", null);
                    }
                }
                else
                {
                    if (txt_age.getText().length() == 0)
                        Toast.makeText(this, "Enter Age to Search Record", Toast.LENGTH_SHORT).show();
                    else
                    {
                        flg = 1;
                        c = db.rawQuery("select * from MyTab where age='" + txt_age.getText().toString() + "' order by rno", null);
                    }
                }

                if (flg == 1)
                {
                    if(c.getCount() > 0) {
                        while (c.moveToNext()) {
                            sb.append("\n Roll No = " + c.getString(0));
                            sb.append(" Name = " + c.getString(1));
                            sb.append(" Age = " + c.getString(2) + "\n");
                        }
                        temp = sb.toString();
                        refresh("Search Result", temp);
                    }
                    else
                        refresh("Search Result", "No Record Found");
                }
            }
            else if (view.findViewById(R.id.btn_ext) == btn_ext)
            {
                System.exit(0);
            }
        }
        catch (Exception e) {
            refresh("Exception", e.toString());
        }
    }

    public void refresh(String ttl, String msg)
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setCancelable(true);
        ad.setTitle(ttl);
        ad.setMessage(msg);
        ad.show();
    }
}