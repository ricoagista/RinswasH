import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LaundryService laundryService = new LaundryService();
    private static final LaporanService laporanService = new LaporanService();
    private static final List<Laundry> riwayatDiambil = new ArrayList<>(); // History of taken laundry

    public static void main(String[] args) {
        // Load history of taken laundry from the separate database
        riwayatDiambil.addAll(laundryService.bacaRiwayatDiambil());

        int pilihan;
        do {
            System.out.println("\n1. Tambah Transaksi\n2. Tampilkan Semua\n3. Ambil Laundry\n4. Hapus Transaksi\n5. Laporan\n6. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1 -> tambahTransaksi();
                case 2 -> tampilkanSemua();
                case 3 -> ambilLaundry();
                case 4 -> hapusTransaksi();
                case 5 -> laporanService.tampilkanLaporan(laundryService.bacaSemua(), riwayatDiambil);
                case 6 -> System.out.println("Keluar...");
                default -> System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 6);
    }

    private static void tambahTransaksi() {
        System.out.print("Nama Pelanggan: ");
        String nama = scanner.nextLine();
        System.out.print("Jenis Layanan (Cuci/Kering): ");
        String layanan = scanner.nextLine();
        System.out.print("Tanggal Masuk: ");
        String tMasuk = scanner.nextLine();
        System.out.print("Tanggal Selesai: ");
        String tSelesai = scanner.nextLine();
        System.out.print("Berat Cucian (kg): ");
        int berat = scanner.nextInt();

        Laundry l = new Laundry(nama, layanan, tMasuk, tSelesai, berat);
        laundryService.tambahTransaksi(l);
    }

    private static void tampilkanSemua() {
        List<Laundry> list = laundryService.bacaSemua();
        if (list.isEmpty()) {
            System.out.println("Tidak ada transaksi laundry.");
            return;
        }

        System.out.println("\n+================= DAFTAR TRANSAKSI LAUNDRY =================+");
        System.out.printf("| %-15s | %-12s | %-12s | %-12s | %-9s |\n",
                "Nama Pelanggan", "Jenis Layanan", "Tanggal Masuk", "Tanggal Selesai", "Berat");
        System.out.println("+-----------------+--------------+--------------+--------------+-----------+");

        for (Laundry l : list) {
            System.out.println(l.toFormattedString());
        }

        System.out.println("+--------------------------------------------------------------------------+");
    }

    private static void ambilLaundry() {
        System.out.print("Nama Pelanggan yang ingin mengambil laundry: ");
        String namaCari = scanner.nextLine().trim();

        List<Laundry> semuaTransaksi = laundryService.bacaSemua();
        boolean ditemukan = false;

        for (Laundry l : semuaTransaksi) {
            if (l.getNamaPelanggan().equalsIgnoreCase(namaCari)) {
                ditemukan = true;

                System.out.println("Detail Laundry:");
                System.out.println(l.toFormattedString());

                System.out.print("Apakah Anda yakin ingin mengambil laundry ini? (y/n): ");
                String konfirmasi = scanner.nextLine().trim();

                if (konfirmasi.equalsIgnoreCase("y")) {
                    if (laundryService.hapusTransaksi(l.getTanggalSelesai())) {
                        riwayatDiambil.add(l); // Add to history list
                        laundryService.simpanRiwayatDiambil(l); // Save to separate database
                        System.out.println("Laundry berhasil diambil.");
                    } else {
                        System.out.println("Gagal mengambil laundry.");
                    }
                } else {
                    System.out.println("Pengambilan laundry dibatalkan.");
                }
                break;
            }
        }

        if (!ditemukan) {
            System.out.println("Laundry dengan nama pelanggan \"" + namaCari + "\" tidak ditemukan.");
        }
    }

    private static void hapusTransaksi() {
        System.out.print("Tanggal Selesai yang ingin dihapus: ");
        String tSelesai = scanner.nextLine();
        if (laundryService.hapusTransaksi(tSelesai))
            System.out.println("Transaksi berhasil dihapus.");
        else
            System.out.println("Transaksi tidak ditemukan.");
    }
}