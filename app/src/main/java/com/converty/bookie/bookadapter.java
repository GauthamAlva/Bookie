package com.converty.bookie;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.converty.bookie.BookQueiryClass.LOG_TAG;

public class bookadapter extends ArrayAdapter<book> {
    public bookadapter(Activity context, ArrayList<book> bookArrayList) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, bookArrayList);
    }
    @Nullable
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listlayout, parent, false);
            // Get the {@link AndroidFlavor} object located at this position in the list
            book current = getItem(position);

            // Find the TextView in the list_item.xml layout with the ID version_name
            TextView title = (TextView) listItemView.findViewById(R.id.titleid);
            // Get the version name from the current AndroidFlavor object and
            // set this text on the name TextView
            title.setText(current.gettitle());


            TextView auther = (TextView) listItemView.findViewById(R.id.authorid);

            auther.setText(current.getauthor());

            ImageView img = (ImageView) listItemView.findViewById(R.id.imageView);

            new ImageLoader(img).execute(current.getimg());


            TextView date = (TextView) listItemView.findViewById(R.id.dateid);
            date.setText(current.getdate());

            // Return the whole list item layout (containing 2 TextViews and an ImageView)
            // so that it can be shown in the ListView

        }
        return listItemView;
    }
}
