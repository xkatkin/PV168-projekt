package cz.muni.fi.pv168.swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WelcomePage {
    private JButton enterButton;
    private JPasswordField passwordField1;
    private JTextField welcomeText;
    private JPanel mainPanel;
    private JLabel picLabel;
    private JLabel welcomeLabel;
    private JLabel passwordLabel;

    public WelcomePage(JFrame main) {
        passwordField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setContentPane(new DatabaseLayout().getPanel1());
                main.pack();
                main.revalidate();
            }
        });
    }

    public JButton getEnterButton() {
        return enterButton;
    }

    public JPasswordField getPasswordField1() {
        return passwordField1;
    }

    public JTextField getWelcomeText() {
        return welcomeText;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() throws IOException{
        Image image = ImageIO.read(ClassLoader.getSystemResource("logo.jpg")).getScaledInstance(500, 500 ,Image.SCALE_DEFAULT);
        picLabel = new JLabel(new ImageIcon(image));
    }
}
