package it.unimol.gioco.ui;

import it.unimol.gioco.game_object.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

public class Gioco extends Canvas implements Runnable, KeyListener /*MouseMotionListener */ {

    private static final int larghezza = 1280;
    private static final int altezza = 720;
    private static final String nomeGioco = "Super Mario";

    BufferedImage sfondo = null;
    BufferedImage mario = null;
    BufferedImage cattivo = null;
    BufferedImage guscio = null;
    BufferedImage proiettile = null;
    BufferedImage vitaMario = null;
    BufferedImage vitaBrowser = null;
    BufferedImage marioGameOver = null;
    BufferedImage vittoria = null;

    public static boolean giocoAttivo = false;
    private Browser browser;
    private Giocatore giocatore;
    public static CopyOnWriteArrayList<Proiettile> proiettili;
    public static CopyOnWriteArrayList<Guscio> gusci;

    public Gioco() {
        caricaRisorse();
        iniziaGioco();
    }

    public static void creaFinestra() {
        Gioco gioco = new Gioco();

        JFrame finestraGioco = new JFrame(nomeGioco);
        Dimension dimensioneFinestra = new Dimension(larghezza, altezza);
        finestraGioco.setPreferredSize(dimensioneFinestra);
        //finestraGioco.setMaximumSize(dimensioneFinestra);

        finestraGioco.pack();
        finestraGioco.setResizable(false);
        finestraGioco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestraGioco.setVisible(true);

        finestraGioco.add(gioco); //oggetto su cui si può disegnare
        finestraGioco.addKeyListener(gioco);
        // gioco.addMouseMotionListener(gioco);

        Thread threadGioco = new Thread(gioco);
        threadGioco.start();
        Musica o = new Musica();
        Thread musica=new Thread(o);
        musica.start();
        if (giocoAttivo){
                musica.interrupt();
        }
    }

    //finestraGioco.setLayout(new GridLayout(10,10));

      /*  JButton button = new JButton();
          button.setSize(10,10);
         try {
            button.setIcon(new ImageIcon(vitaBrowser));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        finestraGioco.add(button);
        button.setBorder(null);
        button.setContentAreaFilled(false);*/

    private void iniziaGioco() {
        giocatore =new Giocatore(mario, proiettile, 100, 500, 100, 150, this);
        browser = new Browser(cattivo, guscio, 150, 120, 10, 10, this);
        browser.start();
    }

    private void caricaRisorse() {
        CaricatoreImmagini loader = new CaricatoreImmagini();
        sfondo = loader.caricaImmagine("/sfondo.jpg");
        mario = loader.caricaImmagine("/mario.png");
        cattivo = loader.caricaImmagine("/cattivo.png");
        guscio = loader.caricaImmagine("/guscio.png");
        proiettile = loader.caricaImmagine("/proiettile.png");
        vitaMario = loader.caricaImmagine("/vita.png");
        vitaBrowser = loader.caricaImmagine("/vitaBrowser.png");
        marioGameOver = loader.caricaImmagine("/Game-Over.png");
        vittoria = loader.caricaImmagine("/vittoria.jpeg");

        System.out.println("Risorse caricate");
    }


    private void disegna() {
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            createBufferStrategy(2);
            return;
        }
        Graphics g = buffer.getDrawGraphics();

        g.drawImage(sfondo, 0, 0, larghezza, altezza, this);
        browser.disegna(g);
        giocatore.disegna(g);

        if (proiettile != null) {
            for (Proiettile p : proiettili) {
                p.disegna(g);
            }
        }
        if (guscio != null) {
            for (Guscio p : gusci) {
                p.disegna(g);
            }
        }

        for (int i = 0; i < giocatore.vitaMario * 30; i += 30) {
            g.drawImage(vitaMario, i, 50, 25, 25, this);
        }
        for (int j = 0; j < browser.vitaBrowser * 30; j += 30) {
            g.drawImage(vitaBrowser, j, 85, 25, 25, this);
        }

        Date cronomertro = new Date();
        long t = cronomertro.getTime();
        String t1 = String.valueOf(t);
        g.drawString("tempo: " + t1, 5, 20);
        g.drawString("vita: ", 5, 35);

        if (controlloVittoria()) {
            g.drawImage(vittoria, 500, 0, 400, 720, this);

            giocoAttivo = false;
        } else if (!giocoAttivo) {
            g.drawImage(marioGameOver, 100, 100, 1000, 440, this);
        }

