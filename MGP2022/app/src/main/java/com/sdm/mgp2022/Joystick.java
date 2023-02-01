package com.sdm.mgp2022;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.constraintlayout.widget.ConstraintSet;

public class Joystick implements EntityBase {

    //Default Values
    private int outerCircleCenterPositionX = 200;
    private int outerCircleCenterPositionY = 580;
    private int innerCircleCenterPositionX = 200;
    private int innerCircleCenterPositionY = 580;
    private int outerCircleRadius = 80;
    private int innerCircleRadius = 40;
    private Paint outerCirclePaint;
    private Paint innerCirclePaint;

    private boolean isPressed = false;
    private double joystickCenterToTouchDistance;
    private double actuatorX;
    private double actuatorY;

    private boolean isDone = false;
    private boolean isInit = false;

    public Joystick()
    {
        outerCircleCenterPositionX = 200;
        outerCircleCenterPositionY = 680;
        innerCircleCenterPositionX = 200;
        innerCircleCenterPositionY = 680;
        outerCircleRadius = 80;
        innerCircleRadius = 40;
    }

    public Joystick(int outX, int outY, int inX,int inY, int outRad, int inRad)
    {
        outerCircleCenterPositionX = outX;
        outerCircleCenterPositionY = outY;
        innerCircleCenterPositionX = inX;
        innerCircleCenterPositionY = inY;
        outerCircleRadius = outRad;
        innerCircleRadius = inRad;
    }

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
        _canvas.drawCircle(
                outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                outerCircleRadius,
                outerCirclePaint);
        _canvas.drawCircle(
                innerCircleCenterPositionX,
                innerCircleCenterPositionY,
                innerCircleRadius,
                innerCirclePaint);
    }

    public boolean IsInit() { return true; }

    public void SetRenderLayer(int _newLayer) {
        return;
    }

    public int GetRenderLayer() {
        return LayerConstants.RENDERJOYSTICK_LAYER;
    }

    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_JOYSTICK;
    }

    public static Joystick Create() {
        Joystick result = new Joystick();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_JOYSTICK);
        return result;
    }

    public static Joystick Create(int outX, int outY, int inX,int inY, int outRad, int inRad) {
        Joystick result = new Joystick(outX, outY, inX, inY, outRad, inRad);
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_JOYSTICK);
        return result;
    }
}
