package com.architjn.epic;

import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.architjn.epic.adapters.ImgsAdapter;
import com.architjn.epic.utils.ImageItemDecoration;
import com.architjn.epic.utils.items.Image;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));
        ArrayList<Image> imgList = getImagesList();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getImageSize(), getImageSize());
        rv = (RecyclerView) findViewById(R.id.main_recyclerview);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.addItemDecoration(new ImageItemDecoration(8, getNumberOfImage()));
        rv.setAdapter(new ImgsAdapter(this, imgList, layoutParams));
    }

    private int getImageSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int numPossible = width / 200;
        return width / numPossible;
    }

    private int getNumberOfImage() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width / 200;
    }

    private ArrayList<Image> getImagesList() {
        ArrayList<Image> images = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN
        };


        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;


        Cursor cur = getContentResolver().query(imageUri,
                projection, // Which columns to return
                null,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                null        // Ordering
        );
        if (cur.moveToFirst()) {
            int bucketColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int pathColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.DATA);

            do {
                images.add(new Image(cur.getString(bucketColumn), cur.getString(pathColumn)));
            } while (cur.moveToNext());
        }

        return images;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
