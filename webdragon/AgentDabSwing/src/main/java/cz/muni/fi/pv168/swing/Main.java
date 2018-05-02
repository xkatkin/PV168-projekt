package cz.muni.fi.pv168.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("Agent Database");
                frame.setVisible(true);
                frame.setContentPane(new AgentsFrame().getMainPanel());
                frame.pack();
            }
        });
    }
}
