package com.example.hawking.geturltest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnGet, btnMap;
    TextView txtResult;
    EditText txtEmail, txtPass;
//    String urlStr = "http://echo.jsontest.com/insert-key-here/insert-value-here/key/values";
    String urlGET = "http://www.davdk.tk/controllers/getCoordinateJson.php?id_device=1&start_date=1480032000&end_date=1480118340";
    String urlPost = "http://davdk.tk/index.php?ctr=auth&act=login_mobile_post";
    int auth = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnMap = (Button) findViewById(R.id.btnMap);
        btnGet = (Button) findViewById(R.id.btnGet);
        txtResult = (TextView) findViewById(R.id.txtResult);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    String jsonStr = readJsonFromUrl(urlGET).toString();
//                    System.out.println(jsonStr);
//
//                    JSONObject js = new JSONObject(jsonStr);
//                    System.out.println(js.toString());
//                    System.out.println(js.getString("lat"));
                    String rs = "xxx";
                    ArrayList<JSONObject> list = readListJsonFromUrl(urlGET);
                    for (JSONObject json: list){
                        System.out.println("json-- "+json.getString("address")+"||"+json.getString("lat"));
//                        rs += json.getString("lat") + "||";
                    }
                    txtResult.setText(rs);
                } catch (IOException io){
                    io.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public String readJsonFromUrl(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000); // millis
        conn.setConnectTimeout(15000); // millis
        conn.setDoOutput(true);
        conn.connect();
        int response = conn.getResponseCode();
        System.out.print(response);
        try {
            InputStream in = new BufferedInputStream(conn.getInputStream());

            InputStreamReader is = new InputStreamReader(in);
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(is);
            String read = br.readLine();
            sb.append(read);
//            System.out.println("json line: " + read);
//            while (read != null) {
//                System.out.println(read);
//                sb.append(read);
//                read = br.readLine();
//            }
            return sb.toString();
        } finally {
            conn.disconnect();
        }
    }

    public ArrayList<JSONObject> readListJsonFromUrl(String urlStr) throws IOException {
        ArrayList<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000); // millis
        conn.setConnectTimeout(15000); // millis
        conn.setDoOutput(true);
        conn.connect();
        int response = conn.getResponseCode();
        System.out.print(response);
        try {
            InputStream in = new BufferedInputStream(conn.getInputStream());
            InputStreamReader is = new InputStreamReader(in);
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(is);
            String read = br.readLine();

            while (read != null) {
                sb.append(read);
                JSONObject reader = new JSONObject(read.toString().trim());
                jsonObjects.add(reader);
                read = br.readLine();
            }
            return jsonObjects;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

