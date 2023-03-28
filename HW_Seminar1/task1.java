/*
 * Вычислить n-ое треугольного число (сумма чисел от 1 до n)
 */

package JavaCourseHW.HW_Seminar1;

import java.util.Scanner;

public class task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("int: ");
        int n = scanner.nextInt();
        scanner.close();
        System.out.printf("%d triangular number: %d", n, summa(n));
    }

    static int summa(int n){
        int res = 0;
        for (int i = 1; i < n+1; i++) {
            res += i;
        }
        return res;
    }
}
