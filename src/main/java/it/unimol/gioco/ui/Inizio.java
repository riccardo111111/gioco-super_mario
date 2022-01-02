package it.unimol.gioco.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inizio {
    private JButton giocaButton;
    private JPanel Inizio;
    private JPanel immagine;

    public Inizio() {
        this.giocaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Gioco.creaFinestra();
            /*JFrame.HIDE_ON_CLOSE;
                JFrame.dispose();
                JFrame.DISPOSE_ON_CLOSE();
            */
            }
        });
    }

    public JPanel getInizio(){
        return Inizio;
    }

    public JPanel getImmagine() {
        return immagine;
    }

    public void setImmagine(JPanel immagine) {
        this.immagine = immagine;
    }
}
