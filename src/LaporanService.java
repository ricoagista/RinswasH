import java.util.*;

public class LaporanService {

    public void tampilkanLaporan(List<Laundry> list) {
        if (list.isEmpty()) {
            System.out.println("Tidak ada transaksi.");
            return;
        }

        int totalBerat = 0;
        for (Laundry l : list) {
            totalBerat += l.getBerat();
        }

        System.out.println("+----------------------------+");
        System.out.println("|     LAPORAN LAUNDRY       |");
        System.out.println("+----------------------------+");
        System.out.println("Total Transaksi : " + list.size());
        System.out.println("Total Berat     : " + totalBerat + " kg");
        System.out.println("+----------------------------+");
    }
}
