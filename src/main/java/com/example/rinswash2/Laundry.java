package com.example.rinswash2;


import javafx.beans.property.*;

public class Laundry {
    private final StringProperty nama;
    private final StringProperty layanan;
    private final StringProperty tMasuk;
    private final StringProperty tSelesai;
    private final IntegerProperty berat;

    public Laundry(String nama, String layanan, String tMasuk, String tSelesai, int berat) {
        this.nama = new SimpleStringProperty(nama);
        this.layanan = new SimpleStringProperty(layanan);
        this.tMasuk = new SimpleStringProperty(tMasuk);
        this.tSelesai = new SimpleStringProperty(tSelesai);
        this.berat = new SimpleIntegerProperty(berat);
    }

    public String getNama() { return nama.get(); }
    public String getLayanan() { return layanan.get(); }
    public String getTMasuk() { return tMasuk.get(); }
    public String getTSelesai() { return tSelesai.get(); }
    public int getBerat() { return berat.get(); }

    public void setNama(String value) { nama.set(value); }
    public void setLayanan(String value) { layanan.set(value); }
    public void setTMasuk(String value) { tMasuk.set(value); }
    public void setTSelesai(String value) { tSelesai.set(value); }
    public void setBerat(int value) { berat.set(value); }

    public StringProperty namaProperty() { return nama; }
    public StringProperty layananProperty() { return layanan; }
    public StringProperty tMasukProperty() { return tMasuk; }
    public StringProperty tSelesaiProperty() { return tSelesai; }
    public IntegerProperty beratProperty() { return berat; }

    @Override
    public String toString() {
        return getNama() + "|" + getLayanan() + "|" + getTMasuk() + "|" + getTSelesai() + "|" + getBerat();
    }

    public static Laundry fromString(String line) {
        String[] parts = line.split("\\|");
        return new Laundry(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]));
    }
}
