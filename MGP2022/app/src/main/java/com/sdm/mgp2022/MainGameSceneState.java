package com.sdm.mgp2022;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    private Joystick jstick, jstick2;
    private PlayerEntity player;
    private List<EnemyEntity> enemyList = new ArrayList<EnemyEntity>();
    private EnemyEntity enemy;

    float spawnTimer = 0, spawnInterval = 100;
    float shootTimer;

    double currentTarget = 9999;
    int ScreenWidth, ScreenHeight;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view) {

        RenderBackground.Create();
       //SmurfEntity.Create();

        player = PlayerEntity.Create();
        //StarEntity.Create();
        PauseButtonEntity.Create();
        RenderText.Create(player);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        jstick = Joystick.Create();     //Move Joystick
        jstick2 = Joystick.Create(2000, 680, 2000, 680, 80, 40);    //Aim Joystick

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

        //String scoreText = String.format("SCORE : %d", GameSystem.Instance.GetIntFromSave("Score"));

        //Paint paint = new Paint();
        //paint.setColor(Color.WHITE);
        //paint.setTextSize(64);

        //_canvas.drawText(scoreText, 10, 220, paint);
    }

    @Override
    public void Update(float _dt) {

        if(GameSystem.Instance.GetHealth() <= 0)
        {
            int curr_score = GameSystem.Instance.GetScore();
            GameSystem.Instance.SaveEditBegin();

            if (!GameSystem.Instance.sharedPref.contains("HScore"))
            {
                GameSystem.Instance.SetIntInSave("HScore", curr_score);
                Leaderboard.Instance.AddToScoreboard(curr_score);
            }

            else
            {
                if(curr_score >= GameSystem.Instance.GetIntFromSave("HScore"))
                {
                    GameSystem.Instance.SetIntInSave("HScore", curr_score);
                    Leaderboard.Instance.AddToScoreboard(curr_score);
                }
            }

            GameSystem.Instance.SaveEditEnd();

            GamePage.Instance.LoseGame();
            player.ResetGameValues();
            spawnTimer = 0;
            shootTimer = 0;
        }

        /*else if(GameSystem.Instance.GetScore() >= 100)
        {
            GamePage.Instance.WinGame();
            player.ResetGameValues();
            spawnTimer = 0;
            shootTimer = 0;
        }*/

        //StateManager.Instance.PrintAllStates();
        //Enemy Spawner
        if (spawnTimer <= 0 && !GameSystem.Instance.GetIsPaused()) {
            spawnTimer += spawnInterval;

            enemyList.add(EnemyEntity.Create());

            //Log.d("enemyspawner", "spawned an enemy");
        }
        else {
            if(!GameSystem.Instance.GetIsPaused())
                spawnTimer--;
            //Log.d("timer", "second until spawn: " + spawnTimer);
        }

        //Bullet Spawner
        if (shootTimer <= 0 && !GameSystem.Instance.GetIsPaused()) {
            shootTimer += player.shootInterval;

            if(player.canFire)
            {
                BulletEntity bullet = BulletEntity.Create(player.GetTargetX(), player.GetTargetY());
                bullet.xPos = player.GetPosX();
                bullet.yPos = player.GetPosY();
            }

           // Log.d("enemyspawner", "spawned an enemy");
        }
        else {
            if(!GameSystem.Instance.GetIsPaused())
                shootTimer--;
            //Log.d("timer", "second until shoot: " + shootTimer);
        }

        //Joystick Movement
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

            //JSTICK2

            //if(TouchManager.Instance.GetPosX() == jstick2.)
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
        double currentTarget = 9999;
        while (iteratorEnemy.hasNext())
        {
            EnemyEntity enemyIT = iteratorEnemy.next();
            enemyIT.MoveToTarget(player.GetPosX(), player.GetPosY());
            //Log.d("ITERATOR MOVEMENT", "PLAYER X: " + player.GetPosX() + "PLAYER Y: " + player.GetPosY());
            //Log.d("ITERATOR MOVEMENT", "ENEMYIT XPOS: " + enemyIT.GetPosX() + "ENEMYIT YPOS: " + enemyIT.GetPosY());

            if(enemyIT.GetDistanceFromPlayer() < currentTarget)
            {
                currentTarget = enemyIT.GetDistanceFromPlayer();

                //If enemy is within screen
                if(enemyIT.xPos > 0 && enemyIT.xPos < ScreenWidth &&
                        enemyIT.yPos > 0 && enemyIT.yPos < ScreenHeight)
                {
                    //Player aim closest
                    player.SetTarget(enemyIT.GetPosX(), enemyIT.GetPosY());
                    player.canFire = true;
                }

                //if target outside of screen dont shoot
                else if(enemyIT.xPos < 0 && enemyIT.xPos > ScreenWidth &&
                        enemyIT.yPos < 0 && enemyIT.yPos  > ScreenHeight)
                {
                    player.canFire = false;
                }
            }

        }

        EntityManager.Instance.Update(_dt);
        player.UpdateJoystick(jstick);
    }

    public void EndGame()
    {
        //Intent intent = new Intent();
        //intent.setClass(this, Lo)
    }

}



