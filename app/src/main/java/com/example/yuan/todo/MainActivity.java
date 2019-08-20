package com.example.yuan.todo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindString;
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
        todoLists = read();
        todoAdapter = new TodoAdapter(this, R.layout.item_todo, todoLists);
        listView.setAdapter(todoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (view.getId()) {
                    case R.id.deleteItem: todoLists.remove(position); break;
                    default: ToastUtil.showShort(MainActivity.this, todoLists.get(position).getTodo());
                }
            }
        });
    }

    public List<Todo> read() {
        List<Todo> list = new LinkedList<>();
        ObjectInputStream objectInputStream = null;
        try {
            FileInputStream fileInputStream = this.openFileInput(AddTodoActivity.fileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            Todo todoFromFile = (Todo) objectInputStream.readObject();
            Log.d(TAG, "read: " + todoFromFile.toString());
            list.add(todoFromFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    protected void onStart() {
        super.onStart();
        todoLists = read();
    }

}
