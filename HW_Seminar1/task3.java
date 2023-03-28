/*
 * Вывести все простые числа от 1 до 1000 
 * (числа, которые делятся только на 1 и на себя без остатка)
 */

package JavaCourseHW.HW_Seminar1;

public class task3 {
    public static void main(String[] args) {
        int vulBegin = 1;
        int vulEnd = 1000;
        primes(vulBegin, vulEnd);
    }

    static void primes(int vBegin, int vEnd){
        for (; vBegin < vEnd+1; vBegin++) {
            int vBeginE = vBegin / 2 + 1;
            boolean flag = true;
            if (vBegin == 1) flag = false;
            for (int i = 2; i < vBeginE; i++) {
                if (vBegin % i == 0){
                    flag = false;
                    break;
                }
            }
            if (flag == true){
                System.out.println(vBegin);
            }
        }
    }
}
