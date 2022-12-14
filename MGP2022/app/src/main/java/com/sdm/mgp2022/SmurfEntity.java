package com.sdm.mgp2022;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class SmurfEntity implements EntityBase/*, Collidable*/{

    private Bitmap bmp = null;
    private boolean isDone = false;
    private int xPos = 0, yPos = 0;
    private Sprite spriteSheet;
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

        //bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.smurf_sprite);
        //spriteSheet = new Sprite(bmp, 4, 4, 16);
        // spriteSheet = new Sprite(bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.smurf_sprite), 4, 4, 16);
        spriteSheet = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite), 4, 4, 16);
        isInit = true;
    }

    public void Update(float _dt) {
        spriteSheet.Update(_dt);

        // Addon codes provided on week 6 slides

    }

    public void Render(Canvas _canvas) {
        spriteSheet.Render(_canvas, xPos + 400, yPos + 400);
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
}
