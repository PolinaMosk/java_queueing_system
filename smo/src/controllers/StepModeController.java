package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import smo.statistics.CustomEvent;
import smo.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class StepModeController {

    private Main mainApp;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TableColumn<?, ?> request_num;

    @FXML
    private TableColumn<?, ?> request_priority;

    @FXML
    private TableView<CustomEvent> table;

    @FXML
    private TableColumn<?, ?> time;

    @FXML
    private TableColumn<?, ?> type;

    @FXML
    private TableColumn<?, ?> device_num;

    @FXML
    private HBox device_info;

    @FXML
    private Button next;

    @FXML
    private HBox buffer_info;

    @FXML
    private Button turn_back;

    private ObservableList<CustomEvent> event_info;
    private ObservableList<CustomEvent> event_row;
    private int step;
    private int limit1;
    private int limit2;
    private List<Label> prev_label1 = new ArrayList<>();
    private List<Label> prev_label2 = new ArrayList<>();

    @FXML
    void initialize() {
        time.setCellValueFactory(new PropertyValueFactory<>("event_time"));
        type.setCellValueFactory(new PropertyValueFactory<>("event_type"));
        request_num.setCellValueFactory(new PropertyValueFactory<>("request_num"));
        request_priority.setCellValueFactory(new PropertyValueFactory<>("request_priority"));
        device_num.setCellValueFactory(new PropertyValueFactory<>("device_num"));
    }

    public void print_info(List<CustomEvent> events) {
        this.event_row = FXCollections.observableArrayList();
        this.event_info = FXCollections.observableArrayList();
        this.event_info.addAll(events);
        this.step = 0;
    }

    public void next_step(MouseEvent mouseEvent) {
        if (step == event_info.size() - 1) return;
        this.buffer_info.getChildren().clear();
        this.event_row.add(event_info.get(step));
        this.table.setItems(event_row);
        List<Label> label = new ArrayList<>();
        if (event_info.get(step).getBuffer() != null) {
            for (int i = 0; i < limit1; i++) {
                Label labeli = new Label();
                labeli.setStyle("-fx-border-color: black;");
                labeli.setFont(new Font("Times New Roman", this.buffer_info.getWidth() / (2.5 * limit1)));
                labeli.setAlignment(Pos.CENTER);
                label.add(i, labeli);
                labeli.setBorder(null);
                if (event_info.get(step).getBuffer().get(i) == null) {
                    labeli.setText("---");
                } else {
                    labeli.setText(event_info.get(step).getBuffer().get(i).getNumber() + "[" + event_info.get(step).getBuffer().get(i).getSource_priority() + "]");
                }
                label.set(i, labeli);
            }
        } else {
           label = prev_label1;
        }
        this.buffer_info.getChildren().setAll(label);


        boolean flag = false;
        this.device_info.getChildren().clear();
        List<Label> labels = new ArrayList<>();
        if (event_info.get(step).getDevices() != null) {
            for (int i = 0; i < limit2; i++) {
                Label labeli = new Label();
                labeli.setStyle("-fx-border-color: black;");
                labeli.setFont(new Font("Times New Roman", this.device_info.getWidth() / (2.5 * limit2)));
                labeli.setAlignment(Pos.CENTER);
                labels.add(i, labeli);
                labeli.setBorder(null);
                if (event_info.get(step).getDevices().get(i) == null) {
                    labeli.setText("---");
                } else {
                    labeli.setText(event_info.get(step).getDevices().get(i).getNumber() + "[" + event_info.get(step).getDevices().get(i).getSource_priority() + "]");
                }
                labels.set(i, labeli);
                if (labels.get(i) == null) flag = true;
            }
        } else {
            if (prev_label2 == null) flag = true; else
            labels = prev_label2;
        }
        this.device_info.getChildren().setAll(labels);


        this.step++;
        prev_label1.clear();
        prev_label1.addAll(label);
        if (!flag) prev_label2.clear();
        if (!flag) prev_label2.addAll(labels);
    }

    public void setLimit(int limit1, int limit2){
        this.limit1 = limit1;
        this.limit2 = limit2;
    }

    public void back(MouseEvent mouseEvent) {
        step = 0;
        mainApp.openMainMenu();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}

