package com.sqlserverclient.appointment;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.sqlserverclient.ConnectionHelper;
import com.sqlserverclient.R;
import com.sqlserverclient.timetable.TimetablePage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentPage extends AppCompatActivity {

    Connection connect;
    String ConnectionResult = "";
    SimpleAdapter adapter;
    Boolean isSuucess = false;

    public String id, t_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_page);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        t_id = intent.getStringExtra("t_id");
        Log.d("!",id);
    }

    public void onStart() {
        super.onStart();
        ListView listView = (ListView) findViewById(R.id.listViewAppointment);

        List<Map<String,String>> MyDataList = null;
        //ListItemTimetable MyData = new ListItemTimetable();
        //MyDataList = MyData.getListTimetable();
        MyDataList = getListAppointment();

        //TextView count = (TextView) findViewById(R.id.idTimetable);

        String[] Fromw = {"Spec","Name","Phone","Date","S_app","E_app","Cabinet"};
        int[] Tow = {R.id.A_spec,R.id.A_name,R.id.A_phone,R.id.A_date,R.id.A_start,R.id.A_end,R.id.A_cabinet};
        adapter = new SimpleAdapter(this, MyDataList, R.layout.list_appointment, Fromw, Tow);
        listView.setAdapter(adapter);

//        List<Map<String, String>> finalMyDataList = MyDataList;
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Intent intent = new Intent(TimetablePage.this, AppointmentPage.class);
//                Map<String,String> item = finalMyDataList.get(i);
//                Log.d("!!!!!", String.valueOf(item.get("ID")));
//                intent.putExtra("id", String.valueOf(item.get("ID")));
//                startActivity(intent);
//
//            }
//        });
    }

    public List<Map<String,String>> getListAppointment()
    {
        List<Map<String,String>> data = null;
        data = new ArrayList<Map<String,String>>();
        try{
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.CONN();
            if(connect!=null)
            {
                int c = 1;
                String query = "SELECT ????????, ??????, ??????????????????????????, ??????????????, ????????_????????????, ????????????_????????????, ??????????_????????????, ?????????????? FROM ???????????????????? LEFT JOIN ???????? ON ??????????????????_id_??????????????????="+id+" LEFT JOIN ?????????? ON ??????????_id_????????????=id_???????????? WHERE ????????=????????_???????????? AND id_????????????????????="+t_id+";";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);

                while (rs.next())
                {
                    Map<String,String> dtname = new HashMap<String,String>();
                    dtname.put("Name", rs.getString("??????"));
                    dtname.put("Spec", rs.getString("??????????????????????????"));
                    dtname.put("Phone", rs.getString("??????????????"));
                    dtname.put("Date", rs.getString("????????_????????????"));
                    dtname.put("S_app", rs.getString("????????????_????????????"));
                    dtname.put("E_app", rs.getString("??????????_????????????"));
                    dtname.put("Cabinet", rs.getString("??????????????"));
                    dtname.put("Count", String.valueOf(c));
                    c=c+1;
                    data.add(dtname);
                }
                ConnectionResult = "Success";
                isSuucess = true;
                //!!! ?????????? !!!
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