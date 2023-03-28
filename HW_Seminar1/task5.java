/*
 * Задано уравнение вида q + w = e, q, w, e >= 0. 
 * Некоторые цифры могут быть заменены знаком вопроса, например, 2? + ?5 = 69. 
 * Требуется восстановить выражение до верного равенства. 
 * Предложить хотя бы одно решение или сообщить, что его нет.
 */

package JavaCourseHW.HW_Seminar1;

import java.util.Arrays;
import java.util.Scanner;
// import java.util.stream.IntStream;

public class task5 {
    public static void main(String[] args) {
        // Получение значений для уравнения вида q + w = e
        Scanner s = new Scanner(System.in);
        System.out.println("q: ");
        String q = s.nextLine();
        System.out.println("w: ");
        String w = s.nextLine();
        System.out.println("e: ");
        String e = s.nextLine();
        s.close();
        System.out.printf("\n%s + %s = %s\n", q, w, e);
        equation(q, w, e);
    }

    static void equation(String q, String w, String e) {
        // Разделения написанного посимвольно
        char[] qChar = q.toCharArray();
        char[] wChar = w.toCharArray();
        char[] eChar = e.toCharArray();
        // Проверка на введенное число (метод checkStr). Если введено число, то ответ будет false, 
        // если в веденной строке есть знаки вопроса, то ответ true
        boolean qBool = checkStr(qChar);
        boolean wBool = checkStr(wChar);
        boolean eBool = checkStr(eChar);

        // Введены все числа, все значения false, например 16+17=35
        if (qBool == false && wBool == false && eBool == false) {
            threeFalse(q, w, e);
        }

        // Неизвестно только одно число (одно true)
        if (qBool == false && wBool == false && eBool == true) {
            oneTrue(q, w, e, "001");
        }
        if (qBool == false && wBool == true && eBool == false) {
            oneTrue(q, w, e, "010");
        }
        if (qBool == true && wBool == false && eBool == false) {
            oneTrue(q, w, e, "100");
        }

        // Неизвестны два числа (два true)
        if (qBool == false && wBool == true && eBool == true) {
            twoTrue(q, w, e, "011");
        }
        if (qBool == true && wBool == false && eBool == true) {
            twoTrue(q, w, e, "101");
        }
        if (qBool == true && wBool == true && eBool == false) {
            twoTrue(q, w, e, "110");
        }

        // Неизвестны все три числа, например ??+?7=?5
        if (qBool == true && wBool == true && eBool == true) {
            threeTrue(q, w, e);
        }
    }

    // Проверка есть ли в веденных числах знак вопроса
    static boolean checkStr(char[] ch) {
        boolean res = false;
        for (char c : ch) {
            if (c == '?')
                res = true;
        }
        return res;
    }

    // Количество знаков вопроса
    static int countQuestion(char[] ch) {
        int res = 0;
        for (char c : ch) {
            if (c == '?')
                res++;
        }
        return res;
    }

    // Метод для трех известных чисел
    static void threeFalse(String q, String w, String e){
        // Получения чисел из строк
        int qInt = Integer.parseInt(q);
        int wInt = Integer.parseInt(w);
        int eInt = Integer.parseInt(e);
        // Проверка правильности уравнения
        if (qInt + wInt == eInt)
            System.out.println("Equality is true");
        else
            System.out.println("Equality is false");
    }

    // Метод для двух известных чисел
    static void oneTrue(String q, String w, String e, String key){
        switch (key) {
            case "001": // Пример 16+17=3?
                char[] eChar = e.toCharArray(); 
                // Получение известных чисел из строк
                int qInt = Integer.parseInt(q);
                int wInt = Integer.parseInt(w);
                // Вызов метода resString
                // проверяет есть ли решения для введенного уравнения
                String eStr = resString(qInt, wInt, eChar);
                if (eStr == "-1")
                    System.out.println("No solution");
                else
                    System.out.printf("%d + %d = %s\n", qInt, wInt, eStr);
                break;

            case "010": // Пример 16+1?=33
                char[] wChar = w.toCharArray();
                // Получение известных чисел из строк
                qInt = -(Integer.parseInt(q));
                int eInt = Integer.parseInt(e);
                // Вызов метода resString
                // проверяет есть ли решения для введенного уравнения
                String wStr = resString(qInt, eInt, wChar);
                if (wStr == "-1")
                    System.out.println("No solution");
                else
                    System.out.printf("%d + %s = %d\n", -qInt, wStr, eInt);
                break;

            case "100": // Пример 1?+17=33
                char[] qChar = q.toCharArray();
                // Получение известных чисел из строк
                wInt = -(Integer.parseInt(w));
                eInt = Integer.parseInt(e);
                // Вызов метода resString
                // проверяет есть ли решения для введенного уравнения
                String qStr = resString(wInt, eInt, qChar);
                if (qStr == "-1")
                    System.out.println("No solution");
                else
                    System.out.printf("%s + %d = %d\n", qStr, -wInt, eInt);
                break;
        }
    }

