package com.sdm.mgp2022;

import android.app.Activity;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.CheckBox;

// Created by TanSiewLan2021

public class Options extends Activity implements OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_back;
    private CheckBox cb_sfx;
    private CheckBox cb_music;
    private boolean sfxState = false, musicState = true;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.option);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this); //Set Listener to this button --> Back Button

        cb_sfx = (CheckBox) findViewById(R.id.cb_sfx);
        //cb_sfx.setOnClickListener(this); //Set Listener to this button --> Back Button

        cb_music = (CheckBox)findViewById(R.id.cb_music);
        //cb_music.setOnClickListener(this); //Set Listener to this button --> Back Button
        cb_sfx.setChecked(AudioManager.Instance.GetSFXState());
        cb_music.setChecked(AudioManager.Instance.GetMusicState());
        StateManager.Instance.AddState(new Options());

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

        /*if (v == btn_options)
        {
            // intent --> to set to another class which another page or screen that we are launching.
            intent.setClass(this, GamePage.class);
            StateManager.Instance.ChangeState("Options"); // Default is like a loading page

        }*/

        if (v == btn_back)
        {
            intent.setClass(this, Mainmenu.class);
            if(cb_sfx.isChecked())
                sfxState = true;
            else
                sfxState = false;

            if(cb_music.isChecked())
                musicState = true;
            else
                musicState = false;

            AudioManager.Instance.SetAudioState(sfxState, musicState);
            //AudioManager.Instance.PlayAudio(R.raw.select,0.9f);
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
        return "Options";
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
