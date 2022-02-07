package com.example.foodapp.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foodapp.R;

import java.util.ArrayList;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    /**String[] titleList;*/
    LayoutInflater inflater;
    ArrayList<String> titleList;

    public CustomBaseAdapter(Context ctx, ArrayList<String> titleList) {
        this.context = ctx;
        this.titleList = titleList;
        inflater = (LayoutInflater.from(ctx));

    }


    @Override
    public int getCount() {
        return titleList.size();

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


        item1.setText(titleList.get(i));

        return view;
    }
}