    // Метод для одного известного числа
    static void twoTrue(String q, String w, String e, String key){
        char[] qChar = q.toCharArray();
        char[] wChar = w.toCharArray();
        char[] eChar = e.toCharArray();
        switch (key) {
            case "011": //  Пример 16+1?=3?
                int maxLen = Math.max(qChar.length, wChar.length);
                // Проверка на возможность решения уровнения
                if (maxLen > eChar.length || maxLen < eChar.length - 1) {
                    System.out.println("No solution");
                } else {
                    // Получение известного числа из строки
                    int qInt = Integer.parseInt(q);
                    boolean flag = true; // Флаг для проверки, что есть хотя бы одно решение
                    // Получение массива возможных решений для данной переменной (метод arrayStr)
                    String[] wArrStr = arrayStr(wChar);
                    // Перебор массива возможных решений и их вывод
                    for (int i = 0; i < wArrStr.length; i++) {
                        char[] wArrChar = wArrStr[i].toCharArray();
                        if (wArrChar[0] != '0') { // Исключение таких чисел как 01, 025 и тп
                            int wInt = Integer.parseInt(wArrStr[i]);
                            String eStr = resString(qInt, wInt, eChar);
                            if (eStr != "-1") {
                                System.out.printf("%d + %d = %s\n", qInt, wInt, eStr);
                                flag = false; // Решение есть (флаг - false)
                            }
                        }
                    }
                    if (flag == true) // Решений нет
                        System.out.println("No solution");
                }
                break;
        
            case "101": //  Пример 1?+17=3?
                maxLen = Math.max(qChar.length, wChar.length);
                // Проверка на возможность решения уровнения
                if (maxLen > eChar.length || maxLen < eChar.length - 1) {
                    System.out.println("No solution");
                } else {
                    // Получение известного числа из строки
                    int wInt = Integer.parseInt(w);
                    boolean flag = true; // Флаг для проверки, что есть хотя бы одно решение
                    // Получение массива возможных решений для данной переменной (метод arrayStr)
                    String[] qArrStr = arrayStr(qChar);
                    // Перебор массива возможных решений и их вывод
                    for (int i = 0; i < qArrStr.length; i++) {
                        char[] qArrChar = qArrStr[i].toCharArray();
                        if (qArrChar[0] != '0') { // Исключение таких чисел как 01, 025 и тп
                            int qInt = Integer.parseInt(qArrStr[i]);
                            String eStr = resString(qInt, wInt, eChar);
                            if (eStr != "-1") {
                                System.out.printf("%d + %d = %s\n", qInt, wInt, eStr);
                                flag = false;
                            }
                        }
                    }
                    if (flag == true) // Решений нет
                        System.out.println("No solution");
                }
                break;

            case "110": //  Пример 1?+1?=33
                maxLen = Math.max(qChar.length, wChar.length);
                // Проверка на возможность решения уровнения
                if (maxLen > eChar.length || maxLen < eChar.length - 1) {
                    System.out.println("No solution");
                } else {
                    // Получение известного числа из строки
                    int eInt = Integer.parseInt(e);
                    boolean flag = true; // Флаг для проверки, что есть хотя бы одно решение
                    // Получение массива возможных решений для данной переменной (метод arrayStr)
                    String[] qArrStr = arrayStr(qChar);
                    // Перебор массива возможных решений и их вывод
                    for (int i = 0; i < qArrStr.length; i++) {
                        char[] qArrChar = qArrStr[i].toCharArray();
                        if (qArrChar[0] != '0') { // Исключение таких чисел как 01, 025 и тп
                            int qInt = -(Integer.parseInt(qArrStr[i]));
                            String wStr = resString(qInt, eInt, wChar);
                            if (wStr != "-1") {
                                System.out.printf("%d + %s = %d\n", -qInt, wStr, eInt);
                                flag = false;
                            }
                        }
                    }
                    if (flag == true) // Решений нет
                        System.out.println("No solution");
                }
                break;
        }
    }

