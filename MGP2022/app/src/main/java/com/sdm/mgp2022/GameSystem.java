package com.sdm.mgp2022;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();
    public static final String SHARED_PREF_ID = "GameSaveFile";
    public static final String LEADERBOARD_PREF_ID = "LeaderboardFile";

    // Game stuff
    private boolean isPaused = false;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    private int hp = 3;
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
        // Get our shared preferences (Save File)
        sharedPref = GamePage.Instance.getSharedPreferences(SHARED_PREF_ID, 0);

        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new LosePage());
        StateManager.Instance.AddState(new WinPage());
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new MainGameSceneState());

        ResetGameValues();
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
        hp -= 1;
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
        hp = 3;
        score = 0;

        //Live Score
        //GameSystem.Instance.SaveEditBegin();
        //GameSystem.Instance.SetIntInSave("Score", 0);
        //GameSystem.Instance.SaveEditEnd();
    }

    public void SaveEditBegin()
    {
        // Safety check, only allow if not already editing
        if(editor != null)
            return;

        // Start the editing
        editor = sharedPref.edit();
    }

    public void SaveEditEnd()
    {
        // Check if has editor
        if(editor == null)
            return;

        editor.commit();
        editor = null;  // Safety to ensure other functions will fail once commit done
    }

    public void SetIntInSave(String _key, int _value)
    {
        if(editor == null)
            return;

        editor.putInt(_key, _value);
    }

    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key, 0);
    }
}
