package org.example.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputHandler {
    private BufferedReader reader;

    public InputHandler() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public int scanInt(int min, int max) {
        while (true) {
            try {
                String input = reader.readLine();

                if (input == null || input.trim().isEmpty()) {
                    System.out.println("Пустой ввод. Попробуйте снова:");
                    continue;
                }

                int num = Integer.parseInt(input.trim());
                if (num >= min && num <= max) {
                    return num;
                } else {
                    System.out.println("Неверное число. Должно быть от " + min + " до " + max + ":");
                }
            } catch (Exception e) {
                System.out.println("Некорректный ввод. Введите число:");
            }
        }
    }

    public int scanInt(int min) {
        while (true) {
            try {
                String input = reader.readLine();

                if (input == null || input.trim().isEmpty()) {
                    System.out.println("Пустой ввод. Попробуйте снова:");
                    continue;
                }

                int num = Integer.parseInt(input.trim());
                if (num >= min) {
                    return num;
                } else {
                    System.out.println("Неверное число. Должно быть не меньше " + min + ":");
                }
            } catch (Exception e) {
                System.out.println("Некорректный ввод. Введите число:");
            }
        }
    }

    public String scanString() {
        try {
            return reader.readLine().trim();
        } catch (Exception e) {
            return "";
        }
    }
}