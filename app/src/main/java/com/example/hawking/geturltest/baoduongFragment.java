package com.example.hawking.geturltest;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class baoduongFragment extends Fragment {
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baoduong,container,false);
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
