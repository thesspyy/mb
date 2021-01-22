package com.example.lab2;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyDragdrop extends Activity implements View.OnClickListener, View.OnTouchListener, View.OnDragListener  {

    private ViewGroup.LayoutParams layoutParams;
    Button dbuttonNext,dbuttonBack;
    TextView txtDest,txtScore;
    Button btnYes,btnNo;
    int score = 0 ;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.dragdrop);
        init();
        dbuttonNext.setOnClickListener(this);
        dbuttonBack.setOnClickListener(this);
        btnYes.setOnTouchListener(this);
        btnNo.setOnTouchListener(this);
        txtDest.setOnDragListener(this);
    }
    private void init(){
        dbuttonNext = (Button) findViewById(R.id.dbtn_next);
        dbuttonBack = (Button) findViewById(R.id.dbtn_back);
        btnYes = (Button) findViewById(R.id.btnYes);
        btnNo = (Button) findViewById(R.id.btnNo);
        txtDest = (TextView) findViewById(R.id.txtDestination);
        txtScore = (TextView) findViewById(R.id.txtScore);

    }

    public void onClick(View v){
        Intent intent = null;
        switch(v.getId()) {
            case R.id.dbtn_back:
                intent = new Intent(getApplicationContext(), MyActivity.class);
                break;
            case R.id.dbtn_next:
                intent = new Intent(getApplicationContext(), MyLocation.class);
                break;
        }
        startActivity(intent);
        MyDragdrop.this.finish();
    }

    @Override
    public boolean onTouch (View v, MotionEvent event) {
        View. DragShadowBuilder mShadow = new View. DragShadowBuilder (v);
        ClipData. Item item = new ClipData.Item(v.getTag().toString());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);
            switch (v.getId()) {
                case R.id.btnYes:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        v.startDragAndDrop(data, mShadow, null, 0);
                    } else {
                        v.startDrag(data, mShadow, null, 0);
                    }
                    break;
                case R.id.btnNo:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        v.startDragAndDrop(data, mShadow, null, 0);
                    } else {
                        v.startDrag(data, mShadow, null, 0);
                    }
                    break;

            }
            return false;

        }

    @Override
    public boolean onDrag (View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                v.invalidate();
                return true;
            case DragEvent.ACTION_DRAG_ENTERED:
                String clipData = event.getClipDescription().getLabel().toString();
                txtDest.setText(clipData);
                v.invalidate();
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                txtDest.setText("");
                v.invalidate();
                return true;
            case DragEvent.ACTION_DROP:
                clipData = event.getClipDescription().getLabel().toString();
                Toast.makeText(getApplicationContext(), clipData, Toast. LENGTH_SHORT).show();
                v.invalidate();
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                if (event.getResult()) {
                    Toast.makeText(MyDragdrop.this, "Awesome!",Toast.LENGTH_SHORT).show();
                    score++;
                }
                else {
                    Toast.makeText(MyDragdrop.this, "Aw Snap! Try dropping it again",Toast.LENGTH_SHORT).show();
                    score--;
                }
                txtDest.setText("");
                txtScore.setText ("Score : "+score);
                return true;
                default:
                    return false;

            }
    }

    }