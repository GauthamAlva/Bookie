package com.converty.bookie;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class MusicAsyntask extends AsyncTaskLoader<List<book>> {
    String murl;
    public MusicAsyntask(Context context,String url){
        super(context);
        murl=url;
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public List<book> loadInBackground() {
        if(murl==null)  return null;
        List<book> booklist=BookQueiryClass.fetchdata(murl);

        return booklist;
    }


}
