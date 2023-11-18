module GestorPAT {
    requires javafx.fxml;
    requires javafx.controls;

    opens GestorPAT javafx.graphics, javafx.fxml;
}