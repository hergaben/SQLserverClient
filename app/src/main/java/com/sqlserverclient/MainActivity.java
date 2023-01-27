package com.sqlserverclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.sqlserverclient.timetable.TimetablePage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adaptery.OnItemClickListener {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_ADDRESS = "address";
    public static final String EXTRA_DEPARTMENT = "department";
    public static final String EXTRA_DETAIL = "detail";
    public static final String EXTRA_ID = "id_поликлиники";
//    public static final String EXTRA_DATE = "date";
//    public static final String EXTRA_SWORK = "swork";
//    public static final String EXTRA_EWORK = "ework";

    private static String JSON_URL = "http://192.168.0.15/appdbserver/polyclinic.php";

    List<com.sqlserverclient.PolyModelClass> polyList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        polyList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);

        MainActivity.GetData getData = new MainActivity.GetData();
        getData.execute();
    }

    @Override
    public void onItemClick(int position) {
        Intent Intent = new Intent(this, TimetablePage.class);
        com.sqlserverclient.PolyModelClass clickedItem = polyList.get(position);

        Intent.putExtra(EXTRA_NAME, clickedItem.getName());
        Intent.putExtra(EXTRA_ADDRESS, clickedItem.getAddress());
        Intent.putExtra(EXTRA_DEPARTMENT, clickedItem.getDepartment());
        Intent.putExtra(EXTRA_DETAIL, clickedItem.getDetail());
        Intent.putExtra(EXTRA_ID, clickedItem.getId());
//        Intent.putExtra(EXTRA_DATE, clickedItem.getDate());
//        Intent.putExtra(EXTRA_SWORK, clickedItem.getS_work());
//        Intent.putExtra(EXTRA_EWORK, clickedItem.getE_work());

        startActivity(Intent);
    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try{
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("Polyclinics");
                for (int i = 0 ; i < jsonArray.length() ; i++){

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    com.sqlserverclient.PolyModelClass model = new com.sqlserverclient.PolyModelClass();
                    model.setName(jsonObject1.getString("название_поликлиники"));
                    model.setDepartment(jsonObject1.getString("название_отделения"));
                    model.setAddress(jsonObject1.getString("адрес"));
                    model.setDetail(jsonObject1.getString("детали"));

                    polyList.add(model);
                    model.setId(jsonObject1.getString("id_поликлиники"));
//                    model.setDate(jsonObject1.getString("дата"));
//                    model.setS_work(jsonObject1.getString("начало_работы"));
//                    model.setE_work(jsonObject1.getString("конец_работы"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(polyList);
        }
    }

    private void PutDataIntoRecyclerView(List<com.sqlserverclient.PolyModelClass> polyList){

        com.sqlserverclient.Adaptery adaptery = new com.sqlserverclient.Adaptery(this, polyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptery);

        adaptery.setOnItemClickListener(MainActivity.this);

    }


}