package com.sdm.mgp2022;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

public class LeaderboardPage extends Activity implements OnClickListener, StateBase {

    private Button btn_back;
    private LinearLayout layout_ldb;

    private Map<String, Integer> leaderboard_list = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Create", "On Create");
        super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.leaderboard);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this); //Set Listener to this button --> Back Button

        layout_ldb = (LinearLayout)findViewById((R.id.LLayout));

        leaderboard_list = Leaderboard.leaderboard;
        for(Map.Entry<String, ?> entry : leaderboard_list.entrySet())
        {
            TextView textView = new TextView(this);
            textView.setText(entry.getKey() + ": " + entry.getValue());
            textView.setTextSize(16);
            textView.setTextColor(Color.WHITE);
            textView.setTextAlignment(textView.TEXT_ALIGNMENT_CENTER);
            layout_ldb.addView(textView);
            //Log.d("New SB", "Name: " + entry.getKey() + ", Score: " + entry.getValue());
        }

        StateManager.Instance.AddState(new LeaderboardPage());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        if (v == btn_back)
        {
            intent.setClass(this, Mainmenu.class);
        }

        startActivity(intent);
    }

    @Override
    public String GetName() {
        return "LeaderboardPage";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        Log.d("Enter", "On Enter");
    }

    @Override
    public void OnExit() {

    }

    @Override
    public void Render(Canvas _canvas) {

    }

    @Override
    public void Update(float _dt) {
        leaderboard_list = Leaderboard.leaderboard;
    }
}