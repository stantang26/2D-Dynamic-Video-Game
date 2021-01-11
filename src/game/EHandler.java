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
public class EHandler {
    private LinkedList<Enemy> elist = new LinkedList<Enemy>();
    private Game game;
    private OHandler oh;
    private PHandler ph;
    private Random gen = new Random();
    
    public EHandler(OHandler oh,PHandler ph,Game game){
        this.game = game;
        this.oh = oh;
        this.ph = ph;
    }
    
    public void tick (Player p){
        //goes through all enemies and runs through their tick classes
        for (int i = 0; i < elist.size(); i++) {
            if(elist.get(i).getHealth()<1){
                //if enemy health reaches 0, spawns power up
                if(elist.get(i).getClass() == Cryinlee.class){
                    p.setMoney(p.getMoney()+1000);
                }else{
                    PowerUp u = new PowerUp(elist.get(i).getX()+gen.nextInt(60)-30,elist.get(i).getY()+gen.nextInt(60)-30,p,game);
                    switch(gen.nextInt(5)+1){ //generates either health primary ammo or secondary ammo 
                        case 1: 
                            u.setType(u.type.HEALTH);
                            ph.addPowerUp(u);
                            break;
                        case 2:
                            u.setType(u.type.AMMO1);
                            ph.addPowerUp(u);
                            break;
                        case 3:
                            u.setType(u.type.AMMO2);
                            ph.addPowerUp(u);
                            break;
                        default: break;
                    }
                    //generates money powerup 
                    u = new PowerUp(elist.get(i).getX()+gen.nextInt(20)-10,elist.get(i).getY()+gen.nextInt(20)-10,p,game);
                    u.setType(u.type.MONEY);
                    ph.addPowerUp(u);
                }
                removeEnemy(elist.get(i));
                //removes enemy from list if health reaches 0
            }
            elist.get(i).tick();
            oh.tick(elist.get(i));
            //checks if enemies hit objects
            
            for (int j = 0; j < elist.size(); j++) {
                //runs through enemies and checks if any overlap with each other and spreads them out
                if(i!=j){
                    if(elist.get(j).isStuckUp(elist.get(i)) == true){
                        elist.get(i).setVelY(3);
                        elist.get(i).setRange(gen.nextInt(300)+200);
                    }if(elist.get(j).isStuckDown(elist.get(i)) == true){
                        elist.get(i).setVelY(-3);
                        elist.get(i).setRange(gen.nextInt(300)+200);
                    }if(elist.get(j).isStuckLeft(elist.get(i)) == true){
                        elist.get(i).setVelX(3);
                        elist.get(i).setRange(gen.nextInt(300)+200);
                    }if(elist.get(j).isStuckRight(elist.get(i)) == true){
                        elist.get(i).setVelY(-3);
                        elist.get(i).setRange(gen.nextInt(300)+200);    
                    }
                }
            }
        }
    }
    
    public void render (Graphics g){
        for (int i = 0; i < elist.size(); i++) {
            elist.get(i).render(g);
        }
    }
    
    public boolean isHit(Bullet b){
        //if a bullet touches an enemy, does its damage to the enemy
        for (int i = 0; i < elist.size(); i++) {
            if(elist.get(i).getX() < b.getX() && elist.get(i).getX()+elist.get(i).getWidth() > b.getX() && elist.get(i).getY() < b.getY() && elist.get(i).getY()+elist.get(i).getHeight() > b.getY()){
                
                    elist.get(i).setHealth(elist.get(i).getHealth()-b.getDamage());
                
                return true;
                
            }
        }
        return false;
    }
    
    public void addEnemy(Enemy e){
        elist.add(e);
    }
    public void removeEnemy(Enemy e){
        elist.remove(e);
    }
    public void removeAll(){
        elist.clear();
    }
    public int getEnemies(){
        return elist.size();
    }
}
