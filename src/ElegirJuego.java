import bolas.BolasColoresAleatorias;

import javax.swing.*;

public class ElegirJuego {
    public static void elegirJuego(){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bolas de colores aleatorias");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            BolasColoresAleatorias panelBolas = new BolasColoresAleatorias(frame);
            frame.setContentPane(panelBolas);
            frame.setVisible(true);
        });
    }

}
