/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Swapnil Jain
 */
public class Bomb extends Actor {
    public boolean visible;
    public long bombPlaceTime;
    
    public Bomb(int x, int y) {
        super(x, y);
        ImageIcon iia = new ImageIcon("images/bomb.png");
        Image image = iia.getImage();
        this.setImage(image);
    }
    
    public void placeBomb() {
        this.visible = true;
        this.bombPlaceTime = System.currentTimeMillis();
    }
}
