package com.example.foodapp.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foodapp.R;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    String[] titleList;
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, String [] titleList) {
        this.context = ctx;
        this.titleList = titleList;
        inflater = (LayoutInflater.from(ctx));

    }


    @Override
    public int getCount() {
        return titleList.length;

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.listitem, null);
        TextView item1 = (TextView) view.findViewById(R.id.listPriceText);


        item1.setText(titleList[i]);

        return view;
    }
}
