package com.hse.bank.utils;

import java.util.Scanner;

public class ConsoleReader {
    private static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static String readLine() {
        try {
            if (scanner.hasNextLine()) {
                return scanner.nextLine();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static double readDouble() {
        while (true) {
            String input = readLine().trim();
            if (!input.isEmpty()) {
                try {
                    return Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    System.out.print("Ошибка! Введите корректное число: ");
                }
            } else {
                System.out.print("Введите число: ");
            }
        }
    }

    public static int readInt() {
        while (true) {
            String input = readLine().trim();
            if (!input.isEmpty()) {
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.print("Ошибка! Введите целое число: ");
                }
            } else {
                System.out.print("Введите целое число: ");
            }
        }
    }
}