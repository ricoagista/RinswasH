import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Produk {
    String tanggalPanen;
    String jenisTanaman;
    String jenisBenih;
    String tanggalTanam;
    int beratHasilPanen;

    public Produk(String tanggalPanen, String jenisTanaman, String jenisBenih, String tanggalTanam, int beratHasilPanen) {
        this.tanggalPanen = tanggalPanen;
        this.jenisTanaman = jenisTanaman;
        this.jenisBenih = jenisBenih;
        this.tanggalTanam = tanggalTanam;
        this.beratHasilPanen = beratHasilPanen;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %d", tanggalPanen, jenisTanaman, jenisBenih, tanggalTanam, beratHasilPanen);
    }

    public String toFormattedString() {
        return String.format("| %-15s | %-15s | %-15s | %-15s | %-15d |",
                tanggalPanen, jenisTanaman, jenisBenih, tanggalTanam, beratHasilPanen);
    }
}

public class Main {
    private static final String FILENAME = "silupa_databases.txt"; //
    private static final int MAX_PRODUK = 100; //
    private static Scanner scanner = new Scanner(System.in);

    private static String tentukanPeriode(int count) { //
        int periodeNum = (count - 1) / 4 + 1; //
        switch (periodeNum) {
            case 1: return "Q1     "; //
            case 2: return "Q2     "; //
            case 3: return "Q3     "; //
            case 4: return "Q4     "; //
            default: return "N/A    "; //
        }
    }

    public static void tampilkanLaporan() { //
        List<Produk> produkList = bacaProdukDariFile();
        if (produkList.isEmpty()) {
            System.out.println("Tidak ada data produk untuk ditampilkan dalam laporan.");
            return;
        }

        System.out.println("+----------------------+-------------------+-------------------+ "); //
        System.out.println("| Rata-rata Berat (kg) |   Kategori Panen  |   Periode Panen   | "); //
        System.out.println("+----------------------+-------------------+-------------------+ "); //

        int totalBerat = 0; //
        int count = 0; //
        int periodeCount = 0; //
        double rataRata; //
        String kategori; //
        String periode; //

        for (Produk produk : produkList) {
            totalBerat += produk.beratHasilPanen; //
            count++; //
            periodeCount++; //

            if (periodeCount == 4) { //
                rataRata = (double) totalBerat / periodeCount; //
                if (rataRata > 1000) { //
                    kategori = "Melimpah"; //
                } else if (rataRata > 500) { //
                    kategori = "Banyak "; //
                } else {
                    kategori = "Sedikit"; //
                }
                periode = tentukanPeriode(count); //
                System.out.printf("|   %10.2f         |      %-5s      |      %-5s      |\n", rataRata, kategori, periode); //
                totalBerat = 0; //
                periodeCount = 0; //
            }
        }

        if (periodeCount > 0) { //
            rataRata = (double) totalBerat / periodeCount; //
            if (rataRata > 1000) { //
                kategori = "Melimpah"; //
            } else if (rataRata > 500) { //
                kategori = "Banyak  "; //
            } else {
                kategori = "Sedikit "; //
            }
            periode = tentukanPeriode(count); //
            System.out.printf("|   %10.2f         |      %-5s       |      %-5s      |\n", rataRata, kategori, periode); //
        }
        System.out.println("+----------------------+-------------------+-------------------+ "); //
    }


