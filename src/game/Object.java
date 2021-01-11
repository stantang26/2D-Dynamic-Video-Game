    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author stant
 */
public class Object {
    private int x; //indicates position of player along x axis
    private int y; //indicates position of player along y axis
    private int width;
    private int height;
    private BufferedImage Object;
    Game game;
    public Object (int x, int y,int width, int height, Game game){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.game = game;
    }
    public void init (BufferedImage Object){
        this.Object = Object;
    }
    public void tick(Player p){
        //moves the object based on player movement
        if(p.left == p.left.MOVING || p.right == p.right.MOVING){
            x-=p.getVelX();
        }else if (p.act == p.act.DODGE){
        
            if(p.left == p.left.STUCK || p.right == p.right.STUCK){
                
            }else {
                x-=p.getVelX();
            }

        }
        if(p.up == p.up.MOVING || p.down == p.down.MOVING){
             y-=p.getVelY();
        }else if (p.act == p.act.DODGE){
            if(p.up == p.up.STUCK ||p.down == p.down.STUCK){
              
            }else {
                 y-=p.getVelY();
            }
        }
       
    }
    public void render(Graphics g){
        //draws the image of the object
        g.drawImage(Object, this.x, this.y, width,height,game);
        
    }
    
    public boolean isStuckUp(Player p){
        if(p.getX()>=x-p.getWidth() && p.getX()<x+width&&p.getY()<=y+height+2 && p.getY()+p.getHeight()>= y +height){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isStuckDown(Player p){
        if(p.getX()>=x-p.getWidth() && p.getX()<x+width&&p.getY()+p.getHeight()+2>=y && p.getY()<=y){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isStuckLeft(Player p){
        if(p.getY()>=y-p.getHeight() && y+height>=p.getY()&&p.getX()<x+width+2 && p.getX()+p.getWidth() >= x+width){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isStuckRight(Player p){
        if(p.getY()<=y+height && p.getY()+p.getHeight()>= y&&p.getX()>=x-p.getWidth()-2 && p.getX()<x){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isStuckUp(Enemy p){
        if(p.getX()>=x-p.getWidth() && p.getX()<x+width&&p.getY()<=y+height+2 && p.getY()+p.getHeight()>= y +height){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isStuckDown(Enemy p){
        if(p.getX()>=x-p.getWidth() && p.getX()<x+width&&p.getY()+p.getHeight()+2>=y && p.getY()<=y){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isStuckLeft(Enemy p){
        if(p.getY()>=y-p.getHeight() && y+height>=p.getY()&&p.getX()<x+width+2 && p.getX()+p.getWidth() >= x+width){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isStuckRight(Enemy p){
        if(p.getY()<=y+height && p.getY()+p.getHeight()>= y&&p.getX()>=x-p.getWidth()-2 && p.getX()<x){
            return true;
        }else{
            return false;
        }
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    int getHealth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setHealth(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }

