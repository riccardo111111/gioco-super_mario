package it.unimol.gioco.game_object;

import it.unimol.gioco.ui.Giocatore;

public  class GestoreCollisioni {
    public static boolean controllaCollisioneMarioGuscio(Giocatore mario, Guscio guscio) {
        return mario.getBordi().intersects(guscio.getBordi());
    }

    public static boolean controllaCollisioneGuscioMissili(Guscio guscio, Proiettile missile) {
        return guscio.getBordi().intersects(missile.getBordi());
    }

    public static boolean controllaCollisioneMissileBrowser(Proiettile missile, Browser browser) {
        return missile.getBordi().intersects(browser.getBordi());
    }
}
