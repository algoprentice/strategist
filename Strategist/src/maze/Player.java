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
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.ImageIcon;
import static maze.Commons.*;

public class Player extends Actor {

    private int direction;
    private ArrayList missiles;
    private Bomb bomb;
    
    Player(int x, int y) {
        super(x,y);
        
        direction = RIGHT;
        missiles = new ArrayList();
        bomb = new Bomb(0,0);
        
        ImageIcon iia = new ImageIcon("images/sokoban.png");
        Image image = iia.getImage();
        this.setImage(image);
    }
    
    public void move(int dx, int dy) {
        this.setX(this.x() + dx);
        this.setY(this.y() + dy);
     //   System.out.println(this.x() + " , " + this.y());
        
    }
    
    public void keyPressed(KeyEvent e, Map worldMap) {
        int dx=0, dy=0;
        int key = e.getKeyCode();
        boolean changeMade = false;
        
        if (key == KeyEvent.VK_LEFT) {
     
            if(!wallCollision((ArrayList) worldMap.get("walls"), Commons.LEFT_COLLISION)) {
                dx = -SPACE;
                changeMade = true;
                this.setDirection(LEFT);   
            }
            
        }else if (key == KeyEvent.VK_RIGHT) {
            
            if(!wallCollision((ArrayList) worldMap.get("walls"), Commons.RIGHT_COLLISION)) {
                dx = SPACE;
                changeMade = true;
                this.setDirection(RIGHT);
            }
            
        }else if (key == KeyEvent.VK_UP) {
       
            if(!wallCollision((ArrayList) worldMap.get("walls"), Commons.TOP_COLLISION)) {
                dy = -SPACE;
                changeMade = true;
                this.setDirection(UP);
            }
        }else if (key == KeyEvent.VK_DOWN) {
            
            if(!wallCollision((ArrayList) worldMap.get("walls"), Commons.BOTTOM_COLLISION)) {
                dy = SPACE;
                changeMade = true;
                this.setDirection(BOTTOM);
            }
        } else if(key == KeyEvent.VK_SPACE) {
            fire();
        } else if(key == KeyEvent.VK_A) {
            if(!bomb.visible) {
                bomb.setX(this.x());
                bomb.setY(this.y());
                bomb.placeBomb();
            }
        }
        
        if(changeMade) {
            this.move(dx,dy);
        }
    }
    
    public Bomb getBomb() {
        return bomb;
    }
    
    public void fire() {
        missiles.add(new Missile(this.x()+7, this.y()+7, this.getDirection()));
    }
    
    public ArrayList getMissiles() {return missiles;}
    
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
  
        }

        if (key == KeyEvent.VK_RIGHT) {

        }

        if (key == KeyEvent.VK_UP) {
     
        }

        if (key == KeyEvent.VK_DOWN) {
       
        }
    }
    
    private boolean wallCollision(ArrayList walls, int COLLISION) {
        if(COLLISION == Commons.LEFT_COLLISION) {
            for(int i = 0; i < walls.size(); i++) {
                Wall wall = (Wall) walls.get(i);
                if(this.isLeftCollision(wall)) return true;
            }
        }
        
        if(COLLISION == Commons.RIGHT_COLLISION) {
            for(int i = 0; i < walls.size(); i++) {
                Wall wall = (Wall) walls.get(i);
                if(this.isRightCollision(wall)) return true;
            }
        }
        
        if(COLLISION == Commons.TOP_COLLISION) {
            for(int i = 0; i < walls.size(); i++) {
                Wall wall = (Wall) walls.get(i);
                if(this.isTopCollision(wall)) return true;
            }
        }
        
        if(COLLISION == Commons.BOTTOM_COLLISION) {
            for(int i = 0; i < walls.size(); i++) {
                Wall wall = (Wall) walls.get(i);
                if(this.isBottomCollision(wall)) return true;
            }
        }
        
        return false;
    }
    
    public int getDirection() {
        return this.direction;
    } 
    
    public void setDirection(int direc) {
        this.direction = direc;
    }
}
