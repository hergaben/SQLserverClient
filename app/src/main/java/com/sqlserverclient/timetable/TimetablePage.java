package com.sqlserverclient.timetable;

import static com.sqlserverclient.MainActivity.EXTRA_ADDRESS;
import static com.sqlserverclient.MainActivity.EXTRA_ID;
import static com.sqlserverclient.MainActivity.EXTRA_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sqlserverclient.ConnectionHelper;
import com.sqlserverclient.MainActivity;
import com.sqlserverclient.R;
import com.sqlserverclient.appointment.AppointmentPage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimetablePage extends AppCompatActivity {

    Connection connect;
    String ConnectionResult = "";
    SimpleAdapter adapter;
    Boolean isSuucess = false;

    public String id, poly_p, address_p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_page);

        Intent intent = getIntent();
        id = intent.getStringExtra(EXTRA_ID);
        poly_p = intent.getStringExtra(EXTRA_NAME);
        address_p = intent.getStringExtra(EXTRA_ADDRESS);

        TextView poly = (TextView) findViewById(R.id.poly_t);
        TextView address = (TextView) findViewById(R.id.address_t);

        poly.setText(poly_p);
        address.setText(address_p);
    }

    public void onStart() {
        super.onStart();
        ListView listView = (ListView) findViewById(R.id.listViewTimetable);

        List<Map<String,String>> MyDataList = null;
        //ListItemTimetable MyData = new ListItemTimetable();
        //MyDataList = MyData.getListTimetable();
        MyDataList = getListTimetable();

        //TextView count = (TextView) findViewById(R.id.idTimetable);

        String[] Fromw = {"Count","Date","Start_work","End_work"};
        int[] Tow = {R.id.idTimetable,R.id.Date,R.id.S_work,R.id.E_work};
        adapter = new SimpleAdapter(this, MyDataList, R.layout.list_timetable, Fromw, Tow);
        listView.setAdapter(adapter);

        List<Map<String, String>> finalMyDataList = MyDataList;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(TimetablePage.this, AppointmentPage.class);
                Map<String,String> item = finalMyDataList.get(i);
                Log.d("!!!!!", String.valueOf(item.get("ID")));
                intent.putExtra("t_id", String.valueOf(item.get("ID")));
                intent.putExtra("id", String.valueOf(id));
                startActivity(intent);

            }
        });
    }

    public List<Map<String,String>> getListTimetable()
    {
        List<Map<String,String>> data = null;
        data = new ArrayList<Map<String,String>>();
        try{
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.CONN();
            if(connect!=null)
            {
                int c = 1;
                String query = "SELECT id_расписания, дата, начало_работы, конец_работы FROM расписание WHERE отделение_id_отделения="+ id +";";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);

                while (rs.next())
                {
                    Map<String,String> dtname = new HashMap<String,String>();
                    dtname.put("ID", rs.getString("id_расписания"));
                    dtname.put("Date", rs.getString("дата"));
                    dtname.put("Start_work", rs.getString("начало_работы"));
                    dtname.put("End_work", rs.getString("конец_работы"));
                    dtname.put("Count", String.valueOf(c));
                    c=c+1;
                    data.add(dtname);
                }
                ConnectionResult = "Success";
                isSuucess = true;
                //!!! Обрыв !!!
                connect.close();
            }
            else{
                ConnectionResult = "Failed";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }
}