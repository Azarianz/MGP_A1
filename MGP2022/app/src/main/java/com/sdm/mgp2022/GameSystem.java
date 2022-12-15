package com.sdm.mgp2022;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();

    // Game stuff
    private boolean isPaused = false;
    private int hp = 100;
    private int score = 0;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {

    }

    public void Init(SurfaceView _view)
    {

        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new LosePage());
        StateManager.Instance.AddState(new WinPage());
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new MainGameSceneState());

    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

    public void TakeDamage()
    {
        hp -= 10;
    }

    public int GetHealth()
    {
        return hp;
    }

    public void AddScore(int val)
    {
        score += val;
    }

    public int GetScore(){return score;}

    public void ResetGameValues()
    {
        hp = 100;
        score = 0;
    }

}
