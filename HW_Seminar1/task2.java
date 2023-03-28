/*
 * Вычислить n! (произведение чисел от 1 до n)
 */

package JavaCourseHW.HW_Seminar1;

import java.util.Scanner;

public class task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("int: ");
        int n = scanner.nextInt();
        scanner.close();
        System.out.printf("%d! = %d", n, factorial(n));
    }
    
    static int factorial(int n){
        int res = 1;
        for (int i = 2; i < n+1; i++) {
            res *= i;
        }
        return res;
    }

}
