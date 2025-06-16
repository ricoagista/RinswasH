package com.example.rinswash;
import javafx.fxml.FXML;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


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
    }

}
