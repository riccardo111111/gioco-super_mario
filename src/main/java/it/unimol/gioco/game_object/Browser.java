package it.unimol.gioco.game_object;

import it.unimol.gioco.ui.Gioco;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Browser extends Thread implements Runnable, GestioneBordi {
    private int x;
    private int y;
    private int altezza;
    private int larghezza;
    private boolean attivo;
    private int velocita = 5;
    private final int maxVelocitaBrowser =20;
    private final int maxVelocitaGuscio = 30;
    private final int temp = 100;
    public int vitaBrowser;

    BufferedImage imageBrowser;
    BufferedImage imageGuscio;

    private Gioco main;

    Random rand;

    public Browser(BufferedImage image, BufferedImage guscio, int larghezza, int altezza, int x, int y, Gioco main) {
        this.x = x;
        this.y = y;
        this.larghezza = larghezza;
        this.altezza = altezza;
        this.imageBrowser = image;
        attivo = true;
        this.main = main;
        this.imageGuscio = guscio;
        Gioco.gusci = new CopyOnWriteArrayList<>();
        rand = new Random();
        vitaBrowser = 3;
    }

    public void run() {
        attivo = true;

        while (attivo) {
            for (int i = 0; i <rand.nextInt(temp)+10; i++) {
                aggiorna();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            spara();
    }
}
    private void aggiorna() {
        Random rnd=new Random();
        if (this.x <= 0) {
            velocita=rnd.nextInt(maxVelocitaBrowser)+10;

        } else if (this.x >= main.getLarghezza() - this.larghezza) {
            velocita=rnd.nextInt(maxVelocitaBrowser)+1;
            velocita *= -1;
        }
        x+= velocita;

    }
    public void spara(){
        Gioco.gusci.add( new Guscio(imageGuscio, 30,30, x+larghezza/2, 120, rand.nextInt(maxVelocitaGuscio) + 2, main));
    }

    public void disegna(Graphics g) {
        g.drawImage(imageBrowser, x, y, larghezza, altezza, null);
    }

    public boolean getAttivo(){
        return attivo;
    }

    public void setAttivo(boolean valore){
        this.attivo=valore;
    }

    public int getX() {
        return x;
    }
    public void setX() {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return y;
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

  public Rectangle getBordi(){
      return new Rectangle(x,y,larghezza,altezza);
  }
}

