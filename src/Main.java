import java.io.*;
import java.util.*;

class Produk {
    String tanggalPanen;
    String jenisTanaman;
    String jenisBenih;
    String tanggalTanam;
    int beratHasilPanen;
}

public class Main {
    private static final String FILENAME = "silupa_databases.txt";
    private static final int MAX_PRODUK = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        welcomeMessage();
        int pilihan;
        do {
            System.out.println("\nPilih Program:");
            System.out.println("1. Masukkan data produk");
            System.out.println("2. Tampilkan Komoditas");
            System.out.println("3. Update data produk");
            System.out.println("4. Hapus data produk");
            System.out.println("5. Tampilkan Hasil Laporan");
            System.out.println("6. Keluar");
            System.out.print("Masukkan pilihan: ");
            pilihan = scanner.nextInt();
            scanner.nextLine(); // buffer newline

            switch (pilihan) {
                case 1 -> tambahProduk(scanner);
                case 2 -> tampilkanDaftarProduk();
                case 3 -> updateInformasiProduk(scanner);
                case 4 -> hapusProduk(scanner);
                case 5 -> tampilkanLaporan();
                case 6 -> System.out.println("Terima kasih telah menggunakan Sistem Lumbung Padi (SiLuPa).");
                default -> System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (pilihan != 6);
    }

    private static void welcomeMessage() {
        System.out.println("======= SELAMAT DATANG DI SISTEM LUMBUNG PADI (SiLuPa) =======");
    }

    private static void tambahProduk(Scanner scanner) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            Produk produk = new Produk();
            System.out.print("Masukkan Tanggal Tanam (DD-MM-YYYY): ");
            produk.tanggalTanam = scanner.nextLine();
            System.out.print("Masukkan Jenis Tanaman: ");
            produk.jenisTanaman = scanner.nextLine();
            System.out.print("Masukkan Jenis Benih: ");
            produk.jenisBenih = scanner.nextLine();
            System.out.print("Masukkan Tanggal Panen (DD-MM-YYYY): ");
            produk.tanggalPanen = scanner.nextLine();
            System.out.print("Masukkan Berat Hasil Panen (kg): ");
            produk.beratHasilPanen = scanner.nextInt();
            scanner.nextLine(); // flush newline
            writer.write(String.format("%s %s %s %s %d%n", produk.tanggalPanen, produk.jenisTanaman,
                    produk.jenisBenih, produk.tanggalTanam, produk.beratHasilPanen));
            System.out.println("\nPRODUK BERHASIL DITAMBAHKAN.");
        } catch (IOException e) {
            System.out.println("Error: Tidak dapat membuka file.");
        }
    }

    private static void tampilkanDaftarProduk() {
        try (Scanner fileScanner = new Scanner(new File(FILENAME))) {
            System.out.println("+-----------------+-----------------+-----------------+-----------------+-----------------+");
            System.out.println("| Tanggal Panen   | Jenis Tanaman   | Jenis Benih     | Tanggal Tanam   | Berat (kg)      |");
            System.out.println("+-----------------+-----------------+-----------------+-----------------+-----------------+");

            while (fileScanner.hasNext()) {
                Produk p = new Produk();
                p.tanggalPanen = fileScanner.next();
                p.jenisTanaman = fileScanner.next();
                p.jenisBenih = fileScanner.next();
                p.tanggalTanam = fileScanner.next();
                p.beratHasilPanen = fileScanner.nextInt();
                System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15d |\n",
                        p.tanggalPanen, p.jenisTanaman, p.jenisBenih, p.tanggalTanam, p.beratHasilPanen);
            }

            System.out.println("+-----------------+-----------------+-----------------+-----------------+-----------------+");
        } catch (IOException e) {
            System.out.println("Error: Tidak dapat membuka file.");
        }
    }

    private static void updateInformasiProduk(Scanner scanner) {
        List<Produk> produkList = new ArrayList<>();
        boolean found = false;

        System.out.print("Masukkan Tanggal Panen yang ingin diupdate (YYYY-MM-DD): ");
        String targetTanggal = scanner.nextLine();

        try (Scanner fileScanner = new Scanner(new File(FILENAME))) {
            while (fileScanner.hasNext()) {
                Produk p = new Produk();
                p.tanggalPanen = fileScanner.next();
                p.jenisTanaman = fileScanner.next();
                p.jenisBenih = fileScanner.next();
                p.tanggalTanam = fileScanner.next();
                p.beratHasilPanen = fileScanner.nextInt();

                if (p.tanggalPanen.equals(targetTanggal)) {
                    found = true;
                    System.out.print("Masukkan Jenis Tanaman Baru: ");
                    p.jenisTanaman = scanner.nextLine();
                    System.out.print("Masukkan Jenis Benih Baru: ");
                    p.jenisBenih = scanner.nextLine();
                    System.out.print("Masukkan Tanggal Tanam Baru: ");
                    p.tanggalTanam = scanner.nextLine();
                    System.out.print("Masukkan Berat Hasil Panen Baru (kg): ");
                    p.beratHasilPanen = scanner.nextInt();
                    scanner.nextLine(); // flush
                }

                produkList.add(p);
            }
        } catch (IOException e) {
            System.out.println("Error: Tidak dapat membaca file.");
            return;
        }

        if (!found) {
            System.out.println("Produk dengan Tanggal Panen " + targetTanggal + " tidak ditemukan.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Produk p : produkList) {
                writer.write(String.format("%s %s %s %s %d%n", p.tanggalPanen, p.jenisTanaman,
                        p.jenisBenih, p.tanggalTanam, p.beratHasilPanen));
            }
            System.out.println("Informasi produk berhasil diupdate.");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data.");
        }
    }

    private static void hapusProduk(Scanner scanner) {
        List<Produk> produkList = new ArrayList<>();
        boolean found = false;

        System.out.print("Masukkan Tanggal Panen yang ingin dihapus (YYYY-MM-DD): ");
        String targetTanggal = scanner.nextLine();

        try (Scanner fileScanner = new Scanner(new File(FILENAME))) {
            while (fileScanner.hasNext()) {
                Produk p = new Produk();
                p.tanggalPanen = fileScanner.next();
                p.jenisTanaman = fileScanner.next();
                p.jenisBenih = fileScanner.next();
                p.tanggalTanam = fileScanner.next();
                p.beratHasilPanen = fileScanner.nextInt();

                if (!p.tanggalPanen.equals(targetTanggal)) {
                    produkList.add(p);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Tidak dapat membaca file.");
            return;
        }

        if (!found) {
            System.out.println("Produk dengan Tanggal Panen " + targetTanggal + " tidak ditemukan.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Produk p : produkList) {
                writer.write(String.format("%s %s %s %s %d%n", p.tanggalPanen, p.jenisTanaman,
                        p.jenisBenih, p.tanggalTanam, p.beratHasilPanen));
            }
            System.out.println("Produk dengan Tanggal Panen " + targetTanggal + " berhasil dihapus.");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data.");
        }
    }

    private static void tampilkanLaporan() {
        try (Scanner fileScanner = new Scanner(new File(FILENAME))) {
            int totalBerat = 0;
            int count = 0;
            int periodeCount = 0;
            System.out.println("+----------------------+-------------------+-------------------+");
            System.out.println("| Rata-rata Berat (kg) |   Kategori Panen  |   Periode Panen   |");
            System.out.println("+----------------------+-------------------+-------------------+");

            while (fileScanner.hasNext()) {
                Produk p = new Produk();
                p.tanggalPanen = fileScanner.next();
                p.jenisTanaman = fileScanner.next();
                p.jenisBenih = fileScanner.next();
                p.tanggalTanam = fileScanner.next();
                p.beratHasilPanen = fileScanner.nextInt();

                totalBerat += p.beratHasilPanen;
                count++;
                periodeCount++;

                if (periodeCount == 4) {
                    cetakLaporan(totalBerat, periodeCount, count);
                    totalBerat = 0;
                    periodeCount = 0;
                }
            }

            if (periodeCount > 0) {
                cetakLaporan(totalBerat, periodeCount, count);
            }

            System.out.println("+----------------------+-------------------+-------------------+");
        } catch (IOException e) {
            System.out.println("Error: Tidak dapat membuka file.");
        }
    }

    private static void cetakLaporan(int totalBerat, int periodeCount, int count) {
        double rataRata = (double) totalBerat / periodeCount;
        String kategori = (rataRata > 1000) ? "Melimpah" : (rataRata > 500) ? "Banyak" : "Sedikit";
        String periode = tentukanPeriode(count);
        System.out.printf("|   %10.2f         |      %-7s     |      %-5s        |\n", rataRata, kategori, periode);
    }

    private static String tentukanPeriode(int count) {
        int periode = (count - 1) / 4 + 1;
        return switch (periode) {
            case 1 -> "Q1";
            case 2 -> "Q2";
            case 3 -> "Q3";
            case 4 -> "Q4";
            default -> "N/A";
        };
    }
}
