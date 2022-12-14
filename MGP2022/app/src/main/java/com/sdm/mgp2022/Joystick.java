package com.sdm.mgp2022;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.constraintlayout.widget.ConstraintSet;

public class Joystick implements EntityBase {

    private int outerCircleCenterPositionX = 275;
    private int outerCircleCenterPositionY = 750;
    private int innerCircleCenterPositionX = 275;
    private int innerCircleCenterPositionY = 750;
    private int outerCircleRadius = 60;
    private int innerCircleRadius = 40;
    private Paint outerCirclePaint;
    private Paint innerCirclePaint;

    private boolean isPressed = false;
    private double joystickCenterToTouchDistance;
    private double actuatorX;
    private double actuatorY;

    private boolean isDone = false;
    private boolean isInit = false;

    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius);
    }

    public void setActuator(double touchPositionX, double touchPositionY) {
        double deltaX = touchPositionX - outerCircleCenterPositionX;
        double deltaY = touchPositionY - outerCircleCenterPositionY;
        double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        if(deltaDistance < outerCircleRadius) {
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        } else {
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }
    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPositionX - touchPositionX, 2) +
                        Math.pow(outerCircleCenterPositionY - touchPositionY, 2)
        );
        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }

    public void resetActuator() {
        actuatorX = 0;
        actuatorY = 0;
    }

    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {
        isInit = true;
    }

    public void Update(float _dt) {
        if(TouchManager.Instance.HasTouch())
        {
            if(TouchManager.Instance.IsDown()){
                if(isPressed((double)TouchManager.Instance.GetPosX(), (double)TouchManager.Instance.GetPosY()))
                {
                    setIsPressed(true);
                }
            }

            else if(TouchManager.Instance.IsMove()){
                if(getIsPressed())
                {
                    setActuator((double)TouchManager.Instance.GetPosX(), (double)TouchManager.Instance.GetPosY());
                }
            }
        }

        else
        {
            setIsPressed(false);
            resetActuator();
        }

        updateInnerCirclePosition();
    }

    public void Render(Canvas _canvas) {
        // paint of circles
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.BLUE);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // Draw outer circle
        _canvas.drawCircle(100,200,60,outerCirclePaint);
        _canvas.drawCircle(100,200,40,innerCirclePaint);
    }

    public boolean IsInit() { return true; }

    public void SetRenderLayer(int _newLayer) {
        return;
    }

    public int GetRenderLayer() {
        return LayerConstants.RENDERTEXT_LAYER;
    }

    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_JOYSTICK;
    }

    public static Joystick Create() {
        Joystick result = new Joystick();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_JOYSTICK);
        return result;
    }
}
