public class Laundry {
    private String namaPelanggan;
    private String jenisLayanan;
    private String tanggalMasuk;
    private String tanggalSelesai;
    private int berat;

    public Laundry(String namaPelanggan, String jenisLayanan, String tanggalMasuk, String tanggalSelesai, int berat) {
        this.namaPelanggan = namaPelanggan;
        this.jenisLayanan = jenisLayanan;
        this.tanggalMasuk = tanggalMasuk;
        this.tanggalSelesai = tanggalSelesai;
        this.berat = berat;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public int getBerat() {
        return berat;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%d", namaPelanggan, jenisLayanan, tanggalMasuk, tanggalSelesai, berat);
    }

    public String toFormattedString() {
        return String.format("| %-15s | %-12s | %-12s | %-12s | %-4d kg |",
                namaPelanggan, jenisLayanan, tanggalMasuk, tanggalSelesai, berat);
    }
}
