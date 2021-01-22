package com.example.lab2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity implements View.OnClickListener{
    Button buttonNext,buttonExit;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.main_menu);
        init();

        buttonNext.setOnClickListener(this);
        buttonExit.setOnClickListener(this);
    }
    private void init(){
        buttonExit = (Button) findViewById(R.id.mbtn_exit);
        buttonNext = (Button) findViewById(R.id.mbtn_next);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.mbtn_next:
                Intent intent = new Intent(getApplicationContext(),MyDragdrop.class);
                startActivity(intent);
                MyActivity.this.finish();
                break;
            case R.id.mbtn_exit:


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                //set title
                alertDialogBuilder.setTitle("Confirm Exit Program");


                alertDialogBuilder
                        .setMessage ("click yes to exit!")
                        .setCancelable (false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MyActivity.this.finish();

                            }
                        })

                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
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
