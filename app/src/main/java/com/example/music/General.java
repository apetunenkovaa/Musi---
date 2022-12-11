package com.example.music;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class General extends AppCompatActivity {
    private Adapter pAdapter;
    private List<Mask> list = new ArrayList<>();
    Spinner spinner;
    EditText filter;
    String[] i = {"по умолчанию","по наименованию", "по жанру"};
    ListView lvMusic;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        lvMusic = findViewById(R.id.Music);
        ListView ivProducts = findViewById(R.id.Music);
        pAdapter = new Adapter(General.this, list);
        ivProducts.setAdapter(pAdapter);

        spinner=findViewById(R.id.sort);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, i);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Sort(list);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Sort(List<Mask> list){
        lvMusic.setAdapter(null);
        switch(spinner.getSelectedItemPosition()){
            case 0:
                new GetMusic().execute();
                break;
            case 1:
                Collections.sort(list, Comparator.comparing(Mask::getName));
                break;
            case 2:
                Collections.sort(list, Comparator.comparing(Mask::getGenre));
                break;
            default:
                break;
        }
        SetAdapter(list);
    }

    public void SetAdapter(List<Mask> list)
    {
        pAdapter = new Adapter(General.this,list);
        lvMusic.setAdapter(pAdapter);
        pAdapter.notifyDataSetInvalidated();
    }

    public void onAdd(View view) {
        startActivity(new Intent(this, Add.class));
    }

    public void onSearch(View view) {

    }
    private class GetMusic extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
               
                URL url = new URL("https://ngknn.ru:5001/ngknn/ПетуненковаАП/api/Musics");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                JSONArray tempArray = new JSONArray(s);
                for (int i = 0;i<tempArray.length();i++)
                {

                    JSONObject musicJson = tempArray.getJSONObject(i);
                    Mask tempProduct = new Mask(
                            musicJson.getInt("ID_Music"),
                            musicJson.getString("Name"),
                            musicJson.getString("Executor"),
                            musicJson.getString("Genre"),
                            musicJson.getString("Duration"),
                            musicJson.getString("Image")
                    );
                    list.add(tempProduct);
                    pAdapter.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {


            }
        }


    }

}