package ru.geekbrains.java3.lesson3;

import java.io.*;
import java.util.*;

/*
•	Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
• 	Последовательно сшить 5 файлов в один (файлы также ~100 байт).
Может пригодиться следующая конструкция:
ArrayList<InputStream> al = new ArrayList<>();
...
Enumeration<InputStream> e = Collections.enumeration(al);
•	Написать консольное приложение, которое умеет постранично читать текстовые
 файлы (размером > 10 mb), вводим страницу, программа выводит ее в консоль
  (за страницу можно принять 1800 символов). Время чтения файла должно находится
   в разумных пределах (программа не должна загружаться дольше 10 секунд), ну
    и чтение тоже не должно занимать >5 секунд.
Чтобы не было проблем с кодировкой используйте латинские буквы.

 */
public class Main {
    static class Timer {
        static long t;

        static void start() {
            t = System.currentTimeMillis();
        }

        static void stop() {
            System.out.println("Time: " + (System.currentTimeMillis() - t));
        }
    }

    public static void main(String[] args) {
        try {
            readContent("1.txt");
            stitchFiles("1.txt", "2.txt", "3.txt", "4.txt", "5.txt", "6.txt");
            showPage("7.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readContent(String path) throws IOException {
        ByteArrayOutputStream out = null;
        BufferedInputStream in = null;
        System.out.println("Task1");
        out = new ByteArrayOutputStream();
        in = new BufferedInputStream(new FileInputStream(path));
        int x;
        while ((x = in.read()) != -1) {
            out.write(x);
            System.out.print((char) x);
        }
        byte[] arr = out.toByteArray();
        System.out.println("\n" + Arrays.toString(arr));
        in.close();
        out.close();
        System.out.println("==============================================");
    }

    public static void stitchFiles(String path1, String path2, String path3, String path4, String path5, String path6) throws IOException {
        System.out.println("Task2");
        Timer.start();
        ArrayList<InputStream> al = new ArrayList<>();
        al.add(new FileInputStream(path1));
        al.add(new FileInputStream(path2));
        al.add(new FileInputStream(path3));
        al.add(new FileInputStream(path4));
        al.add(new FileInputStream(path5));
        BufferedInputStream in = new BufferedInputStream(new SequenceInputStream(Collections.enumeration(al)));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path6));
        int x;
        while ((x = in.read()) != -1) {
            out.write(x);
            System.out.print((char) x);
        }
        in.close();
        out.close();
        System.out.println("\n" + "==============================================");
        Timer.stop();
    }

    //можно было добавить while, чтобы можно было выйти из приложения или еще другие страницы загружать,
    //но думаю по заданию надо замерить время именно сколько исполняется всё для одной страницы
    public static void showPage(String path7) throws IOException {
        System.out.println("Task3");
        Timer.start();
        int numberOfSymbols = 1800;
        RandomAccessFile raf = new RandomAccessFile(path7, "rw");
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите страницу: ");
        int page = sc.nextInt() - 1;
        raf.seek(page * numberOfSymbols);
        for (int i = 0; i < numberOfSymbols; i++) {
            System.out.print((char) raf.read());
        }
        raf.close();
        System.out.println("\n" + "==============================================");
        Timer.stop();
    }
}
