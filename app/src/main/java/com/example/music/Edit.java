package com.example.music;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Edit extends AppCompatActivity {

    Bundle arg;
    EditText Name;
    EditText Executor;
    EditText Genre;
    EditText Duration;
    Bitmap bm = null;
    ImageView Img;
    Mask mask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        arg = getIntent().getExtras();
        mask = arg.getParcelable(Mask.class.getSimpleName());
        Name = findViewById(R.id.Et_Name);
        Executor = findViewById(R.id.Et_Executor);
        Genre = findViewById(R.id.Et_Genre);
        Duration = findViewById(R.id.Et_Duration);
        Img= findViewById(R.id.imImg);
        Name.setText(mask.getName());
        Executor.setText(mask.getExecutor());
        Genre.setText(mask.getGenre());
        Duration.setText(mask.getDuration());
        Image_D decodeImage = new Image_D(Edit.this);
        Bitmap userImage = decodeImage.getUserImage(mask.getImage());
        Img.setImageBitmap(userImage);
        /*if(!mask.getImage().equals("null")){
            bm=userImage;
        }
*/

    }

    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    bm = BitmapFactory.decodeStream(is);
                    Img.setImageURI(uri);
                } catch (Exception e) {
                    Log.e(e.toString(), e.getMessage());
                }
            }
        }
    });

    public void Update(View view) {
        try{
            mask.setName(Name.getText().toString());
            mask.setExecutor(Executor.getText().toString());
            mask.setGenre(Genre.getText().toString());
            mask.setDuration(Duration.getText().toString());
            Image_E IE = new  Image_E();
            mask.setImage(IE.Image(bm));
            Updates(mask, view);
            SystemClock.sleep(1000);
            Back(view);
        }
        catch (Exception ex)
        {
            Toast.makeText(Edit.this, "Что-то пошло не так!", Toast.LENGTH_SHORT).show();
        }
    }

    private void Updates(Mask mask, View view)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/ПетуненковаАП/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI update = retrofit.create(RetrofitAPI.class);
        Call<Mask> call = update.createPut(mask, mask.getID());
        call.enqueue(new Callback<Mask>() {
            @Override
            public void onResponse(Call<Mask> call, Response<Mask> response) {
                Toast.makeText(Edit.this, "Успешное изменение!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Mask> call, Throwable t) {
                Toast.makeText(Edit.this, "Что-то пошло не так!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Back(View view) {
        Intent intent = new Intent(this, General.class);
        startActivity(intent);
    }

    private  void delete(Mask mask, View view)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/ПетуненковаАП/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<Mask> call = null;
        RetrofitAPI retrofitAPIsDel = retrofit.create(RetrofitAPI.class);
        call = retrofitAPIsDel.createDelete(mask.getID());
        call.enqueue(new Callback<Mask>() {
            @Override
            public void onResponse(Call<Mask> call, Response<Mask> response) {
                Toast.makeText(Edit.this, "Успешное удаление!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Mask> call, Throwable t) {
                Toast.makeText(Edit.this, "Что-то пошло не так!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void Delete(View view) {
        delete(mask, view);
        SystemClock.sleep(1000);
        Back(view);
    }

    public void getImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
    }
}