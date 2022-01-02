package it.unimol.gioco.game_object;

import it.unimol.gioco.ui.Gioco;

import java.awt.*;
import java.awt.image.BufferedImage;

//definiamo la classe proietile che viene utilizzato dal giocatore per sparare
public class Proiettile extends Thread implements GestioneBordi, Runnable{

    private boolean attivo;
    private Gioco main;

    private final double velocita = 20;
    private int altezza;
    private int larghezza;
    private int x, y;
    private BufferedImage imageProiettile;


    public Proiettile(BufferedImage image, int x, int y, int larghezza, int altezza, Gioco main) {
        this.x = x;
        this.y = y;
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.imageProiettile = image;
        this.main = main;

        this.start();
    }

    public void run() {
        attivo = true;
        while (attivo) {
            aggiorna();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
//elimina i proiettili che si trovano fuori dalla finesta
    private void aggiorna() {
        y -= velocita;
        if (y + altezza < 0) {
            this.setAttivo(false);
            Gioco.proiettili.remove(this);
            System.out.println("proiettile rimoso" );
        }
    }

    public void disegna(Graphics g) {
        g.drawImage(imageProiettile, x, y, larghezza, altezza, main);
    }

    public void setAttivo(boolean stato) {
        attivo = stato;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLarghezza() {
        return larghezza;
    }

    public int getAltezza() {
        return altezza;
    }

    public Rectangle getBordi() {
        return new Rectangle(x, y, larghezza, altezza);
    }
}
