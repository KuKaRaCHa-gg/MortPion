module com.example.mortpion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.jfr;


    opens com.example.mortpion to javafx.fxml;
    exports com.example.mortpion;
}