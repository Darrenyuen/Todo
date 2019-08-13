package com.example.yuan.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends ArrayAdapter<Todo> {

    private int resourceId;
    private List<Todo> list;

    public TodoAdapter( Context context, int resource, List<Todo> objects) {
        super(context, resource, objects);
        resourceId = resource;
        list = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Todo todo = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.firstChar);
            viewHolder.deleteView = view.findViewById(R.id.delete);
            view.setTag(viewHolder);
        }
        viewHolder.textView.setText(todo.getTodo());
        viewHolder.deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
            }
        });
        return view;
    }

    static class ViewHolder {
        TextView textView;
        TextView deleteView;
    }
}
