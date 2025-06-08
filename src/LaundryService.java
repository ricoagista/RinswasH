import java.io.*;
import java.util.*;

public class LaundryService {
    private static final String DATABASE_FILE = "laundry_database.txt";
    private static final String TAKEN_DATABASE_FILE = "laundry_taken_database.txt";

    public List<Laundry> bacaSemua() {
        List<Laundry> semuaTransaksi = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATABASE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length == 5) {
                    Laundry laundry = new Laundry(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]));
                    semuaTransaksi.add(laundry);
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca database: " + e.getMessage());
        }
        return semuaTransaksi;
    }

    public List<Laundry> bacaRiwayatDiambil() {
        List<Laundry> riwayatDiambil = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TAKEN_DATABASE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length == 5) {
                    Laundry laundry = new Laundry(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]));
                    riwayatDiambil.add(laundry);
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca riwayat laundry yang diambil: " + e.getMessage());
        }
        return riwayatDiambil;
    }

    public void tambahTransaksi(Laundry laundry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILE, true))) {
            writer.write(laundry.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Gagal menambahkan transaksi: " + e.getMessage());
        }
    }

    public boolean hapusTransaksi(String tanggalSelesai) {
        List<Laundry> semuaTransaksi = bacaSemua();
        boolean ditemukan = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILE))) {
            for (Laundry laundry : semuaTransaksi) {
                if (laundry.getTanggalSelesai().equals(tanggalSelesai)) {
                    ditemukan = true;
                } else {
                    writer.write(laundry.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal menghapus transaksi: " + e.getMessage());
        }

        return ditemukan;
    }

    public void simpanRiwayatDiambil(Laundry laundry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TAKEN_DATABASE_FILE, true))) {
            writer.write(laundry.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Gagal menyimpan riwayat laundry yang diambil: " + e.getMessage());
        }
    }
}