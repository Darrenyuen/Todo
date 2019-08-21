package com.example.yuan.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.todoList)
    ListView listView;
    @BindView(R.id.toAddTodoActivity)
    FloatingActionButton floatingActionButton;

    private List<Todo> todoLists;
    private TodoAdapter todoAdapter;

    @OnClick(R.id.toAddTodoActivity)
    void toAddTodoActivity() {
        Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        todoLists = new LinkedList<>();
        loadDataFromDatabase();
        todoAdapter = new TodoAdapter(this, R.layout.item_todo, todoLists);
        listView.setAdapter(todoAdapter);
    }

    public void loadDataFromDatabase() {
        Log.d(TAG, "load();: " + "查询数据库");
        // TODO: 2019/8/21 从SQLite数据库中查询数据
        DatabaseHelper databaseHelper = new DatabaseHelper(this, "TODO", null, 1);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        // TODO: 2019/8/21 遍历查询所有todo对象
        String sql = "select * from todo";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        Todo todo;
        while (cursor.moveToNext()) {
            todo = new Todo();
            todo.setTodo(cursor.getString(cursor.getColumnIndex("title")));
            todo.setDate(cursor.getString(cursor.getColumnIndex("date")));
            todo.setTime(cursor.getString(cursor.getColumnIndex("time")));
            todo.setCode(cursor.getInt(cursor.getColumnIndex("code")));
            Log.d(TAG, "load(): " + todo.getTodo());
            todoLists.add(todo);
        }
        sqLiteDatabase.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        todoLists.clear();
        loadDataFromDatabase();
        todoAdapter = new TodoAdapter(this, R.layout.item_todo, todoLists);
        listView.setAdapter(todoAdapter);
        Log.d(TAG, "onRestart: " + todoLists.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
