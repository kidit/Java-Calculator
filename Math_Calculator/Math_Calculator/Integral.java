package Math_Calculator;

import java.util.Scanner;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//정적분 로직
public class Integral extends JFrame{
    final private JTextField fxField;
    final private JTextField gxField;
    final private JTextArea resultTextArea;

    public static class Equations{
        public static int N_equation(){
            return Integer.parseInt(JOptionPane.showInputDialog("몇 차 방정식 입니까? "));
        }
        //최대, 최소 값 (a,b)
        public static int[] Integer_input() {
            int max = Integer.parseInt(JOptionPane.showInputDialog("최대 값 : "));
            int min = Integer.parseInt(JOptionPane.showInputDialog("최소 값 : "));

            return new int[]{max,min};
        }
        //다항식 생성 f(x), g(x) ...
        public static int[] Equation(Scanner scanner, int x) {
            String input = scanner.nextLine();
            String[] function_Equation = input.split(" ");
            int[] Define_Equals;

            if (function_Equation.length == x + 1) {
                try {
                    Define_Equals = new int[x + 1];
                    for (int i = 0; i < Define_Equals.length; i++) {
                        Define_Equals[i] = Integer.parseInt(function_Equation[i]);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("문자, 숫자가 아닌 다른 것이 포함되어 있습니다.");
                    return null; // 숫자 변환에 실패한 경우 null 반환
                }
            } else {
                System.out.println("입력한 항의 개수가 " + x + "개가 아닙니다. 다시 시도해주세요.");
                return null; // 잘못된 입력 크기인 경우 null 반환
            }

            return Define_Equals; // 모든 검사를 통과하면 배열 반환
        }
    }

