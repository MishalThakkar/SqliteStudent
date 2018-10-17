package com.example.mishalthakkar.sqlitestudent;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper mydb ;
    EditText Name,Surname,Marks,Id;
    Button button,buttonViewAllData,UpdateButton,DeleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DatabaseHelper(this);
        Name = findViewById(R.id.editText);
        Surname = findViewById(R.id.editText2);
        Marks = findViewById(R.id.editText3);
        Id = findViewById(R.id.editText4);
        button = findViewById(R.id.button);
        buttonViewAllData = findViewById(R.id.button2);
        UpdateButton = findViewById(R.id.button3);
        DeleteButton = findViewById(R.id.button4);
        AddData();
        viewAll();
        updatedata();
        deletedata();
    }
    public void AddData()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = mydb.insertvalues(Name.getText().toString(),Surname.getText().toString(),Marks.getText().toString());
                if (flag == true)
                    Toast.makeText(getApplicationContext(),"Data Successfully Added",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Data Insertion Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void viewAll()
    {
        buttonViewAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = mydb.getallData();
                if (result.getCount() == 0)
                {
                    // show message
                    showdata("ERROR","NO DATA FOUND");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext())
                {
                    buffer.append("Id : "+ result.getString(0) + "\n");
                    buffer.append("Name : "+ result.getString(1) + "\n");
                    buffer.append("Surname : "+ result.getString(2) + "\n");
                    buffer.append("Marks : "+ result.getString(3) + "\n\n");
                }
                // Show all data
                showdata("Data",buffer.toString());
            }
        });
    }

    public void showdata(String title,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updatedata()
    {
        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean flag = mydb.updatedata(Id.getText().toString(),Name.getText().toString(),Surname.getText().toString(),Marks.getText().toString());
                if (flag == true)
                    Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Data Updation Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deletedata()
    {
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer Deleted_Rows = mydb.deletedata(Id.getText().toString());
                if (Deleted_Rows > 0)
                    Toast.makeText(getApplicationContext(),"Data Deleted",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Data not Deleted",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
