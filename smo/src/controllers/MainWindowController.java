package controllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import smo.Config;
import smo.Main;

public class MainWindowController {

    private Main mainApp;

    @FXML
    private URL location;

    @FXML
    private TextField alpha_input;

    @FXML
    private RadioButton auto_mode;

    @FXML
    private TextField beta_input;

    @FXML
    private TextField buffer_size;

    @FXML
    private TextField lambda_input;

    @FXML
    private TextField num_of_device;

    @FXML
    private TextField num_of_source;

    @FXML
    private Button start_button;

    @FXML
    private RadioButton step_mode;

    @FXML
    private TextField time_input;

    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    void initialize() {
        step_mode.setToggleGroup(toggleGroup);
        auto_mode.setToggleGroup(toggleGroup);
        alpha_input.setText("0.0");
        beta_input.setText("1.0");
        lambda_input.setText("0.5");
        num_of_source.setText("10");
        num_of_device.setText("5");
        buffer_size.setText("5");
        time_input.setText("1000.0");
    }

    @FXML
    void startSimulation(MouseEvent mouseEvent) {
        double alpha = Double.parseDouble(alpha_input.getText());
        double beta = Double.parseDouble(beta_input.getText());
        double lambda = Double.parseDouble(lambda_input.getText());
        int num_source = Integer.parseInt(num_of_source.getText());
        int num_device = Integer.parseInt(num_of_device.getText());
        int buf_size = Integer.parseInt(buffer_size.getText());
        Double time = Double.valueOf(time_input.getText());
        boolean debug = toggleGroup.getSelectedToggle().equals(step_mode);
        Config config = new Config(num_source, num_device, alpha, beta, lambda, buf_size, debug, time);
        mainApp.init(config);
        if (toggleGroup.getSelectedToggle().equals(step_mode)){
            mainApp.openStepWindow();
        }
        if (toggleGroup.getSelectedToggle().equals(auto_mode)){
            mainApp.openAutoWindow();
        }
        mainApp.start_sim();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setPrevConfig(Config config){
        if (config != null) {
            this.alpha_input.setText(String.valueOf(config.getA()));
            this.beta_input.setText(String.valueOf(config.getB()));
            this.lambda_input.setText(String.valueOf(config.getLambda()));
            this.num_of_source.setText(String.valueOf(config.getNum_of_source()));
            this.num_of_device.setText(String.valueOf(config.getNum_of_device()));
            this.buffer_size.setText(String.valueOf(config.getBuff_size()));
            this.time_input.setText(String.valueOf(config.getTime_finish()));
        }
    }
}
