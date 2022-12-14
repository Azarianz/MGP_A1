package com.sdm.mgp2022;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;

// Created by TanSiewLan2021

public class WinPage extends Activity implements OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_playagain;
    private Button btn_quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.winmenu);

        btn_playagain = (Button)findViewById(R.id.btn_playAgain);
        btn_playagain.setOnClickListener(this); //Set Listener to this button --> Start Button

        btn_quit = (Button)findViewById(R.id.btn_quit);
        btn_quit.setOnClickListener(this); //Set Listener to this button --> Back Button

        StateManager.Instance.AddState(new Mainmenu());
    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        if (v == btn_playagain)
        {
            // intent --> to set to another class which another page or screen that we are launching.
            Intent intent = new Intent(WinPage.this, GamePage.class);
            StateManager.Instance.ChangeState("MainGame"); // Default is like a loading page
            startActivity(intent);
        }
        else if (v == btn_quit)
        {
            this.finishAffinity();
        }


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
        return "WinPage";
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
