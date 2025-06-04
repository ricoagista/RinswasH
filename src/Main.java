import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LaundryService laundryService = new LaundryService();
    private static final LaporanService laporanService = new LaporanService();

    public static void main(String[] args) {
        int pilihan;
        do {
            System.out.println("\n1. Tambah Transaksi\n2. Tampilkan Semua\n3. Update Transaksi\n4. Hapus Transaksi\n5. Laporan\n6. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1 -> tambahTransaksi();
                case 2 -> tampilkanSemua();
                case 3 -> updateTransaksi();
                case 4 -> hapusTransaksi();
                case 5 -> laporanService.tampilkanLaporan(laundryService.bacaSemua());
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


    private static void updateTransaksi() {
        System.out.print("Tanggal Masuk untuk update: ");
        String tMasuk = scanner.nextLine();
        System.out.println("Masukkan data baru:");
        System.out.print("Nama Pelanggan: ");
        String nama = scanner.nextLine();
        System.out.print("Jenis Layanan: ");
        String layanan = scanner.nextLine();
        System.out.print("Tanggal Selesai: ");
        String tSelesai = scanner.nextLine();
        System.out.print("Berat Cucian (kg): ");
        int berat = scanner.nextInt();

        Laundry baru = new Laundry(nama, layanan, tMasuk, tSelesai, berat);
        if (laundryService.updateTransaksi(tMasuk, baru))
            System.out.println("Transaksi berhasil diupdate.");
        else
            System.out.println("Transaksi tidak ditemukan.");
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
