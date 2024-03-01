package Math_Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Euler extends JFrame {
    private JFrame frame;
    private JTextField degreeField;
    private JTextField fxField;
    private JButton differenceButton;
    private JButton integralButton;
    private JTextArea resultArea;

    public Euler(){
        initialize();
    }

    private void initialize(){
        frame = new JFrame("Euler 계산기");
        frame.setBounds(100,100,450,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,5,5));

        JLabel lblDegree = new JLabel("다항식의 차수 : ");
        frame.getContentPane().add(lblDegree);

        degreeField = new JTextField();
        frame.getContentPane().add(degreeField);
        degreeField.setColumns(10);

        JLabel lblFx = new JLabel("계수 (높은 차수부터) :  ");
        frame.getContentPane().add(lblFx);

        fxField = new JTextField();
        frame.getContentPane().add(fxField);
        fxField.setColumns(20);

        differenceButton = new JButton("미분");
        differenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                differenctiate();
            }
        });
        frame.getContentPane().add(differenceButton);

        integralButton = new JButton("적분");
        integralButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                integrate();
            }
        });
        frame.getContentPane().add(integralButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        frame.getContentPane().add(scrollPane);

        frame.setVisible(true);
    }

    private void differenctiate(){
        int degree = Integer.parseInt(degreeField.getText());
        int[] f_x = Arrays.stream( fxField.getText().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        int[] differenctiate = differentiatePolynomial(f_x);
        resultArea.setText("미분 결과" + polynomialToString(differenctiate) + " + C");
    }

    private void integrate(){
        int degree = Integer.parseInt(degreeField.getText());
        int[] f_x = Arrays.stream( fxField.getText().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        double[] integrated = integratePolynomial(f_x);
        resultArea.setText("적분 결과" + polynomialToString(integrated) + " + C");
    }

    private int[] differentiatePolynomial(int[] f_x){
        if (f_x.length == 1){
            return new int[]{0};
        }
        int[] result = new int[f_x.length - 1];;
        for (int i=0; i< f_x.length - 1; i++){
            result[i] = f_x[i] * (f_x.length - i - 1);
        }
        return result;
    }

    //수정 필수
    private double[] integratePolynomial(int[] f_x){
        double[] result = new double[f_x.length + 1];
        for (int i=0; i< f_x.length; i++){
            result[i] = (double)f_x[i] / (f_x.length - i + 1);
        }
        return result;
    }

    //수정 필수
    private String polynomialToString(int[] polynomial){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i< polynomial.length; i++){
            sb.append(polynomial[i]).append("ex^").append(polynomial.length - i);
            if(i < polynomial.length - 1){
                sb.append(" + ");
            }
        }
        return sb.toString();
    }

    private String polynomialToString(double[] polynomial){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i< polynomial.length; i++){
            sb.append(String.format("%.2f", polynomial[i])).append("ex^").append(polynomial.length - i);
            if(i < polynomial.length - 1){
                sb.append(" + ");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            public void run(){
                try{
                    Euler window = new Euler();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}