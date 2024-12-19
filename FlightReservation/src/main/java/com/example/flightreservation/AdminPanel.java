package com.example.flightreservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminPanel extends Application {
    private FlightDatabase flightDatabase;
    private TableView<Flight> flightTable;

    public AdminPanel() {
    }

    public void start(Stage primaryStage) {
        this.flightDatabase = new FlightDatabase();
        this.flightDatabase.loadFlightsFromFile("flights.txt");
        this.flightTable = this.createFlightTable();
        this.flightTable.setItems(FXCollections.observableArrayList(this.flightDatabase.getFlights()));
        Button addButton = new Button("Add Flight");
        Button deleteButton = new Button("Delete Flight");
        Button viewButton = new Button("View Flights");
        addButton.setStyle("-fx-background-color: #66b3ff; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        deleteButton.setStyle("-fx-background-color: #66b3ff; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        viewButton.setStyle("-fx-background-color: #66b3ff; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        HBox buttonBox = new HBox(10.0, new Node[]{addButton, deleteButton, viewButton});
        buttonBox.setStyle("-fx-padding: 10; -fx-alignment: center;");
        StackPane contentArea = new StackPane();
        contentArea.getChildren().add(this.flightTable);
        addButton.setOnAction((e) -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(this.createFlightForm());
        });
        deleteButton.setOnAction((e) -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(this.createDeleteForm());
        });
        viewButton.setOnAction((e) -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(this.flightTable);
        });
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        backButton.setOnAction((e) -> {
            primaryStage.close();
            LoginUI loginUI = new LoginUI();

            try {
                loginUI.start(new Stage());
            } catch (Exception var4) {
                Exception ex = var4;
                ex.printStackTrace();
            }

        });

        VBox layout = new VBox(10.0, new Node[]{buttonBox, contentArea, backButton});
