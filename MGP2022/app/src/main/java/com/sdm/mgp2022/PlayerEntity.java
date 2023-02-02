package com.sdm.mgp2022;

import android.graphics.Matrix;
import android.os.Build;
import android.os.Vibrator;
import android.os.VibrationEffect;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class PlayerEntity implements EntityBase, Collidable{

    private double MAX_SPEED = 6.0f;
    private Bitmap bmp = null;
    private Bitmap bmp1 = null;
    private boolean isDone = false;
    private double xPos = 0, yPos = 0;
    private double xVel = 0, yVel = 0;
    private Sprite spriteSheet;
    private boolean isInit = false;

    private Vibrator _vibrator;

    public boolean canFire = true;

    private double targetX = 0, targetY = 0;

    public float shootInterval = 80.0f;

    int ScreenWidth, ScreenHeight;

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
        bmp1 = BitmapFactory.decodeResource(_view.getResources(), R.drawable.bubble_transparent);
        spriteSheet = new Sprite(bmp, 1, 4, 16);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        // spriteSheet = new Sprite(bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.smurf_sprite), 4, 4, 16);
        xPos = ScreenWidth/2;
        yPos = ScreenHeight/2;
        isInit = true;

        _vibrator =(Vibrator) _view.getContext().getSystemService(_view.getContext().VIBRATOR_SERVICE);
    }

    public void Update(float _dt) {
        if(GameSystem.Instance.GetIsPaused())
            return;
        spriteSheet.Update(_dt);

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
        if(GameSystem.Instance.shieldActivated)
        {
            Matrix transform = new Matrix();
            transform.postTranslate((int)xPos- bmp1.getWidth() * 0.5f, (int)yPos- bmp1.getHeight() * 0.5f);
            _canvas.drawBitmap(bmp1, transform, null);
        }
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
        return "Player";
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
        return 50;
    }

    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType()
                && _other.GetType() != "Bullet"
                && _other.GetType() != "Shield"
                && _other.GetType() != "Health")
        {
            if(!GameSystem.Instance.shieldActivated)
            {
                // Collided with enemy
                GameSystem.Instance.TakeDamage();
                GameSystem.Instance.AddScore(-5);
                //currScore -= 5;
            }

            //GameSystem.Instance.SaveEditBegin();
            //GameSystem.Instance.SetIntInSave("Score", currScore);
            //GameSystem.Instance.SaveEditEnd();

            startVibrate();
            if(AudioManager.Instance.GetSFXState())
                AudioManager.Instance.PlayAudio(R.raw.player_hit, 0.9f);
        }
    }

    public void startVibrate()
    {
        if(Build.VERSION.SDK_INT >= 26)
        {
            _vibrator.vibrate(VibrationEffect.createOneShot(150,10));
        }
        else
        {
            long pattern[] = {0,50,0};
            _vibrator.vibrate(pattern,-1);
        }
    }

    public void stopVibrate()
    {
        _vibrator.cancel();
    }

    public void ResetGameValues()
    {
        xPos = ScreenWidth/2;
        yPos = ScreenHeight/2;
        xVel = 0;
        yVel = 0;
        targetX = 0;
        targetY = 0;
        //canFire = false;
    }
}
