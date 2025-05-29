// LaporanService.java
import java.util.*;

public class LaporanService {

    public void tampilkanLaporan(List<Produk> produkList) {
        if (produkList.isEmpty()) {
            System.out.println("Tidak ada data.");
            return;
        }

        System.out.println("+----------------------+-------------------+-------------------+");
        System.out.println("| Rata-rata Berat (kg) |   Kategori Panen  |   Periode Panen   |");
        System.out.println("+----------------------+-------------------+-------------------+");

        int total = 0, count = 0, periodeCount = 0;

        for (int i = 0; i < produkList.size(); i++) {
            total += produkList.get(i).getBeratHasilPanen();
            count++;
            periodeCount++;

            if (periodeCount == 4) {
                cetakRataRata(total, periodeCount, hitungPeriode(count));
                total = 0;
                periodeCount = 0;
            }
        }

        if (periodeCount > 0) {
            cetakRataRata(total, periodeCount, hitungPeriode(count));
        }

        System.out.println("+----------------------+-------------------+-------------------+");
    }

    private void cetakRataRata(int total, int jumlah, String periode) {
        double rata = (double) total / jumlah;
        String kategori;
        if (rata > 1000) kategori = "Melimpah";
        else if (rata > 500) kategori = "Banyak";
        else kategori = "Sedikit";
        System.out.printf("|   %10.2f         |      %-7s     |      %-5s       |\n", rata, kategori, periode);
    }

    private String hitungPeriode(int count) {
        int p = (count - 1) / 4 + 1;
        return "Q" + p;
    }
}
