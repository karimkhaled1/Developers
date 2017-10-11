package com.example.karim.developers;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class myappsAdapter extends ArrayAdapter<aplication> {
    TextView name;
    TextView downloads;
    public myappsAdapter(Context context, ArrayList<aplication> resource) {
        super(context,R.layout.myapps ,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View customview=layoutInflater.inflate(R.layout.myapps,parent,false);
        name=(TextView)customview.findViewById(R.id.myappname);
        downloads=(TextView)customview.findViewById(R.id.mydownload);
        String getname=getItem(position).getPackageName();
        int numberOfDownloads=getItem(position).getDownloads();
        String x=numberOfDownloads+"";
        name.setText(name.getText()+getname);
        downloads.setText(downloads.getText()+x);
        return customview;
    }
}
