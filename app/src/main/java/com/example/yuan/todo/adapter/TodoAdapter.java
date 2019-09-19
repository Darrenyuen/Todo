package com.example.yuan.todo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuan.todo.AlarmService;
import com.example.yuan.todo.DatabaseHelper;
import com.example.yuan.todo.R;
import com.example.yuan.todo.bean.Todo;
import com.example.yuan.todo.util.ToastUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoAdapter extends ArrayAdapter<Todo> {

    private final String TAG = this.getContext().getClass().getSimpleName();

    int resourceId;
    private List<Todo> todoList;

    public TodoAdapter( Context context, int resource, List<Todo> objects) {
        super(context, resource, objects);
        resourceId = resource;
        todoList = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: " + position);
        final Todo todo = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        final char[] todoTitle = todo.getTodo().toCharArray();
        Log.d(TAG, "getView: " + todoTitle[0]);
        viewHolder.todo.setText(String.valueOf(todoTitle[0]));
        viewHolder.date.setText(todo.getDate());
        viewHolder.time.setText(todo.getTime());
        //判断是否过期
        Calendar calendar = Calendar.getInstance();
        String[] date = todo.getDate().split("-");
        int setYear = Integer.parseInt(date[0]);
        int setMonth = Integer.parseInt(date[1]);
        int setDay = Integer.parseInt(date[2]);
        String[] time = todo.getTime().split(":");
        int setHour = Integer.parseInt(time[0]);
        int setMin = Integer.parseInt(time[1]);
        Log.d(TAG, "getView: " + setYear + setMonth + setDay + setHour + setMin);

        viewHolder.todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + getItem(position).getTodo());
                ToastUtil.showShort(getContext(), getItem(position).getTodo());
            }
        });

        viewHolder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("是否删除该待办事项");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        todoList.remove(position);
                        notifyDataSetChanged();
                        DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), "TODO", null, 1);
                        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
                        //执行多条件sql语句进行删除具体数据项
                        String sql = "delete from todo where title = '" + todo.getTodo() +"' and date = '" + todo.getDate() + "' and time = '" + todo.getTime() + "' and code = '" + todo.getCode() + "'";
                        sqLiteDatabase.execSQL(sql);
                        sqLiteDatabase.close();
                        Intent intentToAlarmService = new Intent(getContext(), AlarmService.class);
                        intentToAlarmService.putExtra("todo", todo.getTodo());
                        intentToAlarmService.putExtra("date", todo.getDate());
                        intentToAlarmService.putExtra("time", todo.getTime());
                        intentToAlarmService.putExtra("remindTypeCode", todo.getCode());
                        intentToAlarmService.putExtra("isSetAlarm", false);
                        getContext().startService(intentToAlarmService);
                        Log.d(TAG, "onClick: " + "删除成功");
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });

        return view;
    }

    class ViewHolder {

        @BindView(R.id.todo)
        TextView todo;
        @BindView(R.id.dateText)
        TextView date;
        @BindView(R.id.timeText)
        TextView time;
        @BindView(R.id.deleteItem)
        ImageView deleteItem;
        @BindView(R.id.isOut)
        TextView outText;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
