package com.example.yuan.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoAdapter extends ArrayAdapter<Todo> {

    int resourceId;

    public TodoAdapter( Context context, int resource, List<Todo> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Todo todo = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        char[] todoTitle = todo.getTodo().toCharArray();
        viewHolder.todo.setText(todoTitle[0]);
        viewHolder.date.setText(todo.getDate());
        viewHolder.time.setText(todo.getTime());
        return view;
    }

    class ViewHolder {

        @BindView(R.id.todo)
        TextView todo;
        @BindView(R.id.dateText)
        TextView date;
        @BindView(R.id.timeText)
        TextView time;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
