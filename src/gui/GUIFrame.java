package gui;

import core.Maximin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by AR on 13.02.2017.
 */
public class GUIFrame extends JFrame {

    private final int FRAME_WIDHT = 640;
    private final int FRAME_HEIGHT = 480;

    private JPanel rootPanel;
    private JButton buttonStart;
    private JTextField textfld_num_points;
    private JPanel paintPanel;

    public GUIFrame() {
        super("MiAPR_2");
        setSize(FRAME_WIDHT, FRAME_HEIGHT);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        add(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        buttonStart.addActionListener(new DrawAction());
    }

    public void showMessage(String errorStr) {
        JOptionPane.showMessageDialog(this, errorStr, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        new GUIFrame();
    }

    private class DrawAction implements ActionListener {

        private Boolean btnClick = false;
        private int num_points, num_clusters;

        private void draw() {
            if (btnClick) {

                Graphics g = paintPanel.getGraphics();
                Dimension dim = paintPanel.getSize();
                g.clearRect(0, 0, dim.width, dim.height);

                Maximin maximin = new Maximin(num_points, dim.width, dim.height);
                maximin.search();

                for (core.Cluster cluster : maximin.clusters) {
                    Color newColor = getRandomColor();
                    for (Point point : cluster.getPoints()) {
                        g.setColor(newColor);
                        g.drawLine((int) cluster.getCentroid().getX(), (int) cluster.getCentroid().getY(),(int) point.x, (int) point.y);
                    }
                    g.setColor(Color.RED);
                    g.fillOval((int) cluster.getCentroid().getX(), (int) cluster.getCentroid().getY(), 10, 10);
                }
                btnClick = false;
            }
        }

        private Color getRandomColor() {
            int R, G, B;
            Random random = new Random();
            R = random.nextInt(256);
            G = random.nextInt(256);
            B = random.nextInt(256);
            return new Color(R, G, B);
        }

        private int getNum_points() {
            try {
                int res = Integer.parseInt(textfld_num_points.getText());
                if (res<10000||res>100000){
                    showMessage("Количество точек предлагается брать от 10.000 до 100.000!");
                    return -1;
                }
                return res;
            } catch (Exception e) {
                showMessage("Количество точек должно быть целым числом в диапазоне от 10.000 до 100.000!");
            }
            return -1;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            num_points = getNum_points();
            if(num_points!=-1){
                btnClick = true;
                draw();
            }
        }

    }
}
