package org.game;

import java.awt.*;

public class Block {
    int r, c;
    int x, y;
    int w, h;
    int val;
    Color originalColor;
    Color color;

    public boolean isClicked;
    public boolean isCollidingMouse;

    public Block(int r, int c, int x, int y, int w, int h, int val, Color color) {
        this.r = r;
        this.c = c;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.val = val;
        this.originalColor = color;
        this.color = color;

        this.isClicked = false;
        this.isCollidingMouse = false;
    }

    public boolean contains(int px, int py) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }

    public void update(int[][] board) {
        if (isCollidingMouse) {
            color = new Color(Math.min(color.getRed()+20, 255), Math.min(color.getGreen()+20, 255), Math.min(color.getBlue()+20, 255));
        }
        else {
            color = originalColor;
        }

        if (isClicked) {
            // Check if there are any available rooms around
            boolean flag = false;
            int targetR=-1, targetC=-1;

            int[][] directions = {
                    {0, 1},
                    {0, -1},
                    {1, 0},
                    {-1, 0}
            };

            for (int[] d : directions) {
                int dx = d[0];
                int dy = d[1];

                int newR = dy + r;
                int newC = dx + c;

                if (newC < 0 || newC >= board[0].length || newR < 0 || newR >= board.length) continue;

                if (board[newR][newC] == 0) {
                    flag = true;
                    targetC = newC;
                    targetR = newR;
                }
            }

            if (flag) {
                board[targetR][targetC] = val;
                board[r][c] = 0;

                r = targetR;
                c = targetC;
                x = c * w;
                y = r * h;

                isClicked = false;
            }
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(color);
        g2.fillRect(x, y, w, h);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(x, y, w, h);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, w / 2));
        String text = String.valueOf(val);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int centerX = x + (w - textWidth) / 2;
        int centerY = y + (h + textHeight) / 2 - 2;
        g2.drawString(text, centerX, centerY);
    }
}