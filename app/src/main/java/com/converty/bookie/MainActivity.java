package com.converty.bookie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<book>> {
    private static String s1;
private static String apiurl="https://www.googleapis.com/books/v1/volumes?q=subject:";
    private static int id=0;
private  bookadapter adapter=null;
private SearchView searchView=null;
 private ListView list=null;
 public static TextView empty=null;
 private ProgressBar cyclic=null;
    private ImageView image =null;
 private TextView internet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager cm=(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info=cm.getActiveNetworkInfo();
        boolean isConnected = info != null &&
                info.isConnectedOrConnecting();
        internet=(TextView)findViewById(R.id.internet);
        if(!isConnected){
            cyclic = (ProgressBar) findViewById(R.id.progressBar_cyclic);
            cyclic.setVisibility(View.GONE);
            internet.setText("No Internet Available");
        }
        else {
            image=(ImageView)findViewById(R.id.img);
            image.setImageResource(R.drawable.images);
            cyclic = (ProgressBar) findViewById(R.id.progressBar_cyclic);
            cyclic.setVisibility(View.GONE);
            list = (ListView) findViewById(R.id.list);
            searchView = (SearchView) findViewById(R.id.search);
            //toDo make a cycic progress bar inorder to wait and add acctivity to the activity_main and check weather there is stable internet connection
            adapter = new bookadapter(this, new ArrayList<book>());
            list.setAdapter(adapter);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    adapter.clear();
                    s1 = apiurl + s;
                 if(empty!=null){
                     empty.setVisibility(View.GONE);

                 }

                       image.setVisibility(View.GONE);


                        LoaderManager.getInstance(MainActivity.this).initLoader(++id, null, MainActivity.this);
                    return false;
                }


                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
    }

    @NonNull
    @Override
    public Loader<List<book>> onCreateLoader(int id, @Nullable Bundle args) {
       return new MusicAsyntask(MainActivity.this,s1);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<book>> loader, List<book> data) {
        if (data != null && !data.isEmpty()){
            adapter.clear();
            cyclic.setVisibility(View.GONE);
            adapter.addAll(data);
        }else {
            empty=(TextView)findViewById(R.id.empty_view);
            cyclic.setVisibility(View.GONE);
            empty.setText("No items Available");
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<book>> loader) {
adapter.clear();
    }
}