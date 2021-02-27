package com.company.mysqllite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditData extends Activity implements View.OnClickListener {
    Button btn_save, btn_cancel;
    EditText editText_eName, editText_eTel, editText_eid;
    String TAG_ID = "StudentID",
            TAG_NAME = "Name",
            TAG_TEL = "Tel";
    String stdid, name, tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
    }

    private void init() {
        editText_eName = (EditText) findViewById(R.id.editText_ename);
        editText_eTel = (EditText) findViewById(R.id.editText_etel);
        editText_eid = (EditText) findViewById(R.id.editText_eid);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        //get intent extra
        Bundle bundle = getIntent().getExtras();
        stdid = bundle.getString(TAG_ID);
        name = bundle.getString(TAG_NAME);
        tel = bundle.getString(TAG_TEL);
        //set text
        editText_eName.setText(name);
        editText_eTel.setText(tel);
        editText_eid.setText(stdid);

    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()) {
            case R.id.btn_save:

                MyDatabase myDB = new MyDatabase(this);
                myDB.getWritableDatabase();
                String newName = editText_eName.getText().toString();
                String newTel = editText_eTel.getText().toString();
                Long flag = myDB.UpdateData(stdid, newName, newTel);
                if (flag > 0) {
                    i = new Intent(getApplicationContext(), MainActivity.class);
                    Toast.makeText(getApplicationContext(), "Updated successfully...",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Update Fail...",
                            Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.btn_cancel:
                i = new Intent(getApplicationContext(), MainActivity.class);
                break;
        }
        if (i != null) {
            startActivity(i);
            finish();

        }
    }

}

