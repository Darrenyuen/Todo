package com.example.yuan.todo;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AlertActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean isPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = MediaPlayer.create(this, R.raw.ring01);
        mediaPlayer.start();
        new AlertDialog.Builder(AlertActivity.this).setIcon(R.drawable.ic_remind).setTitle("待办事项").setMessage(getIntent().getStringExtra("todo")).setPositiveButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertActivity.this.finish();
            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
