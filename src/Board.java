import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JFrame implements KeyListener {
    private int width;
    private int height;
    private int cellSize;
    private int boardSize;
    private Snake snake;
    private Food food;
    private JPanel panel;
    public Board(Snake snake, Food food){
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.width = gd.getDisplayMode().getWidth();
        this.height = gd.getDisplayMode().getHeight();
        this.snake = snake;
        this.food = food;
        addKeyListener(this);
        if(width > height)
            this.cellSize = (int)((float)height * 0.98f / Main.boardSize);
        else
            this.cellSize = (int)((float)width * 0.98f / Main.boardSize);

        this.setTitle("JSnake");
        this.setSize(this.width,this.height);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.panel = new JPanel(){
            @Override
            public void paint(Graphics g){
                var g2d = (Graphics2D)g;
                g2d.setColor(new Color(40, 44, 52));
                g2d.fillRect(0,0,width,height);
                int drawSize = cellSize*Main.boardSize;
                g2d.translate((width-height)/2,0);
                g2d.setColor(g2d.getColor().darker());
                g2d.fillRect(0,0,drawSize,drawSize);
                Color c =g2d.getColor().darker();
                g2d.setColor(c);
                for(int i=0;i<Main.boardSize;i++)
                    for(int j=0;j<Main.boardSize;j++){
                        g2d.setColor(c);
                        int finalI = i;
                        int finalJ = j;
                        snake.body.forEach(e -> {
                            if(e.x == finalI && e.y == finalJ)
                                g2d.setColor(Color.red);
                        });
                        if(food.x == i && food.y==j) g2d.setColor(Color.GREEN);
                        g2d.fillRect(i*cellSize+2,j*cellSize+2,cellSize-4,cellSize-4);
                    }
                if(Main.isOver){
                    g2d.setColor(new Color(40, 44, 52).brighter());
                    g2d.fillRect(cellSize*Main.boardSize/4,cellSize*Main.boardSize/4,Main.boardSize/2*cellSize,Main.boardSize/2*cellSize);
                    g2d.setColor(Color.white);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setFont(g2d.getFont().deriveFont(Font.BOLD,20));
                    g2d.drawString("Game Over :( Your score was "+snake.body.size(),(int)((float)cellSize*(float)Main.boardSize/2.8),cellSize*Main.boardSize/2);
                    g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC,15));
                    g2d.drawString("Press any button to restart",(int)((float)cellSize*(float)Main.boardSize/2.8)+70,cellSize*Main.boardSize/2+20);
                }

            }
        };
        panel.setSize(this.getSize());
        this.add(panel);
    }
    public void keyTyped(KeyEvent e) {

    }
    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                if (!snake.direction.equals("down"))snake.direction="up";
                break;
            case KeyEvent.VK_DOWN:
                if (!snake.direction.equals("up"))snake.direction="down";
                break;
            case KeyEvent.VK_LEFT:
                if (!snake.direction.equals("right"))snake.direction="left";
                break;
            case KeyEvent.VK_RIGHT:
                if (!snake.direction.equals("left"))snake.direction="right";
                break;
        }
        if(Main.isOver){
            Main.isOver = false;
            snake.init();
            food.spawn();
        }
    }
    public void keyReleased(KeyEvent e) {

    }
}
