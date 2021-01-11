    package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 *
 * @author tangs6732
 */
public class Controller {
    private LinkedList<Bullet> blist = new LinkedList<Bullet>(); //initializes a new List of Bullet objects, tracks the number of bullets and allows them to be removed when neccessary
    Bullet b; //declares a new bullet
    Game game; //new instance of CPT class
    Player p;
    public int shots = 0; //int variable used to track how many shots the player made in their runthrough, used so the player can start the game by pressing J
    private int pammo = 100;
    private int pmag = 20;
    private int pmaxmag = 20;
    private int pmaxammo = 100;
    private int sammo = 100;
    private int smag = 20;
    private int smaxmag = 20;
    private int smaxammo = 100;
    private int damage = 0;


    public gunType primary;
    public gunType secondary;
    public gun gun;
    BufferedImage image;

    public Controller(Player p,Game game){ //when created, sets the game instance to the parameter given
        this.game = game;
        this.p = p;
        gun = gun.PRIMARY;
        primary = primary.NONE;
        secondary = secondary.PISTOL;
    }
    
    public enum gunType{
        SHOTGUN,PISTOL,RIFLE,SNIPER,NONE
    }
    
    public enum gun {
        PRIMARY,SECONDARY
    }
    
    public void tick(){
        //uses for loop to traverse the list
        if(gun == gun.PRIMARY){
            switch(primary){
                case SHOTGUN: damage = 20;
                    break;
                case RIFLE: damage = 60;
                    break;
                case SNIPER: damage = 100;break;
                case NONE: gun = gun.SECONDARY; break;
            }
        }else{
            damage = 20;
        }
        
        for(int i = 0; blist.size()>i;i++){
            //for each bullet, sets the bullet b to the one of the list
            b = blist.get(i);
            //ticks/shoots the bullet selected
            b.shoot();
            if(game.h.isHit(b) == true ){
                removeBullet(b);
            }//if the bullet hits an object, remove it
            
            if(b.getPlayer()==true){ //if the bullet is shot from a plyer and hits an enemy remove it
                
                if(game.eh.isHit(b) == true){
                    removeBullet(b);
                }
              
            }else if(b.getPlayer() == false && blist.get(i).getY()> p.getY() && blist.get(i).getY() < p.getY()+p.getHeight() && blist.get(i).getX() > p.getX() && blist.get(i).getX()< p.getX()+p.getWidth()){
                removeBullet(b); //if the bullet is shot from an enemy and hits a player runs appropriate methods
                if(p.act != p.act.DODGE && p.isHit() == false){
                    p.setHit(true);
                    p.setHtick1(System.currentTimeMillis());
                    p.setHtick2(p.getHtick1());
                    p.setHealth(p.getHealth()-b.getDamage());
                }
            }
            
           if (b.getX()<= -5 || b.getX() > game.length || b.getY() <= -5 || b.getY() > game.width){
                //if the bullet leaves the bounds of the game, it is removed from the list
                removeBullet(b);
            }
        
            
        }
    }
    
    public void render(Graphics g){
        //uses for loop to traverse list
        
        for(int i = 0; blist.size()>i;i++){
            //sets the bullet b to the corresponding on from the list
            b = blist.get(i);
            //renderst the bullet selected
            b.render(g);
        }
    }
    public void addBullet(Bullet block){
        //adds bullets to the list as long as the shots variable is greater than 0(so the player can start the game without shooting a bullet) increments the shots variabel
        blist.add(block);
        shots++;
    }
    public void removeBullet(Bullet block){
       //removes the bullet from the list
        blist.remove(block);
        
    }
        public int getPammo() {
        return pammo;
    }

    public void setPammo(int pammo) {
        this.pammo = pammo;
    }

    public int getPmag() {
        return pmag;
    }

    public void setPmag(int pmag) {
        this.pmag = pmag;
    }

    public int getPmaxmag() {
        return pmaxmag;
    }

    public void setPmaxmag(int pmaxmag) {
        this.pmaxmag = pmaxmag;
    }

    public int getPmaxammo() {
        return pmaxammo;
    }

    public void setPmaxammo(int pmaxammo) {
        this.pmaxammo = pmaxammo;
    }
     public int getSammo() {
        return sammo;
    }

    public void setSammo(int sammo) {
        this.sammo = sammo;
    }

    public int getSmag() {
        return smag;
    }

    public void setSmag(int smag) {
        this.smag = smag;
    }

    public int getSmaxmag() {
        return smaxmag;
    }

    public void setSmaxmag(int smaxmag) {
        this.smaxmag = smaxmag;
    }

    public int getSmaxammo() {
        return smaxammo;
    }

    public void setSmaxammo(int smaxammo) {
        this.smaxammo = smaxammo;
    }
        public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    
}


