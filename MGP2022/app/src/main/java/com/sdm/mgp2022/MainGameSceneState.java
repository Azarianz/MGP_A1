package com.sdm.mgp2022;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    private Joystick jstick;
    private PlayerEntity player;
    private List<EnemyEntity> enemyList = new ArrayList<EnemyEntity>();
    private EnemyEntity enemy;

    float spawnTimer = 0, spawnInterval = 120;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        RenderBackground.Create();
       //SmurfEntity.Create();
        RenderText.Create();
        player = PlayerEntity.Create();
        jstick = Joystick.Create();
        StarEntity.Create();
        PauseButtonEntity.Create();
        InventoryEntity.Create();

        // Example to include another Renderview for Pause Button
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        GamePage.Instance.finish();
    }

    @Override
    public void Render(Canvas _canvas) {
        EntityManager.Instance.Render(_canvas);

    }

    @Override
    public void Update(float _dt) {

        //Spawner
        if (spawnTimer <= 0 && !GameSystem.Instance.GetIsPaused()) {
            spawnTimer += spawnInterval;

            enemyList.add(EnemyEntity.Create());

            Log.d("enemyspawner", "spawned an enemy");
        }
        else {
            if(!GameSystem.Instance.GetIsPaused())
                spawnTimer--;
            Log.d("timer", "second until spawn: " + spawnTimer);
        }

        EntityManager.Instance.Update(_dt);

        //Joystick
        if (TouchManager.Instance.IsDown())
        {
            //Example of touch on screen in the main game to trigger back to Main menu
            //StateManager.Instance.ChangeState("Mainmenu");
            jstick.setIsPressed(true);

            //Joystick
            if(jstick.isPressed(TouchManager.Instance.GetPosX(),
                    TouchManager.Instance.GetPosY()))
            {
                jstick.setIsPressed(true);
            }
        }
        else if(TouchManager.Instance.IsMove()){
            if(jstick.getIsPressed())
            {
                jstick.setActuator(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY());
            }
        }
        else if(TouchManager.Instance.IsUp())
        {
            jstick.setIsPressed(false);
            jstick.resetActuator();
        }

        //Movement Update
        Iterator<EnemyEntity> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()) {
            EnemyEntity enemyIT = iteratorEnemy.next();
            enemyIT.MoveToTarget(player.GetPosX(), player.GetPosY());
            //Log.d("ITERATOR MOVEMENT", "PLAYER X: " + player.GetPosX() + "PLAYER Y: " + player.GetPosY());
            //Log.d("ITERATOR MOVEMENT", "ENEMYIT XPOS: " + enemyIT.GetPosX() + "ENEMYIT YPOS: " + enemyIT.GetPosY());
        }
        player.UpdateJoystick(jstick);
    }

}



