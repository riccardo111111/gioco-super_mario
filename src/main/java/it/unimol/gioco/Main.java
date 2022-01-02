package it.unimol.gioco;

import it.unimol.gioco.ui.Inizio;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Inizio start = new Inizio();
        JFrame ui = new JFrame();
        ui.setContentPane(start.getInizio());
        ui.setVisible(true);
        ui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ui.pack();

    }
}
