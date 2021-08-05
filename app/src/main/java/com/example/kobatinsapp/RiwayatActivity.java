package com.example.kobatinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RiwayatActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private ListView lv;
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String,String>> memberList;
    private static String url_semua_anggota = "http://192.168.1.35/keuangan_kbtns/android/AllMember.php";
    private static final String TAG_SUKSES = "sukses";
    private static final String TAG_MEMBER = "tempmember";
    private static final String TAG_IDMEM = "tanggal";
    private static final String TAG_NAMA = "jumlah";
    JSONArray member = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);
        getSupportActionBar().hide();

        memberList = new ArrayList<HashMap<String, String>>();
        lv = (ListView) findViewById(R.id.list);
        new AmbilDataJson().execute();
    }

    class AmbilDataJson extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(RiwayatActivity.this, "Loading....",
                    Toast.LENGTH_LONG).show();
        }

        protected String doInBackground(String...args){
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(url_semua_anggota, "POST",params);

            Log.d("Semua Anggota:", json.toString());
            try{
                int sukses = json.getInt(TAG_SUKSES);
                if(sukses == 1){
                    member = json.getJSONArray(TAG_MEMBER);
                    for (int i = 0; i <member.length();i++){
                        JSONObject c = member.getJSONObject(i);

                        String tanggal = c.getString(TAG_IDMEM);
                        String jumlah = c.getString(TAG_NAMA);
                        HashMap<String,String> map = new HashMap<String, String>();
                        map.put(TAG_IDMEM,tanggal);
                        map.put(TAG_NAMA,jumlah);
                        memberList.add(map);
                    }
                } else{
                    Intent i = new Intent(getApplicationContext(),PemasukanActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(RiwayatActivity.this,
                    memberList,R.layout.list_item,new String[]
                    {TAG_IDMEM,TAG_NAMA}, new int[] {R.id.tanggal, R.id.jumlah});
            lv.setAdapter(adapter);
        }
    }
}