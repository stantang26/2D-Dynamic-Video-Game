/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author stant
 */
public class Barrel extends Object{
    
    private int health = 5;
    private int damage = 10;
    
    public Barrel(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
    }
    
    
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
