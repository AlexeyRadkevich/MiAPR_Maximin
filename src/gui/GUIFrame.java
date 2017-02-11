package gui;

import core.Maximin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by AR on 08.02.2017.
 */
public class GUIFrame extends JFrame {
    private JPanel rootPanel;
    private JButton buttonStart;
    private JTextField textfld_num_points;
    private JPanel paintPanel;

    public GUIFrame() {
        super("MiAPR");
        setSize(640, 480);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        buttonStart.addActionListener(new SearchAction());
    }

    public static void main(String[] args) {
        new GUIFrame();
    }

    private class SearchAction implements ActionListener {

        Boolean btnClick = false;
        int num_points;

        public void draw() {
            if (btnClick) {
                Random random = new Random();
                int R, G, B;
                Graphics g = paintPanel.getGraphics();
                Dimension dim = paintPanel.getSize();
                g.clearRect(0, 0, dim.width, dim.height);

                Maximin maxMeans = new Maximin(20000);

                for (core.Cluster cluster : maxMeans.arr_clusters) {
                    R = random.nextInt(256);
                    G = random.nextInt(256);
                    B = random.nextInt(256);
                    for (Point point : cluster.getPoints()) {
                        g.setColor(new Color(R, G, B));
                        g.fillOval((int) point.x, (int) point.y, 5, 5);
                    }
                    g.setColor(Color.RED);
                    g.fillOval((int) cluster.getCentroid().getX(), (int) cluster.getCentroid().getY(), 15, 15);
                }
                btnClick = false;
            }
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            btnClick = true;
           // num_points = Integer.parseInt(textfld_num_points.getText());
            draw();
        }


    }
}
