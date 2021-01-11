/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stant
 */
public class Cryinlee extends Enemy{
    
    Random gen = new Random();
    public Cryinlee(int x, int y, int width, int height, Game game, OHandler oh, Player p) throws IOException {
        super(x, y, width, height, game, oh, p);
        super.setHealth(1000);
        //sets position and health for the boss
    }
    
    public void tick(){
        //spawns 5 new bullets on top of the base enemy bullet spawn 

        if(gen.nextInt(100) == 1){
                try {
                    b = new Bullet(super.getX()+super.getWidth()/2,super.getY()+super.getHeight()/2,12, p.getX()+p.getWidth()/2+gen.nextInt(80)-40, p.getY()+p.getHeight()/2+gen.nextInt(80)-40,15,false,p,game );
                    c.addBullet(b);
                    b = new Bullet(super.getX()+super.getWidth()/2,super.getY()+super.getHeight()/2,12, p.getX()+p.getWidth()/2+gen.nextInt(80)-40, p.getY()+p.getHeight()/2+gen.nextInt(80)-40,15,false,p,game );
                    c.addBullet(b);
                    b = new Bullet(super.getX()+super.getWidth()/2,super.getY()+super.getHeight()/2,12, p.getX()+p.getWidth()/2+gen.nextInt(80)-40, p.getY()+p.getHeight()/2+gen.nextInt(80)-40,15,false,p,game );
                    c.addBullet(b);
                    b = new Bullet(super.getX()+super.getWidth()/2,super.getY()+super.getHeight()/2,12, p.getX()+p.getWidth()/2+gen.nextInt(80)-40, p.getY()+p.getHeight()/2+gen.nextInt(80)-40,15,false,p,game );
                    c.addBullet(b);
                    b = new Bullet(super.getX()+super.getWidth()/2,super.getY()+super.getHeight()/2,12, p.getX()+p.getWidth()/2+gen.nextInt(80)-40, p.getY()+p.getHeight()/2+gen.nextInt(80)-40,15,false,p,game );
                    c.addBullet(b);
                   
                } catch (IOException ex) {
                    Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
                }
                c.addBullet(b);
        }else if (gen.nextInt(100)==2){
            super.setVelX(3); //randomly changes direction and speed 
        }else if (gen.nextInt(100)==3){
            super.setVelX(-3);
        }else if (gen.nextInt(100)==4){
            super.setVelY(3);
        }else if (gen.nextInt(100)==5){
            super.setVelY(-3);
        }else if (gen.nextInt(100)==69){
            super.setRange(gen.nextInt(600)-300);
        }
        super.tick();
    }
    
    public void render(Graphics g){
        super.render(g);
        //renders the enemy along with the boss health bar 
        if(super.getX()>0 && super.getX()<game.getWidth()&&super.getY()>0 && super.getY()<game.getHeight()){
             g.setFont(new Font("Serif", Font.PLAIN|Font.BOLD, 32));
             g.setColor(Color.LIGHT_GRAY);
            g.drawString("Cryin' Lee", 30, 50);
            g.fillRect(30, 100, super.getHealth(), 30);
            g.setColor(Color.BLACK);
        }
    }
}
