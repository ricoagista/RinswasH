package com.example.rinswash2;


import java.io.*;
import java.util.*;

public class LaundryService {
    private static final String FILE_NAME = "laundry_data.txt";

    public List<Laundry> loadData() {
        List<Laundry> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return list;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                list.add(Laundry.fromString(sc.nextLine()));
            }
        } catch (Exception e) {
            System.out.println("Gagal membaca data: " + e.getMessage());
        }

        return list;
    }

    public void saveData(List<Laundry> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Laundry l : list) {
                writer.println(l.toString());
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }
    }
}
