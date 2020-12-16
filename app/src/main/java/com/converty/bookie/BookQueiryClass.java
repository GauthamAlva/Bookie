package com.converty.bookie;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.util.Log.e;

public class BookQueiryClass {
    private BookQueiryClass(){}
    public static final String LOG_TAG=book.class.getSimpleName();
    public static List<book> fetchdata(String url){
        URL ur=createurl(url);
        String response=null;
        response=makehttpreq(ur);
        List<book> list= extractjason(response);
        return list;
    }
    private static URL createurl(String url){
        URL modurl=null;
        try{
            modurl=new URL(url);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"Error formating request",e);
        }
        return modurl;
    }
    private static String makehttpreq(URL urlformat){
        InputStream stream=null;
           HttpURLConnection urlConnection =null;
           String response=null;
           if(urlformat==null) return  null;
           try{
               urlConnection=(HttpURLConnection)urlformat.openConnection();
               urlConnection.setReadTimeout(1000);
               urlConnection.setConnectTimeout(15000);
               urlConnection.setRequestMethod("GET");
               urlConnection.connect();
               if(urlConnection.getResponseCode()==200){
                   stream=urlConnection.getInputStream();
                   response=convertdata(stream);
               }else{
                   Log.e(LOG_TAG,"error respond code"+urlConnection.getResponseCode());
               }
           }catch (IOException e){
               Log.e(LOG_TAG,"error retriving book info",e);
           }finally {
               if(urlConnection!=null) urlConnection.disconnect();
               if(stream!=null) {
                   try {
                       stream.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
       }
return response;
    }
    private static String convertdata(InputStream inputStream){
        StringBuilder builder=new StringBuilder();
        String response="";
        if(response==null){
            return response;
        }else{

            InputStreamReader inputStreamReader=null;
            inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader   bufferedReader=new BufferedReader(inputStreamReader);
            String line= null;
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (line!=null){
                builder.append(line);
                try {
                    line=bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }
    public static ArrayList<book> extractjason(String url){
        ArrayList<book> booklist=new ArrayList<>();
       try {
           JSONObject basejson=new JSONObject(url);


               JSONArray items = basejson.getJSONArray("items");
               for (int i = 0; i < items.length(); i++) {
                   JSONObject currentbook = items.getJSONObject(i);
                   JSONObject volumeinfo = currentbook.getJSONObject("volumeInfo");
                   String title = volumeinfo.getString("title");
                   String date = volumeinfo.getString("publishedDate");
                   JSONArray autorinfo = volumeinfo.getJSONArray("authors");
                   String autor = autorinfo.getString(0);
                   JSONObject tunbnailinfo = volumeinfo.getJSONObject("imageLinks");
                   String tubnail = tunbnailinfo.getString("smallThumbnail");
                   book boo = new book(tubnail, autor, date, title);
                   booklist.add(boo);

               }
       }catch (JSONException e){
           Log.e("QueryUtils", "Problem parsing the music JSON results", e);
       }
        return booklist;
    }
}
