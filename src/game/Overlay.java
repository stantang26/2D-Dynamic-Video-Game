/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author stant
 */
public class Overlay {
    Player p;
    Controller c;
    Game game;
    LHandler lh;
    BufferedImage gun;
    BufferedImage image;
    public void init(Player p, Controller c, LHandler lh,Game game){
       this.p = p;
       this.c = c;
       this.game = game;
       this.lh = lh;
    }
    
    public void render(Graphics g){
        g.setColor(Color.red);
        try {
            image = ImageIO.read(new File ("heart.png"));
        } catch (IOException ex) {
            Logger.getLogger(Overlay.class.getName()).log(Level.SEVERE, null, ex);
        }
        //draws health bar, money and numer of enemies left
        g.drawImage(image, 30, game.width-200,50,50, game);
        g.fillRect(100, game.width-180, p.getHealth()*3, 20);
        g.setFont(new Font("Serif",Font.BOLD, 30));
        g.setColor(Color.WHITE);
        g.drawString("MONEY: $" + p.getMoney(), 50,game.width-110);
        g.drawString("BOUNTIES LEFT: " + lh.getEnemies(), 50,game.width-60);
        
        //displays the current gun of the player and ammo
        if(c.gun == c.gun.PRIMARY){
            switch(c.primary){
                case SHOTGUN: try {
                        gun = ImageIO.read(new File ("Shotgun.gif"));
                    } catch (IOException ex) {
                        Logger.getLogger(Overlay.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case RIFLE:try {
                        gun = ImageIO.read(new File ("Rifle.gif"));
                    } catch (IOException ex) {
                        Logger.getLogger(Overlay.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            g.drawString(c.getPmag() + "/" + c.getPammo(), game.length-150,game.width-50);
       }else{
            try {
              gun = ImageIO.read(new File ("Revolver.gif"));
           } catch (IOException ex) {
                 Logger.getLogger(Overlay.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.drawString(c.getSmag() + "/" + c.getSammo(), game.length-150,game.width-50);
       }
        g.drawImage(gun, game.length -150, game.width-200,100,100, game);
    }
}
