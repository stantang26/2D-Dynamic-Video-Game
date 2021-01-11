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
public class OHandler {
    private LinkedList<Object> olist = new LinkedList<Object>();
    private Game game;
    private Random gen = new Random();
    
    public OHandler(Game game){
        this.game = game;
        //olist.add(new Object(gen.nextInt(1000)-500))
    }
    
    public void tick(Player p){
        //goes through every object and runs through tick method
          for (int i = 0; i < olist.size(); i++) {
            olist.get(i).tick(p);
          }
        
        for (int i = 0; i < olist.size(); i++) {
            //olist.get(i).tick(p);
            
            if(olist.get(i).getClass() == Barrel.class && olist.get(i).getHealth() == 0){
                olist.remove(olist.get(i));
            }
            
            //checks to see if the player hits an object and sets the players' movement based on that
            if(olist.get(i).isStuckUp(p) == true){
                
                p.up = p.up.STUCK;
                
                //p.setVelY(-p.getVelY());
            }else if (p.up != p.up.MOVING){
                p.up = p.up.NOTMOVING;
            }
            
            if(olist.get(i).isStuckDown(p) == true){
                p.down = p.down.STUCK;
               // p.setVelY(-p.getVelY());
                
            }else if (p.down != p.down.MOVING){
                p.down = p.down.NOTMOVING;
            }
            
            if(olist.get(i).isStuckUp(p) == true ||olist.get(i).isStuckDown(p) == true  ){
                break;
            }
            
            
        }
        //checks to see if the player hits an object and sets the players' movement based on that
        for (int i = 0; i < olist.size(); i++) {
            if(olist.get(i).isStuckLeft(p) == true){
                p.left = p.left.STUCK;
                //p.setVelX(-p.getVelX());
                
            }else if (p.left != p.left.MOVING){
                p.left = p.left.NOTMOVING;
            }
            
            if(olist.get(i).isStuckRight(p) == true){
                p.right = p.right.STUCK;
               // p.setVelX(-p.getVelX());
                
            }else if (p.right != p.right.MOVING){
                p.right = p.right.NOTMOVING;
            }
           
         if(olist.get(i).isStuckLeft(p) == true||olist.get(i).isStuckRight(p) == true ){
                break;
            }
        }
    }
    
    public void tick(Enemy p){
        
        //runs the same check as with players but with enemies, checking if the enemy hits any objects and changing movement appropriately
        for (int i = 0; i < olist.size(); i++) {
            //olist.get(i).tick(p);
            if(olist.get(i).getClass() == Barrel.class && olist.get(i).getHealth() == 0){
                olist.remove(olist.get(i));
            }
            if(olist.get(i).isStuckUp(p) == true){
                

                p.setVelY(3);
            }
            if(olist.get(i).isStuckDown(p) == true){
 
                p.setVelY(-3);
                
        
            }
            if(olist.get(i).isStuckLeft(p) == true){
               
                p.setVelX(3);
                
            }  
                        
            if(olist.get(i).isStuckRight(p) == true){

                p.setVelX(-3);
                
            }
           
            if(olist.get(i).isStuckUp(p) == true ||olist.get(i).isStuckDown(p) == true || olist.get(i).isStuckLeft(p) == true||olist.get(i).isStuckRight(p) == true){
                return;
            }
        }
    }
    //checks if enemies are stuck in any direction on an object
    public boolean isStuck(Enemy p){
        
        
        for (int i = 0; i < olist.size(); i++) {

            if(olist.get(i).getX()<p.getX() && olist.get(i).getX()+olist.get(i).getWidth()>p.getX()+p.getWidth()&&olist.get(i).getY()<p.getY() && olist.get(i).getY()+olist.get(i).getHeight()>p.getY()+p.getHeight()){
                return true;
            }
        }
        return false;
    }
    
    public boolean isStuck(Object p){
        
        
        for (int i = 0; i < olist.size(); i++) {

            if(olist.get(i).getX()<p.getX() && olist.get(i).getX()+olist.get(i).getWidth()>p.getX()+p.getWidth()&&olist.get(i).getY()<p.getY() && olist.get(i).getY()+olist.get(i).getHeight()>p.getY()+p.getHeight()){
                return true;
            }
        }
        return false;
    }
    
    public boolean isHit(Bullet b){
        for (int i = 0; i < olist.size(); i++) {
            if(olist.get(i).getX() < b.getX() && olist.get(i).getX()+olist.get(i).getWidth() > b.getX() && olist.get(i).getY() < b.getY() && olist.get(i).getY()+olist.get(i).getHeight() > b.getY()){
                if(olist.get(i).getClass() == Barrel.class){
                    olist.get(i).setHealth(olist.get(i).getHealth()-1);
                }
                return true;
                
            }
        }
        return false;
    }
    
    public void render (Graphics g){
        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).render(g);
        }
    }
    
    public void addObject(Object o){
        olist.add(o);
    }
    public void removeObject(Object o){
        olist.remove(o);
    }
    public void removeAll(){
        olist.clear();
        
    }
    public int getSize(){
        return olist.size();
    }
}
