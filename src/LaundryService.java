import java.io.*;
import java.util.*;

public class LaundryService {
    private static final String FILENAME = "laundry_database.txt";

    public void tambahTransaksi(Laundry laundry) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME, true))) {
            writer.println(laundry.toString());
        } catch (IOException e) {
            System.out.println("Gagal menyimpan transaksi: " + e.getMessage());
        }
    }

    public List<Laundry> bacaSemua() {
        List<Laundry> list = new ArrayList<>();
        File file = new File(FILENAME);
        if (!file.exists()) return list;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split("\\|");
                if (data.length == 5) {
                    list.add(new Laundry(data[0], data[1], data[2], data[3], Integer.parseInt(data[4])));
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal membaca file: " + e.getMessage());
        }

        return list;
    }

    public void tulisSemua(List<Laundry> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (Laundry l : list) {
                writer.println(l.toString());
            }
        } catch (IOException e) {
            System.out.println("Gagal menulis ulang file: " + e.getMessage());
        }
    }

    public boolean updateTransaksiByNama(String nama, Laundry transaksiBaru) {
        List<Laundry> list = bacaSemua();
        boolean updated = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNamaPelanggan().equalsIgnoreCase(nama)) {
                list.set(i, transaksiBaru);
                updated = true;
                break;
            }
        }

        if (updated) tulisSemua(list);
        return updated;
    }


    public boolean hapusTransaksi(String tanggalSelesai) {
        List<Laundry> list = bacaSemua();
        boolean removed = list.removeIf(l -> l.getTanggalSelesai().equals(tanggalSelesai));
        if (removed) tulisSemua(list);
        return removed;
    }
}
