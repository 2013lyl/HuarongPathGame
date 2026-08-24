package org.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Main extends JPanel {
    int WIDTH=800, HEIGHT=800;
    int FPS=60;
    static final int ROW=5, COL=5;
    final int BLOCK_WIDTH=WIDTH/COL,BLOCK_HEIGHT=HEIGHT/ROW;

    final Color backgroundColor = new Color(255, 255, 0);
    final Color blockColor = new Color(255, 255, 200);

    Random rand = new Random();

    boolean running = true;

    float updateDuring=(float)1000/FPS;
    Timer updateTimer;

    int mouseX, mouseY;

    int[][] board = new int[ROW][COL];

    List<Block> blockList = new ArrayList<>();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);

        for (int r=0;r<ROW+1;r++) {
            g2.drawLine(0, r * BLOCK_HEIGHT, WIDTH, r * BLOCK_HEIGHT);
        }

        for (int c=0;c<COL+1;c++) {
            g2.drawLine(c * BLOCK_WIDTH, 0, c * BLOCK_WIDTH, HEIGHT);
        }

        for (Block block : blockList) {
            block.draw(g);
        }
    }

    public boolean checkWin() {
        for (int r=0;r<ROW;r++) {
            for (int c=0;c<COL;c++) {
                int expect;
                if (r == ROW-1 && c == COL-1) {
                    expect = 0;
                } else {
                    expect = r * COL + c + 1;
                }
                if (board[r][c] != expect) {
                    return false;
                }
            }
        }

        return true;
    }

    public void initGame() {
        board = new int[ROW][COL];

        blockList = new ArrayList<>();

        List<Integer> remainingBlock = new ArrayList<>();
        int length = ROW*COL;
        for (int i=1;i<length;i++) {
            remainingBlock.add(i);
        }
        for (int i=0;i<ROW;i++) {
            int l;
            if (i < ROW-1) {
                l = COL;
            }
            else {
                l = COL-1;
            }
            for (int j=0;j<l;j++) {
                int index = rand.nextInt(remainingBlock.size());
                board[i][j] = remainingBlock.get(index);
                Block newBlock = new Block(i, j, j*BLOCK_WIDTH, i*BLOCK_WIDTH, BLOCK_WIDTH, BLOCK_HEIGHT, board[i][j], blockColor);
                blockList.add(newBlock);
                remainingBlock.remove(index);
            }
        }
    }

    public void update() {
        repaint();

        if (running) {
            for (Block block : blockList) {
                if (block.contains(mouseX, mouseY)) {
                    block.isCollidingMouse = true;
                }
                else {
                    block.isCollidingMouse = false;
                }

                block.update(board);
            }

            if (checkWin()) {
                running = false;
            }
        }
    }

    public void clicked() {
        for (Block block : blockList) {
            if (block.contains(mouseX, mouseY)) {
                block.isClicked = true;
            }
            else {
                block.isClicked = false;
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Huarong Path");
        frame.setSize(main.WIDTH, main.HEIGHT);
        frame.getContentPane().setBackground(main.backgroundColor);

        frame.add(main);

        main.initGame();

        main.running = true;

        main.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                main.mouseX = e.getX();
                main.mouseY = e.getY();
            }
        });

        main.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                main.clicked();
            }
        });

        main.updateTimer = new Timer(
                (int) main.updateDuring, e -> {
                    main.update();
                }
        );
        main.updateTimer.start();

        frame.setVisible(true);
    }
}