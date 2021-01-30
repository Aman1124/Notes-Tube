package com.example.notestube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

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
}