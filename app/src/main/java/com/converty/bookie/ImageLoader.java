package com.converty.bookie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class ImageLoader extends AsyncTask<String,Integer, Bitmap> {


    private ImageView image;


    public ImageLoader(ImageView image) {

        this.image = image;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String imgurl = strings[0];
        Bitmap bump = null;
        try {
            InputStream stream = new java.net.URL(imgurl).openStream();
            bump = BitmapFactory.decodeStream(stream);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bump;
    }
    protected void onPostExecute(Bitmap result) {
        image.setImageBitmap(result);
    }
}
