package seminar6.homework;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        String[] ram = {"1", "DRAM", "SRAM"};
        String[] os = {"3", "Windows", "Linux", "MacOS"};
        String[] colors = {"4", "Белый", "Красный", "Черный", "Розовый", "Синий", "Коричневый"};

        HashSet<Laptops> laptops = randomLaptops(ram, os, colors, 100);
        interficeConsol(laptops, ram, os, colors);
    }

    static HashSet<Laptops> randomLaptops(String[] ram, String[] os, String[] colors, int count) {
        HashSet<Laptops> res = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            Laptops laptop = new Laptops();
            laptop.setRam(ram[random.nextInt(1, ram.length)]);
            laptop.setHdd(random.nextInt(1, 10));
            laptop.setOs(os[random.nextInt(1, os.length)]);
            laptop.setColor(colors[random.nextInt(1, colors.length)]);
            res.add(laptop);
        }
        return res;
    }

    static void interficeConsol(HashSet<Laptops> laptops, String[] ram, String[] os, String[] colors) {
        HashMap<Integer, List<Integer>> mapFilter = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("\nВведите цифру, соответствующую необходимому критерию:\n" +
                    "0 - Выбранные фильтры\n" +
                    "1 - ОЗУ\n" +
                    "2 - Объем ЖД\n" +
                    "3 - Операционная система\n" +
                    "4 - Цвет\n" +
                    "5 - Поиск");
            String userAnswer = scanner.nextLine();
            switch (userAnswer) {
                case "0":
                    interficeConsolFilter(mapFilter, ram, os, colors);
                    break;
                case "1":
                    System.out.println("\nДля ОЗУ доступны следующие фильтры:");
                    List<Integer> ramAnswer = interficeConsolCase(ram, 0);
                    addMapFilter(ramAnswer, mapFilter, 1);
                    break;
                case "2":
                    List<Integer> hddAnswer = interficeConsolHdd(); //[0, 10]
                    if (hddAnswer.get(0) != -1) {
                        if (mapFilter.containsKey(2)) {
                            System.out.println("Вы можете указать только один диапазон значений. " +
                                    "Переписать текущий диапазон значений (0 - нет, 1 - да)?");
                            String userAnswerNext = scanner.nextLine();
                            switch (userAnswerNext) {
                                case "0":
                                    break;
                                case "1":
                                    mapFilter.remove(2);
                                    List<Integer> list = new ArrayList<>();
                                    list.add(hddAnswer.get(0));
                                    list.add(hddAnswer.get(1));
                                    mapFilter.put(2, list);
                                    break;
                                default:
                                    System.out.println("Неверный ответ. Попробуйте ещё раз.");
                            }
                        } else {
                            List<Integer> list = new ArrayList<>();
                            list.add(hddAnswer.get(0));
                            list.add(hddAnswer.get(1));
                            mapFilter.put(2, list);
                        }
                    }
                    break;
                case "3":
                    System.out.println("\nДля операционной системы доступны следующие фильтры:");
                    List<Integer> osAnswer = interficeConsolCase(os, 0);
                    addMapFilter(osAnswer, mapFilter, 3);
                    break;
                case "4":
                    System.out.println("\nВ наличии есть следующие цвета:");
                    List<Integer> colorAnswer = interficeConsolCase(colors, 0);
                    addMapFilter(colorAnswer, mapFilter, 4);
                    break;
                case "5":
                    interficeConsolFind(laptops, mapFilter, ram, os, colors);
                    flag = false;
                    break;
                default:
                    System.out.println("Неверный критерий. Попробуйте ещё раз.");
            }
        }
    }

    static void interficeConsolFilter(HashMap<Integer, List<Integer>> filters, String[] ram,
                                      String[] os, String[] colors) {
        System.out.println("\nВыбранные фильтры:");
        for (Integer key :
                filters.keySet()) {
            if (ram[0].equals(key.toString())) {
                List<Integer> ramList = filters.get(key);
                for (int i = 0; i < ramList.size(); i++) {
                    System.out.println("ОЗУ - " + ram[ramList.get(i)]);
                }
            }
            if ((key.toString()).equals("2")) {
                List<Integer> hddList = filters.get(2);
                System.out.println("Объем ЖД от " + hddList.get(0) + " ТБ до " + hddList.get(1) + " ТБ");
            }
            if (os[0].equals(key.toString())) {
                List<Integer> osList = filters.get(key);
                for (int i = 0; i < osList.size(); i++) {
                    System.out.println("Операционная система - " + os[osList.get(i)]);
                }
            }
            if (colors[0].equals(key.toString())) {
                List<Integer> colorsList = filters.get(key);
                for (int i = 0; i < colorsList.size(); i++) {
                    System.out.println("Цвет - " + colors[colorsList.get(i)]);
                }
            }
        }

        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("\nДля возвращения назад нажмите 1.");
            String userAnswer = scanner.nextLine();
            if (isNumeric(userAnswer) && Integer.parseInt(userAnswer) == 1) flag = false;
        }
    }

    static List<Integer> interficeConsolCase(String[] arr, int count) {
        for (int i = 1; i < arr.length; i++) {
            System.out.println(i + " - " + arr[i]);
            count++;
        }
        System.out.println((count + 1) + " - Назад");
        int userAnswerInt = userAnserInt(count);
        if (userAnswerInt == count + 1) {
            return List.of(0, 0);
        }
        return List.of(userAnswerInt, count);
    }

    static List<Integer> interficeConsolHdd() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nВведите необходимый диапазон поиска объема ЖД в ТБ. Для отмены введите число -1.");
        boolean flag = true;
        int userAnswerIntDown = 0;
        while (flag) {
            System.out.println("\nНижний диапазон:");
            String userAnswer = scanner.nextLine();
            if (isNumeric(userAnswer) && -1 <= Integer.parseInt(userAnswer)) {
                userAnswerIntDown = Integer.parseInt(userAnswer);
                flag = false;
                if (userAnswerIntDown == -1) {
                    return List.of(-1);
                }
            } else {
                System.out.println("Неверный ввод числа. Попробуйте ещё раз.");
            }
        }
        flag = true;
        int userAnswerIntUp = 0;
        while (flag) {
            System.out.println("\nВерхний диапазон:");
            String userAnswer = scanner.nextLine();
            if (isNumeric(userAnswer) && Integer.parseInt(userAnswer) > userAnswerIntDown) {
                userAnswerIntUp = Integer.parseInt(userAnswer);
                flag = false;
                if (userAnswerIntUp == -1) {
                    return List.of(-1);
                }
            } else {
                System.out.println("Неверный ввод числа. Попробуйте ещё раз.");
            }
        }
        return List.of(userAnswerIntDown, userAnswerIntUp);
    }

    static void interficeConsolFind(HashSet<Laptops> laptops,
                                      HashMap<Integer, List<Integer>> filters,
                                      String[] ram, String[] os, String[] colors){
        System.out.println("Выбранным фильтрам удовлетворяют следующие ноутбуки:");
        HashSet<Laptops> res = laptops;
        HashSet<Laptops> temp = new HashSet<>();
        for (Integer key : // {1: [1, 2]; 2: [0, 3], 4: [7]}
                filters.keySet()) {
            if (ram[0].equals(key.toString())) {
                List<Integer> ramList = filters.get(key);
                temp = res;
                res = intersection(temp, findByRam(laptops, ram[ramList.get(0)]));
                for (int i = 1; i < ramList.size(); i++) {
                    res.addAll(findByRam(temp, ram[ramList.get(i)]));
                }
            }
            if ((key.toString()).equals("2")) {
                List<Integer> hddList = filters.get(2);
                temp = res;
                res = intersection(temp, findByHdd(laptops, hddList.get(0), hddList.get(1)));
            }
            if (os[0].equals(key.toString())) {
                List<Integer> osList = filters.get(key);
                temp = res;
                res = intersection(temp, findByOs(laptops, os[osList.get(0)]));
                for (int i = 1; i < osList.size(); i++) {
                    res.addAll(findByOs(temp, os[osList.get(i)]));
                }
            }
            if (colors[0].equals(key.toString())) {
                List<Integer> colorsList = filters.get(key);
                temp = res;
                res = intersection(temp, findByColor(laptops, colors[colorsList.get(0)]));
                for (int i = 1; i < colorsList.size(); i++) {
                    res.addAll(findByColor(temp, colors[colorsList.get(i)]));
                }
            }
        }
        printLaptops(res);
    }

    static HashSet<Laptops> findByRam(HashSet<Laptops> laptops, String ram){
        HashSet<Laptops> res = new HashSet<>();
        for (Laptops laptop :
                laptops) {
            if ((laptop.getRam()).equals(ram)){
                res.add(laptop);
            }
        }
        return res;
    }

    static HashSet<Laptops> findByHdd(HashSet<Laptops> laptops, int hddMin, int hddMax){
        HashSet<Laptops> res = new HashSet<>();
        for (Laptops laptop :
                laptops) {
            if (hddMin <= laptop.getHdd() && laptop.getHdd() <= hddMax){
                res.add(laptop);
            }
        }
        return res;
    }

    static HashSet<Laptops> findByOs(HashSet<Laptops> laptops, String os){
        HashSet<Laptops> res = new HashSet<>();
        for (Laptops laptop :
                laptops) {
            if ((laptop.getOs()).equals(os)){
                res.add(laptop);
            }
        }
        return res;
    }

    static HashSet<Laptops> findByColor(HashSet<Laptops> laptops, String color){
        HashSet<Laptops> res = new HashSet<>();
        for (Laptops laptop :
                laptops) {
            if ((laptop.getColor()).equals(color)){
                res.add(laptop);
            }
        }
        return res;
    }

   static HashSet<Laptops> intersection(HashSet<Laptops> setA, HashSet<Laptops> setB) {
        if (setA.size() > setB.size()) {
            return intersection(setB, setA);
        }
       HashSet<Laptops> res = new HashSet<>();
        for (Laptops element : setA) {
            if (setB.contains(element)) {
                res.add(element);
            }
        }
        return res;
    }

    static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static int userAnserInt(int count) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        int res = 0;
        while (flag) {
            String userAnswer = scanner.nextLine();
            if (isNumeric(userAnswer) && 0 < Integer.parseInt(userAnswer) && Integer.parseInt(userAnswer) <= count + 1) {
                res = Integer.parseInt(userAnswer);
                flag = false;
            } else {
                System.out.println("Неверный фильтр. Попробуйте ещё раз.");
            }
        }
        return res;
    }

    static void addMapFilter(List<Integer> answer, HashMap<Integer, List<Integer>> mapFilter, int caseUser){
        if (answer.get(1) != 0) {
            if (mapFilter.containsKey(caseUser)) {
                List<Integer> list = mapFilter.get(caseUser);
                if (!list.contains(answer.get(0))) {
                    list.add(answer.get(0));
                }
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(answer.get(0));
                mapFilter.put(caseUser, list);
            }
        }
    }

    static void printLaptops(HashSet<Laptops> laptops){
        for (Laptops laptop :
                laptops) {
            System.out.println(laptop);
        }
    }
}
