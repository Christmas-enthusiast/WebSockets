module com.mycompany.clientfrontend {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.clientfrontend to javafx.fxml;
    exports com.mycompany.clientfrontend;
}
