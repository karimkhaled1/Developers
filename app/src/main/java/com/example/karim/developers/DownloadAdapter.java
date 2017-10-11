package com.example.karim.developers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.karim.developers.R;

import java.util.ArrayList;

/**
 * Created by karim on 3/10/2017.
 */

public class DownloadAdapter extends ArrayAdapter<objectForsql> {
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View customview=layoutInflater.inflate(R.layout.downloaded,parent,false);
        objectForsql app=getItem(position);
        TextView appname=(TextView)customview.findViewById(R.id.appname);
        appname.setText(app.getPackage());
        return customview;
    }

    public DownloadAdapter(Context context, ArrayList<objectForsql> resource) {
        super(context, R.layout.downloaded, resource);
    }


}
