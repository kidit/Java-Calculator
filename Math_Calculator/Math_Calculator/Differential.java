package Math_Calculator;

import java.util.Scanner;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Differential extends JFrame{
    final private JTextField fxField;
    final private JTextField gxField;
    final private JTextArea resultTextArea;

    //f(x)
    public static class f_x{
        public static int[] expandFunction(String[] valiation, int x){
            int[] value = new int[x+1];
            //M차 방정식 전개
            for (int i=0; i<x+1; i++){
                value[i] = Integer.parseInt(valiation[i]);
                System.out.println("x^" + (x - i) + ": " + value[i]);
            }
            return value;
        }
    }
    //g_x
    public static class g_x{
        public static int[] getInput(Scanner scanner, int x){
            String input =scanner.nextLine();
            String[] valiation = input.split(" ");

            if (valiation.length == x+1){
                try{
                    int[] value = new int[x + 1];

                    for (int i=0; i< x+1; i++){
                        value[i] = Integer.parseInt(valiation[i]);
                        System.out.println("x^" + (x - i) + ": " + value[i]);
                    }

                    return value;
                } catch (NumberFormatException e){
                    System.out.println("문자, 숫자가 아닌 다른 것이 포함되어 있습니다.");
                }
            } else {
                System.out.println("입력한 항의 개수 : " + x + "개가 아닙니다. 다시 시도해주세요.");
            }
            return null;
        }
    }


    public Differential(){
        //창 초기화
        setTitle("미분 연산기");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        //입력 필드 추가
        fxField = new JTextField("f(x)");
        gxField = new JTextField("g(x)");
        fxField.setPreferredSize(new Dimension(300,30));
        gxField.setPreferredSize(new Dimension(300,30));
        add(fxField);
        add(gxField);

        //결과 텍스트 영역
        resultTextArea = new JTextArea(20, 30);
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        add(scrollPane);

        //버튼 추가
        JButton derivativeButton = new JButton("n계 도함수");
        JButton sumDifferentialButton = new JButton("합 미분");
        JButton multipleDifferentialButton = new JButton("곱 미분");
        JButton quotientDifferentialButton = new JButton("몫 미분");
        JButton eulerButton = new JButton("오일러 공식");

        add(derivativeButton);
        add(sumDifferentialButton);
        add(multipleDifferentialButton);
        add(quotientDifferentialButton);
        add(eulerButton);

        //버튼 설정
        derivativeButton.addActionListener(new ActionListener() {
            //n계 도함수
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    int x = Integer.parseInt(JOptionPane.showInputDialog("f(x)식을 미분하겠습니다.\nf(x)의 다항식의 차수를 입력하시오. "));
                    int[] value = f_x.expandFunction(fxField.getText().split(" "),x);
                    int z =Integer.parseInt(JOptionPane.showInputDialog("몇 번 미분하시겠습니까? "));
                    for (int i=0; i<z; i++){
                        for (int k=0; k<x; k++){
                            value[k] = value[k] * (x-k);
                        }
                        x--;
                    }
                    resultTextArea.append("n계 도함수 결과: ");
                    if (x<z){
                        resultTextArea.append("0");
                    } else{
                        for (int i=0; i<=x; i++){
                            resultTextArea.append("미분(x^" + (x - i) + "): " + value[i] + "\n");
                        }
                    }
                } catch(NumberFormatException ex){
                    resultTextArea.append("입력이 잘못되었습니다.\n");
                }
            }
        });
        sumDifferentialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    int x = Integer.parseInt(JOptionPane.showInputDialog("다항식의 차수를 입력하시오."));
                    int[] value1 = f_x.expandFunction(fxField.getText().split(" "), x);
                    int[] value2 = g_x.getInput(new Scanner(gxField.getText()), x);

                    if(value2 != null){
                        for (int i=0; i<value1.length; i++){
                            value1[i] += value2[i];
                            value1[i] = value1[i] * (value1.length-i);
                        }
                        resultTextArea.append("합 미분 결과 :\n");
                        for (int i=0; i<x; i++){
                            resultTextArea.append("합 미분(x^" + (x - i) + "): " + value1[i] + "\n");
                        }
                    }
                } catch(NumberFormatException ex){
                    resultTextArea.append("입력이 잘못되었습니다.\n");
                }
            }
        });
        multipleDifferentialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    int x = Integer.parseInt(JOptionPane.showInputDialog("다항식의 차수를 입력하시오."));
                    int[] value3 =f_x.expandFunction(fxField.getText().split(" "), x);
                    int[] value4 = g_x.getInput(new Scanner(gxField.getText()),x);
                    if(value4 != null){
                        int[] result = new int[2 * x + 1];
                        int[] fPrime = new int[x + 1];
                        int[] gPrime = new int[x + 1];

                        for (int i=0; i<=x; i++){
                            fPrime[i] = value3[i] * (x-i);
                            gPrime[i] = value4[i] * (x-i);
                        }
                        for (int i=0; i<=x; i++){
                            for (int j=0; j<=x; j++){
                                result[i + j] = fPrime[i] * value4[j] + value3[i] * gPrime[j];
                            }
                        }
                        resultTextArea.append("곱 미분 결과\n");
                        for (int i=0; i<2 * x; i++){
                            resultTextArea.append("곱 미분(x^" + (2 * x - i - 1) + "): " + result[i] + "\n");
                        }
                    }
                } catch(NumberFormatException ex){
                    resultTextArea.append("입력이 잘못되었습니다.\n");
                }
            }
        });
        quotientDifferentialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    int x = Integer.parseInt(JOptionPane.showInputDialog("차수가 가장 높은 다항식의 차수를 입력하시오."));
                    int[] value5 = f_x.expandFunction(fxField.getText().split(" "),x);
                    int[] value6 = g_x.getInput(new Scanner(gxField.getText()),x);
                    if(value6 != null){
                        int[] fPrime = new int[x + 1];
                        int[] gPrime = new int[x + 1];
                        for (int i=0; i<=x; i++){
                            fPrime[i] = value5[i] * (x-i);
                            gPrime[i] = value6[i] * (x-i);
                        }
                        int[] up = new int[2 * x + 1];
                        int[] down = new int[2 * x + 1];
                        //분모 미분
                        for (int i=0; i<=x; i++){
                            for (int j=0; j<=x; j++){
                                down[i + j] += value6[i] * value6[j];
                            }
                        }
                        //분자 미분
                        for (int i=0; i<=x; i++){
                            for (int j=0; j<=x; j++){
                                up[i + j] += fPrime[i] * value6[j] + value5[i] * gPrime[j];
                            }
                        }
                        resultTextArea.append("몫 미분 결과 (f'(x) * g(x) + f(x) * g'(x) / {g'(x)}^2:\n");
                        for(int i=0; i<2 * x; i++){
                            if (up[i] != 0){
                                resultTextArea.append("(분자 : x^" + (2 * x - i - 1) + "): " + up[i] + "\n");
                            }
                        }
                        for (int i=0; i<2 * x + 1 ; i++){
                            if (down[i] != 0){
                                resultTextArea.append("(분모: x^" + (2 * x-i) + "): " + down[i] + "\n");
                            }
                        }
                    }
                }catch (NumberFormatException ex){
                    resultTextArea.append("입력이 잘못되었습니다.\n");
                }
            }
        });
        eulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int x_value = Integer.parseInt(JOptionPane.showInputDialog("a*e^(nx)의 a 값을 입력하세요."));
                    int y_value = Integer.parseInt(JOptionPane.showInputDialog("a*e^(nx)의 n 값을 입력하세요."));
                    int z_value = Integer.parseInt(JOptionPane.showInputDialog("몇 번 미분하시겠숩나꺼?"));

                    int Euler = 1;
                    int[] result_Euler = new int[x_value + 1];
                    if (z_value < y_value){
                        for (int i=0; i< z_value; i++){
                            Euler *= y_value;
                            result_Euler[i] += x_value * Euler;
                            resultTextArea.append((i + 1) + "번 미분 (f(x) =" + result_Euler[i] + "e^(" + y_value + "x))\n");
                        }
                    }

                } catch(NumberFormatException ex){
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
                Differential differential = new Differential();
                differential.setVisible(true);
            }
        });
    }
}