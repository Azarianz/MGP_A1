package com.sdm.mgp2022;

// Created by TanSiewLan2022
// Sample Entity

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.Set;

public class BulletEntity implements EntityBase, Collidable{

    private Bitmap bmp = null;
    private Sprite spriteSheet;

    private double xVel = 0, yVel = 0;

    public float xPos = 0;
    private float xStart = 0;
    public float yPos = 0;
    private float screenHeight = 0;
    private float speed = 0;
    private boolean isDone = false;
    private boolean isInit = false;

    private int BULLET_SPEED = 10;

    public double targetX=0, targetY=0;
    double targetDist = 0, targetXDist = 0, targetYDist = 0;
    double directionX, directionY;

    public float bulletLifetime = 80.0f;

    private Vibrator _vibrator;

    int ScreenWidth, ScreenHeight;

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {

        // New method using our own resource manager : Returns pre-loaded one if exists
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.star);
        spriteSheet = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.star), 4, 4, 16);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        isInit = true;
        _vibrator = (Vibrator)_view.getContext().getSystemService(_view.getContext().VIBRATOR_SERVICE);
        if(AudioManager.Instance.GetSFXState())
            AudioManager.Instance.PlayAudio(R.raw.shoot, 0.9f);
    }

    public void startVibrate()
    {
        if(Build.VERSION.SDK_INT >= 26)
        {
            _vibrator.vibrate(VibrationEffect.createOneShot(150, 10));
        }
        else
        {
            long pattern[] = {0, 50, 0};
            _vibrator.vibrate(pattern, -1);
        }
    }

    public void stopVibrate()
    {
        _vibrator.cancel();
    }

    @Override
    public void Update(float _dt) {
        if(bulletLifetime <= 0)
        {
            SetIsDone(true);
        }
        else if(bulletLifetime > 0){
            bulletLifetime--;
        }

        // Do nothing if it is not in the main game state
        if (StateManager.Instance.GetCurrentState() != "MainGame")
            return;

        // =========================================================================================
        //   Update velocity of the enemy so that the velocity is in the direction of the player
        // =========================================================================================
        // Calculate vector from enemy to player (in x and y)
        targetXDist = targetX - xPos;
        targetYDist = targetY - yPos;

        // Calculate (absolute) distance between enemy (this) and player
        targetDist = Math.sqrt(
                Math.pow(targetX - xPos, 2) + Math.pow(targetY - yPos, 2));

        // Calculate direction from enemy to player
        directionX = targetXDist/targetDist;
        directionY = targetYDist/targetDist;

        // Set velocity in the direction to the player
        if(targetDist > 0) { // Avoid division by zero
            xVel = directionX * BULLET_SPEED * 1.4f;
            yVel = directionY * BULLET_SPEED * 1.4f;}
        else
        {
            // No Target
            SetIsDone(true);
        }

        // Update position
        xPos += xVel;
        yPos += yVel;

        if(xPos < 0 || xPos > ScreenWidth || yPos < 0 || yPos > ScreenHeight)
        {
            SetIsDone(true);
        }

        if(xVel == targetX && yPos == targetY)
        {
            SetIsDone(true);
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        int lifeTime = 30;

        Matrix transform = new Matrix();
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);

        transform.postTranslate(xPos, yPos);

        //float scaleFactor = 0.5f + Math.abs((float) Math.sin(lifeTime));

        //transform.postScale(scaleFactor,scaleFactor);

        _canvas.drawBitmap(bmp, transform, null);
    }

    @Override
    public boolean IsInit() {

        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.STAR_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_DEFAULT;}

    public static BulletEntity Create(double X, double Y)
    {
        BulletEntity result = new BulletEntity();
        result.targetX = X;
        result.targetY = Y;
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    @Override
    public String GetType() {
        return "Bullet";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public float GetRadius() {
        return bmp.getWidth();
    }

    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType()
                && _other.GetType() !=  "Player") {  // Another entity
            GameSystem.Instance.AddScore(10);

            startVibrate();
            SetIsDone(true);
            if(AudioManager.Instance.GetSFXState())
                AudioManager.Instance.PlayAudio(R.raw.enemy_hit, 0.9f);
        }
    }
}