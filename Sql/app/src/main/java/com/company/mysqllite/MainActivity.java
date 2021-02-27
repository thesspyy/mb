package com.company.mysqllite;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity  extends Activity implements View.OnClickListener {
    Button btnAdd, btnSearch;
    ArrayList<HashMap<String, String>> StudentsList;

    ListView listView1;
    String tag_id = "StudentId",
            tag_name = "Name",
            tag_tel = "Tel",
            TAG_EDIT = "Edit",
            TAG_DELETE = "Delete";
    String[] strCmd = {TAG_EDIT, TAG_DELETE};
    EditText edt_search;
    public static String id_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnAdd.setOnClickListener(this);

        btnAdd.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    private void init() {
        btnAdd = (Button) findViewById(R.id.btn_Add);
        MyDatabase myDB = new MyDatabase(this);
        myDB.getWritableDatabase();
//select data
        StudentsList = myDB.SelectAllData();
// listview
        listView1 = (ListView) findViewById(R.id.listView);
        SimpleAdapter simAdap;
        simAdap = new SimpleAdapter(getApplicationContext(), StudentsList, R.layout.layout_row,
                new String[]{"StudentId", "Name", "Tel"},
                new int[]{R.id.col_stdId, R.id.col_name, R.id.col_tel});
        listView1.setAdapter(simAdap);

        registerForContextMenu(listView1);
        edt_search = (EditText) findViewById(R.id.editText_search);
        btnSearch = (Button) findViewById(R.id.btn_search);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_Add:
                intent = new Intent(getApplicationContext(), InsertData.class);
                break;
            case R.id.btn_search:
                String strSearch = edt_search.getText().toString();
                MyDatabase myDB = new MyDatabase(this);
                myDB.getWritableDatabase();
                StudentsList = myDB.SearchData(strSearch);
                SimpleAdapter simAdap;
                simAdap = new SimpleAdapter(getApplicationContext(), StudentsList, R.layout.layout_row,
                        new String[]{"StudentID", "Name", "Tel"},
                        new int[]{
                        R.id.col_stdId, R.id.col_name, R.id.col_tel});
                listView1.setAdapter(simAdap);
                break;

        }
        if (intent != null) {
            startActivity(intent);

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderIcon(android.R.drawable.ic_menu_edit);
        menu.setHeaderTitle("[" + StudentsList.get(info.position).get(tag_name) + "]");
        String[] menuItems = strCmd;
        for (int i = 0; i < menuItems.length; i++) {
            menu.add(Menu.NONE, i, i, menuItems[i]);

        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = strCmd;
        String CmdName = menuItems[menuItemIndex];
        String Id = StudentsList.get(info.position).get(tag_id).toString();
        String Name = StudentsList.get(info.position).get(tag_name).toString();
        String Tel = StudentsList.get(info.position).get(tag_tel).toString();

        if (TAG_EDIT.equals(CmdName)) {
            Intent i = new Intent(getApplicationContext(), EditData.class);
            i.putExtra(tag_id, Id);
            i.putExtra(tag_name, Name);
            i.putExtra(tag_tel, Tel);
            Toast.makeText(getApplicationContext(), "Edit (StudentID = " + Id + ")", Toast.LENGTH_LONG).show();
            startActivity(i);

        } else if (TAG_DELETE.equals(CmdName)) {

            //-Dialog Confirm-
            final AlertDialog.Builder viewDetail = new AlertDialog.Builder(this);
            viewDetail.setIcon(android.R.drawable.ic_delete);
            viewDetail.setTitle("Confirm Delete ?");
            viewDetail.setMessage("ID : " +Id+ "\nName : " +Name+ "\nTel : " +Tel+ "");
            viewDetail.setPositiveButton("Delete",
                    (dialog, which) -> {
                        //-Delete--//
                        MyDatabase db = new MyDatabase(getApplicationContext());
                        db.getWritableDatabase();
                        Long flag = db.DeleteData(MainActivity.id_delete);
                        if (flag > 0) {
                            Toast.makeText(getApplicationContext(), "Delete (StudentID = " +
                                    MainActivity.id_delete + ") Successfully.", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                            MainActivity.id_delete = "";
                        } else {
                            Toast.makeText(getApplicationContext(), "Delete Failed ",
                                    Toast.LENGTH_LONG).show();
                        }
                        //--End Delete
                    }
            );
            viewDetail.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            viewDetail.show();
            ///--End Dialog ---////
        }
            return super.onContextItemSelected(item);

        }

    }