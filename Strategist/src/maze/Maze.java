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

import javax.swing.JFrame;


public final class Maze extends JFrame {

    private final int OFFSET = 30;

    public Maze() {
        InitUI();
    }

    public void InitUI() {
        Board board = new Board();
        add(board);
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setSize(board.appearingW, board.appearingH);
      
        setLocationRelativeTo(null);
        setTitle("Maze");
    }


    public static void main(String[] args) {
        Maze maze = new Maze();
        maze.setVisible(true);
    }
}