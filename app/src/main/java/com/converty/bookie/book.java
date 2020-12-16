package com.converty.bookie;

import java.net.URL;

public class book {
    String mimg;
    String mauther;
    String mdate;
    String mtitle;
    public book(String image,String Auther,String date,String title ){
        mimg=image;
        mauther=Auther;
        mdate=date;
        mtitle=title;
    }
    public  String getimg(){
        return mimg;
    }
    public  String getauthor(){
        return mauther;
    }
    public  String getdate(){
        return mdate;
    }
    public String gettitle(){
        return mtitle;
    }
}
