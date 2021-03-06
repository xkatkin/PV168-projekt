package cz.muni.fi.pv168.swing;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.DERBY;


public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                int x = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
                int y = Toolkit.getDefaultToolkit().getScreenSize().height / 2;

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("Agent Database");
                frame.setLocation(x - x / 4, y - y / 2);
                frame.setVisible(true);
                frame.setContentPane(new WelcomePage(frame).getMainPanel());
                frame.pack();
            }
        });

    }
}
