package com.sdm.mgp2022;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    private Joystick jstick;
    private PlayerEntity player;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        RenderBackground.Create();
        SmurfEntity.Create();
        RenderText.Create();
        player = PlayerEntity.Create();
        jstick = Joystick.Create();
        StarEntity.Create();
        PauseButtonEntity.Create();
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
        EntityManager.Instance.Update(_dt);

        if (TouchManager.Instance.IsDown())
        {
            //Example of touch on screen in the main game to trigger back to Main menu
            //StateManager.Instance.ChangeState("Mainmenu");
            Log.d("isDown", "IsDown");
            jstick.setIsPressed(true);

            //Joystick
            if(jstick.isPressed(TouchManager.Instance.GetPosX(),
                    TouchManager.Instance.GetPosY()))
            {
                jstick.setIsPressed(true);
                Log.d("isDown", "IsDown");
            }
            else{
                Log.d("isfckd", "JSTICK RETURNED FALSE AIEEEEEYO");
            }
        }

        else if(TouchManager.Instance.IsMove()){
            if(jstick.getIsPressed())
            {
                jstick.setActuator(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY());
                Log.d("IsMove", "IsMove");
            }
        }

        else if(TouchManager.Instance.IsUp())
        {
            jstick.setIsPressed(false);
            jstick.resetActuator();
        }

        player.UpdateJoystick(jstick);
    }

}



