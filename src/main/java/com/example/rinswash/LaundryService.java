package com.example.rinswash;

import java.io.*;
import java.util.*;

public class LaundryService {
    private final String fileName;

    public LaundryService() {
        this("laundry_data.txt");
    }

    public LaundryService(String fileName) {
        this.fileName = fileName;
    }

    public List<Laundry> loadData() {
        List<Laundry> list = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) return list;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!line.trim().isEmpty()) {
                    list.add(Laundry.fromString(line));
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal membaca data: " + e.getMessage());
        }

        return list;
    }

    public void saveData(List<Laundry> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Laundry l : list) {
                writer.println(l.toString());
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }
    }
}

