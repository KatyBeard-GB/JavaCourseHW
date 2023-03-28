/*
 * Реализовать простой калькулятор 
 * (введите первое число, введите второе число, введите требуемую операцию, ответ)
 */

package JavaCourseHW.HW_Seminar1;

import java.util.Scanner;

public class task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("math: ");
        String math = scanner.nextLine();
        System.out.println("number 1: ");
        double number1 = scanner.nextDouble();
        System.out.println("number 2: ");
        double number2 = scanner.nextDouble();
        scanner.close();
        System.out.printf("\n%f %s %f = %f", number1, math, number2, calculator(number1, number2, math));
    }

    static double calculator(double num1, double num2, String math){
        double res = -1;
        char[] ch = math.toCharArray();
        if (ch[0] == '/') res = num1 / num2;
        if (ch[0] == '*') res = num1 * num2;
        if (ch[0] == '-') res = num1 - num2;
        if (ch[0] == '+') res = num1 + num2;
        return res;
    }
}
