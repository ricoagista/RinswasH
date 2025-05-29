// ProdukService.java
import java.io.*;
import java.util.*;

public class ProdukService {
    private static final String FILENAME = "silupa_databases.txt";

    public void tambahProduk(Produk produk) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME, true))) {
            writer.println(produk.toString());
        } catch (IOException e) {
            System.out.println("Gagal menambah produk: " + e.getMessage());
        }
    }

    public List<Produk> bacaSemuaProduk() {
        List<Produk> produkList = new ArrayList<>();
        File file = new File(FILENAME);

        if (!file.exists()) return produkList;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(" ");
                if (data.length == 5) {
                    produkList.add(new Produk(data[0], data[1], data[2], data[3], Integer.parseInt(data[4])));
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal membaca file: " + e.getMessage());
        }

        return produkList;
    }

    public void tulisSemuaProduk(List<Produk> produkList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME, false))) {
            for (Produk p : produkList) {
                writer.println(p.toString());
            }
        } catch (IOException e) {
            System.out.println("Gagal menulis file: " + e.getMessage());
        }
    }

    public boolean updateProduk(String tanggalPanen, Produk produkBaru) {
        List<Produk> list = bacaSemuaProduk();
        boolean updated = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTanggalPanen().equals(tanggalPanen)) {
                list.set(i, produkBaru);
                updated = true;
                break;
            }
        }

        if (updated) tulisSemuaProduk(list);
        return updated;
    }

    public boolean hapusProduk(String tanggalPanen) {
        List<Produk> list = bacaSemuaProduk();
        boolean removed = list.removeIf(p -> p.getTanggalPanen().equals(tanggalPanen));
        if (removed) tulisSemuaProduk(list);
        return removed;
    }
}
