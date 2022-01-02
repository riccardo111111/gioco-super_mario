package it.unimol.gioco.game_object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CaricatoreImmagini {
    BufferedImage image;

    public BufferedImage caricaImmagine(String posizione) {
        try {
            image = ImageIO.read(getClass().getResource(posizione));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


}
