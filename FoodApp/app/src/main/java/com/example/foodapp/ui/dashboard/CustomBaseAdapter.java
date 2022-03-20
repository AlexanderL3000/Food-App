package com.example.foodapp.ui.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.MainActivity;
import com.example.foodapp.R;
import com.example.foodapp.SellActivity;

import java.util.ArrayList;

public class CustomBaseAdapter extends BaseAdapter {

    Context contextA;
    Context context;
    LayoutInflater inflater;
    ArrayList<String> titleList;
    ArrayList<String> titleList2;
    Dialog mDialog;

    public CustomBaseAdapter(Context ctx, Context Actx, ArrayList<String> titleList, ArrayList<String> titleList2) {
        this.context = ctx;
        this.contextA = Actx;
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
        ImageButton ib1 = (ImageButton) view.findViewById(R.id.imageButton1);
        ImageButton ib2 = (ImageButton) view.findViewById(R.id.imageButton2);

        if (i < titleList.size()) {
            item1.setText(titleList.get(i));
            ib1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context.getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
                    /*Intent zoom=new Intent(context.getApplicationContext(), SellActivity.class);
                    zoom.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(zoom);*/
                    mDialog = new Dialog(contextA);
                    mDialog.setContentView(R.layout.buy_popup);
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    mDialog.show();


                }
            });
        }else{
            return view;
        }

        if (i < titleList2.size()) {
            item2.setText(titleList2.get(i));
            ib2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context.getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
                    mDialog = new Dialog(contextA);
                    mDialog.setContentView(R.layout.buy_popup);
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    mDialog.show();
                }
            });
        }else{
            return view;
        }
        return view;

    }
}
