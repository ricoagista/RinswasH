package com.example.rinswash;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LaundryController {

    @FXML private TableView<Laundry> table;
    @FXML private TableColumn<Laundry, String> colNama;
    @FXML private TableColumn<Laundry, String> colLayanan;
    @FXML private TableColumn<Laundry, String> colMasuk;
    @FXML private TableColumn<Laundry, String> colSelesai;
    @FXML private TableColumn<Laundry, Integer> colBerat;
    @FXML private TableColumn<Laundry, Integer> colBiaya;
    @FXML private TextField tfNama;
    @FXML private ComboBox<String> cbLayanan;
    @FXML private Label lTotalPendapatan;
    @FXML private DatePicker dpTMasuk;
    @FXML private DatePicker dpTSelesai;
    @FXML private TextField tfBerat;
    @FXML private Label lblTotalTransaksi;
    @FXML private Label lblTotalBerat;
    @FXML private Label lblJudulEstimasi;
    @FXML private Label lblEstimasiPendapatan;

    private final ObservableList<Laundry> data = FXCollections.observableArrayList();
    private final LaundryService service = new LaundryService();
    private final LaundryService diambilService = new LaundryService("laundry_diambil.txt");

    @FXML
    public void initialize() {
        // Inisialisasi data dan komponen UI
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colLayanan.setCellValueFactory(new PropertyValueFactory<>("layanan"));
        colMasuk.setCellValueFactory(new PropertyValueFactory<>("tMasuk"));
        colSelesai.setCellValueFactory(new PropertyValueFactory<>("tSelesai"));
        colBerat.setCellValueFactory(new PropertyValueFactory<>("berat"));
        colBiaya.setCellValueFactory(new PropertyValueFactory<>("biaya"));
        data.addAll(service.loadData());
        table.setItems(data);

        cbLayanan.getItems().addAll("REGULAR", "EXPRESS");
        dpTMasuk.setValue(LocalDate.now());
        dpTSelesai.setValue(LocalDate.now().plusDays(2));

        // Listener untuk pilihan tabel
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                tfNama.setText(newSel.getNama());
                cbLayanan.setValue(newSel.getLayanan());
                dpTMasuk.setValue(LocalDate.parse(newSel.getTMasuk()));
                dpTSelesai.setValue(LocalDate.parse(newSel.getTSelesai()));
                tfBerat.setText(String.valueOf(newSel.getBerat()));
            }
        });

        // Listener untuk data list
        data.addListener((ListChangeListener<Laundry>) c -> updateLaporanAktif());
        updateLaporanAktif();
    }

    @FXML
    private void tambah() {
        try {
            Laundry l = new Laundry(
                    tfNama.getText(),
                    cbLayanan.getValue(),
                    dpTMasuk.getValue().toString(),
                    dpTSelesai.getValue().toString(),
                    Integer.parseInt(tfBerat.getText())
            );
            data.add(l);
            service.saveData(data);
            clear();
        } catch (Exception e) {
            showError("Input tidak valid!");
        }
    }

    @FXML
    private void update() {
        Laundry selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setNama(tfNama.getText());
                selected.setLayanan(cbLayanan.getValue());
                selected.setTMasuk(dpTMasuk.getValue().toString());
                selected.setTSelesai(dpTSelesai.getValue().toString());
                selected.setBerat(Integer.parseInt(tfBerat.getText()));
                table.refresh();
                service.saveData(data);
                clear();
            } catch (Exception e) {
                showError("Input tidak valid!");
            }
        }
    }

    @FXML
    private void ambilLaundry() {
        Laundry selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            data.remove(selected);
            service.saveData(data);
            List<Laundry> diambilList = diambilService.loadData();
            diambilList.add(selected);
            diambilService.saveData(diambilList);
            clear();
        }
    }

    @FXML
    private void showLaporan() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/rinswash/laporan-view.fxml"));
            Parent root = loader.load();
            LaporanController controller = loader.getController();
            List<Laundry> diambilList = diambilService.loadData();
            controller.setData(diambilList);

            Stage stage = new Stage();
            stage.setTitle("Laporan Laundry Diambil");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            showError("Gagal membuka laporan: " + e.getMessage());
        }
    }

    private void updateLaporanAktif() {
        lblTotalTransaksi.setText("Total Transaksi: " + data.size());
        int totalBerat = data.stream().mapToInt(Laundry::getBerat).sum();
        lblTotalBerat.setText("Total Berat: " + totalBerat + " kg");
        int estimasiPendapatan = data.stream().mapToInt(Laundry::getBiaya).sum();
        lblEstimasiPendapatan.setText("Pendapatan: Rp " + estimasiPendapatan);
    }

    private void clear() {
        tfNama.clear();
        cbLayanan.setValue(null);
        dpTMasuk.setValue(LocalDate.now());
        dpTSelesai.setValue(LocalDate.now().plusDays(2));
        tfBerat.clear();
        table.getSelectionModel().clearSelection();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText("Terjadi Kesalahan");
        alert.showAndWait();
    }
}