    public static void welcomeMessage() { //
        System.out.println("   _____ _     _                   _                     _                         _____                         _ _    _____ _ _           _____        _ _ "); //
        System.out.println("  / ____(_)   | |                 | |                   | |                       |  __ \\                       ( | )  / ____(_) |         |  __ \\      ( | )"); //
        System.out.println(" | (___  _ ___| |_ ___ _ __ ___   | |    _   _ _ __ ___ | |__  _   _ _ __   __ _  | |__) |_ _ _ __   ___ _ __    V V  | (___  _| |    _   _| |__) |_ _   V V "); //
        System.out.println("  \\___ \\| / __| __/ _ \\ '_ ` _ \\  | |   | | | | '_ ` _ \\| '_ \\| | | | '_ \\ / _` | |  ___/ _` | '_ \\ / _ \\ '_ \\         \\___ \\| | |   | | | |  ___/ _` |      "); //
        System.out.println("  ____) | \\__ \\ ||  __/ | | | | | | |___| |_| | | | | | | |_) | |_| | | | | (_| | | |  | (_| | | | |  __/ | | |        ____) | | |___| |_| | |  | (_| |      "); //
        System.out.println(" |_____/|_|___/\\__\\___|_| |_| |_| |______\\__,_|_| |_| |_|_.__/ \\__,_|_| |_|\\__, | |_|   \\__,_|_| |_|\\___|_| |_|       |_____/|_|______\\__,_|_|   \\__,_|      "); //
        System.out.println("                                                                            __/ |                                                                            "); //
        System.out.println("                                                                           |___/                                                                             "); //
    }

