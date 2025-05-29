// Produk.java
public class Produk {
    protected String tanggalPanen;
    protected String jenisTanaman;
    protected String jenisBenih;
    protected String tanggalTanam;
    protected int beratHasilPanen;

    public Produk(String tanggalPanen, String jenisTanaman, String jenisBenih, String tanggalTanam, int beratHasilPanen) {
        this.tanggalPanen = tanggalPanen;
        this.jenisTanaman = jenisTanaman;
        this.jenisBenih = jenisBenih;
        this.tanggalTanam = tanggalTanam;
        this.beratHasilPanen = beratHasilPanen;
    }
    public String getTanggalPanen() {
        return tanggalPanen;
    }

    public int getBeratHasilPanen() {
        return beratHasilPanen;
    }


    // Getter & Setter (optional bisa pakai Lombok)

    @Override
    public String toString() {
        return String.format("%s %s %s %s %d", tanggalPanen, jenisTanaman, jenisBenih, tanggalTanam, beratHasilPanen);
    }

    public String toFormattedString() {
        return String.format("| %-15s | %-15s | %-15s | %-15s | %-15d |",
                tanggalPanen, jenisTanaman, jenisBenih, tanggalTanam, beratHasilPanen);
    }

    // Getter & Setter untuk akses privat
}
