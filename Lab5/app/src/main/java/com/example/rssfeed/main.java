package com.example.rssfeed;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class main extends Activity {
    List headLine;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rssmain);
        if(Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        headLine = new ArrayList();
        listView = (ListView) findViewById(R.id.listView);
        getRss();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,headLine);
        listView.setAdapter(adapter);
    }

    private InputStream getInputStream(URL url){
        try{
            return url.openConnection().getInputStream();
        }catch (IOException e){
            return null;
        }
    }

    private void getRss() {
        String content="";
        try {
            URL url = new URL("http://rssfeeds.sanook.com/rss/feeds/sanook/news.index.xml");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(getInputStream(url),"UTF_8");
            boolean insideItem = false;
            int evenType = xpp.getEventType();
            while (evenType != XmlPullParser.END_DOCUMENT) {
                if (evenType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("title") && insideItem) {
                        content = xpp.nextText();
                    } else if (xpp.getName().equalsIgnoreCase("link") && insideItem) {
                        content += "\n" + xpp.nextText();
                        headLine.add(content);
                    }
                } else if (evenType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("/item")) {
                    insideItem = false;
                }
                evenType = xpp.next();
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(XmlPullParserException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
         }
}
}
