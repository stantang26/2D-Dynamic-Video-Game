/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author stant
 */
public class PHandler {
     private LinkedList<PowerUp> plist = new LinkedList<PowerUp>();
    private Game game;
    private Random gen = new Random();
    Player p;
    
    public PHandler(Player p, Game game){
        this.p = p;
        this.game = game;
    }
    
    public void tick(){
        for (int i = 0; i < plist.size(); i++) {
            //runs through list of powerups and if the player collides with any of them runs the effect of the powerup and removes it from the game
            plist.get(i).tick();
            if(p.getX()-25 < plist.get(i).getX()+50 && p.getX()+p.getWidth() > plist.get(i).getX()-25  && p.getY()-25 < plist.get(i).getY()+50  && p.getY()+p.getWidth() > plist.get(i).getY()-25 ){
                
                removePowerUp(plist.get(i));
            }
        }
    }
    
    public void render (Graphics g){
        for (int i = 0; i < plist.size(); i++) {
            plist.get(i).render(g);//draws each powerup
        }
    }
    
    public void addPowerUp(PowerUp o){
        plist.add(o);
    }
    public void removePowerUp(PowerUp o){
        plist.remove(o);
    }
    public void removeAll(){
        for (int i = 0; i < plist.size(); i++) {
            removePowerUp(plist.get(i));
        }
    }
}
