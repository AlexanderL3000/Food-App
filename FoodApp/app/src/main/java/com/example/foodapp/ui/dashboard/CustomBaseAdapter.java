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
    LayoutInflater inflater;
    ArrayList<String> titleList;
    ArrayList<String> titleList2;

    public CustomBaseAdapter(Context ctx, ArrayList<String> titleList, ArrayList<String> titleList2) {
        this.context = ctx;
        this.titleList2 = titleList2;
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
        TextView item2 = (TextView) view.findViewById(R.id.listPriceText2);

        if (i < titleList.size()) {
            item1.setText(titleList.get(i));
        }else{
            return view;
        }

        if (i < titleList2.size()) {
            item2.setText(titleList2.get(i));
        }else{
            return view;
        }
        return view;
    }
}
