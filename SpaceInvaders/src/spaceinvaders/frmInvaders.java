package spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class frmInvaders extends JFrame implements KeyListener {

    public frmInvaders() {
        initComponents();
        addKeyListener(this);

        int cuadriculaX = 20, cuadriculaY = 170;

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 11; x++) {
                if (y == 0) {
                    invasores[y][x] = new Objeto(cuadriculaX, cuadriculaY, 11, 50, 1);
                }
                if (y == 1) {
                    invasores[y][x] = new Objeto(cuadriculaX, cuadriculaY, 11, 50, 2);
                }
                if (y == 2) {
                    invasores[y][x] = new Objeto(cuadriculaX, cuadriculaY, 11, 50, 2);
                }
                if (y == 3) {
                    invasores[y][x] = new Objeto(cuadriculaX, cuadriculaY, 11, 50, 3);
                }
                if (y == 4) {
                    invasores[y][x] = new Objeto(cuadriculaX, cuadriculaY, 11, 50, 3);
                }

                invasores[y][x].vivo = true;
                cuadriculaX += 50;
            }

            cuadriculaX = 20;
            cuadriculaY += 60;
        }

        for (int i = 0; i < 3; i++) {
            balaInvasores[i] = new Objeto(-20, 0, 0, 10, 0);
        }
    }

    boolean ColisionNaveRara = false;

    int ContadorColisionNaveRara = 0;

    int contadorInvadersMuertos = 0;
    int score, lives = 3;

    int decimasSegundo = 0;

    int aleatorioNaveRara;
    int aleatorioPuntajeNaveRara;
    boolean navRaraON = false;

    boolean disparoBala = false;
    boolean balaEnElAire = false;

    boolean colisionBalaNave = false;

    int[][] contadorColisionesInvaders = new int[5][11];
    int contadorNaveAtacada = 0;

    boolean movDerecha = true;

    boolean dibujarNaveRara = false;

    boolean canMove = true;

    int velocidadInvasores = 20;
    int alturaInvasores = 0;

    Objeto nave = new Objeto(754 / 2, 650, 10, 0, 1);
    Objeto bala = new Objeto(0, 0, 0, 30, 0);

    Objeto invasores[][] = new Objeto[5][11];

    Objeto balaInvasores[] = new Objeto[3];

    Objeto NaveRara = new Objeto(-100, 100, 8, 0, 0);

    int[] posXbalaInvasores = new int[3];

    int[] posYbalaInvasores = new int[3];

    int aleatorioBala0 = 0;
    int aleatorioBala1 = 0;

    int balasDisparadas = -1;

    public void vidaPerdida() {
        temporizador.stop();
        espera.start();
        temporizador.start();
    }

    public void paint(Graphics gr) {
        super.paint(gr);
        this.getContentPane().setBackground(Color.BLACK);

        BufferedImage imgNegro = null;
        BufferedImage imgExplosion = null;
        BufferedImage imgNave = null;
        BufferedImage imgBala = null;
        BufferedImage imgInvader1 = null;
        BufferedImage imgInvader2 = null;
        BufferedImage imgInvader3 = null;
        BufferedImage imgInvader11 = null;
        BufferedImage imgInvader21 = null;
        BufferedImage imgInvader31 = null;
        BufferedImage imgbalaInvaders0 = null;
        BufferedImage imgbalaInvaders1 = null;
        BufferedImage imgNaveRara = null;

        try {
            imgNegro = ImageIO.read(new File("src\\spaceinvaders\\negro.png"));
            imgExplosion = ImageIO.read(new File("src\\spaceinvaders\\explosion.png"));
            imgNave = ImageIO.read(new File("src\\spaceinvaders\\naveImg.png"));
            imgBala = ImageIO.read(new File("src\\spaceinvaders\\bala.png"));
            imgInvader1 = ImageIO.read(new File("src\\spaceinvaders\\invasor1.png"));
            imgInvader2 = ImageIO.read(new File("src\\spaceinvaders\\invasor2.png"));
            imgInvader3 = ImageIO.read(new File("src\\spaceinvaders\\invasor3.png"));
            imgInvader11 = ImageIO.read(new File("src\\spaceinvaders\\invasor1.1.png"));
            imgInvader21 = ImageIO.read(new File("src\\spaceinvaders\\invasor2.1.png"));
            imgInvader31 = ImageIO.read(new File("src\\spaceinvaders\\invasor3.1.png"));
            imgbalaInvaders0 = ImageIO.read(new File("src\\spaceinvaders\\balaInvaders0.png"));
            imgbalaInvaders1 = ImageIO.read(new File("src\\spaceinvaders\\balaInvaders1.png"));
            imgNaveRara = ImageIO.read(new File("src\\spaceinvaders\\naveImgColor.png"));
        } catch (IOException ex) {
            Logger.getLogger(frmInvaders.class.getName()).log(Level.SEVERE, null, ex);
        }

        gr.setFont(new Font("consolas", Font.LAYOUT_LEFT_TO_RIGHT, 25));

        gr.setColor(Color.WHITE);
        gr.drawString("SCORE", 40, 65);
        gr.drawString("LIVES", 470, 65);

        gr.setColor(Color.GREEN);
        gr.drawString(String.valueOf(score), 120, 65);
        gr.fillRect(0, 680, 754, 30);
        //vidas

        if (lives > 0) {
            gr.drawImage(imgNave, 550, 40, null);
        }

        if (lives > 1) {
            gr.drawImage(imgNave, 610, 40, null);
        }

        if (lives > 2) {
            gr.drawImage(imgNave, 670, 40, null);
        }

        gr.drawImage(imgBala, bala.getCoorX(), bala.getCoorY(), null);

        if (nave.getSprite() == 1) {
            gr.drawImage(imgNave, nave.getCoorX(), nave.getCoorY(), null);
        }

        if (nave.getSprite() == 0) {
            gr.drawImage(imgExplosion, nave.getCoorX(), nave.getCoorY() - 15, null);
        }

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 11; x++) {
                if (invasores[y][x].getSprite() == 9 || invasores[y][x].vivo == true) {
                    switch (invasores[y][x].getSprite()) {
                        case 9:
                            gr.drawImage(imgExplosion, invasores[y][x].getCoorX(), invasores[y][x].getCoorY(), null);
                            break;
                        case 0:
                            gr.drawImage(imgNegro, invasores[y][x].getCoorX(), invasores[y][x].getCoorY(), null);
                            break;
                        case 1:
                            gr.drawImage(imgInvader1, invasores[y][x].getCoorX(), invasores[y][x].getCoorY(), null);
                            break;
                        case 2:
                            gr.drawImage(imgInvader2, invasores[y][x].getCoorX(), invasores[y][x].getCoorY(), null);
                            break;
                        case 3:
                            gr.drawImage(imgInvader3, invasores[y][x].getCoorX(), invasores[y][x].getCoorY(), null);
                            break;
                        case -1:
                            gr.drawImage(imgInvader11, invasores[y][x].getCoorX(), invasores[y][x].getCoorY(), null);
                            break;
                        case -2:
                            gr.drawImage(imgInvader21, invasores[y][x].getCoorX(), invasores[y][x].getCoorY(), null);
                            break;
                        case -3:
                            gr.drawImage(imgInvader31, invasores[y][x].getCoorX(), invasores[y][x].getCoorY(), null);
                            break;
                    }
                }

            }
        }

        for (int h = 0; h < 3; h++) {
            switch (balaInvasores[h].getSprite()) {
                case 0:
                    gr.drawImage(imgbalaInvaders0, balaInvasores[h].getCoorX(), balaInvasores[h].getCoorY(), null);
                    break;
                case 1:
                    gr.drawImage(imgbalaInvaders1, balaInvasores[h].getCoorX(), balaInvasores[h].getCoorY(), null);
                    break;
            }
        }

        if (dibujarNaveRara = true) {
            if (NaveRara.getSprite() == 0) {
                gr.drawImage(imgNaveRara, NaveRara.getCoorX(), NaveRara.getCoorY(), null);
            } else {
                gr.drawImage(imgExplosion, NaveRara.getCoorX(), NaveRara.getCoorY(), null);
            }
        }

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT && nave.getCoorX() > 20 && canMove == true) {
            nave.setCoorX(nave.getCoorX() - 10);

        } else if (keyCode == KeyEvent.VK_RIGHT && nave.getCoorX() < 754 - 75 && canMove == true) {
            nave.setCoorX(nave.getCoorX() + 10);

        } else if (keyCode == KeyEvent.VK_SPACE && canMove == true) {
            disparoBala = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            disparoBala = false;
        }

    }

    public int[] InvasoresExtremosIzqDer() {
        int InvasoresIzqDer[] = {0, 10};

        int contadorMuertosColumna = 0;

        for (int x = 0; x < 11; x++) {
            contadorMuertosColumna = 0;

            for (int y = 0; y < 5; y++) {
                if (invasores[y][x].vivo == false) {
                    contadorMuertosColumna++;
                }
            }

            if (contadorMuertosColumna < 5) {
                InvasoresIzqDer[0] = x;
                break;
            }

        }

        for (int x = 10; x > 0; x--) {
            contadorMuertosColumna = 0;

            for (int y = 0; y < 5; y++) {
                if (invasores[y][x].vivo == false) {
                    contadorMuertosColumna++;
                }
            }

            if (contadorMuertosColumna < 5) {
                InvasoresIzqDer[1] = x;
                break;
            }

        }

        return InvasoresIzqDer;
    }

    public void CheckColisionNave_Invasor() {
        for (int y = 4; y >= 0; y--) {
            for (int x = 0; x < 11; x++) {
                boolean c1 = invasores[y][x].getCoorY() + 20 >= nave.getCoorY();
                if (c1) {
                    repaint(400, 20, 400, 20);
                    temporizador.stop();
                    JOptionPane.showMessageDialog(null, "GAME OVER");
                    System.exit(0);

                }
            }
        }
    }

    public void CheckColisionBalaNave_Invasor() {

        for (int y = 4; y >= 0; y--) {
            for (int x = 0; x < 11; x++) {
                boolean c1 = invasores[y][x].getCoorY() + 20 >= bala.getCoorY() && invasores[y][x].getCoorY() <= bala.getCoorY();
                boolean c2 = invasores[y][x].getCoorX() - 5 <= bala.getCoorX() && invasores[y][x].getCoorX() + 43 >= bala.getCoorX() + 4;
                if ((c1 && c2) && invasores[y][x].vivo == true) {
                    switch (invasores[y][x].getSprite()) {
                        case 1:
                            score += 40;
                            break;
                        case 2:
                            score += 20;
                            break;
                        case 3:
                            score += 10;
                            break;
                        case -1:
                            score += 40;
                            break;
                        case -2:
                            score += 20;
                            break;
                        case -3:
                            score += 10;
                            break;
                    }

                    repaint(40, 20, 400, 20);

                    colisionBalaNave = true;
                    invasores[y][x].setSprite(9);
                    invasores[y][x].vivo = false;

                }

            }
        }

    }

    int tmpespera = 0;

    Timer espera = new Timer(100, new ActionListener() {
        public void actionPerformed(ActionEvent g) {
            tmpespera++;

            nave.setSprite(0);
            repaint(nave.getCoorX() - 20, nave.getCoorY() - 10, 52, 29);

            if (tmpespera == 100) {
                nave.setSprite(1);
                repaint(nave.getCoorX() - 20, nave.getCoorY() - 10, 52, 29);

                espera.stop();

            }
        }
    });

    Timer temporizador = new Timer(50, new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            contadorInvadersMuertos = 0;
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 11; x++) {

                    if (invasores[y][x].vivo == false) {
                        contadorInvadersMuertos++;
                    }
                }
            }

            if (contadorInvadersMuertos == 11 * 5) {
                repaint();
                temporizador.stop();
                JOptionPane.showMessageDialog(null, "FELICIDADES GANASTE!!!");
                System.exit(0);
            }
            repaint(nave.getCoorX() - 52, nave.getCoorY(), 52 * 3, 29);

            if (disparoBala == true || balaEnElAire == true) {
                if (balaEnElAire == false) {
                    bala.setCoorX(nave.getCoorX() + 24);
                    bala.setCoorY(nave.getCoorY() - 15);
                    balaEnElAire = true;
                    colisionBalaNave = false;
                } else //if balaenelaire == true 
                {
                    bala.setCoorY(bala.getCoorY() - bala.getVelY());

                    if (bala.getCoorY() < 70 || colisionBalaNave == true) {
                        balaEnElAire = false;
                        bala.setCoorY(700);
                        repaint(bala.getCoorX(), 70, 4, 650);
                    }
                }
                repaint(bala.getCoorX(), bala.getCoorY(), 4, 24);

            }

            if (decimasSegundo == velocidadInvasores) {

                for (int y = 0; y < 5; y++) {
                    for (int x = 0; x < 11; x++) {

                        if (invasores[y][x].vivo == true) {
                            invasores[y][x].setSprite(invasores[y][x].getSprite() * -1);
                            repaint(invasores[y][x].getCoorX() - 40, invasores[y][x].getCoorY(), 100, 20);
                        }

                        if (movDerecha) {
                            invasores[y][x].setCoorX(invasores[y][x].getCoorX() + 10);
                        } else if (!movDerecha) {
                            invasores[y][x].setCoorX(invasores[y][x].getCoorX() - 10);
                        }
                    }

                }

                if (invasores[0][InvasoresExtremosIzqDer()[0]].getCoorX() <= 20
                        || invasores[1][InvasoresExtremosIzqDer()[0]].getCoorX() <= 20
                        || invasores[2][InvasoresExtremosIzqDer()[0]].getCoorX() <= 20
                        || invasores[3][InvasoresExtremosIzqDer()[0]].getCoorX() <= 20
                        || invasores[4][InvasoresExtremosIzqDer()[0]].getCoorX() <= 20) {
                    movDerecha = true;
                    if (velocidadInvasores >= 3) {
                        velocidadInvasores -= 2;
                    }
                    alturaInvasores += 3;

                    for (int y = 0; y < 5; y++) {
                        for (int x = 0; x < 11; x++) {
                            if (invasores[y][x].vivo == true) {
                                invasores[y][x].setCoorY(invasores[y][x].getCoorY() + alturaInvasores);
                            }
                        }
                    }
                }

                if (invasores[0][InvasoresExtremosIzqDer()[1]].getCoorX() + 41 >= 754 - 50
                        || invasores[1][InvasoresExtremosIzqDer()[1]].getCoorX() + 41 >= 754 - 50
                        || invasores[2][InvasoresExtremosIzqDer()[1]].getCoorX() + 41 >= 754 - 50
                        || invasores[3][InvasoresExtremosIzqDer()[1]].getCoorX() + 41 >= 754 - 50
                        || invasores[4][InvasoresExtremosIzqDer()[1]].getCoorX() + 41 >= 754 - 50) {
                    movDerecha = false;
                    if (velocidadInvasores >= 3) {
                        velocidadInvasores -= 2;
                    }
                    alturaInvasores += 3;

                    for (int y = 0; y < 5; y++) {
                        for (int x = 0; x < 11; x++) {
                            if (invasores[y][x].vivo == true) {
                                invasores[y][x].setCoorY(invasores[y][x].getCoorY() + alturaInvasores);
                            }
                        }
                    }
                }

                decimasSegundo = 0;
            }

            CheckColisionBalaNave_Invasor();
            CheckColisionNave_Invasor();

            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 11; x++) {
                    if (invasores[y][x].vivo == false) {
                        contadorColisionesInvaders[y][x]++;
                    }

                    if (contadorColisionesInvaders[y][x] == 10) {
                        invasores[y][x].setSprite(0);
                    }
                }
            }

            if (nave.getSprite() == 0) {
                contadorNaveAtacada++;
                if (contadorNaveAtacada == 20) {
                    nave.setSprite(1);
                    contadorNaveAtacada = 0;
                    canMove = true;
                }
            }

            for (int h = 0; h < 3; h++) {
                if (balaInvasores[h].disparado == true) {
                    balaInvasores[h].setCoorY(balaInvasores[h].getCoorY() + balaInvasores[h].getVelY());
                    repaint(balaInvasores[h].getCoorX(), balaInvasores[h].getCoorY() - 20, 13, 20 * 2);

                    if (balaInvasores[h].getCoorY() + 20 >= 680) {
                        balaInvasores[h].disparado = false;
                        balaInvasores[h].setCoorX(-10);
                        balaInvasores[h].setCoorY(0);

                    }
                    if ((balaInvasores[h].getCoorX() >= nave.getCoorX() - 10 && balaInvasores[h].getCoorX() + 15 <= nave.getCoorX() + 52) && (balaInvasores[h].getCoorY() >= nave.getCoorY())) {
                        balaInvasores[h].disparado = false;
                        balaInvasores[h].setCoorX(-10);
                        balaInvasores[h].setCoorY(0);

                        lives--;
                        nave.setSprite(0);
                        canMove = false;

                        if (lives <= 0) {
                            repaint();
                            nave.setSprite(0);
                            temporizador.stop();
                            JOptionPane.showMessageDialog(null, "GAME OVER : SIN VIDAS");
                            System.exit(0);
                        }

                        repaint(balaInvasores[h].getCoorX(), balaInvasores[h].getCoorY() - 20, 13, 20 * 2);
                        repaint(470, 0, 400, 20);

                        System.out.println(lives);

                    }

                }

            }

            if (balasDisparadas < 3 && balasDisparadas > -1) {
                int posXaleatorio = (int) (Math.random() * 11);

                for (int y = 4; y >= 0; y--) {
                    if (invasores[y][posXaleatorio].vivo == true) {
                        posXbalaInvasores[balasDisparadas] = invasores[y][posXaleatorio].getCoorX() + 16;
                        posYbalaInvasores[balasDisparadas] = invasores[y][posXaleatorio].getCoorY() + 20;
                        break;
                    }
                }

                aleatorioBala0 = (int) (Math.random() * 40) + 1;
                if (aleatorioBala0 == 2 && balaInvasores[balasDisparadas].disparado == false) {
                    balaInvasores[balasDisparadas].disparado = true;

                    System.out.println("disparado");

                    balaInvasores[balasDisparadas].setSprite(0);
                    balaInvasores[balasDisparadas].setVelY(5);

                    balaInvasores[balasDisparadas].setCoorX(posXbalaInvasores[balasDisparadas]);
                    balaInvasores[balasDisparadas].setCoorY(posYbalaInvasores[balasDisparadas]);
                    if (balasDisparadas < 3) {
                        balasDisparadas++;
                    }
                }

                aleatorioBala1 = (int) (Math.random() * 60) + 1;
                if (aleatorioBala1 == 2 && balaInvasores[balasDisparadas].disparado == false) {
                    balaInvasores[balasDisparadas].disparado = true;

                    System.out.println("disparado");

                    balaInvasores[balasDisparadas].setSprite(1);
                    balaInvasores[balasDisparadas].setVelY(10);

                    balaInvasores[balasDisparadas].setCoorX(posXbalaInvasores[balasDisparadas]);
                    balaInvasores[balasDisparadas].setCoorY(posYbalaInvasores[balasDisparadas]);
                    if (balasDisparadas < 3) {
                        balasDisparadas++;
                    }
                }

            } else {

                if (balaInvasores[0].disparado == false && balaInvasores[1].disparado == false && balaInvasores[2].disparado == false) {
                    balasDisparadas = 0;
                }

            }

            if (aleatorioNaveRara != 5) {
                aleatorioNaveRara = (int) (Math.random() * 100 + 1);
                dibujarNaveRara = false;
                System.out.println(aleatorioNaveRara);
            }

            if (aleatorioNaveRara == 5);
            {
                dibujarNaveRara = true;

                NaveRara.setCoorX(NaveRara.getCoorX() + 5);

                aleatorioPuntajeNaveRara = (int) (Math.random() * 3000 + 1);
                while (aleatorioPuntajeNaveRara < 500 || aleatorioPuntajeNaveRara % 10 != 0) {
                    aleatorioPuntajeNaveRara = (int) (Math.random() * 3000 + 1);
                }

                if (NaveRara.getCoorX() >= 754) {
                    aleatorioNaveRara = 0;
                    NaveRara.setCoorX(-100);
                }

                if (bala.getCoorX() >= NaveRara.getCoorX() && bala.getCoorX() <= NaveRara.getCoorX() + 52) {
                    if (bala.getCoorY() <= NaveRara.getCoorY()) {
                        ColisionNaveRara = true;

                    }
                }
            }

            if (ColisionNaveRara == true) {
                if (ContadorColisionNaveRara == 20) {
                    NaveRara.setSprite(0);
                    ColisionNaveRara = false;
                    aleatorioNaveRara = 0;
                    NaveRara.setCoorX(-100);
                    score += aleatorioPuntajeNaveRara;
                    repaint(400, 20, 400, 20);

                } else {
                    ContadorColisionNaveRara++;
                    NaveRara.setSprite(1);
                }

            }

            repaint(NaveRara.getCoorX() - 52, NaveRara.getCoorY(), NaveRara.getCoorX(), NaveRara.getCoorY() + 10);

            decimasSegundo++;
        }
    });

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(735, 700));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        temporizador.start();
        repaint();
    }//GEN-LAST:event_formWindowActivated

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
    }//GEN-LAST:event_formKeyPressed

    public static void main(String args[]) {        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmInvaders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmInvaders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmInvaders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmInvaders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmInvaders().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
