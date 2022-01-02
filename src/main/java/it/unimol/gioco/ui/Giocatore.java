package it.unimol.gioco.ui;

import it.unimol.gioco.game_object.GestioneBordi;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Giocatore extends it.unimol.gioco.game_object.Giocatore implements Runnable, GestioneBordi {

    public Giocatore(BufferedImage image, BufferedImage proiettile, int x, int y, int larghezza, int altezza, Gioco main) {
        super(image, proiettile, x, y, larghezza, altezza, main);
    }

    public void disegna(Graphics g) {
        g.drawImage(getImageMario(), getX(),getY(),getLarghezza(), getAltezza(), null);
    }
}
