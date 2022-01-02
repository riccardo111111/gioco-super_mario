package it.unimol.gioco.game_object;

import it.unimol.gioco.ui.Gioco;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class Giocatore implements Runnable, GestioneBordi {
    private int x;
    private int y;
    private final int z = 530;
    private int larghezza;
    private int altezza;
    private final int velocita = 30;
    BufferedImage imageMario;
    BufferedImage imageProiettile;
    Gioco main;
    public int vitaMario;

    public BufferedImage getImageMario() {
        return imageMario;
    }

    public Giocatore(BufferedImage image, BufferedImage proiettile, int x, int y, int larghezza, int altezza, Gioco main) {
        this.x = x;
        this.y = y;
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.imageMario = image;
        this.imageProiettile=proiettile;
        this.main = main;
        vitaMario=10;

        Gioco.proiettili=new CopyOnWriteArrayList<>();
    }
    @Override
    public void run() {
        System.out.println(1);
    }
    public void spara(){

        Gioco.proiettili.add(new Proiettile(imageProiettile, x+larghezza/2, y, 20, 40, main));

    }
   /* public void disegna(Graphics g) {
        g.drawImage(imageMario, x, y, larghezza, altezza, null);
    }
    */

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLarghezza() {
        return larghezza;
    }

    public void setLarghezza(int larghezza) {
        this.larghezza = larghezza;
    }

    public int getAltezza() {
        return altezza;
    }

    public void setAltezza(int altezza) {
        this.altezza = altezza;
    }

    public void spostaDestra() {
        if ((x + larghezza) < main.getLarghezza()) {
            x += velocita;
        }
    }

    public void spostaSinistra() {
        if (x > 0) {
            x -= velocita;
        }
    }

    public Rectangle getBordi(){
        return new Rectangle(x,y,larghezza,altezza);
    }
}
