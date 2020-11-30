package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import smo.Main;
import smo.statistics.Results;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AutoModeController {

    private Main mainApp;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> av_time_buf;

    @FXML
    private TableColumn<?, ?> av_time_device;

    @FXML
    private TableColumn<?, ?> av_time_system;

    @FXML
    private Button back;

    @FXML
    private TableColumn<?, ?> d1;

    @FXML
    private TableColumn<?, ?> d2;

    @FXML
    private TableColumn<?, ?> device_num;

    @FXML
    private TableColumn<?, ?> p_reject;

    @FXML
    private TableColumn<?, ?> proceed_num;

    @FXML
    private TableColumn<?, ?> ratio_k;

    @FXML
    private TableColumn<?, ?> reject_num;

    @FXML
    private TableColumn<?, ?> source_num;

    @FXML
    private TableView<Results> table1;

    @FXML
    private TableView<Results> table2;

    @FXML
    private TableColumn<?, ?> time_device;

    @FXML
    void initialize() {
        source_num.setCellValueFactory(new PropertyValueFactory<>("source_num"));
        proceed_num.setCellValueFactory(new PropertyValueFactory<>("proceed_num"));
        reject_num.setCellValueFactory(new PropertyValueFactory<>("reject_num"));
        p_reject.setCellValueFactory(new PropertyValueFactory<>("p_reject"));
        av_time_buf.setCellValueFactory(new PropertyValueFactory<>("av_time_buf"));
        av_time_device.setCellValueFactory(new PropertyValueFactory<>("av_time_device"));
        av_time_system.setCellValueFactory(new PropertyValueFactory<>("av_time_system"));
        d1.setCellValueFactory(new PropertyValueFactory<>("disp_buff"));
        d2.setCellValueFactory(new PropertyValueFactory<>("disp_device"));

        device_num.setCellValueFactory(new PropertyValueFactory<>("device_num"));
        time_device.setCellValueFactory(new PropertyValueFactory<>("time_device"));
        ratio_k.setCellValueFactory(new PropertyValueFactory<>("ratio_k"));
    }

    public void printInfo(List<Results> sources, List<Results> device){
        ObservableList<Results> source_res = FXCollections.observableArrayList();
        source_res.addAll(sources);
        this.table1.setItems(source_res);
        ObservableList<Results> device_res = FXCollections.observableArrayList();
        device_res.addAll(device);
        this.table2.setItems(device_res);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void back(MouseEvent mouseEvent) {
        this.table1.getItems().clear();
        this.table1.setItems(null);
        this.table2.getItems().clear();
        this.table2.setItems(null);
        mainApp.openMainMenu();
    }
}
