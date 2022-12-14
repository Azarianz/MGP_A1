package com.sdm.mgp2022;

// Created by TanSiewLan2022
// Sample Entity

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.SurfaceView;

import java.util.Random;

public class StarEntity implements EntityBase, Collidable{

    private Bitmap bmp = null;
    private Sprite spriteSheet;

    private float xPos = 150;
    private float xStart = 0;
    private float yPos = 150;
    private float screenHeight = 0;
    private float speed = 0;
    private boolean isDone = false;
    private boolean isInit = false;

    private int triesCount = 10;

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


        isInit = true;

        //_vibrator = (Vibrator)_view.getContext().getSystemService(_view.getContext().VIBRATOR_SERVICE);
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

        // Do nothing if it is not in the main game state
        if (StateManager.Instance.GetCurrentState() != "MainGame")
            return;



        // Check out of screen
        if (xPos <= -bmp.getHeight() * 0.5f){

            // Move it to another random pos again

        }



    }

    @Override
    public void Render(Canvas _canvas) {
        int lifeTime = 30;

        Matrix transform = new Matrix();
        transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);

        transform.postTranslate(xPos, yPos);

        float scaleFactor = 0.5f + Math.abs((float) Math.sin(lifeTime));

        transform.postScale(scaleFactor,scaleFactor);

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

    public static StarEntity Create()
    {
        StarEntity result = new StarEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    @Override
    public String GetType() {
        return "StarEntity";
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
                && _other.GetType() !=  "SmurfEntity") {  // Another entity
            SetIsDone(true);
        }
    }
}