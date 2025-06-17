module com.example.rinswash {
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf; // Ensure this is included for iText PDF

    opens com.example.rinswash to javafx.fxml;
    exports com.example.rinswash;
}