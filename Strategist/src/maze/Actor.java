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
import java.awt.Rectangle;

public class Actor {
    private final int SPACE = 20;

    private int x;
    private int y;
    private Image image;

    public Actor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image img) {
        image = img;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public boolean isLeftCollision(Actor actor) {
        return ((this.x() - SPACE) == actor.x()) &&
                (this.y() == actor.y());
    }

    public boolean isRightCollision(Actor actor) {
        return ((this.x() + SPACE) == actor.x())
                && (this.y() == actor.y());
    }

    public boolean isTopCollision(Actor actor) {
        return ((this.y() - SPACE) == actor.y()) &&
                (this.x() == actor.x());
    }

    public boolean isBottomCollision(Actor actor) {
        return ((this.y() + SPACE) == actor.y())
                && (this.x() == actor.x());
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }
    
    public boolean collideWith(Actor actor) {
        Rectangle rself = this.getBounds();
        Rectangle ractor = actor.getBounds();
        
        return rself.intersects(ractor);
    }
}
