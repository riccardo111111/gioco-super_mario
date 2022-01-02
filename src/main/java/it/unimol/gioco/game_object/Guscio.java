package it.unimol.gioco.game_object;

import it.unimol.gioco.ui.Gioco;

import java.awt.*;
import java.awt.image.BufferedImage;

// definiamo la classe guscio che vine utilizzato dal browser per sparare
public class Guscio extends Thread implements GestioneBordi {
    private int x;
    private int y;
    private int larghezza;
    private int altezza;
    private int velocita;
    BufferedImage imageGuscio;
    private boolean attivo;
    private Gioco main;

    public Guscio(BufferedImage image, int larghezza, int altezza, int x, int y, int velocita, Gioco main) {

        this.x=x;
        this.y=y;
        this.larghezza = larghezza;
        this.altezza = altezza;
        this.imageGuscio = image;
        attivo = true;
        this.main = main;
        this.velocita = velocita;

        this.start();
    }

    public void run() {
        attivo = true;
        while (attivo) {
            aggiorna();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
//rimuove i gusci se escono dalla finestrta
    private void aggiorna() {
        y += velocita;
        if (y +altezza > Gioco.getAltezza()) {
            this.setAttivo(false);
            Gioco.gusci.remove(this);
            System.out.println("guscio rimoso");
        }
    }

    public void setAttivo(boolean stato) {
        attivo = stato;
    }

    public void disegna(Graphics g) {
        g.drawImage(imageGuscio, x, y, larghezza, altezza, main);
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

