/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author stant
 */
public class Door extends Object{
    
    private boolean open = false;
    
    public Door(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
    }
    
}
