package juegos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class ElegirJuego {
    public static void elegirJuego() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Menu de juegos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 700);
            frame.setLocationRelativeTo(null);

            Image icon = Toolkit.getDefaultToolkit().getImage(
                    ElegirJuego.class.getResource("/resources/logo-el-casar.png")
            );
            frame.setIconImage(icon);

            JPanel panel = new JPanel(new BorderLayout(10, 10));

            JPanel encabezado = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel labelTexto = new JLabel("Elige un juego:");
            labelTexto.setFont(new Font("Arial", Font.PLAIN, 30));


            ImageIcon logoOriginal = new ImageIcon(ElegirJuego.class.getResource("/resources/logo-el-casar.png"));
            Image imgRedimensionada = logoOriginal.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
            ImageIcon logoEscalado = new ImageIcon(imgRedimensionada);

            JLabel labelIcono = new JLabel(logoEscalado);

            encabezado.add(labelIcono);
            encabezado.add(labelTexto);

            JPanel botones = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton botonBolas = new JButton("Bolas de Colores Aleatorias" );
            JButton botonOtro = new JButton("Otro Juego");

            String repo = "https://github.com/AlbertoLopezHerraiz/entrenamientos";
            JPanel pie = new JPanel(new BorderLayout());
            JLabel marca = new JLabel("<html><a href='"+repo+"'>© 2025 Aplicación para porteros // Alberto López Herraiz</a></html>");
            marca.setFont(new Font("Arial", Font.PLAIN, 10));
            marca.setForeground(Color.GRAY);
            marca.setHorizontalAlignment(SwingConstants.RIGHT);
            marca.setBorder(BorderFactory.createEmptyBorder(2, 10, 4, 10));
            marca.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            marca.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked( MouseEvent e ){
                    try{
                        Desktop.getDesktop().browse(new URI(repo));
                    }catch (Exception exc){
                        JOptionPane.showMessageDialog(null, "No se pudo abrir el enlace");
                    }
                }
            });

            pie.add(marca);

            botonBolas.addActionListener(e -> {
                frame.dispose();
                eleccionBolas();
            });

            botonOtro.addActionListener(e -> {
                frame.dispose();
            });

            botones.add(botonBolas);
            botones.add(botonOtro);

            panel.add(encabezado, BorderLayout.NORTH);
            panel.add(botones, BorderLayout.CENTER);
            panel.add(pie, BorderLayout.SOUTH);

            frame.setContentPane(panel);
            frame.setVisible(true);
        });
    }

    private static void eleccionBolas() {
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
    public static void main(String[] args) {
        elegirJuego();
    }
}
