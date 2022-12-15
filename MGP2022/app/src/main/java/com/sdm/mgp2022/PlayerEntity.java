package com.sdm.mgp2022;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class PlayerEntity implements EntityBase, Collidable{

    private double MAX_SPEED = 6.0f;
    public int health = 100;
    private Bitmap bmp = null;
    private boolean isDone = false;
    private double xPos = 0, yPos = 0;
    private double xVel = 0, yVel = 0;
    private Sprite spriteSheet;
    private boolean isInit = false;

    private double targetX = 0, targetY = 0;

    public float shootInterval = 60;

    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {
        //indicate what image to use
        //load the image

        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.saucer);

        spriteSheet = new Sprite(bmp, 1, 4, 16);

        // spriteSheet = new Sprite(bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.smurf_sprite), 4, 4, 16);

        isInit = true;
    }

    public void Update(float _dt) {
        if(GameSystem.Instance.GetIsPaused())
            return;
        spriteSheet.Update(_dt);

        if(health <= 0)
        {
            StateManager.Instance.ChangeState("LosePage"); // Default is like a loading page
        }

        // Addon codes provided on week 6 slides
    }

    public void UpdateJoystick(Joystick joystick)
    {
        if(GameSystem.Instance.GetIsPaused())
            return;
        // Update velocity based on actuator of joystick
        xVel = joystick.getActuatorX()*MAX_SPEED;
        yVel = joystick.getActuatorY()*MAX_SPEED;

        // Update position
        xPos += xVel;
        yPos += yVel;
    }

    public void Render(Canvas _canvas) {
        spriteSheet.Render(_canvas, (int)xPos, (int)yPos);
    }

    public boolean IsInit() {
        return bmp != null;
    }

    public void SetRenderLayer(int _newLayer) {
        return;
    }

    public int GetRenderLayer() {
        return LayerConstants.RENDERPLAYER_LAYER;
    }

    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_PLAYER;
    }

    public static PlayerEntity Create() {
        PlayerEntity result = new PlayerEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PLAYER);
        return result;
    }

    public void SetTarget(double x, double y)
    {
        targetX = x;
        targetY = y;
    }

    public double GetTargetX()
    {
        return targetX;
    }
    public double GetTargetY()
    {
        return targetY;
    }

    @Override
    public String GetType() {
        return "PlayerEntity";
    }

    @Override
    public float GetPosX() {
        return (float)xPos;
    }

    @Override
    public float GetPosY() {
        return (float)yPos;
    }

    @Override
    public float GetRadius() {
        return 0;
    }

    public int GetHealth()
    {
        return health;
    }

    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType()
                && _other.GetType() !=  "BulletEntity") {
            // Another entity
            health -= 10;
        }
    }
}
