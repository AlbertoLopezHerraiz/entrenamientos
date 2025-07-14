import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;


public class BolasColoresAleatorias extends JPanel {
    private List<Color> colores;
    private Timer cambioTimer;
    private Timer cuentaAtrasTimer;

    private int repeticionesTotales = -1;
    private int repeticionesRestantes = -1;
    private int cuentaAtras = -1;
    private int intervaloMs = 3000;

    public BolasColoresAleatorias(JFrame frame) {
        colores = new ArrayList<>();
        pedirIntervalo();
    }

    private void pedirIntervalo() {
        SwingUtilities.invokeLater(() -> {
            if (repeticionesTotales == -1) {
                String repInput = JOptionPane.showInputDialog(
                        null,
                        "¿Cuántas repeticiones quieres hacer por ciclo?",
                        "Número de repeticiones",
                        JOptionPane.QUESTION_MESSAGE
                );

                if (repInput == null) System.exit(0);

                try {
                    repeticionesTotales = Integer.parseInt(repInput);
                    if (repeticionesTotales <= 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido.");
                    repeticionesTotales = -1;
                    pedirIntervalo();
                    return;
                }
            }

            String input = JOptionPane.showInputDialog(
                    null,
                    "¿Cada cuántos segundos deseas cambiar las bolas de color?\n(Ingresa un número entero)",
                    "Intervalo de tiempo",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) {
                System.exit(0);
            }

            try {
                int segundos = Integer.parseInt(input);
                if (segundos < 4) {
                    JOptionPane.showMessageDialog(null, "Debe ser al menos 4 segundos para mostrar cuenta atrás.");
                    pedirIntervalo();
                    return;
                }

                intervaloMs = segundos * 1000;
                repeticionesRestantes = repeticionesTotales;
                iniciarTimer();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido.");
                pedirIntervalo();
            }
        });
    }


    private void iniciarTimer() {
        mezclarColores();
        repaint();

        if (cambioTimer != null && cambioTimer.isRunning()) {
            cambioTimer.stop();
        }

        cambioTimer = new Timer(intervaloMs, e -> {
            if (repeticionesRestantes > 1) {
                repeticionesRestantes--;
                mezclarColores();
                repaint();
                iniciarCuentaAtras();
            } else {
                cambioTimer.stop();
                pedirIntervalo();
            }
        });

        iniciarCuentaAtras();
        cambioTimer.setInitialDelay(intervaloMs);
        cambioTimer.start();
    }

    private void iniciarCuentaAtras() {
        cuentaAtras = -1;

        Timer preCuenta = new Timer(intervaloMs - 3000, e1 -> {
            cuentaAtras = 3;

            cuentaAtrasTimer = new Timer(1000, e2 -> {
                if (cuentaAtras > 1) {
                    cuentaAtras--;
                    repaint();
                } else {
                    cuentaAtras = -1;
                    repaint();
                    cuentaAtrasTimer.stop();
                }
            });

            cuentaAtrasTimer.start();
            repaint();
        });

        preCuenta.setRepeats(false);
        preCuenta.start();
    }


    private void mezclarColores() {
        colores = Arrays.asList(Color.BLUE, Color.RED, Color.WHITE, Color.YELLOW);
        Collections.shuffle(colores);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (colores == null || colores.isEmpty()) return;
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        int numBolas = colores.size();
        int maxDiametro = (int) Math.min(height / 1.2, width / (numBolas * 1.2));

        int totalEspacio = width - (numBolas * maxDiametro);
        int espacioEntre = totalEspacio / (numBolas + 1);
        int y = (height - maxDiametro) / 2;

        int x = espacioEntre;
        for (Color color : colores) {
            g.setColor(color);
            g.fillOval(x, y, maxDiametro, maxDiametro);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, maxDiametro, maxDiametro);
            x += maxDiametro + espacioEntre;
        }
        g.setFont(new Font("Arial", Font.BOLD, maxDiametro/4));
        if (repeticionesRestantes == 1) {
            if(repeticionesTotales>=4) g.setColor(Color.orange);
            g.drawString("¡ÚLTIMA RONDA, ÁNIMO! ", (width) / 4, (height +g.getFontMetrics().getAscent()) / 4);

        } else g.drawString("Rondas restantes: " + (repeticionesRestantes - 1), (width) / 4, ((height + g.getFontMetrics().getAscent()-15) / 4));
        if (cuentaAtras >= 1) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, maxDiametro));
            String texto = String.valueOf(cuentaAtras);
            FontMetrics fm = g.getFontMetrics();
            int textoAncho = fm.stringWidth(texto);
            int textoAlto = fm.getAscent();
            g.drawString(texto, (width - textoAncho) / 2, 3 * (height + textoAlto) / 4);
            g.setFont(new Font("Arial", Font.BOLD, maxDiametro / 4));

        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bolas de colores aleatorias");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            BolasColoresAleatorias panel = new BolasColoresAleatorias(frame);
            frame.setContentPane(panel);
            frame.setVisible(true);
        });
    }
}