    public Integral(){
        setTitle("적분 계산기");
        setSize(400 ,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        fxField = new JTextField("f(x)");
        gxField = new JTextField("g(x)");
        fxField.setPreferredSize(new Dimension(100, 40));
        gxField.setPreferredSize(new Dimension(100, 40));
        add(fxField);
        add(gxField);

        resultTextArea = new JTextArea(20,30);
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        add(scrollPane);

        JButton Definite_Integral = new JButton("정적분");
        JButton Indefinite_Integral = new JButton ("부정적분");
        JButton Integration_by_Parts = new JButton ("부분적분");
        JButton Substitution = new JButton ("치환적분");

        add(Definite_Integral);
        add(Indefinite_Integral);
        add(Integration_by_Parts);
        add(Substitution);

        Definite_Integral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    resultTextArea.append("f(x), g(x)의 정적분을 시행하도록 하겠습니다.\n");
                    int x = Equations.N_equation();
                    int[] Max_Min = Equations.Integer_input();
                    int[] f_x = Equations.Equation(new Scanner(fxField.getText()),x);
                    int[] g_x = Equations.Equation(new Scanner(gxField.getText()),x);
                    double[] F_x = new double[f_x.length]; double[] G_x = new double[f_x.length]; double[] h_x = new double[f_x.length];
                    double result0=0.0; double result1=0.0; double result2=0.0;

                    for (int i=0; i<x+1; i++){
                        double D_integral0 = f_x[i];
                        double term0 = D_integral0 / (i+1);
                        result0 += term0 * (Math.pow(Max_Min[0],i+1) - Math.pow(Max_Min[1],i+1));

                        double D_integral1 = g_x[i];
                        double term1 = D_integral1 / (i+1);
                        result1 += term1 * (Math.pow(Max_Min[0],i+1) - Math.pow(Max_Min[1],i+1));
                    }

                    for (int i=0; i<h_x.length;i++){
                        F_x[i] = (double)f_x[i] / (x-i+1);
                        G_x[i] = (double)g_x[i] / (x-i+1);
                        h_x[i] = F_x[i] + G_x[i];
                        result2 += h_x[i];
                    }

                    resultTextArea.append("f(x) 정적분 결과 : " + result0 + "\n");
                    resultTextArea.append("다항식 형태 : ");
                    for (int i=0; i<f_x.length; i++){
                        if(f_x[i] != 0){
                            resultTextArea.append("(" + f_x[i] + " / " + (x-i+1) +" * x^" + (x-i+1) + ")");
                            if (i < f_x.length - 1){
                                resultTextArea.append(" + ");
                            }
                        }
                    }
                    resultTextArea.append("\n");
                    resultTextArea.append("g(x) 정적분 결과 : " + result1 + "\n");
                    resultTextArea.append("다항식 형태 : ");
                    for (int j=0; j<g_x.length; j++){
                        if (g_x[j] !=0){
                            resultTextArea.append("(" + g_x[j] + " / " + (x-j+1) + " * x^" + (x-j+1) + ")");
                            if(j < g_x.length - 1){
                                resultTextArea.append(" + ");
                            }
                        }
                    }
                    resultTextArea.append("\n");
                    resultTextArea.append("h(x) 정적분 결과 : " + result2 + "\n");
                    resultTextArea.append("h(x) = Definite integral {f(x) + g(x)} 다항식 형태 : ");
                    for (int k=0; k<h_x.length; k++){
                        if(h_x[k]!=0){
                            resultTextArea.append("(" + h_x[k] + " / " + " * x^" + (x-k+1) + ")");
                            if(k < h_x.length - 1){
                                resultTextArea.append(" + ");
                            }
                        }
                    }
                }catch(NumberFormatException ex){
                    resultTextArea.append("입력이 잘못되었습니다.\n");
                }
            }
        });
        Indefinite_Integral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    int x = Equations.N_equation();
                    int[] f_x = Equations.Equation(new Scanner(fxField.getText()),x);
                    int[] g_x = Equations.Equation(new Scanner(gxField.getText()),x);
                    resultTextArea.append("f(x) 부정적분 값\n");
                    resultTextArea.append("F(x) : ");
                    for (int i=0; i<f_x.length; i++){
                        resultTextArea.append("(" + f_x[i] + " / " + (x-i+1) + " * x^" + (x-i+1) + ")");
                        if(i < x){
                            resultTextArea.append(" + ");
                        }
                    }
                    resultTextArea.append(" + C\n");
                }catch(NumberFormatException ex){
                    resultTextArea.append("입력이 잘못되었습니다.\n");
                }
            }
        });
        Integration_by_Parts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    int x = Equations.N_equation();
                    int[] f_x = Equations.Equation(new Scanner(fxField.getText()),x);
                    int[] g_x = Equations.Equation(new Scanner(gxField.getText()),x);

                    float[] G_x = new float[x+1]; float[] Prime_f_x = new float[x];
                    float[] f_G = new float[2 * x + 1]; float[] fP_G = new float[2 * x];
                    float[] f_g = new float[2 * x + 1]; float[] If_g = new float[2 * x + 1];
                    float[] IFP_G = new float[2 * x + 1];

                    resultTextArea.append("G(x) : ");
                    for (int i=0; i< G_x.length; i++){
                        G_x[i] = (float)g_x[i] / (x-i+1);
                        if(G_x[i] !=0){
                            resultTextArea.append("(" + G_x[i] + " x^" + (x+1 -i) + ")");
                            if( i < G_x.length - 1){
                                resultTextArea.append(" + ");
                            }
                        }
                    }
                    resultTextArea.append("\n");
                    resultTextArea.append("f'(x) : ");
                    for (int i=0; i<Prime_f_x.length; i++){
                        Prime_f_x[i] = f_x[i] * (x-i);
                        if(Prime_f_x[i] !=0){
                            resultTextArea.append("(" + Prime_f_x[i] + "x^" + (x-i) + ")");
                            if(i < Prime_f_x.length){
                                resultTextArea.append(" + ");
                            }
                        }
                    }
                    resultTextArea.append("\n");
                    resultTextArea.append("f(x) * G(x) : ");
                    for (int i=0; i<f_x.length; i++){
                        for (int j=0; j<G_x.length; j++){
                            f_G[i+j] += (float)f_x[i] * G_x[i];
                        }
                    }
                    for (int i=0; i<f_G.length; i++){
                        if(f_G[i] !=0){
                            resultTextArea.append("(" + f_G[i] + " x^" + (f_G.length - i) + ")");
                            if (i< f_G.length){
                                resultTextArea.append(" + ");
                            }
                        }
                    }
                    resultTextArea.append("\n");
                    resultTextArea.append("f'(x) * G(x) : ");
                    for (int i=0; i<Prime_f_x.length; i++){
                        for (int j=0; j< G_x.length; j++){
                            fP_G[i + j] += Prime_f_x[i] * G_x[j];
                        }
                    }
                    for (int i=0; i< fP_G.length;i++){
                        if(fP_G[i] != 0){
                            resultTextArea.append("(" + fP_G[i] + " x^" + (fP_G.length - i) + ")");
                            if(i < fP_G.length - 1){
                                resultTextArea.append(" + ");
                            }
                        }
                    }
                    resultTextArea.append("\n");
                    resultTextArea.append("부분 적분 결과 : ");
                    for (int i=0; i< f_x.length; i++){
                        for (int j=0; j<g_x.length; j++){
                            f_g[i + j] += f_x[i] * g_x[j];
                        }
                    }
                    for (int i=0; i<f_g.length; i++){
                        If_g[i] = f_g[i] / (2 * x - i + 1);
                        if(If_g[i] != 0){
                            resultTextArea.append("(" + If_g[i] + " / " + (If_g.length + 1 - i) + " x^" + (If_g.length + 1 - i) + ")");
                            if(i < If_g.length -1){
                                resultTextArea.append(" + ");
                            }
                        }
                    }

                }catch(NumberFormatException ex){
                    resultTextArea.append("입력이 잘못되었습니다.\n");
                }
            }
        });
        Substitution.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    int x = Equations.N_equation();
                    int[] f_x = Equations.Equation(new Scanner(fxField.getText()),x);
                    int[] g_x = Equations.Equation(new Scanner(gxField.getText()),x);

                    double[] Prime_G = new double[x];
                    double[] F_x = new double[x+1];

                    resultTextArea.append("g'(x) : ");
                    for (int i=0; i<Prime_G.length; i++){
                        Prime_G[i] += g_x[i] * (x-i);
                        resultTextArea.append(Prime_G[i] + " x^" + (x-i-1));
                        if(i < g_x.length - 1){
                            resultTextArea.append(" + ");
                        }
                    }
                    resultTextArea.append("\n");
                    String g_x_combined = "";
                    for (int i=0; i<g_x.length; i++){
                        g_x_combined += g_x[i] + "x^" + (g_x.length - i +1);
                        if(i < g_x.length -1){
                            g_x_combined += " + ";
                        }
                    }
                    resultTextArea.append("f(g(x)) , t = ("+g_x_combined+"):\n\n");
                    for (int i = 0; i < f_x.length; i++) {
                        System.out.print(f_x[i]);
                        if (i < f_x.length - 1) {
                            resultTextArea.append("*(" + g_x_combined + ") + ");
                        }
                    }
                    resultTextArea.append("\n");
                    resultTextArea.append("integral { f(g(x)) * g'(x) dx } -> integral { f(t) dt } : ");
                    for (int i=0; i< F_x.length; i++){
                        F_x[i] +=(double)f_x[i] / (x-i+1);
                        resultTextArea.append(F_x[i] + "(" + g_x_combined +")^" + (x-i+1));
                        if(i < f_x.length - 1){
                            resultTextArea.append(" + ");
                        }
                    }

                }catch(NumberFormatException ex){
                    resultTextArea.append("입력이 잘못되었습니다.\n");
                }
            }
        });
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String message =
                        "f(x), g(x)를 입력 하실 때 다음과 같이 입력 해 주십시오.\n" +
                                "항이 하나도 빠지지 않은 형테\nex) f(x) = 2x^3 + 5x^2 + 10x + 3 -> 2 5 10 3\n\n" +
                                "항이 하나 빠져 있는 상태\n ex) f(x) = 2x^3 + 10x + 3 -> 2 0 10 3\n\n" +
                                "두 항의 차수가 다른 상태\n ex) f(x) = 2x^2 + 4x + 1, g(x) = 3x + 1 -> f(x)칸 : 2 4 1, g(x)칸 : 0 4 1";
                JOptionPane.showMessageDialog(null, message, "f(x), g(x) 입력 하는 방법",JOptionPane.INFORMATION_MESSAGE);
                Integral integral = new Integral();
                integral.setVisible(true);
            }
        });
    }
}