// Main.java
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ProdukService produkService = new ProdukService();
    private static final LaporanService laporanService = new LaporanService();

    public static void main(String[] args) {
        int pilihan;
        do {
            System.out.println("\n1. Tambah Produk\n2. Tampilkan Produk\n3. Update Produk\n4. Hapus Produk\n5. Laporan\n6. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1 -> tambahProduk();
                case 2 -> tampilkanProduk();
                case 3 -> updateProduk();
                case 4 -> hapusProduk();
                case 5 -> laporanService.tampilkanLaporan(produkService.bacaSemuaProduk());
                case 6 -> System.out.println("Keluar...");
                default -> System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 6);
    }

    private static void tambahProduk() {
        System.out.print("Tanggal Tanam: "); String tTanam = scanner.next();
        System.out.print("Jenis Tanaman: "); String jTanaman = scanner.next();
        System.out.print("Jenis Benih: "); String jBenih = scanner.next();
        System.out.print("Tanggal Panen: "); String tPanen = scanner.next();
        System.out.print("Berat Panen (kg): "); int berat = scanner.nextInt();
        Produk p = new Produk(tPanen, jTanaman, jBenih, tTanam, berat);
        produkService.tambahProduk(p);
    }

    private static void tampilkanProduk() {
        List<Produk> list = produkService.bacaSemuaProduk();
        for (Produk p : list) {
            System.out.println(p.toFormattedString());
        }
    }

    private static void updateProduk() {
        System.out.print("Tanggal Panen untuk update: "); String tPanen = scanner.next();
        System.out.println("Masukkan data baru:");
        System.out.print("Tanggal Tanam: "); String tTanam = scanner.next();
        System.out.print("Jenis Tanaman: "); String jTanaman = scanner.next();
        System.out.print("Jenis Benih: "); String jBenih = scanner.next();
        System.out.print("Berat Panen (kg): "); int berat = scanner.nextInt();
        Produk pBaru = new Produk(tPanen, jTanaman, jBenih, tTanam, berat);
        if (produkService.updateProduk(tPanen, pBaru))
            System.out.println("Produk berhasil diupdate.");
        else
            System.out.println("Produk tidak ditemukan.");
    }

    private static void hapusProduk() {
        System.out.print("Tanggal Panen yang dihapus: "); String tPanen = scanner.next();
        if (produkService.hapusProduk(tPanen))
            System.out.println("Produk berhasil dihapus.");
        else
            System.out.println("Produk tidak ditemukan.");
    }
}
