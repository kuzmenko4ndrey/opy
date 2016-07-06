/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solve;

/**
 *
 * @author neophron
 */
public class Vertex {
    
    private int x, y, color = -1;
    
    public Vertex(int _x, int _y) {
        x = _x;
        y = _y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public boolean setColor(int c) {
        if (color == -1) {
            color = c;
            return true;
        }
        return false;
    }
    
    public int getColor() {
        return color;
    }
    
}
