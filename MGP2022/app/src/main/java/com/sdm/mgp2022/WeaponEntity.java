package com.sdm.mgp2022;

import com.sdm.mgp2022.EntityBase;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class WeaponEntity implements EntityBase {
    public String name;
    public int damage;
    Inventory inventory; // Add a reference to the Inventory object


    // Constructor
    public WeaponEntity(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public WeaponEntity() {

    }

    // Override EntityBase methods
    @Override
    public boolean IsDone() {
        // Return whether the weapon is no longer in the inventory
        // (you will need to implement this in the Inventory class)
        return inventory.contains(this);
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        // You can leave this method empty, as the IsDone() method
        // already determines whether the weapon is "done"
    }

    @Override
    public void Init(SurfaceView _view) {

    }

    @Override
    public void Update(float _dt) {

    }

    @Override
    public void Render(Canvas _canvas) {
        // You will need to implement this method to draw the
        // weapon on the screen
    }

    @Override
    public boolean IsInit() {
        return true;
    }

    @Override
    public int GetRenderLayer() {
        // You can return a value here to specify the render layer
        // for the weapon (e.g. return 0 for background, 1 for
        // entities, 2 for HUD, etc.)
        return LayerConstants.RENDERWEAPON_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        // You can leave this method empty, as the render layer
        // for the weapon is not expected to change during the game
    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_WEAPON;
    }

    public static WeaponEntity Create() {
        WeaponEntity result = new WeaponEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_WEAPON);
        return result;
    }
}