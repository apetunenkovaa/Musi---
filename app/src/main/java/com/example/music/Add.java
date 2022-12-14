package com.example.music;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;

import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;


import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Add extends AppCompatActivity {

    EditText Name;
    EditText Executor;
    EditText Genre;
    EditText Duration;
    Bitmap Image = null,bm;
    ImageView Img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Name = findViewById(R.id.Name);
        Executor = findViewById(R.id.Executor);
        Genre = findViewById(R.id.Genre);
        Duration = findViewById(R.id.Duration);
        Img = findViewById(R.id.Img);
        bm = BitmapFactory.decodeResource(Add.this.getResources(), R.drawable.img);
        Img.setImageBitmap(bm);
    }

    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    bm = BitmapFactory.decodeStream(is);
                    Img.setImageBitmap(bm);
                } catch (Exception e) {
                    Log.e(e.toString(), e.getMessage());
                }
            }
        }
    });


    public void getImage(View v)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
    }


    public void onClickBack(View view) {
        Intent intent = new Intent(this, General.class);
        startActivity(intent);
    }

    public void onAdd(View view) {
     Image_E encodeImage = new Image_E();
        String name = Name.getText().toString();
        String executor = Executor.getText().toString();
        String genre = Genre.getText().toString();
        String duration = Duration.getText().toString();
        post(name, executor, genre, duration, encodeImage.Image(bm), view);
        SystemClock.sleep(1000);
        onClickBack(view);
    }

  private void post(String name, String executor, String genre,String duration, String image, View v) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/ПетуненковаАП/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Mask modal = new Mask(null, name, executor,genre, duration, image);
        Call<Mask> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<Mask>() {
            @Override
            public void onResponse(Call<Mask> call, Response<Mask> response) {
                Toast.makeText(Add.this, "Данные успешно добавлены!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Mask> call, Throwable t) {
                Toast.makeText(Add.this, "Что-то пошло не так!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}