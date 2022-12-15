package com.sdm.mgp2022;

import com.sdm.mgp2022.WeaponEntity;

import java.util.List;
import java.util.ArrayList;

class Inventory {
    List<WeaponEntity> weapons;

    // Constructor
    public Inventory() {
        this.weapons = new ArrayList<>();
    }

    // Method to add a weapon to the inventory
    public void addWeapon(WeaponEntity weapon) {
        weapons.add(weapon);
    }

    // Method to remove a weapon from the inventory
    public void removeWeapon(WeaponEntity weapon) {
        weapons.remove(weapon);
    }

    // Method to display the weapons in the inventory
    public void displayWeapons() {
        for (WeaponEntity weapon : weapons) {
            System.out.println(weapon.name + ": " + weapon.damage + " damage");
        }
    }

    // Method to check if the inventory contains a given weapon
    public boolean contains(WeaponEntity weapon) {
        return weapons.contains(weapon);
    }
}