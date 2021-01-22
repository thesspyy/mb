package com.example.lab2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyLocation extends Activity implements View.OnClickListener{
    Button buttonBack,buttonExit;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.location);
        init();

        buttonBack.setOnClickListener(this);
        buttonExit.setOnClickListener(this);
    }
    private void init(){
        buttonExit = (Button) findViewById(R.id.gbtn_exit);
        buttonBack = (Button) findViewById(R.id.gbtn_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gbtn_back:
                Intent intent = new Intent(getApplicationContext(), MyDragdrop.class);
                startActivity(intent);
                MyLocation.this.finish();
                break;
            case R.id.gbtn_exit:


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                //set title
                alertDialogBuilder.setTitle("Confirm Exit Program");


                alertDialogBuilder
                        .setMessage("click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MyLocation.this.finish();

                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
                break;
        }

    }
}
