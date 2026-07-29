package gui;

import application.Segmento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public  class GamePanel extends JPanel implements KeyListener {


    void restartGame() {

        list.clear();

        list.add(new Segmento(20, 20));

        score = 0;

        gameOver = false;

        direcao = "RIGHT";

        boolean ocupado;
        do {
            foodX = random.nextInt(SCREEN_WIDTH / 20) * 20;
            foodY = random.nextInt(SCREEN_HEIGHT / 20) * 20;

            ocupado = false;

            for (Segmento s : list) {
                if (s.getSegX() == foodX && s.getSegY() == foodY) {
                    ocupado = true;

                    break;
                }
            }

        } while (ocupado);

        repaint();

    }

    String direcao = "RIGHT";
    Timer timer;
    boolean gameOver = false;
    int foodX = 200;
    int foodY = 200;
    int score = 0;

    Random random = new Random();

    java.util.List<Segmento> list = new ArrayList<>();


    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;


    GamePanel() {

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        this.setBackground(Color.black);

        this.setFocusable(true);

        this.addKeyListener(this);

        list.add(new Segmento(20,20));

        timer = new Timer(100, e -> move());
        timer.start();

    }

    void move() {

        if (gameOver) {
            return;
        }

        Segmento cabeca = list.get(0);

        int oldX = cabeca.getSegX();
        int oldY = cabeca.getSegY();

        if (direcao.equals("RIGHT")) {
            if (cabeca.getSegX() + 20 >= SCREEN_WIDTH) {
                gameOver = true;
            } else {
                cabeca.setSegX(cabeca.getSegX() + 20);
            }
        } else if (direcao.equals("LEFT")) {
            if (cabeca.getSegX() - 20 < 0) {
                gameOver = true;
            } else {
                cabeca.setSegX(cabeca.getSegX() - 20);
            }
        } else if (direcao.equals("UP")) {
            if (cabeca.getSegY() - 20 < 0) {
                gameOver = true;
            } else {
                cabeca.setSegY(cabeca.getSegY() - 20);
            }
        } else if (direcao.equals("DOWN")) {
            if (cabeca.getSegY() + 20 >= SCREEN_HEIGHT)
                gameOver = true;
            else {
                cabeca.setSegY(cabeca.getSegY() + 20);
            }
        }

        for (int i = 1; i < list.size(); i++) {
            int tempX = list.get(i).getSegX();
            int tempY = list.get(i).getSegY();

            list.get(i).setSegX(oldX);
            list.get(i).setSegY(oldY);

            oldX = tempX;
            oldY = tempY;
        }

        boolean ocupado;

        if (cabeca.getSegX() == foodX && cabeca.getSegY() == foodY) {
            score++;

            do {
                foodX = random.nextInt(SCREEN_WIDTH / 20) * 20;
                foodY = random.nextInt(SCREEN_HEIGHT / 20) * 20;

                ocupado = false;

                for (Segmento s : list) {
                    if (s.getSegX() == foodX && s.getSegY() == foodY) {
                        ocupado = true;

                        break;
                    }
                }

            } while (ocupado);
            list.add(new Segmento(oldX, oldY));

        }
        for (int i = 1; i < list.size(); i++) {
            if (cabeca.getSegX() == list.get(i).getSegX()
                    && cabeca.getSegY() == list.get(i).getSegY()) {
                gameOver = true;
                break;
            }
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameOver){
            Font fonte = new Font("Arial", Font.BOLD, 30);
            g.setFont(fonte);

            FontMetrics fm = g.getFontMetrics();

            String texto = "GAME OVER";

            int x = (SCREEN_WIDTH - fm.stringWidth(texto)) / 2;
            int y = SCREEN_HEIGHT / 2;

            g.drawString(texto, x, y);
        }
        else {
            g.setColor(Color.red);
            g.fillOval(foodX,foodY,20,20);

            g.setColor(Color.WHITE);
            g.drawString("Score: " + score, 10, 20);

            for (int i = 0; i < list.size(); i++) {

                if (i == 0) {
                    g.setColor(Color.darkGray);
                } else {
                    g.setColor(Color.green);
                }

                Segmento s = list.get(i);
                g.fillRect(s.getSegX(), s.getSegY(), 20, 20);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP && !direcao.equals("DOWN")){
            direcao = "UP";
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && !direcao.equals("UP")) {
            direcao = "DOWN";
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && !direcao.equals("RIGHT")) {
            direcao = "LEFT";
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !direcao.equals("LEFT")) {
            direcao = "RIGHT";
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && gameOver) {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

