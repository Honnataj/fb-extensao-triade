module com.triade2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.triade2 to javafx.fxml;
    exports com.triade2;
}