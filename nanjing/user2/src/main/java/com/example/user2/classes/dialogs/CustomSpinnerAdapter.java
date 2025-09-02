package com.example.user2.classes.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private List<String> dataList;
    private int count; // 用于保存选项数量
    public CustomSpinnerAdapter(Context context, List<String> dataList) {
        super(context, android.R.layout.simple_spinner_item, dataList);
        this.dataList = dataList;
    }
    // 添加一个方法来设置选项数量
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public String getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(dataList.get(position));
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(dataList.get(position));
        return view;
    }
}

