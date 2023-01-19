package com.sdm.mgp2022;

import android.app.Activity;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;

// Created by TanSiewLan2021

public class Mainmenu extends Activity implements OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_start;
    private Button btn_options;
    private Button btn_quit;
    private Button btn_leaderboard;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.mainmenu);

        btn_start = (Button)findViewById(R.id.btn_mm_start);
        btn_start.setOnClickListener(this); //Set Listener to this button --> Start Button

        btn_options = (Button)findViewById(R.id.btn_mm_options);
        btn_options.setOnClickListener(this); //Set Listener to this button --> Options Button

        btn_leaderboard = (Button)findViewById(R.id.btn_mm_leaderboard);
        btn_leaderboard.setOnClickListener(this); //Set Listener to this button --> Back Button

        btn_quit = (Button)findViewById(R.id.btn_mm_quit);
        btn_quit.setOnClickListener(this); //Set Listener to this button --> Back Button

        StateManager.Instance.AddState(new Mainmenu());

        mp = MediaPlayer.create(this, R.raw.select);

    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        Intent intent = new Intent();

        if (v == btn_start)
        {
            // intent --> to set to another class which another page or screen that we are launching.
            intent.setClass(this, GamePage.class);
            //AudioManager.Instance.PlayAudio(R.raw.select,0.9f);
            StateManager.Instance.ChangeState("MainGame"); // Default is like a loading page
        }

        else if (v == btn_options)
        {
            //AudioManager.Instance.PlayAudio(R.raw.select, 0.9f);
            intent.setClass(this, Options.class);
        }

        else if (v == btn_leaderboard)
        {
            intent.setClass(this, LeaderboardPage.class);
        }

        else if (v == btn_quit)
        {
            this.finishAffinity();
        }

        mp.start();
        startActivity(intent);

    }

    @Override
    public void Render(Canvas _canvas) {
    }
	
    @Override
    public void OnEnter(SurfaceView _view) {
    }
	
    @Override
    public void OnExit() {
    }
	
    @Override
    public void Update(float _dt) {
    }
	
    @Override
    public String GetName() {
        return "Mainmenu";
    }

    @Override
    protected void onPause() {
        super.onPause();
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
