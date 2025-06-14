module com.example.rinswash {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rinswash to javafx.fxml;
    exports com.example.rinswash;
}