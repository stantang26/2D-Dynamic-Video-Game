/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author stant
 */
public class Cursor extends MouseAdapter {
    Game game;
    int x;
    int y;
    int radius = 50;
    long thispress = System.currentTimeMillis();
    long lastpress;
    Player p;
    Controller c;
    BufferedImage image;
    Random gen = new Random();
    
    public Cursor(Player p,Game game, Controller c) throws IOException {
    this.p = p;
    this.game = game;
    this.c = c;
    image = ImageIO.read(new File ("crosshair.png"));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e); //To change body of generated methods, choose Tools | Templates.
        x = e.getX();
        y = e.getY();
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e); //To change body of generated methods, choose Tools | Templates.
        x = e.getX();
        y = e.getY();
        
    }
    
    

    @Override
    public void mousePressed(MouseEvent event) {
        super.mouseClicked(event); //To change body of generated methods, choose Tools | Templates.
        
        if(game.state == game.state.MENU){//when the game is in the MENU state
            
            if(event.getX()> 390 && event.getX()<650 && event.getY()>330 && event.getY()<420){
                //if the player clicks on the first button of the screen, sets the state to GAME and calls the run method
                game.state = game.state.GAME;
                game.run();
                
            }else if(event.getX()> 390 && event.getX()<650 && event.getY()>450 && event.getY()<540){
                //if the player clicks on the second button of the screen, sets the state to INSTRUCTIONS
                game.state = game.state.INSTRUCTIONS;
            }else if(event.getX()> 390 && event.getX()<650 && event.getY()>570 && event.getY()<660){
                //if the player clicks on the second button of the screen, sets the state to INSTRUCTIONS
                System.exit(0);
            }
            
        }else if (game.state == game.state.INSTRUCTIONS){//when the game is in the INSTRUCTIONS state
             if(event.getX()>100 && event.getX()<350 && event.getY()>630 && event.getY()<700){
                //game.reset();
                game.state = game.state.MENU;
            }
             
        }else if (game.state == game.state.PAUSE){
            if(event.getX()>360 && event.getX()<700 && event.getY()>350 && event.getY()<460){
                //game.reset();
                game.state = game.state.MENU;
            }
            
        }else if(game.state == game.state.DEATH){//when the game is the DEATH state
            
            if(event.getX()>360 && event.getX()<700 && event.getY()>350 && event.getY()<460){
                //if the player clicks on the MENU button, calls on the reset method 
                game.reset();
            }
        }else if(game.state == game.state.STORE){
            if(event.getX()>50 && event.getX()<320 && event.getY()>620 && event.getY()<710){
                game.state = game.state.GAME;
                game.run();
            }else if (event.getX()>130 && event.getX()<290 && event.getY()>100 && event.getY()<170){
                if(c.primary != c.primary.SHOTGUN){
                    if(p.getMoney()>=3000){
                        c.primary = c.primary.SHOTGUN;
                        p.setMoney(p.getMoney()-3000);
                    }
                }
            }else if (event.getX()>700 && event.getX()<920 && event.getY()>90 && event.getY()<150){
                if(c.primary != c.primary.RIFLE){
                    if(p.getMoney()>=2000){
                        c.primary = c.primary.RIFLE;
                        p.setMoney(p.getMoney()-2000);
                    }
                }
            }
        }else if (game.state == game.state.GAME){
            int tick = 100;
             try {
                thispress = System.currentTimeMillis(); //uses thispress and last press give a cooldown between shots
                
                if(c.gun == c.gun.PRIMARY){
                    switch(c.primary){
                        case SHOTGUN: tick = 500;
                            break;
                        case PISTOL: tick = 400;
                            break;
                        case RIFLE: tick = 200;
                            break;
                        case SNIPER: tick = 1000;
                            break;
                    }
                    if(thispress - lastpress > tick){ //if the time between the current press and the last press is greater than 400 milliseconds
                        //Creates a new object of the class Bullet and spawns it at the players position
                        if(p.act == p.act.NONE ){
                            if(c.getPmag()>0){

                                Bullet b;

                                b = new Bullet(p.getX()+p.getWidth()/2+20, p.getY()+p.getHeight()/2,12,x,y,c.getDamage(),true,p, game); 
                                game.c.addBullet(b);
                                if(c.primary == c.primary.SHOTGUN){
                                    b = new Bullet(p.getX()+ p.getWidth()/2, p.getY()+p.getHeight()/2,12,x+gen.nextInt(80)-40,y+gen.nextInt(80)-40,c.getDamage(),true,p,game);
                                    game.c.addBullet(b);
                                    b = new Bullet(p.getX()+p.getWidth()/2, p.getY()+p.getHeight()/2,12,x+gen.nextInt(80)-40,y+gen.nextInt(80)-40,c.getDamage(),true,p,game);
                                    game.c.addBullet(b);
                                    b = new Bullet(p.getX()+p.getWidth()/2, p.getY()+p.getHeight()/2,12,x+gen.nextInt(80)-40,y+gen.nextInt(80)-40,c.getDamage(),true,p,game);
                                    game.c.addBullet(b);
                                    b = new Bullet(p.getX()+p.getWidth()/2, p.getY()+p.getHeight()/2,12,x+gen.nextInt(80)-40,y+gen.nextInt(80)-40,c.getDamage(),true,p,game);
                                    game.c.addBullet(b);
                                }

                                
                                c.setPmag(c.getPmag()-1);
                                p.act = p.act.FIRE;
                                p.setFtick1(System.currentTimeMillis());
                                p.setFtick2(p.getFtick1());
                            }
                        }
                        lastpress = thispress;
                    }
                }else if (c.gun == c.gun.SECONDARY){
                    switch(c.secondary){
                        case SHOTGUN: tick = 1000;
                            break;
                        case PISTOL: tick = 200;
                            break;
                        case RIFLE: tick = 100;
                            break;
                        case SNIPER: tick = 1000;
                            break;
                    }
                    if(thispress - lastpress > tick){ //if the time between the current press and the last press is greater than 400 milliseconds
                        //Creates a new object of the class Bullet and spawns it at the players position
                        if(p.act == p.act.NONE ){
                            if(c.getSmag()>0){

                                Bullet b;

                                b = new Bullet(p.getX()+p.getWidth()/2, p.getY()+p.getHeight()/2,12,x,y,c.getDamage(),true,p, game); 

                                game.c.addBullet(b);
                                c.setSmag(c.getSmag()-1);
                                p.act = p.act.FIRE;
                                p.setFtick1(System.currentTimeMillis());
                                p.setFtick2(p.getFtick1());
                            }
                        }
                        lastpress = thispress;
                    }
                }
                
            } catch (IOException ex) {
                Logger.getLogger(Cursor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void render(Graphics g){
        g.drawImage(image, x-radius/2, y-radius/2, radius,radius,game);
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

    
}
