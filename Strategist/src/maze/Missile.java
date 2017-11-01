/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.Image;
import javax.swing.ImageIcon;
import static maze.Commons.*;
/**
 *
 * @author Swapnil Jain
 */
public class Missile extends Actor {
    private boolean visible;
    private int direction;
    
    public Missile(int x, int y, int direction) {
        super(x, y);
        ImageIcon iia = new ImageIcon("images/missile.png");
        Image image = iia.getImage();
        this.setImage(image);
        
        this.direction = direction;
        visible = true;
    }
    
    public boolean isVisible() {return visible;}
    public void setVisible(boolean vis) {visible = vis;} 
    
    public void move() {
        int speed = 5;
        switch (direction) {
            case LEFT:
                this.setX(this.x() - speed);
                break;
            case RIGHT:
                this.setX(this.x() + speed);
                break;
            case UP:
                this.setY(this.y() - speed);
                break;
            case BOTTOM:
                this.setY(this.y() + speed);
                break;
            default:
                break;
        }
    }
}
