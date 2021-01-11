package game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author stant
 */
public class PowerUp {
    Random gen = new Random(); //initializes a new random number generator
    private int x; // position along x axis

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

    public type getType() {
        return type;
    }

    public void setType(type type) {
        this.type = type;
    }
    private int y ; //position along y axis
    private BufferedImage image; //image of the powerup
    type type;//determines type of powerup
    Player p;
    Game game;
    
    public PowerUp(int x, int y,Player p, Game game){
        this.p = p;
        this.game = game;
        this.x = x;
        this.y = y;

    }
    
    public enum type{
        HEALTH,AMMO1,AMMO2,MONEY
    }
    
    public void tick(){
        //moves the powerup based on movement of player
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
        
        //if the player is in contact with the powerup, executes code based on type of powerup
        if(p.getX()-25 < x+50 && p.getX()+p.getWidth()> x-25 && p.getY()-25 < y +50&& p.getY()+p.getWidth()> y-25){
            switch(type){
                case HEALTH: 
                    if(p.getHealth()+10>= 100){
                        p.setHealth(100);
                    }else{
                        p.setHealth(p.getHealth()+10);
                    }
                    break;
                case AMMO1:
                    if(game.c.getPammo() +10>= game.c.getPmaxammo()){
                        game.c.setPammo(game.c.getPmaxammo());
                    }else{
                        game.c.setPammo(game.c.getPammo()+10);
                    }
                    break;
                case AMMO2:
                    if(game.c.getSammo() +20>= game.c.getSmaxammo()){
                        game.c.setSammo(game.c.getSmaxammo());
                    }else{
                        game.c.setSammo(game.c.getSammo()+20);
                    }
                    break;
                case MONEY: p.setMoney(p.getMoney()+gen.nextInt(100)+100);
                    break;
            }
        }
    }
    public void render(Graphics g){
        
        try {
        switch(type){
            //renders powerup based on its type
            case HEALTH:image = ImageIO.read(new File("healthkit.png"));break;
            case AMMO1:image = ImageIO.read(new File ("ammo1.png"));break;
            case AMMO2: image = ImageIO.read(new File ("ammo2.png"));break;
            case MONEY:image = ImageIO.read(new File ("money.png"));break;
            default: 
            
                image = ImageIO.read(new File ("muzzle flash 1.png"));
           
        }
         } catch (IOException ex) {
                Logger.getLogger(PowerUp.class.getName()).log(Level.SEVERE, null, ex);
            }
        
         g.drawImage(image,x,y,75,75,game);
        
    }
}
