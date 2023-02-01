package com.sdm.mgp2022;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Leaderboard {

    public static final Leaderboard Instance = new Leaderboard();
    public static Map<String, Integer> leaderboard = new HashMap<>();

    public static String LEADERBOARD_PREF_ID = "LeaderboardFile";
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    private SurfaceView view = null;

    public void Init(SurfaceView _view)
    {
        view = _view;
        //Retrieve the leaderboard
        sharedPref = GamePage.Instance.getSharedPreferences(LEADERBOARD_PREF_ID, 0);

        Map<String, ?> allEntries = sharedPref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            leaderboard.put(entry.getKey(), (Integer) entry.getValue());
        }
    }

    public void AddToScoreboard(int score)
    {
        int count = leaderboard.size() + 1;
        String stringTag = "Player" + count;
        Log.d("Scoreboard Player", stringTag);

        SaveEditBegin();
        SetIntInSave(stringTag, score);
        SaveEditEnd();
        Log.d("Add SB", "ADDED TO SCOREBOARD");
    }

    public void ClearScoreboard()
    {
        editor = sharedPref.edit();
        editor.clear();
        editor.commit();
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