    // Метод для всех неизвестных чисел
    static void threeTrue(String q, String w, String e){
        char[] qChar = q.toCharArray();
        char[] wChar = w.toCharArray();
        char[] eChar = e.toCharArray();
        int maxLen = Math.max(qChar.length, wChar.length);
        // Проверка на возможность решения уровнения
        if (maxLen > eChar.length || maxLen < eChar.length - 1) {
            System.out.println("No solution");
        } else {
            boolean flag = true; // Флаг для проверки, что есть хотя бы одно решение
            // Получение массива возможных решений для двух переменных (метод arrayStr)
            String[] qArrStr = arrayStr(qChar);
            String[] wArrStr = arrayStr(wChar);
            for (int i = 0; i < wArrStr.length; i++) { // Перебор первой строки
                char[] wArrChar = wArrStr[i].toCharArray();
                if (wArrChar[0] != '0'){
                for (int j = 0; j < qArrStr.length; j++) { // Перебор второй строки
                    char[] qArrChar = qArrStr[j].toCharArray();
                    if (qArrChar[0] != '0') {
                        int wInt = Integer.parseInt(wArrStr[i]);
                        int qInt = Integer.parseInt(qArrStr[j]);
                        String eStr = resString(qInt, wInt, eChar);
                        if (eStr != "-1") {
                            System.out.printf("%d + %d = %s\n", qInt, wInt, eStr);
                            flag = false;
                        }
                    }
                }}
            }
            if (flag == true) // Решений нет
                System.out.println("No solution");
        }
    }

    // Получение значения одной неизвестной переменной по двум известным
    static String resString(int ch1Int, int ch2Int, char[] chTrue) {
        int chAnswer = ch1Int + ch2Int;
        if (chAnswer >= 0) {
            String chStr = chAnswer + ""; // Перевод числа в строку
            char[] chChar = chStr.toCharArray(); // Получение массива символов
            String resStr = "";
            if (chChar.length == chTrue.length) { // Проверка полученного ответа и введенного
                for (int index = 0; index < chChar.length; index++) { // Перебор полученного ответа
                    if (chChar[index] != chTrue[index] && chTrue[index] != '?') {
                        return "-1";
                    } else
                        resStr += chChar[index];
                }
            } else { // На вход например получено ?3, а получилась сумма чисел 123
                return "-1";
            }
            return resStr;
        } else // Сумма меньше 0
            return "-1";
    }

    // Получение массива строк возможных решений по введеной стоке
    // 1? -> [10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
    static String[] arrayStr(char[] chTrue) {
        int chQuestion = countQuestion(chTrue); // Количество неизвестных цифр
        // Определение количества возможных решений
        int countArr = 0;
        double temp = Math.pow(10, chQuestion - 1);
        int startArr = (int) temp; // Начальное значение
        temp = Math.pow(10, chQuestion);
        int endArr = (int) temp; // Конечное значение, не включая его

        if (chQuestion == 1)
            countArr = 10;
        else
            countArr = endArr - startArr;

        // Создание массива для получения возможных решений
        String[] arrStr = new String[countArr];
        Arrays.fill(arrStr, "");

        // Перебор введенной стороки
        for (int index = 0, chQuestionCount = chQuestion; index < chTrue.length; index++) {
            if (chTrue[index] != '?') { // Если встречается число, то оно записывается в строку
                for (int i = 0; i < arrStr.length; i++) { // Число записывается в каждую возможную строку
                    arrStr[i] += chTrue[index];
                }
            } else { // Если встречается вопрос
                if (chQuestion == 1) { // Вопрос всего один
                    for (int i = 0; i < 10; i++) {
                        arrStr[i] += i;
                    }
                } else if (index == 0) { // Неизвестно первое число, например ??489
                    for (int i = startArr, j = 0; i < endArr; i++, j++) {
                        arrStr[j] += (int) i / startArr; // Целочисленное деление
                    }
                } else { // Все остальные случае
                    temp = Math.pow(10, chQuestion - chQuestionCount);
                    for (int i = 0, count = 0; i < countArr / (int) temp; i++, count++) {
                        if (count == 10)
                            count = 0;
                        for (int j = 0; j < (int) temp; j++) {
                            int indexA = i * (int) temp + j;
                            arrStr[indexA] += count;
                        }
                    }
                    chQuestionCount--;
                }
            }
        }
        return arrStr;
    }
}
