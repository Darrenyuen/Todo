package com.example.yuan.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Todo> todoList = new ArrayList<>();
    private ListView listView;
    private TodoAdapter todoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.todoList);
        todoList.add(new Todo("学习"));
        todoList.add(new Todo("写代码"));
        todoList.add(new Todo("健身"));
//        String[] todo = new String[]{"看书", "写代码", "健身"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todo);
        todoAdapter = new TodoAdapter(this, R.layout.todo_item, todoList);
        listView.setAdapter(todoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = todoList.get(position);
                Toast.makeText(MainActivity.this, todo.getTodo(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
