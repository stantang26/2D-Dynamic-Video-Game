/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author stant
 */
public class LHandler {
    Game game;
    OHandler h;
    EHandler eh;
    PHandler ph;
    Player p;
    Random gen = new Random();
    private int level = 0;//level the player is on determines enemies and what objects are on screen
    private int length;
    private int width;
    private int enemies; //number of enemies in the level

    BufferedImage image;
    
    public LHandler(Game game, OHandler h,EHandler eh,PHandler ph,Player p){
        this.eh = eh;
        this.p = p;
        this.ph = ph;
        this.game = game;
        this.h = h;
        length = game.background.getWidth();
        width = game.background.getHeight();
    }
    public void init() throws IOException{
       
        clear();
        
        int x;
        int y;
        
        switch(level){
            case 0:
                game.background = new Object(-200, -400, 1600, 1200, game); //adds objects to prevent user from leaving bounds of the game
                game.background.init(ImageIO.read(new File("Ground.png")));
                Object o = new Object(game.background.getX(),game.background.getY(),500,game.background.getHeight(),game);
                h.addObject(o);
                o = new Object(game.background.getX()+game.background.getWidth()-500,game.background.getY(),500,game.background.getHeight()-370,game);
                h.addObject(o);
                o = new Object(game.background.getX(),game.background.getY(),game.background.getWidth(),30,game);
                h.addObject(o);
                o = new Object(game.background.getX(),game.background.getY()+game.background.getHeight()-250,game.background.getWidth(),30,game);
                h.addObject(o);
                
                break;      

            default:
                //adds objects to prevent user from leaving bounds of the game, matching the map
                game.background = new Object(-2550, -5*game.width-100, game.length*4, game.width*6, game);
                game.background.init(ImageIO.read(new File("background1.png")));
                o = new Object(game.background.getX()+2300,game.background.getY()+game.background.getWidth()-270,550,600,game);
                h.addObject(o);
                o = new Object(game.background.getX()+3400,game.background.getY()+game.background.getWidth()-270,530,600,game);
                h.addObject(o);
                o = new Object(game.background.getX()-1000,game.background.getY(),1060,game.background.getHeight(),game);
                h.addObject(o);
                o = new Object(game.background.getX()+game.background.getWidth()-300,game.background.getY(),1000,game.background.getHeight(),game);
                h.addObject(o);
                o = new Object(game.background.getX(),game.background.getY()-1000,game.background.getWidth(),1000,game);
                h.addObject(o);
                o = new Object(game.background.getX(),game.background.getY()+game.background.getHeight()-50,game.background.getWidth(),1000,game);
                h.addObject(o);
                o = new Object(game.background.getX()+550,game.background.getY(),1200,950,game);
                h.addObject(o);
                o = new Object(game.background.getX(),game.background.getY()+1550,1150,600,game);
                h.addObject(o);
                o = new Object(game.background.getX(),game.background.getY()+1550,600,1800,game);
                h.addObject(o);
                o = new Object(game.background.getX()+1720,game.background.getY()+950,570,620,game);
                h.addObject(o);
                o = new Object(game.background.getX()+2840,game.background.getY()+950,1270,1220,game);
                h.addObject(o);
                o = new Object(game.background.getX()+3440,game.background.getY(),1270,1220,game);
                h.addObject(o);
                o = new Object(game.background.getX()+1720,game.background.getY()+2150,1150,600,game);
                h.addObject(o);
                o = new Object(game.background.getX()+2300,game.background.getY()+2750,1100,600,game);
                h.addObject(o);
                o = new Object(game.background.getX()+600,game.background.getY()+2750,580,600,game);
                h.addObject(o);
                o = new Object(game.background.getX()+630,game.background.getY()+3350,1100,600,game);
                h.addObject(o);
                
                for (int i = 0; i < gen.nextInt(10)+30; i++) { //generates random objects and places them on the map 
                    x = gen.nextInt(game.background.getWidth())+game.background.getX();
                    y = gen.nextInt(game.background.getHeight())+game.background.getY();
                    int width = gen.nextInt(50)+100;
                    o = new Object(x,y,width,width,game);
                    if(h.isStuck(o) == true){
                        i--;
                    }else{ //chooses random object images for each of the object placed
                        if(i%2 == 0){
                            o.init(ImageIO.read(new File("barrel.png")));
                        }else if (i%3 == 0){
                            o.init(ImageIO.read(new File("Cactus.gif")));
                        }else if (i%5 == 0){
                            o.init(ImageIO.read(new File("rock2.png")));
                        }else{
                             o.init(ImageIO.read(new File("rock.png")));
                        }
                        h.addObject(o);
                    }
                
                }
                enemies = gen.nextInt(10)+10+5*game.level; //generates random amount of enemies and places on the map
                for (int i = 0; i < enemies; i++) {
                    x = gen.nextInt(game.background.getWidth()-100)+game.background.getX()+100;
                    y = gen.nextInt(game.background.getHeight()-100)+game.background.getY()+100;
                    Enemy e = new Enemy(x,y,75,75,game,h,p);
                    if(h.isStuck(e) == true){
                        i--;
                    }else{
                        eh.addEnemy(e);
                    }
                }
                if(level>1){ //if the level is greater than 1, places the boss into the map
                    Cryinlee cryin = new Cryinlee(game.background.getX()+game.background.getWidth()-500,game.background.getY(),100,100,game,h,p);
                    cryin.init(ImageIO.read(new File("Cryinlee.gif")));
                    eh.addEnemy(cryin);
                }
                break;
        }
    }
    
    public void tick() throws IOException{
        enemies = eh.getEnemies();
        //sets appropriate game states baseed on what level and where the player is
        if(level == 0){
            if(game.background.getY()>300){
                level = game.level;
                clear();
                init();    
            }else if (game.background.getX()<-600){
                game.state = game.state.STORE;
                level = 0;
                clear();
                init();
            }
        }else if (level !=0 && enemies <1){
            clear();
            level = 0;
            game.level++;
            init();
        }
    }
    public void clear(){
        //removes all objects, enemies, and powerups from all handler classes
        eh.removeAll();
        h.removeAll();
        ph.removeAll();
    }
    
    public int getEnemies() {
        return enemies;
    }

    public void setEnemies(int enemies) {
        this.enemies = enemies;
    }
}