    public static void tambahProduk() { //
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME, true))) {
            System.out.println("\nMasukkan Tanggal tanam (DD-MM-YYYY): "); //
            String tanggalTanam = scanner.next();
            System.out.println("Masukkan Jenis Tanaman: "); //
            String jenisTanaman = scanner.next();
            System.out.println("Masukkan Jenis Benih: "); //
            String jenisBenih = scanner.next();
            System.out.println("Masukkan Tanggal Panen (DD-MM-YYYY): "); //
            String tanggalPanen = scanner.next();
            System.out.println("Masukkan Berat Hasil Panen (kg): "); //
            int beratHasilPanen = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Produk produk = new Produk(tanggalPanen, jenisTanaman, jenisBenih, tanggalTanam, beratHasilPanen);
            writer.println(produk.toString()); //
            System.out.println("\nPRODUK BERHASIL DITAMBAHKAN."); //

        } catch (IOException e) {
            System.out.println("Error: Tidak dapat membuka atau menulis ke file. " + e.getMessage()); //
        }
    }

    private static List<Produk> bacaProdukDariFile() {
        List<Produk> produkList = new ArrayList<>();
        File file = new File(FILENAME);
        if (!file.exists()) {
            // System.out.println("Info: File database tidak ditemukan, akan dibuat baru jika ada penambahan data.");
            return produkList; // Return empty list, file might be created on first add
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(" ");
                if (data.length == 5) {
                    try {
                        produkList.add(new Produk(data[0], data[1], data[2], data[3], Integer.parseInt(data[4])));
                    } catch (NumberFormatException e) {
                        System.out.println("Warning: Skipping malformed line in file: " + line);
                    }
                } else {
                    System.out.println("Warning: Skipping improperly formatted line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            // This case should ideally be handled by the file.exists() check,
            // but kept here for robustness.
            System.out.println("Error: Tidak dapat membuka file untuk dibaca. " + e.getMessage()); //
        }
        return produkList;
    }

    private static void tulisProdukKeFile(List<Produk> produkList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME, false))) { // false to overwrite
            for (Produk p : produkList) {
                writer.println(p.toString());
            }
        } catch (IOException e) {
            System.out.println("Error: Tidak dapat menulis ke file. " + e.getMessage());
        }
    }

    public static void tampilkanDaftarProduk() { //
        List<Produk> produkList = bacaProdukDariFile();
        if (produkList.isEmpty()) {
            System.out.println("Error: Tidak dapat membuka file atau tidak ada data produk."); //
            return;
        }

        System.out.println("Isi Komoditas:"); //
        System.out.println("+-----------------+-----------------+-----------------+-----------------+-----------------+"); //
        System.out.println("| Tanggal Panen   | Jenis Tanaman   | Jenis Benih     | Tanggal Tanam   | Berat (kg)      |"); //
        System.out.println("+-----------------+-----------------+-----------------+-----------------+-----------------+"); //
        for (Produk produk : produkList) {
            System.out.println(produk.toFormattedString()); //
        }
        System.out.println("+-----------------+-----------------+-----------------+-----------------+-----------------+"); //
    }

    public static void updateInformasiProduk() { //
        List<Produk> produkList = bacaProdukDariFile();
        if (produkList.isEmpty()) {
            System.out.println("Error: Tidak ada data produk untuk diupdate.");
            return;
        }

        System.out.println("Masukkan Tanggal Panen yang ingin diupdate (DD-MM-YYYY): "); //
        String tanggalPanenToUpdate = scanner.next();
        scanner.nextLine(); // Consume newline

        boolean found = false; //
        for (int i = 0; i < produkList.size(); i++) {
            Produk produk = produkList.get(i);
            if (produk.tanggalPanen.equals(tanggalPanenToUpdate)) { //
                found = true; //
                System.out.println("Produk ditemukan. Masukkan data baru:");
                System.out.println("Masukkan Jenis Tanaman Baru: "); //
                produk.jenisTanaman = scanner.next();
                System.out.println("Masukkan Jenis Benih Baru: "); //
                produk.jenisBenih = scanner.next();
                System.out.println("Masukkan Tanggal Tanam Baru (DD-MM-YYYY): "); //
                produk.tanggalTanam = scanner.next();
                System.out.println("Masukkan Berat Hasil Panen Baru (kg): "); //
                produk.beratHasilPanen = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                produkList.set(i, produk); // Update the list
                break;
            }
        }

        if (!found) { //
            System.out.println("Produk dengan Tanggal Panen " + tanggalPanenToUpdate + " tidak ditemukan."); //
        } else {
            tulisProdukKeFile(produkList);
            System.out.println("Informasi produk berhasil diupdate."); //
        }
    }

    public static void hapusProduk() { //
        List<Produk> produkList = bacaProdukDariFile();
        if (produkList.isEmpty()) {
            System.out.println("Error: Tidak ada data produk untuk dihapus.");
            return;
        }
        System.out.println("Masukkan Tanggal Panen yang ingin dihapus (DD-MM-YYYY): "); //
        String tanggalPanenToDelete = scanner.next();
        scanner.nextLine(); // Consume newline

        List<Produk> updatedProdukList = new ArrayList<>();
        boolean found = false; //
        for (Produk produk : produkList) {
            if (produk.tanggalPanen.equals(tanggalPanenToDelete)) { //
                found = true; //
            } else {
                updatedProdukList.add(produk);
            }
        }

        if (!found) { //
            System.out.println("Produk dengan Tanggal Panen " + tanggalPanenToDelete + " tidak ditemukan."); //
        } else {
            tulisProdukKeFile(updatedProdukList);
            System.out.println("Produk dengan Tanggal Panen " + tanggalPanenToDelete + " berhasil dihapus."); //
        }
    }


    public static void main(String[] args) { //
        int pilihan;
        welcomeMessage(); //

        do {
            System.out.println("\nPilih Program:"); //
            System.out.println("\n1. Masukkan data produk"); //
            System.out.println("2. Tampilkan Komoditas"); //
            System.out.println("3. Update data produk"); //
            System.out.println("4. Hapus data produk"); //
            System.out.println("5. Tampilkan Hasil Laporan"); //
            System.out.println("6. Keluar"); //
            System.out.print("\nMasukkan pilihan: "); //

            while (!scanner.hasNextInt()) {
                System.out.println("Pilihan tidak valid. Masukkan angka.");
                System.out.print("\nMasukkan pilihan: ");
                scanner.next(); // discard non-integer input
            }
            pilihan = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (pilihan) { //
                case 1:
                    tambahProduk(); //
                    break;
                case 2:
                    tampilkanDaftarProduk(); //
                    break;
                case 3:
                    updateInformasiProduk(); //
                    break;
                case 4:
                    hapusProduk(); //
                    break;
                case 5:
                    tampilkanLaporan(); //
                    break;
                case 6:
                    System.out.println("Terima kasih telah menggunakan Sistem Lumbung Padi (SiLuPa)."); //
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi."); //
            }
        } while (pilihan != 6); //

        scanner.close();
    }
}