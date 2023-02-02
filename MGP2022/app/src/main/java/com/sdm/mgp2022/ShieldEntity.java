package com.sdm.mgp2022;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class ShieldEntity implements EntityBase, Collidable{

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
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.shield);
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
    }

    @Override
    public void Render(Canvas _canvas) {
        Matrix transform = new Matrix();
        transform.postTranslate(xPos, yPos);
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
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_SHIELD;}

    public static ShieldEntity Create(int _xPos, int _yPos)
    {
        ShieldEntity result = new ShieldEntity();
        result.xPos = _xPos;
        result.yPos = _yPos;
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_SHIELD);
        return result;
    }

    @Override
    public String GetType() {
        return "Shield";
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
        if(_other.GetType() ==  "Player")
        {
            //startVibrate();
            SetIsDone(true);
            GameSystem.Instance.powerUpExist = false;
            GameSystem.Instance.shieldActivated = true;

            if(AudioManager.Instance.GetSFXState())
                AudioManager.Instance.PlayAudio(R.raw.shield_pickup, 0.9f);
        }
    }
}