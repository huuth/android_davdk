package com.example.hawking.geturltest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

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

/**
 * Created by thuytien on 4/17/17.
 */

public class hanhtrinhFragment extends Fragment implements OnMapReadyCallback {
    private View rootView;
    GoogleMap mMap;
    MapView mMapView;
    String urlGET = "http://www.davdk.tk/controllers/getCoordinateJson.php?id_device=1&start_date=1480032000&end_date=1480118340";

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fragment_hanhtrinh, container, false);
            MapsInitializer.initialize(this.getActivity());
            mMapView = (MapView) rootView.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
        }
        catch (InflateException e){
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mMap = googleMap;
        ArrayList<LatLng> coordList = new ArrayList<LatLng>();
        try {
            ArrayList<JSONObject> list = readListJsonFromUrl(urlGET);
            for(JSONObject ll: list){
                double lat = Float.parseFloat(ll.getString("lat"));
                double lng = Float.parseFloat(ll.getString("lng"));
                coordList.add(new LatLng(lat, lng));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PolylineOptions polylineOptions = new PolylineOptions();

// Create polyline options with existing LatLng ArrayList
        polylineOptions.addAll(coordList);
        polylineOptions
                .width(5)
                .color(Color.RED);

// Adding multiple points in map using polyline and arraylist
        mMap.addPolyline(polylineOptions);
        mMap.addMarker(new MarkerOptions().position(new LatLng(coordList.get(0).latitude, coordList.get(0).longitude)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(coordList.get(coordList.size()-1).latitude, coordList.get(coordList.size()-1).longitude)));
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(coordList.get(coordList.size()-1).latitude, coordList.get(coordList.size()-1).longitude));

        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
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

}