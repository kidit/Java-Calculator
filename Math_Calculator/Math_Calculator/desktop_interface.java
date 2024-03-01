package Math_Calculator;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class desktop_interface extends JFrame {
    public desktop_interface() {
        super("미적분 계산기");
        this.setDefaultCloseOperation(3);
        this.setSize(240, 160);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        this.add(panel);
        JButton differentiationButton = new JButton("미분");
        JButton IntegrationButton = new JButton("적분");
        panel.add(differentiationButton);
        panel.add(IntegrationButton);
        differentiationButton.addActionListener(new ActionListener(this) {
            public void actionPerformed(ActionEvent actionEvent) {
                Differential differential = new Differential();
                differential.setVisible(true);
            }
        });
        IntegrationButton.addActionListener(new ActionListener(this) {
            public void actionPerformed(ActionEvent actionEvent) {
                Integral integral = new Integral();
                integral.setVisible(true);
            }
        });
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new desktop_interface();
    }
}