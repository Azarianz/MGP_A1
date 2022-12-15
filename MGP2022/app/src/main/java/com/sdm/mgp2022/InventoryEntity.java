package com.sdm.mgp2022;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.List;

public class InventoryEntity implements EntityBase{
    private Bitmap bmp, bmp1 = null;
    private Bitmap sbmp, sbmp1 = null;

    private List<ClipData.Item> items;
    private int maxCapacity;

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

        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.inventory_holder);

        bmp1 = BitmapFactory.decodeResource(_view.getResources(), R.drawable.inventory_holder);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        sbmp = Bitmap.createScaledBitmap(bmp, (int)(ScreenWidth)/12, (int)(ScreenHeight)/7, true);
        sbmp1 = Bitmap.createScaledBitmap(bmp1, (int)(ScreenWidth)/16, (int)(ScreenHeight)/10, true);

        xPos = ScreenWidth/2;
        yPos = ScreenHeight - 150;

        isInit = true;
    }

    public void Update(float _dt) {

        // Addon codes provided on week 6 slides
        /*buttonDelay += _dt;

        if(TouchManager.Instance.HasTouch())
        {
            if(TouchManager.Instance.IsDown() && !Paused)
            {
                float imgRadius = sbmp.getHeight() * 0.5f;

                if(Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius)
                        && buttonDelay >= 0.25)
                {
                    Paused = true;
                    GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                }
            }
        }
        else
            Paused = false;*/

    }

    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(sbmp, xPos - sbmp.getWidth() * 0.5f, yPos - sbmp.getHeight() * 0.5f, null);
        _canvas.drawBitmap(sbmp1, (xPos - 250) - sbmp1.getWidth() * 0.5f, yPos - sbmp1.getHeight() * 0.5f, null);
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

    public EntityBase.ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_INVENTORY;
    }

    public static InventoryEntity Create() {
        InventoryEntity result = new InventoryEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_INVENTORY);
        return result;
    }
}