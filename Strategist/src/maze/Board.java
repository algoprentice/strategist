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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import static maze.Commons.*;

public class Board extends JPanel implements ActionListener {
   
    private boolean gameover = false;
    private final Timer timer;
    private final long startTime = System.currentTimeMillis();
    private final ArrayList walls = new ArrayList();
    private ArrayList world;
    private Map worldMap;
    
    private ArrayList guards = new ArrayList();
    private Player player;
    private Terminal terminal;
    private ArrayList apples = new ArrayList();
    
    private int w = 0, h = 0;
    public int appearingW = 0, appearingH = 0;
    
    private final String level =
            "#################################\n"+
            "#                       #########\n"+
            "# ######### ########### ### ### ?\n"+
            "# #      @#       ##@   ### #   #\n"+
            "#$# ####### ############### # ###\n"+
            "# #                  @# ### # ###\n"+
            "# ################### #     #   #\n"+
            "# ################### ##### ### #\n"+
            "#                  @#           #\n"+
            "#################################\n";
    
    
    public Board() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
        
        timer = new Timer(Commons.DELAY, this);
        timer.start();
    }
    
    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }
    
    public final void initWorld() {
        int x = Commons.OFFSET, y = Commons.OFFSET;
        Wall wall;
        Apple apple;
        Guard guard;
        
        for(int i = 0; i < level.length(); i++) {
            char item = level.charAt(i);
            
            if(item == '\n') {
                y += SPACE;
                if(this.w < x) this.w = x;
                x = OFFSET;
            } else if(item == '#') {
                wall = new Wall(x,y);
                walls.add(wall);
                x += SPACE;
            } else if(item == '?') {
                terminal = new Terminal(x,y);
                x += SPACE;
            } else if(item == ' ') {
                x += SPACE;
            } else if(item == '$') {
                player = new Player(x,y);
                x += SPACE;
            } else if(item == '@') {
                apple = new Apple(x,y);
                apples.add(apple);
                
                guard = new Guard(x,y);
                guards.add(guard);
                
                x += SPACE;
            }
            
            h = y;
        }
        
        appearingW = (w + OFFSET)/2;
        appearingH = h + 2*OFFSET;
        
        
    }
    
    public void buildWorld(Graphics g) {
        world = new ArrayList();
        worldMap = new HashMap<> ();
        
        world.addAll(walls);
        world.add(player);
        world.add(terminal);
        world.addAll(apples);
        world.addAll(guards);
        
        worldMap.put("walls", walls);
        worldMap.put("player", player);
        worldMap.put("terminal", terminal);
        worldMap.put("apples", apples);
        worldMap.put("guards", guards);
        
        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.appearingW*2, this.appearingH);

        for (int i = 0; i < world.size(); i++) {

            Actor item = (Actor) world.get(i);

            if(item instanceof Guard) {
                Guard guard = (Guard) item;
                if(guard.isVisible()) g.drawImage(item.getImage(), item.x() + 4, item.y() + 4, this);
            }
            else if ((item instanceof Player)
                    || (item instanceof Terminal) || (item instanceof Apple)) {
                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } else {
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }
        }
        
        ArrayList missiles = player.getMissiles();
        for(int i = 0; i < missiles.size(); i++) {
            Missile missile = (Missile) missiles.get(i);
            g.drawImage(missile.getImage(), missile.x(), missile.y(), this);
        }
        
        Bomb bomb = player.getBomb();
        if(bomb.visible) {
            g.drawImage(bomb.getImage(), bomb.x(), bomb.y(), this);
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(gameover) {
            drawGameOver(g);
        } else if(isCompleted()) {
            drawCompleted(g);
        } else {
            int offsetMaxX = appearingW;
            int offsetMinX = 0;
            
            int camX = player.x() - appearingW / 2;
            
            if (camX > offsetMaxX)
                camX = offsetMaxX;
            else if (camX < offsetMinX)
                camX = offsetMinX;
            
            g.translate(-camX, 0);      
            
            buildWorld(g);
        }
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(isCompleted()) {    
            timer.stop();
        }
        
        updateApples();
        updateBomb();
        updateMissiles();
        updateGuards();
        
        
        repaint();

    }
    
    public void updateMissiles() {
        ArrayList missiles = player.getMissiles();
        for(int i = 0; i < missiles.size(); i++) {
            Missile missile = (Missile) missiles.get(i);
            missile.move();
                        
            for(int k = 0; k < walls.size(); k++) {
                
                Wall wall = (Wall) walls.get(k);
                if(missile.collideWith(wall)) {
                    System.out.println("Co");
                    missiles.remove(i);
                    break;
                }
            }
        }    
    }
    
    public void updateGuards() {
        for(int i = 0; i < guards.size(); i++) {
            Guard guard = (Guard) guards.get(i);
            if(guard.isVisible()) {
                guard.move(walls);
                
                if(player.collideWith(guard)) {
                    this.gameover = true;
                    return;
                }  
                
                ArrayList missiles = player.getMissiles();
                for(int m = 0; m < missiles.size(); m++) {
                    
                    Missile missile = (Missile) missiles.get(m);
                    
                    if(missile.collideWith(guard)) {
                        missiles.remove(m);
                        guard.setVisible(false);
                        guard.timeOfDeath = System.currentTimeMillis();
                        break;
                    }
                }
                
            } else {
                long currentTime = System.currentTimeMillis();
                if(currentTime - guard.timeOfDeath > 3000) guard.setVisible(true);
            }
        }
    }
    
    public void updateApples() {
        for(int i = 0; i < apples.size(); i++) {
            Apple apple = (Apple) apples.get(i);
            if(apple.isVisible() && apple.collideWith(player)) {
                apples.remove(i);
                System.out.println(apples.size() + " " + world.size());
            }
        }
    }
    
    private void drawGameOver(Graphics g) {
        
        String msg = "Better Luck Next Time!";
        Font small = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.red);
        g.setFont(small);
        g.drawString(msg, (appearingW - fm.stringWidth(msg)) / 2,
                appearingH / 2 - SPACE);
    }
    
    private void drawCompleted(Graphics g) {
        double timeTaken = System.currentTimeMillis() - startTime;
        timeTaken /= 1000;
        
        String msg = "Well Done!";
        Font small = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.red);
        g.setFont(small);
        g.drawString(msg, (appearingW - fm.stringWidth(msg)) / 2,
                appearingH / 2 - SPACE);
        
        msg = "Total Time Taken: " + timeTaken + " secs.";
        g.drawString(msg, (appearingW - fm.stringWidth(msg)) / 2, appearingH / 2 + SPACE);
    }
    
    public boolean isCompleted() {
        return (apples.isEmpty() && terminal.x() == player.x() && terminal.y() == player.y());
    }

    private void updateBomb() {
        long currentTime = System.currentTimeMillis();
        
        Bomb bomb = player.getBomb();
        long delayTime = currentTime - bomb.bombPlaceTime; 
        if(delayTime >= 5000) {
            bomb.visible = false;
        } 
        
        if(delayTime < 2000) return;
        if(!bomb.visible) return;
        
        boolean coll = false;
        for(int i = 0; i < guards.size(); i++) {
            Guard guard = (Guard) guards.get(i);
            if(bomb.collideWith(guard)) {
                guard.setVisible(false);
                guard.timeOfDeath = System.currentTimeMillis();
                coll = true;
            }
        }
        
        if(coll) bomb.visible = false;
    }
    
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e, worldMap);
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }
}
