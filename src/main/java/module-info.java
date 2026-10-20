module com.example.labbredovisning3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.labbredovisning3 to javafx.fxml;
    exports com.example.labbredovisning3;
}