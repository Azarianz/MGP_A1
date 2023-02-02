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

    float shieldTimer = 100, shieldEndInterval = 100;

    double currentTarget = 9999;
    int ScreenWidth, ScreenHeight;
    int ScreenWScale, ScreenHScale;

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

        jstick = Joystick.Create((ScreenWidth / 5) , (ScreenHeight / 4) * 3, (ScreenWidth / 5), (ScreenHeight / 4) * 3, 90, 40);     //Move Joystick
        jstick2 = Joystick.Create((ScreenWidth / 5) * 4 , (ScreenHeight / 4) * 3, (ScreenWidth / 5) * 4 , (ScreenHeight / 4) * 3, 90, 40);    //Aim Joystick
        ScreenWScale = (ScreenWidth / 5);
        ScreenHScale = (ScreenHeight / 4);

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

        if(GameSystem.Instance.enemyCounter >=5 && !GameSystem.Instance.powerUpExist)
        {
            Random num = new Random();

            int newNum = num.nextInt(2);

            if(newNum == 0)
            {
                ShieldEntity.Create(GameSystem.Instance.xPos, GameSystem.Instance.yPos);
            }
            else if (newNum == 1)
            {
                HealthEntity.Create(GameSystem.Instance.xPos, GameSystem.Instance.yPos);
            }
            GameSystem.Instance.powerUpExist = true;
            GameSystem.Instance.enemyCounter = 0;
        }
        if(shieldTimer <= 0 && GameSystem.Instance.shieldActivated && !GameSystem.Instance.GetIsPaused())
        {
            shieldTimer += shieldEndInterval;
            GameSystem.Instance.shieldActivated = false;
        }
        else
        if(!GameSystem.Instance.GetIsPaused() && GameSystem.Instance.shieldActivated)
        {
            shieldTimer--;
        }

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
            //jstick.setIsPressed(true);

            //Joystick
            if(Collision.SphereToSphere(TouchManager.Instance.GetPosX(),
                    TouchManager.Instance.GetPosY(), 0.0f, ScreenWScale, ScreenHScale * 3, 150))
            {
                jstick.setIsPressed(true);
            }

            /*if(jstick.isPressed(TouchManager.Instance.GetPosX(),
                    TouchManager.Instance.GetPosY()))
            {
                jstick.setIsPressed(true);
            }
             */

            //Joystick2
            if(Collision.SphereToSphere(TouchManager.Instance.GetPosX(),
                    TouchManager.Instance.GetPosY(), 0.0f, ScreenWScale * 4, ScreenHScale * 3, 150))
            {
                jstick2.setIsPressed(true);
            }
        }
        else if(TouchManager.Instance.IsMove()){
            if(jstick.getIsPressed())
            {
                jstick.setActuator(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY());
            }

            if(jstick2.getIsPressed())
            {
                jstick2.setActuator(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY());
                //Player aim closest
                player.SetTarget(jstick2.getActuatorX(), jstick2.getActuatorY());

            }
        }
        else if(TouchManager.Instance.IsUp())
        {
            jstick.setIsPressed(false);
            jstick2.setIsPressed(false);
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

            /*
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
            */
        }

        if(player.GetTargetX() == 0 && player.GetTargetY() == 0)
        {
            player.canFire = false;
        }

        else{
            player.canFire = true;
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



