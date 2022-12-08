package com.sdm.mgp2022;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

import java.lang.reflect.Type;

public class RenderText implements EntityBase{

    // paint object
    Paint paint = new Paint();
    private int red = 0, green = 0, blue = 0;

    private boolean isDone = false;
    private int xPos = 0, yPos = 0;
    private boolean isInit = false;

    int frameCount;
    long lastTime = 0;
    long lastFPSTime = 0;
    float fps;
    // Our own font type

    Typeface myFont;

    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {

        //myFont = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.NORMAL);
        myFont = Typeface.createFromFile("fonts/BebasNeue-Bold.otf");
        //myFont = ResourcesCompat.getFont(_view.getContext(), R.fonts.BebasNeue-Bold);
        isInit = true;
    }

    public void Update(float _dt) {
        // get actual fps
        frameCount++;
        long currentTime = System.currentTimeMillis();

        lastTime = currentTime;
        if(currentTime - lastFPSTime > 1000)
        {
            fps = (frameCount * 1000.f)/ (currentTime - lastFPSTime);
            lastFPSTime = currentTime;
            frameCount = 0;
        }
    }

    public void Render(Canvas _canvas) {
        //Paint paint = new Paint();
        paint.setARGB(255, 0,0,0); // number range from 0 - 255
        paint.setStrokeWidth(200);
        paint.setTextSize(50);
        paint.setTypeface(myFont);
        _canvas.drawText("FPS: " + fps, 30, 80, paint);
    }

    public boolean IsInit() {
        return true;
    }

    public void SetRenderLayer(int _newLayer) {
        return;
    }

    public int GetRenderLayer() {
        return LayerConstants.RENDERTEXT_LAYER;
    }

    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_TEXT;
    }

    public static RenderText Create() {
        RenderText result = new RenderText();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_TEXT);
        return result;
    }
}
