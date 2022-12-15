package com.sdm.mgp2022;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Random;

public class EnemyEntity implements EntityBase, Collidable{

    private double MAX_SPEED = 6.0f;  //30 = framerate
    private Bitmap bmp = null;
    private boolean isDone = false;
    public double xPos = 0, yPos = 0;
    private double xVel = 0, yVel = 0;
    private Sprite spriteSheet;
    private boolean isInit = false;

    double playerX = 0, playerY = 0, playerDist = 0, playerXDist =0, playerYDist = 0;
    double directionX, directionY;

    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {
        //indicate what image to use
        //load the image
        Random ran = new Random();

        int area = ran.nextInt(4);

        //up
        if (area == 0)
        {
            xPos = ran.nextInt(801);
            yPos = ran.nextInt(801) + 600;
        }

        //down
        else if (area == 1)
        {
            xPos = ran.nextInt(801);
            yPos -= ran.nextInt(801) + 600;
        }

        //left
        if (area == 2)
        {
            xPos -= ran.nextInt(801) + 600;
            yPos = ran.nextInt(801);
        }

        //right
        else if (area == 3)
        {
            xPos = ran.nextInt(801) + 600;
            yPos = ran.nextInt(801);
        }

        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.flystone);

        spriteSheet = new Sprite(bmp, 1, 5, 16);

        // spriteSheet = new Sprite(bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.smurf_sprite), 4, 4, 16);

        isInit = true;
    }

    public void Update(float _dt) {
        if(GameSystem.Instance.GetIsPaused())
            return;
        spriteSheet.Update(_dt);

        // =========================================================================================
        //   Update velocity of the enemy so that the velocity is in the direction of the player
        // =========================================================================================
        // Calculate vector from enemy to player (in x and y)
        playerXDist = playerX - GetPosX();
        playerYDist = playerY - GetPosY();

        // Calculate (absolute) distance between enemy (this) and player
        playerDist = Math.sqrt(
                Math.pow(playerX - GetPosX(), 2) + Math.pow(playerY - GetPosY(), 2));

        // Calculate direction from enemy to player
        directionX = playerXDist/playerDist;
        directionY = playerYDist/playerDist;

        // Set velocity in the direction to the player
        if(playerDist > 0) { // Avoid division by zero
            xVel = directionX * MAX_SPEED * 0.4f;
            yVel = directionY * MAX_SPEED * 0.4f;
        }
        else {
            xVel = 0;
            yVel = 0;
        }

        // Update position
        xPos += xVel;
        yPos += yVel;
    }

    public void MoveToTarget(double xtar, double ytar)
    {
        playerX = xtar;
        playerY = ytar;

        //Log.d("Enemy Position", "XPOS: " + this.xPos + "YPOS: " + this.yPos);
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

    public static EnemyEntity Create() {
        EnemyEntity result = new EnemyEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PLAYER);
        return result;
    }

    public double GetDistanceFromPlayer(){
        return playerDist;
    }

    @Override
    public String GetType() {
        return "Enemy";
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

    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() == "Player")
        {
            Log.d("TAG", "OnHit: Player");
            SetIsDone(true);
        }
    }
}
