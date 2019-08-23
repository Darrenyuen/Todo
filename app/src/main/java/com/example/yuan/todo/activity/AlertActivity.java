package com.example.yuan.todo.activity;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.yuan.todo.R;

public class AlertActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    private String todo;
    private int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        todo = intent.getStringExtra("todo");
        code = intent.getIntExtra("remindTypeCode", 0);
        Log.d(TAG, "onCreate: " + todo + code);

        if (code == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bell);
            mediaPlayer.start();
            new AlertDialog.Builder(AlertActivity.this).setIcon(R.drawable.ic_remind).setTitle("待办事项").setMessage(todo).setPositiveButton("收到", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertActivity.this.finish();
                }
            }).show();
        } else {
            vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(30000);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_remind).setTitle("待办事项").setMessage(todo).setPositiveButton("收到", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    vibrator.cancel();
//                    AlertActivity.this.finish();
                    onDestroy();
                }
            });
            builder.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
