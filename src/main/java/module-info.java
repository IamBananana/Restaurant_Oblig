module com.example.oblig_restaurant {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.oblig_restaurant to javafx.fxml;
    exports com.example.oblig_restaurant;
}