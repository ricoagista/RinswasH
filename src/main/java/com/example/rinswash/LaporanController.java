package com.example.rinswash;
import javafx.fxml.FXML;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

// Tambahan import
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// iText PDF
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class LaporanController {
    // LaporanController.java
    @FXML private TableView<Laundry> table;
    @FXML private TableColumn<Laundry, String> colNama;
    @FXML private TableColumn<Laundry, String> colLayanan;
    @FXML private TableColumn<Laundry, String> colMasuk;
    @FXML private TableColumn<Laundry, String> colSelesai;
    @FXML private TableColumn<Laundry, Integer> colBerat;
    @FXML private TableColumn<Laundry, Integer> colBiaya;
    @FXML private Label judulLaporan;
    @FXML private Label lblTotalTransaksi;
    @FXML private Label lblTotalPendapatan;
    @FXML private Button btnPrintNota; // pastikan ada di FXML


    private List<Laundry> currentData;

    public void setData(List<Laundry> data) {
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colLayanan.setCellValueFactory(new PropertyValueFactory<>("layanan"));
        colMasuk.setCellValueFactory(new PropertyValueFactory<>("tMasuk"));
        colSelesai.setCellValueFactory(new PropertyValueFactory<>("tSelesai"));
        colBerat.setCellValueFactory(new PropertyValueFactory<>("berat"));
        colBiaya.setCellValueFactory(new PropertyValueFactory<>("biaya"));
        table.setItems(FXCollections.observableArrayList(data));

        lblTotalTransaksi.setText("Total Transaksi: " + data.size());

        int totalPendapatan = data.stream().mapToInt(Laundry ::getBiaya).sum();
        lblTotalPendapatan.setText("Total Pendapatan: Rp " + totalPendapatan);
        this.currentData = data;
    }

    @FXML
    private void printNota() {
        Laundry selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Pilih transaksi yang ingin dicetak notanya!");
            return;
        }
        try {
            // Buat folder nota_laporan jika belum ada
            File folder = new File("nota_laporan");
            if (!folder.exists()) folder.mkdirs();

            String fileName = "Nota_" + selected.getNama().replaceAll("\\s+", "_") + "_" + selected.getTMasuk() + ".pdf";
            File file = new File(folder, fileName);

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            document.add(new Paragraph("RinswasH - Nota Laundry", titleFont));
            document.add(new Paragraph("Tanggal Cetak: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), normalFont));
            document.add(new Paragraph(" "));

            PdfPTable tablePdf = new PdfPTable(2);
            tablePdf.setWidthPercentage(80);

            tablePdf.addCell("Nama");
            tablePdf.addCell(selected.getNama());
            tablePdf.addCell("Layanan");
            tablePdf.addCell(selected.getLayanan());
            tablePdf.addCell("Tanggal Masuk");
            tablePdf.addCell(selected.getTMasuk());
            tablePdf.addCell("Tanggal Selesai");
            tablePdf.addCell(selected.getTSelesai());
            tablePdf.addCell("Berat (kg)");
            tablePdf.addCell(String.valueOf(selected.getBerat()));
            tablePdf.addCell("Biaya");
            tablePdf.addCell("Rp " + selected.getBiaya());

            document.add(tablePdf);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Terima kasih telah menggunakan layanan RinswasH!", normalFont));
            document.close();

            showInfo("Nota berhasil disimpan di folder 'nota_laporan' dengan nama:\n" + fileName);
        } catch (Exception e) {
            showError("Gagal mencetak nota: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText("Terjadi Kesalahan");
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.setTitle("Info");
        alert.setHeaderText("Sukses");
        alert.showAndWait();
    }
}
