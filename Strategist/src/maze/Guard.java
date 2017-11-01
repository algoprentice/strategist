/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import static maze.Commons.*;
/**
 *
 * @author Swapnil Jain
 */
public class Guard extends Actor {
    private boolean visible;
    private int oldX, oldY;
    public long timeOfDeath;
    private int dx, dy;
    
    public Guard(int x, int y) {
        super(x, y);
        ImageIcon iia = new ImageIcon("images/alien.png");
        Image image = iia.getImage();
        this.setImage(image);
        
        oldX = x;
        oldY = y;
        visible = true;
    }
    
    public boolean isVisible() {return visible;}
    public void setVisible(boolean vis) {visible = vis;} 
    
    
    public void move(ArrayList walls) {
        
        while(true) {
            dx = 0; dy = 0;
            giveD();
            int nx = this.x(), ny = this.y();
            this.setX(nx + dx);
            this.setY(ny + dy);
            
            boolean coll = true;
            for(int i = 0; i < walls.size(); i++) {
                Wall wall = (Wall) walls.get(i);
                if(this.collideWith(wall)) {
                    coll = false;
                    this.setX(nx);
                    this.setY(ny);
                    break;
                }
            }
            
            if(!coll) continue;
            break;
        }
    }
    
    public void giveD() {
        Random randomGenerator = new Random();
        int randomMove = randomGenerator.nextInt(4);
        int motion = 7;
        switch (randomMove) {
            case 1:
                dx = motion;
                dy = 0;
                break;
            case 2:
                dx = 0;
                dy = motion;
                break;
            case 3:
                dx = 0;
                dy = -motion;
                break;
            default:
                dx = -motion;
                dy = 0;
                break;
        }
    }
}
