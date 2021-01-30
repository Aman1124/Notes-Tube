package com.example.notestube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    String[] title = new String[]{"Abc", "Xyz", "Pqr"};
    String[] channel = new String[]{"123", "789", "098"};
    String[] time = new String[]{"1 min ago", "10 min ago", "7 days ago"};
    Bitmap[] img = new Bitmap[]{
            getImageBitmap("https://i.ytimg.com/vi/xr3EMr_hrfA/mqdefault.jpg"),
            getImageBitmap("https://i.ytimg.com/vi/z6HLeNl8DOs/hqdefault.jpg"),
            getImageBitmap("https://i.ytimg.com/vi/Zo9svgiRSeM/hqdefault.jpg")};

    public static class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public Bitmap getImageBitmap(String url){
        ImageDownloader imageTask = new ImageDownloader();
        Bitmap imgBitmap = null;

        try {
            imgBitmap = imageTask.execute(url).get();
        }catch (Exception e){
            e.printStackTrace();
        }

        return imgBitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout,
                new Dashboard(title, channel, time, img)).commit();

       bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               Fragment selectedFragment;
               switch (item.getItemId()){
                   case R.id.nav_notes:
                       selectedFragment = new Notes();
                       break;
                   case R.id.nav_history:
                       selectedFragment = new History(title, channel, img);
                       break;
                   default:
                       selectedFragment = new Dashboard(title, channel, time, img);
                       break;
               }
               getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout,
                       selectedFragment).commit();
               return true;
           }
       });

        mAuth=FirebaseAuth.getInstance();
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=20&q=amazon-gods&type=video&key=AIzaSyAKqsBfJa1xl1c265-Db7KNycAP1GeaZ-M";

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert mgr != null;
        //mgr.hideSoftInputFromWindow(weatherData.getWindowToken(), 0);
        try {
            DownloadTask task = new DownloadTask();
            task.execute(url);

        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"Unable to find weather!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.items,menu);
        MenuItem menuItem=menu.findItem(R.id.search);

        final SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Here");
        SearchView.OnQueryTextListener queryTextListener=new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Searching...", Toast.LENGTH_SHORT).show();
                searchView.clearFocus();
                String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=20&q=amazon-gods&type=video&key=AIzaSyAKqsBfJa1xl1c265-Db7KNycAP1GeaZ-M";

                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert mgr != null;
                //mgr.hideSoftInputFromWindow(weatherData.getWindowToken(), 0);
                try {
                    DownloadTask task = new DownloadTask();
                    task.execute(url);

                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Unable to find weather!",Toast.LENGTH_LONG).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    public static class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();

                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();

                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line);
                }

                result = total.toString();
                Log.i("DATA: ",result);
                return result;
            }
            catch(Exception e){
                System.out.println("Page not found!!");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<String> youtubeLinks,thumbnails,titles,descriptions,channels,time;
            youtubeLinks=new ArrayList<String>();
            titles=new ArrayList<String>();
            descriptions=new ArrayList<String>();
            channels=new ArrayList<String>();
            time=new ArrayList<String>();
            thumbnails=new ArrayList<String>();

            try {
                JSONObject jsonObject=new JSONObject(s);

                String items=jsonObject.getString("items");
                System.out.println(items);

                JSONArray arrayItems=new JSONArray(items);
                String link="";
                for(int i=0;i<arrayItems.length();i++)
                {
//                    Getting VideoId for the youtube
                    JSONObject part=arrayItems.getJSONObject(i);
                    link=part.getString("id");
                    JSONObject links=new JSONObject(link);
                    String id = links.getString("videoId");
                    youtubeLinks.add(id);

//                    Getting Title of the Video
                    String snippet=part.getString("snippet");
                    JSONObject snippetObject=new JSONObject(snippet);
                    String title= snippetObject.getString("title");
                    titles.add(title);

//                    Getting Description
                    String description=snippetObject.getString("description");
                    descriptions.add(description);
//                    System.out.println(description);

//                    Getting Channel Name
                    String channelName=snippetObject.getString("channelTitle");
                    channels.add(channelName);
//                    System.out.println(channelName);

//                    Getting Upload date
                    String publishTime=snippetObject.getString("publishedAt");
                    time.add(publishTime);
                    System.out.println(publishTime);

//                    Getting Thumbnail
                    String thumbnailObject=snippetObject.getString("thumbnails");
                    JSONObject qualityOfThumbnail=new JSONObject(thumbnailObject);
                    String imageObject=qualityOfThumbnail.getString("medium");
                    JSONObject thumbnailLink=new JSONObject(imageObject);
                    String thumbnail=thumbnailLink.getString("url");
                    thumbnails.add(thumbnail);
                    System.out.println(thumbnail);

                }
                for(int i=0;i<youtubeLinks.size();i++)
                {
                    System.out.println(youtubeLinks.get(i)+" "+titles.get(i));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}