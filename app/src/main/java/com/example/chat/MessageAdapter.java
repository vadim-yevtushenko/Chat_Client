package com.example.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;


public class MessageAdapter extends SimpleAdapter {

    private int resource;
    private LayoutInflater inflater;
    private List<? extends Map<String, ?>> data;
    private String[] from;

    public MessageAdapter(Context context,
                          List<? extends Map<String, ?>> data,
                          int resource,
                          String[] from,
                          int[] to) {
        super(context, data, resource, from, to);
        this.data = data;
        this.resource = resource;
        this.from = from;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View container = inflater.inflate(resource, parent, false);
        TextView tvMessage = container.findViewById(R.id.tvName);
        Map<String, String> messageMap = (Map<String, String>) data.get(position);
        tvMessage.setText(messageMap.get(from[0]));
        return container;
    }
}