//        layout.setStyle("-fx-padding: 20; -fx-background-image: url('" + resourcePath + "'); -fx-background-size: cover; -fx-background-position: center;");
//        System.out.println("Image path: " + resourcePath);
        Scene scene = new Scene(layout, 800.0, 600.0);
        primaryStage.setTitle("Admin Panel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createFlightForm() {
        VBox formLayout = new VBox(10.0);
        formLayout.setStyle("-fx-spacing: 15; -fx-alignment: center; -fx-background-color: #e0f7fa; -fx-padding: 20; -fx-border-radius: 10px;");
        Label flightIDLabel = new Label("Flight ID:");
        flightIDLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField flightIDField = new TextField();
        flightIDField.setStyle("-fx-pref-width: 100px; -fx-padding: 10px; -fx-border-radius: 5px; -fx-border-color: #007bff; -fx-font-size: 14px;");
        flightIDField.setPrefWidth(100.0);
        flightIDField.setMaxWidth(100.0);
        Label fromLabel = new Label("From:");
        fromLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        ComboBox<String> fromComboBox = new ComboBox();
        fromComboBox.getItems().addAll(new String[]{"Karachi", "Lahore", "Islamabad", "Peshawar"});
        fromComboBox.setStyle("-fx-pref-width: 200px; -fx-padding: 10px; -fx-border-radius: 5px; -fx-border-color: #007bff; -fx-font-size: 14px;");
        Label toLabel = new Label("To:");
        toLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        ComboBox<String> toComboBox = new ComboBox();
        toComboBox.getItems().addAll(new String[]{"Karachi", "Lahore", "Islamabad", "Peshawar"});
        toComboBox.setStyle("-fx-pref-width: 200px; -fx-padding: 10px; -fx-border-radius: 5px; -fx-border-color: #007bff; -fx-font-size: 14px;");
        Label departureLabel = new Label("Departure Time:");
        departureLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        DatePicker departureDatePicker = new DatePicker(LocalDate.now());
        departureDatePicker.setStyle("-fx-pref-width: 200px; -fx-padding: 10px; -fx-border-radius: 5px; -fx-font-size: 14px;");
        Label arrivalLabel = new Label("Arrival Time:");
        arrivalLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        DatePicker arrivalDatePicker = new DatePicker(LocalDate.now());
        arrivalDatePicker.setStyle("-fx-pref-width: 200px; -fx-padding: 10px; -fx-border-radius: 5px; -fx-font-size: 14px;");
        Button addFlightButton = new Button("Add Flight");
        addFlightButton.setStyle("-fx-background-color: #66b3ff; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        addFlightButton.setOnAction((e) -> {
            try {
                String flightID = flightIDField.getText().trim();
                String fromCity = (String)fromComboBox.getValue();
                String toCity = (String)toComboBox.getValue();
                LocalDate departureTime = (LocalDate)departureDatePicker.getValue();
                LocalDate arrivalTime = (LocalDate)arrivalDatePicker.getValue();
                if (flightID.isEmpty() || fromCity == null || toCity == null || departureTime == null || arrivalTime == null) {
                    Alert alertx = new Alert(AlertType.ERROR, "All fields must be filled.", new ButtonType[]{ButtonType.OK});
                    alertx.showAndWait();
                    return;
                }

                Flight newFlight = new Flight(flightID, fromCity, toCity, departureTime, arrivalTime);
                this.flightDatabase.addFlight(newFlight);
                this.flightTable.setItems(FXCollections.observableArrayList(this.flightDatabase.getFlights()));
                Alert alert = new Alert(AlertType.INFORMATION, "Flight added successfully!", new ButtonType[]{ButtonType.OK});
                alert.showAndWait();
            } catch (Exception var14) {
                Alert alertxx = new Alert(AlertType.ERROR, "Error adding flight.", new ButtonType[]{ButtonType.OK});
                alertxx.showAndWait();
            }

        });
        formLayout.getChildren().addAll(new Node[]{flightIDLabel, flightIDField, fromLabel, fromComboBox, toLabel, toComboBox, departureLabel, departureDatePicker, arrivalLabel, arrivalDatePicker, addFlightButton});
        return formLayout;
    }

    private VBox createDeleteForm() {
        VBox deleteFormLayout = new VBox(10.0);
        deleteFormLayout.setStyle("-fx-spacing: 15; -fx-alignment: center; -fx-background-color: #e0f7fa; -fx-padding: 20; -fx-border-radius: 10px;");
        Label deleteFlightLabel = new Label("Enter Flight ID to delete:");
        deleteFlightLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField deleteFlightIDField = new TextField();
        deleteFlightIDField.setStyle("-fx-pref-width: 100px; -fx-padding: 10px; -fx-border-radius: 5px; -fx-font-size: 14px;");
        deleteFlightIDField.setPrefWidth(150.0);
        deleteFlightIDField.setMaxWidth(150.0);
        Button deleteFlightButton = new Button("Delete Flight");
        deleteFlightButton.setStyle("-fx-background-color: #66b3ff; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        deleteFlightButton.setOnAction((e) -> {
            String flightID = deleteFlightIDField.getText();
            boolean success = this.flightDatabase.deleteFlight(flightID);
            Alert alert;
            if (success) {
                this.flightTable.setItems(FXCollections.observableArrayList(this.flightDatabase.getFlights()));
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Flight Deleted");
                alert.setContentText("Flight with ID " + flightID + " has been deleted.");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Flight Not Found");
                alert.setContentText("No flight found with ID " + flightID + ".");
                alert.showAndWait();
            }

        });
        deleteFormLayout.getChildren().addAll(new Node[]{deleteFlightLabel, deleteFlightIDField, deleteFlightButton});
        return deleteFormLayout;
    }

    private TableView<Flight> createFlightTable() {
        TableView<Flight> table = new TableView();
        TableColumn<Flight, String> flightIdColumn = new TableColumn("Flight ID");
        flightIdColumn.setCellValueFactory((e) -> {
            return new SimpleStringProperty(((Flight)e.getValue()).getFlightId());
        });
        TableColumn<Flight, String> sourceColumn = new TableColumn("Source");
        sourceColumn.setCellValueFactory((e) -> {
            return new SimpleStringProperty(((Flight)e.getValue()).getSource());
        });
        TableColumn<Flight, String> destinationColumn = new TableColumn("Destination");
        destinationColumn.setCellValueFactory((e) -> {
            return new SimpleStringProperty(((Flight)e.getValue()).getDestination());
        });
        TableColumn<Flight, String> departureColumn = new TableColumn("Departure");
        departureColumn.setCellValueFactory((e) -> {
            return new SimpleStringProperty(((Flight)e.getValue()).getDepartureTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });
        TableColumn<Flight, String> arrivalColumn = new TableColumn("Arrival");
        arrivalColumn.setCellValueFactory((e) -> {
            return new SimpleStringProperty(((Flight)e.getValue()).getArrivalTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });
        table.getColumns().addAll(new TableColumn[]{flightIdColumn, sourceColumn, destinationColumn, departureColumn, arrivalColumn});
        return table;
    }

    public static void main(String[] args) {
        launch(args);
    }
}