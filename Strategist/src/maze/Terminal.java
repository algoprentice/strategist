/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

/**
 *
 * @author Swapnil Jain
 */
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

class Terminal extends Actor {

    Terminal(int x, int y) {
        super(x,y);
      
        ImageIcon iia = new ImageIcon("images/area.png");
        Image image = iia.getImage();
        this.setImage(image);
    }
    
}
