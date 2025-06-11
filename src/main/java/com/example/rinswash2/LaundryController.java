package com.example.rinswash2;

import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class LaundryController {
    private final TableView<Laundry> table = new TableView<>();
    private final ObservableList<Laundry> data = FXCollections.observableArrayList();
    private final LaundryService service = new LaundryService();

    // Form Fields
    private final TextField tfNama = new TextField();
    private final ComboBox<String> cbLayanan = new ComboBox<>();
    private final DatePicker dpTMasuk = new DatePicker();
    private final DatePicker dpTSelesai = new DatePicker();
    private final TextField tfBerat = new TextField();

    public BorderPane getMainView() {
        data.addAll(service.loadData());
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Set DatePicker format to dd-MM-yyyy
        dpTMasuk.setConverter(new javafx.util.StringConverter<java.time.LocalDate>() {
            private final java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
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
            private final java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
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
        Button btnDelete = new Button("Hapus");
        btnBox.getChildren().addAll(btnAdd, btnUpdate, btnDelete);
        form.add(btnBox, 1, 5);

        // Table
        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().clear();
        TableColumn<Laundry, String> colNama = (TableColumn<Laundry, String>) createColumn("Nama", "nama");
        TableColumn<Laundry, String> colLayanan = (TableColumn<Laundry, String>) createColumn("Layanan", "layanan");
        TableColumn<Laundry, String> colMasuk = new TableColumn<>("Masuk");
        colMasuk.setCellValueFactory(new PropertyValueFactory<>("tMasuk"));
        colMasuk.setCellFactory(column -> new TableCell<Laundry, String>() {
            private final java.time.format.DateTimeFormatter srcFmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
            private final java.time.format.DateTimeFormatter dstFmt = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    try {
                        setText(dstFmt.format(java.time.LocalDate.parse(item, srcFmt)));
                    } catch (Exception e) {
                        setText(item);
                    }
                }
            }
        });
        TableColumn<Laundry, String> colSelesai = new TableColumn<>("Selesai");
        colSelesai.setCellValueFactory(new PropertyValueFactory<>("tSelesai"));
        colSelesai.setCellFactory(column -> new TableCell<Laundry, String>() {
            private final java.time.format.DateTimeFormatter srcFmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
            private final java.time.format.DateTimeFormatter dstFmt = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    try {
                        setText(dstFmt.format(java.time.LocalDate.parse(item, srcFmt)));
                    } catch (Exception e) {
                        setText(item);
                    }
                }
            }
        });
        TableColumn<Laundry, Integer> colBerat = (TableColumn<Laundry, Integer>) createColumn("Berat", "berat");
        table.getColumns().addAll(colNama, colLayanan, colMasuk, colSelesai, colBerat);

        // Actions
        btnAdd.setOnAction(e -> tambah());
        btnUpdate.setOnAction(e -> update());
        btnDelete.setOnAction(e -> hapus());

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                tfNama.setText(newVal.getNama());
                cbLayanan.setValue(newVal.getLayanan());
                dpTMasuk.setValue(java.time.LocalDate.parse(newVal.getTMasuk()));
                dpTSelesai.setValue(java.time.LocalDate.parse(newVal.getTSelesai()));
                tfBerat.setText(String.valueOf(newVal.getBerat()));
            }
        });

        root.setTop(form);
        root.setCenter(table);
        return root;
    }

    private TableColumn<Laundry, ?> createColumn(String title, String prop) {
        TableColumn<Laundry, Object> col = new TableColumn<>(title);
        col.setCellValueFactory(new PropertyValueFactory<>(prop));
        return col;
    }

    private void tambah() {
        try {
            String nama = tfNama.getText();
            String layanan = cbLayanan.getValue();
            String tglMasuk = dpTMasuk.getValue().toString();
            String tglSelesai = dpTSelesai.getValue().toString();
            int berat = Integer.parseInt(tfBerat.getText());
            Laundry l = new Laundry(nama, layanan, tglMasuk, tglSelesai, berat);
            data.add(0, l); // Tambahkan data di posisi paling atas
            service.saveData(data);
            clear();
        } catch (Exception e) {
            showError("Input tidak valid");
        }
    }

    private void update() {
        Laundry selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

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
            showError("Gagal update data");
        }
    }

    private void hapus() {
        Laundry selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            data.remove(selected);
            service.saveData(data);
            clear();
        }
    }

    private void clear() {
        tfNama.clear();
        cbLayanan.setValue(null);
        dpTMasuk.setValue(java.time.LocalDate.now());
        dpTSelesai.setValue(java.time.LocalDate.now().plusDays(2));
        tfBerat.clear();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
