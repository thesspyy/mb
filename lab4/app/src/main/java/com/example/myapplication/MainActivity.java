package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends Activity implements View.OnClickListener {
    TextView listmap;
    EditText searchT;
    Button btnSearch,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee);
        listmap = (TextView) findViewById(R.id.txtEmployee);
        searchT = (EditText)findViewById(R.id.editSearch);
        btnSearch = (Button)findViewById(R.id.ebtnSearch);
        btnBack = (Button)findViewById(R.id.ebtnBack);
        ShowAll();
        btnBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    private static String getValues(String tag, Element element){

        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public void ShowAll(){
        try{
            InputStream is = getAssets().open("employeedata.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            Element element = (Element) doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("employee");
            for(int i=0; i<nList.getLength();i++){
                Node node = nList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element2 = (Element) node;
                    String name = getValues("name",element2).toString();
                    listmap.setText(listmap.getText() + "\nName: "+getValues("name",element2)+"\n");
                    listmap.setText(listmap.getText() + "surname: "+getValues("surname",element2)+"\n");
                    listmap.setText(listmap.getText()+"-----------------------------");

                }
            }
        } catch (Exception e ){e.printStackTrace();}
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ebtnBack:
                Intent intent = new Intent(getApplicationContext(),Mymenu.class);
                startActivity(intent);
                MainActivity.this.finish();
                break;
            case R.id.ebtnSearch:
                String s = searchT.getText().toString();
                Search(s);
        }
    }

    public  void Search(String s){
        listmap.setText("");
        try{
            InputStream is = getAssets().open("employeedata.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("employee");

            for(int i =0;i<nList.getLength();i++){
                Node node = nList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element2 = (Element) node;
                    String name = getValues("name",element2).toString();
                    if(name.indexOf(s)>=0){
                        listmap.setText(listmap.getText() + "\nName: " + getValues("name",element2) + "\n");
                        listmap.setText(listmap.getText() + "\nSurname: " + getValues("surname",element2) + "\n");
                        listmap.setText(listmap.getText() + "------------------------------------");
                    }
                }
            }

        }catch (Exception e){ e.printStackTrace();}
    }
}
