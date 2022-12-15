package com.sdm.mgp2022;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.display.DisplayManager;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class PauseButtonEntity implements EntityBase {

    private Bitmap bmp, bmp1 = null;
    private Bitmap sbmp, sbmp1 = null;

    int ScreenWidth, ScreenHeight;

    private float buttonDelay = 0;
    private boolean Paused = false;
    private boolean isDone = false;
    private int xPos = 0, yPos = 0;

    private boolean isInit = false;

    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {
        //indicate what image to use
        //load the image

        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.pause);

        bmp1 = BitmapFactory.decodeResource(_view.getResources(), R.drawable.pause1);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        sbmp = Bitmap.createScaledBitmap(bmp, (int)(ScreenWidth)/16, (int)(ScreenHeight)/10, true);
        sbmp1 = Bitmap.createScaledBitmap(bmp1, (int)(ScreenWidth)/16, (int)(ScreenHeight)/10, true);

        xPos = ScreenWidth - 150;
        yPos = 150;

        isInit = true;
    }

    public void Update(float _dt) {

        // Addon codes provided on week 6 slides
        buttonDelay += _dt;

        if(TouchManager.Instance.HasTouch())
        {
            if(TouchManager.Instance.IsDown() && !Paused)
            {
                float imgRadius = sbmp.getHeight() * 0.5f;

                if(Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius)
                        && buttonDelay >= 0.25)
                {
                    GamePage.Instance.LoseGame();
                    //Paused = true;
                    //GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                }
            }
        }
        else
            Paused = false;

    }

    public void Render(Canvas _canvas) {
        if(!Paused)
            _canvas.drawBitmap(sbmp, xPos - sbmp.getWidth() * 0.5f, yPos - sbmp.getHeight() * 0.5f, null);
        else
            _canvas.drawBitmap(sbmp1, xPos - sbmp1.getWidth() * 0.5f, yPos - sbmp1.getHeight() * 0.5f, null);
    }

    public boolean IsInit() {
        return bmp != null;
    }

    public void SetRenderLayer(int _newLayer) {
        return;
    }

    public int GetRenderLayer() {
        return LayerConstants.RENDERPAUSE_LAYER;
    }

    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_PAUSE;
    }

    public static PauseButtonEntity Create() {
        PauseButtonEntity result = new PauseButtonEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PAUSE);
        return result;
    }
}