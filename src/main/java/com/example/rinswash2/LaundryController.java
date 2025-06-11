package com.example.rinswash2;

import javafx.collections.*;
import javafx.collections.ListChangeListener;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class LaundryController {
    private final TableView<Laundry> table = new TableView<>();
    private final ObservableList<Laundry> data = FXCollections.observableArrayList();
    private final LaundryService service = new LaundryService();
    private final LaundryService diambilService = new LaundryService("laundry_diambil.txt");

    // Form Fields
    private final TextField tfNama = new TextField();
    private final ComboBox<String> cbLayanan = new ComboBox<>();
    private final DatePicker dpTMasuk = new DatePicker();
    private final DatePicker dpTSelesai = new DatePicker();
    private final TextField tfBerat = new TextField();

    // Tambahan untuk laporan aktif
    private final Label lblTotalTransaksi = new Label();
    private final Label lblTotalBerat = new Label();

    public BorderPane getMainView() {
        data.addAll(service.loadData());
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Set DatePicker format to yyyy-MM-dd (untuk konsistensi data)
        dpTMasuk.setConverter(new javafx.util.StringConverter<java.time.LocalDate>() {
            private final java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
            @Override
            public String toString(java.time.LocalDate date) {
                return date != null ? dtf.format(date) : "";
            }
            @Override
            public java.time.LocalDate fromString(String string) {
                return (string == null || string.isEmpty()) ? null : java.time.LocalDate.parse(string, dtf);
            }
        });
        dpTSelesai.setConverter(new javafx.util.StringConverter<java.time.LocalDate>() {
            private final java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
            @Override
            public String toString(java.time.LocalDate date) {
                return date != null ? dtf.format(date) : "";
            }
            @Override
            public java.time.LocalDate fromString(String string) {
                return (string == null || string.isEmpty()) ? null : java.time.LocalDate.parse(string, dtf);
            }
        });

        // Form
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(new Label("Nama:"), 0, 0);
        form.add(tfNama, 1, 0);
        form.add(new Label("Layanan:"), 0, 1);
        cbLayanan.getItems().addAll("CUCI", "KERING");
        form.add(cbLayanan, 1, 1);
        form.add(new Label("Tgl Masuk:"), 0, 2);
        dpTMasuk.setValue(java.time.LocalDate.now());
        form.add(dpTMasuk, 1, 2);
        form.add(new Label("Tgl Selesai:"), 0, 3);
        dpTSelesai.setValue(java.time.LocalDate.now().plusDays(2));
        form.add(dpTSelesai, 1, 3);
        form.add(new Label("Berat (kg):"), 0, 4);
        form.add(tfBerat, 1, 4);

        HBox btnBox = new HBox(10);
        Button btnAdd = new Button("Tambah");
        Button btnUpdate = new Button("Update");
        Button btnAmbil = new Button("Ambil Laundry");
        Button btnLaporan = new Button("Laporan Laundry Diambil");
        btnBox.getChildren().addAll(btnAdd, btnUpdate, btnAmbil, btnLaporan);
        form.add(btnBox, 1, 5);

        // Table
        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().clear();
        TableColumn<Laundry, String> colNama = new TableColumn<>("Nama");
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        TableColumn<Laundry, String> colLayanan = new TableColumn<>("Layanan");
        colLayanan.setCellValueFactory(new PropertyValueFactory<>("layanan"));
        TableColumn<Laundry, String> colMasuk = new TableColumn<>("Masuk");
        colMasuk.setCellValueFactory(new PropertyValueFactory<>("tMasuk"));
        TableColumn<Laundry, String> colSelesai = new TableColumn<>("Selesai");
        colSelesai.setCellValueFactory(new PropertyValueFactory<>("tSelesai"));
        TableColumn<Laundry, Integer> colBerat = new TableColumn<>("Berat");
        colBerat.setCellValueFactory(new PropertyValueFactory<>("berat"));
        table.getColumns().addAll(colNama, colLayanan, colMasuk, colSelesai, colBerat);

        // Laporan transaksi aktif di sebelah kanan
        VBox laporanBox = new VBox(10);
        laporanBox.setPadding(new Insets(20));
        laporanBox.setAlignment(Pos.TOP_LEFT);
        laporanBox.setId("laporan-box");

        Label lblJudul = new Label("Transaksi Aktif");
        lblJudul.setId("laporan-judul");
        lblTotalTransaksi.setId("laporan-total-transaksi");
        lblTotalBerat.setId("laporan-total-berat");
        laporanBox.getChildren().addAll(lblJudul, lblTotalTransaksi, lblTotalBerat);

        updateLaporanAktif();
        data.addListener((ListChangeListener<Laundry>) c -> updateLaporanAktif());

        HBox mainContent = new HBox(30, table, laporanBox);
        mainContent.setAlignment(Pos.TOP_LEFT);

        root.setTop(form);
        root.setCenter(mainContent);

        // Event handler
        btnAdd.setOnAction(e -> tambah());
        btnUpdate.setOnAction(e -> update());
        btnAmbil.setOnAction(e -> ambilLaundry());
        btnLaporan.setOnAction(e -> showLaporan());

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                tfNama.setText(newSel.getNama());
                cbLayanan.setValue(newSel.getLayanan());
                dpTMasuk.setValue(java.time.LocalDate.parse(newSel.getTMasuk()));
                dpTSelesai.setValue(java.time.LocalDate.parse(newSel.getTSelesai()));
                tfBerat.setText(String.valueOf(newSel.getBerat()));
            }
        });

        return root;
    }

    private void tambah() {
        try {
            String nama = tfNama.getText();
            String layanan = cbLayanan.getValue();
            String tMasuk = dpTMasuk.getValue().toString();
            String tSelesai = dpTSelesai.getValue().toString();
            int berat = Integer.parseInt(tfBerat.getText());
            Laundry l = new Laundry(nama, layanan, tMasuk, tSelesai, berat);
            data.add(l);
            service.saveData(data);
            clear();
        } catch (Exception e) {
            showError("Input tidak valid!");
        }
    }

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

    private void ambilLaundry() {
        Laundry selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            data.remove(selected);
            service.saveData(data);
            // Simpan ke file laundry_diambil.txt
            ObservableList<Laundry> diambilList = FXCollections.observableArrayList(diambilService.loadData());
            diambilList.add(selected);
            diambilService.saveData(diambilList);
            clear();
        }
    }

    private void showLaporan() {
        java.util.List<Laundry> diambilList = diambilService.loadData();
        if (diambilList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Belum ada laundry yang diambil.", ButtonType.OK);
            alert.setTitle("Laporan Laundry Diambil");
            alert.setHeaderText("Laporan Laundry Diambil");
            alert.showAndWait();
            return;
        }

        // TableView untuk laporan
        TableView<Laundry> laporanTable = new TableView<>();
        laporanTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Laundry, String> colNama = new TableColumn<>("Nama");
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        TableColumn<Laundry, String> colLayanan = new TableColumn<>("Layanan");
        colLayanan.setCellValueFactory(new PropertyValueFactory<>("layanan"));
        TableColumn<Laundry, String> colMasuk = new TableColumn<>("Masuk");
        colMasuk.setCellValueFactory(new PropertyValueFactory<>("tMasuk"));
        TableColumn<Laundry, String> colSelesai = new TableColumn<>("Selesai");
        colSelesai.setCellValueFactory(new PropertyValueFactory<>("tSelesai"));
        TableColumn<Laundry, Integer> colBerat = new TableColumn<>("Berat");
        colBerat.setCellValueFactory(new PropertyValueFactory<>("berat"));

        laporanTable.getColumns().addAll(colNama, colLayanan, colMasuk, colSelesai, colBerat);
        laporanTable.setItems(FXCollections.observableArrayList(diambilList));
        laporanTable.getStyleClass().add("laporan-table");

        int totalTransaksi = diambilList.size();
        int totalBerat = diambilList.stream().mapToInt(Laundry::getBerat).sum();

        // Judul dan summary, konsisten dengan scene utama
        Label lblJudul = new Label("Laporan Laundry Diambil");
        lblJudul.setId("laporan-judul");
        Label totalTransaksiLabel = new Label("Total Transaksi: " + totalTransaksi);
        totalTransaksiLabel.setId("laporan-total-transaksi");
        Label totalBeratLabel = new Label("Total Berat: " + totalBerat + " kg");
        totalBeratLabel.setId("laporan-total-berat");

        VBox laporanBox = new VBox(10, lblJudul, laporanTable, totalTransaksiLabel, totalBeratLabel);
        laporanBox.setPadding(new Insets(24, 18, 24, 18));
        laporanBox.setAlignment(Pos.TOP_LEFT);
        laporanBox.setId("laporan-box");
        laporanBox.setPrefWidth(650);

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Laporan Laundry Diambil");
        dialog.getDialogPane().setContent(laporanBox);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        dialog.showAndWait();
    }

    private void updateLaporanAktif() {
        lblTotalTransaksi.setText("Total Transaksi: " + data.size());
        int totalBerat = data.stream().mapToInt(Laundry::getBerat).sum();
        lblTotalBerat.setText("Total Berat: " + totalBerat + " kg");
    }

    private void clear() {
        tfNama.clear();
        cbLayanan.setValue(null);
        dpTMasuk.setValue(java.time.LocalDate.now());
        dpTSelesai.setValue(java.time.LocalDate.now().plusDays(2));
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
