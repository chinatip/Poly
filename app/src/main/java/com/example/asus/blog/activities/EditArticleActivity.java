package com.example.asus.blog.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupMenu;

import com.example.asus.blog.R;
import com.example.asus.blog.activities.pages.Following;
import com.example.asus.blog.activities.pages.History;
import com.example.asus.blog.activities.pages.Timeline;
import com.example.asus.blog.adapters.AddImageAdapter;
import com.example.asus.blog.models.Article;
import com.example.asus.blog.models.User;
import com.example.asus.blog.models.Utility;
import com.example.asus.blog.util.ArticleStorage;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EditArticleActivity extends AppCompatActivity {
    private EditText header, text, keyword;
    private Button save;
    private ArrayList images;
    private GridView gv;
    private AddImageAdapter addImageAdapter;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    boolean result= Utility.checkPermission(EditArticleActivity.this);
    private User user;
    private Article article;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_article);
        Intent intent = getIntent();
        user = (User) intent.getExtras().get("user");
        article = (Article) intent.getExtras().get("article");
        initComponents();
    }

    public void initComponents() {
        setContentView(R.layout.activity_write_an_article);
        createFormForArticle();
        createToolbar();
        save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Timeline.update();
                Following.update();
                History.update();
                finish();
            }
        });
        images = new ArrayList();
        images.add(null);
        ArrayList temp = article.getImages();
        for(int i =0;i<temp.size();i++){
            images.add(temp.get(i));
        }

        gv = (GridView) findViewById(R.id.addImageGridView);
        addImageAdapter = new AddImageAdapter(this, R.layout.grid_item_layout, images);
        gv.setAdapter(addImageAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    PopupMenu popup = new PopupMenu(EditArticleActivity.this, gv);
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.gall) {
                                userChoosenTask = "Gallery";
                                if (result)
                                    galleryIntent();
                            } else {
                                userChoosenTask = "Take Photo";
                                if (result)
                                    cameraIntent();
                            }
                            return false;
                        }
                    });
                    popup.show();

                }
            }
        });

    }

    public void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void createFormForArticle(){
        header = (EditText) findViewById(R.id.header);
        header.setText(article.getHeader());
        text = (EditText) findViewById(R.id.text);
        text.setText(article.getText());
        keyword = (EditText) findViewById(R.id.keywords);
        String key = article.getKeywords().toString();
        String cut = key.substring(1, keyword.length() - 1);
        keyword.setText(cut);
    }

    private void save(){
        String [] keywordArray = keyword.getText().toString().split(",");
        ArrayList<String> keywordList = new ArrayList<>();
        for (int i = 0; i< keywordArray.length; i++) {
            keywordList.add(keywordArray[i].trim());
        }
        images.remove(0);
        Article article = new Article(user.getUsername(), header.getText().toString(), text.getText().toString(), images, keywordList);
        try {
            ArticleStorage.getInstance().UpdateArticle(getApplicationContext(),article,user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                }
                break;
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File folder = new File("/MyStickers");
        File destination = new File (Environment.getExternalStorageDirectory().getAbsolutePath()+folder,
                System.currentTimeMillis() + ".jpg");
        if(!folder.exists()) folder.mkdirs();

        FileOutputStream fo;

        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        images.add(thumbnail);
        addImageAdapter.notifyDataSetChanged();
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        images.add(bm);
        addImageAdapter.notifyDataSetChanged();
    }
}
