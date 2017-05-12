package com.example.hawking.geturltest;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by thuytien on 4/17/17.
 */

public class BaoduongFragment extends Fragment {

    String urlBaoDuong = "http://davdk.tk/index.php?ctr=statusmotor&act=get_status_mobile&id_device=1";
    ProgressBar progressDau, progressBarLoc, progressBarBugi, progressBarXich;
    TextView txtPercentageBugi, txtPercentageXich, txtPercentageDau, txtPercentageLoc;
    EditText txtTong;
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baoduong,container,false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDau = (ProgressBar) view.findViewById(R.id.progressDau);
        progressBarBugi = (ProgressBar) view.findViewById(R.id.progressBarBugi);
        progressBarLoc = (ProgressBar) view.findViewById(R.id.progressBarLoc);
        progressBarXich = (ProgressBar) view.findViewById(R.id.progressBarXich);

        txtPercentageBugi = (TextView) view.findViewById(R.id.txtPercentageBugi);
        txtPercentageDau = (TextView) view.findViewById(R.id.txtPercentageDau);
        txtPercentageLoc = (TextView) view.findViewById(R.id.txtPercentageLoc);
        txtPercentageXich = (TextView) view.findViewById(R.id.txtPercentageXich);

        txtTong = (EditText) view.findViewById(R.id.txtTong);

        try {
            String jsonStr = readJsonFromUrl(urlBaoDuong);
            JSONObject json = new JSONObject(jsonStr);
            System.out.println("jsoooooonnnn: " + json.toString());
            progressBarBugi.setMax(100);
            progressBarBugi.setProgress((int) Math.ceil(Float.parseFloat(json.getString("bugi"))));
            txtPercentageBugi.setText((int) Math.ceil(Float.parseFloat(json.getString("bugi"))) + "%");

            progressBarXich.setMax(100);
            progressBarXich.setProgress((int) Math.ceil(Float.parseFloat(json.getString("chain"))));
            txtPercentageXich.setText((int) Math.ceil(Float.parseFloat(json.getString("chain"))) + "%");

            progressBarLoc.setMax(100);
            progressBarLoc.setProgress((int) Math.ceil(Float.parseFloat(json.getString("air_filter"))));
            txtPercentageLoc.setText((int) Math.ceil(Float.parseFloat(json.getString("air_filter"))) + "%");

            progressDau.setMax(100);
            progressDau.setProgress((int) Math.ceil(Float.parseFloat(json.getString("lubricant"))));
            txtPercentageDau.setText((int) Math.ceil(Float.parseFloat(json.getString("lubricant"))) + "%");

            txtTong.setText(json.getString("total_distance")+" km");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
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
            System.out.println("json line: " + read);
            while (read != null) {
                System.out.println(read);
                sb.append(read);
                read = br.readLine();
            }
            return sb.toString();
        } finally {
            conn.disconnect();
        }
    }
}
