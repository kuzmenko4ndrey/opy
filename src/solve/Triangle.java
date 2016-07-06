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
public class Triangle {
    
    public Vertex a, b, c;
    public Triangle l = null, r = null;
    
    public int numberOfColVer() {
        int k = 0;
        if (a.getColor() > -1) {
            k++;
        }
        if (b.getColor() > -1) {
            k++;
        }
        if (c.getColor() > -1) {
            k++;
        }
        return k;
    }
    
}
