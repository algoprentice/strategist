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
public class Apple extends Actor {
    private boolean visible;
    
    public Apple(int x, int y) {
        super(x, y);
        ImageIcon iia = new ImageIcon("images/apple.png");
        Image image = iia.getImage();
        this.setImage(image);
        
        visible = true;
    }
    
    boolean isVisible() {
        return this.visible;
    }
    
    public void setVisible(boolean vis) {
        visible = vis;
    }
}
