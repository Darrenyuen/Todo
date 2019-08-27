package com.example.yuan.todo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yuan.todo.R;
import com.example.yuan.todo.bean.TodayInHistory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jarvis yuen
 * Email: yuansssf@gmail.com
 */
public class TodayHisAdapter extends ArrayAdapter<TodayInHistory> {

    final String TAG = getContext().getClass().getSimpleName();

    private int resourceId;

    public TodayHisAdapter(Context context, int resource, List<TodayInHistory> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TodayInHistory todayInHistory = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Log.d(TAG, "getView: " + todayInHistory.getTitle());
        Glide.with(getContext()).load(todayInHistory.getUrl()).into(viewHolder.imageView);
        viewHolder.title.setText(todayInHistory.getTitle());
        viewHolder.date.setText(todayInHistory.getDate());
        viewHolder.luar.setText(todayInHistory.getLuar());
        viewHolder.des.setText(todayInHistory.getDes());
        return view;
    }

    class ViewHolder {

        @BindView(R.id.pic)
        ImageView imageView;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.luar)
        TextView luar;
        @BindView(R.id.des)
        TextView des;

        public ViewHolder(View view) {
            super();
            ButterKnife.bind(this, view);
        }
    }
}
