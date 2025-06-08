import java.util.List;

public class LaporanService {
    public void tampilkanLaporan(List<Laundry> semuaTransaksi, List<Laundry> riwayatDiambil) {
        System.out.println("\n+================= LAPORAN LAUNDRY =================+");

        // Transaksi Aktif
        System.out.println("\n--- Transaksi Aktif ---");
        if (semuaTransaksi.isEmpty()) {
            System.out.println("Tidak ada transaksi aktif.");
        } else {
            int totalBerat = semuaTransaksi.stream().mapToInt(Laundry::getBerat).sum();
            System.out.println("Jumlah Transaksi Aktif: " + semuaTransaksi.size());
            System.out.println("Total Berat Cucian: " + totalBerat + " kg");
        }

        // Riwayat Laundry Diambil
        System.out.println("\n--- Riwayat Laundry Diambil ---");
        if (riwayatDiambil.isEmpty()) {
            System.out.println("Tidak ada riwayat laundry yang diambil.");
        } else {
            System.out.printf("| %-15s | %-12s | %-12s | %-12s | %-9s |\n",
                    "Nama Pelanggan", "Jenis Layanan", "Tanggal Masuk", "Tanggal Selesai", "Berat");
            System.out.println("+-----------------+--------------+--------------+--------------+-----------+");
            for (Laundry l : riwayatDiambil) {
                System.out.println(l.toFormattedString());
            }
        }
        System.out.println("+--------------------------------------------------+");
    }
}