        g.dispose(); //rilascia le risorse
        buffer.show();
    }

    private boolean controllaSconfitta() {
        if (giocatore.vitaMario <= 0) {
            return true;
        }
        return false;
    }

    private boolean controlloVittoria() {
        if (browser.vitaBrowser <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        giocoAttivo = true;
        while (giocoAttivo) {

            aggiorna();
            disegna();
        }
    }

    /*
    Thread t1 = new Thread(() -> {
        for (Proiettile p : proiettili) {
            if (GestoreCollisioni.controllaCollisioneMissileBrowser(p, browser)) {
                proiettili.remove(p);
                browser.vitaBrowser--;
            }
            break;
        }
    });

    public void t2() {
    Thread t2 = new Thread(() -> {
        for (Guscio g : gusci) {
            for (Proiettile p : proiettili) {
                if (GestoreCollisioni.controllaCollisioneGuscioMissili(g, p)) {
                    gusci.remove(g);
                    proiettili.remove(p);
                }
                break;
            }
        }
    });
}
    Thread t3=new Thread(()->{
        for (Guscio g : gusci) {
            if (GestoreCollisioni.controllaCollisioneMarioGuscio(giocatore, g)) {
                gusci.remove(g);
                this.giocatore.vitaMario -= 1;
                break;
            }
        }
    });
    */

    private void aggiorna() {
        if (controllaSconfitta()) {
            this.giocoAttivo = false;
        }

        for (Proiettile p : proiettili) {
            if (GestoreCollisioni.controllaCollisioneMissileBrowser(p, browser)) {
                proiettili.remove(p);
                browser.vitaBrowser--;
            }
            break;
        }
        // TODO: 10/07/2020 eseguire i test

        /*
        int gu=0;
        int pro=0;
        for (int i = 0; i <gusci.size() ; i++) {
            System.out.println("guscio: " + gu++);

            for (int j = 0; j <proiettili.size() ; j++) {
                System.out.println("proietttile: "+pro++);
                if (GestoreCollisioni.controllaCollisioneGuscioMissili(gusci.get(i), proiettili.get(i))) {
                    System.out.println("ok");
                    gusci.remove(gusci.get(i));
                    proiettili.remove(proiettili.get(i));
                }
                break;
            }
        }

         */
/*
        int gu=0;
        int pro=0;

        Iterator<Guscio> a=gusci.iterator();
        Iterator<Proiettile> b=proiettili.iterator();

        while (a.hasNext()){
            System.out.println("guscio: " + gu++);

            while (b.hasNext()){
                System.out.println("proietttile: "+pro++);
                if (GestoreCollisioni.controllaCollisioneGuscioMissili(a.next(), b.next())) {
                    a.remove();
                    b.remove();
                    System.out.println("ok");
                }
                break;
            }
        }
         */
        /*
        for (Guscio g : gusci) {
            System.out.println("guscio: " + gu++);
        for (Proiettile p : proiettili) {
                System.out.println("proietttile: "+pro++);

                if (GestoreCollisioni.controllaCollisioneGuscioMissili(g, p)) {
                    System.out.println("ok");
                    gusci.remove(g);
                    proiettili.remove(p);
                }
                break;
            }
        }

         */

        for (Guscio g : gusci) {
            if (GestoreCollisioni.controllaCollisioneMarioGuscio(giocatore, g)) {
                gusci.remove(g);
                this.giocatore.vitaMario -= 1;
                break;
            }
        }
    }

    public int getLarghezza() {
        return larghezza;
    }

    public static int getAltezza() {
        return altezza;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        switch (keycode) {
            case KeyEvent.VK_LEFT:
                giocatore.spostaSinistra();
                break;
            case KeyEvent.VK_RIGHT:
                giocatore.spostaDestra();
                break;
            case KeyEvent.VK_SPACE:
                giocatore.spara();
                break;
               /* if (giocatore.getY() >= 450) {
                    giocatore.salta();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    giocatore.giù();*/
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
/*
    @Override
    public void mouseDragged(MouseEvent e) {

    }
    @Override
    public void mouseMoved(MouseEvent e) {
        int posizione = (e.getPoint().x) - (giocatore.getLarghezza() / 2);
        if (posizione >= 0 && posizione + giocatore.getLarghezza() <= larghezza) {
            giocatore.setX(posizione);
            System.out.println("Mouse moved");
        }
    }*/

    public void setGiocoAttivo(boolean giocoAttivo) {
        this.giocoAttivo = giocoAttivo;
    }
    public boolean isGiocoAttivo() {
        return giocoAttivo;
    }
}


