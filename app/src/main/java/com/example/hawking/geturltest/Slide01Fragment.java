package com.example.hawking.geturltest;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by thuytien on 4/17/17.
 */

public class Slide01Fragment extends Fragment implements View.OnClickListener{
    private Button btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide01,container,false);
        btn= (Button) view.findViewById(R.id.btn);
        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getContext(),ThirdActivity.class);
        startActivity(intent);
    }
}
