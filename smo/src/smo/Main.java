package smo;

import controllers.AutoModeController;
import controllers.MainWindowController;
import controllers.StepModeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import smo.statistics.CustomEvent;
import smo.statistics.Debug;
import smo.statistics.Results;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Main extends Application {
    private Stage primaryStage;
    private static int num_of_source;
    private double time_finish;
    private StepModeController step_controller;
    private AutoModeController auto_controller;
    private MainWindowController main_controller;
    private Debug debug;
    private int limit1;
    private int limit2;

    private static List<Source> source = new ArrayList<>();
    private static List<Device> device = new ArrayList<>();
    private static BufferDispatcher buff_disp;
    private static DeviceDispatcher dev_disp;
    private static Comparator<Source> comparator = Comparator.comparing(Source::getCurrent_time);
    private List<Results> results_source = new ArrayList<>();
    private List<Results> results_device = new ArrayList<>();
    private Config prev_config;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        openMainMenu();
        primaryStage.show();
        this.prev_config = null;
    }

    public void openMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_window.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.setTitle("SMO");

            MainWindowController controller = loader.getController();
            this.main_controller = controller;
            controller.setMainApp(this);
            this.main_controller.setPrevConfig(this.prev_config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openStepWindow(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/step_mode_results.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.setTitle("SMO Step Mode");

            StepModeController controller = loader.getController();
            this.step_controller = controller;
            primaryStage.show();
            controller.setMainApp(this);
            controller.setLimit(limit1, limit2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAutoWindow(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/auto_results.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.setTitle("SMO Auto Mode");

            AutoModeController controller = loader.getController();
            this.auto_controller = controller;
            primaryStage.show();
            auto_controller.setMainApp(this);
            auto_controller.printInfo(results_source, results_device, buff_disp.getBuffer().getBuf());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start_sim() {
        double current_time = 0;
        int req_count = 0;
        while (current_time < this.time_finish){
            Pair<Double, Integer> res = generate(current_time, req_count);
            current_time = res.getKey();
            req_count = res.getValue();
            dev_disp.put(current_time);
        }
        if (this.debug != null) {
            step_controller.print_info(debug.getCalendar());
        }
        else {
            int i = 0;
            for (Source s: source) {
                results_source.add(buff_disp.form_stats(s.getPriority()));
                results_source.get(i).setTime_sim(this.time_finish);
                results_source.get(i).setAv_time_system(dev_disp.getTime_system(i));
                results_source.get(i).setAv_time_buf(dev_disp.getTime_buffer().get(i));
                results_source.get(i).setAv_time_device(dev_disp.getAv_time_device().get(i));
                i++;
            }
            i = 0;
            System.out.println();
            for (Device d: device) {
                Results res = new Results();
                res.setDevice_num(i);
                res.setTime_sim(this.time_finish);
                res.setProceed_num(d.getReq_count());
                res.setTime_device(d.getTime_on_device());
                results_device.add(res);
                i++;
            }
            auto_controller.printInfo(results_source, results_device, buff_disp.getBuffer().getBuf());
        }
        end_sim();
    }

    public void end_sim(){
        source.clear();
        device.clear();
        buff_disp = null;
        dev_disp = null;
        results_source.clear();
        results_device.clear();
    }

    public void init(Config config){
        this.prev_config = config;
        time_finish = config.getTime_finish();
        num_of_source = config.getNum_of_source();
        if (!config.isDebug()) this.debug = null;
        else this.debug = new Debug();
        this.limit1 = config.getBuff_size();
        this.limit2 = config.getNum_of_device();
        for (int i = 0; i < config.getNum_of_source(); i++){
            Source s = new Source(i, config.getA(), config.getB());
            source.add(s);
            source.get(i).setCurrent_time(-1);
        }

        for (int i = 0; i < config.getNum_of_device(); i++){
            Device d = new Device(i + 1, config.getLambda());
            device.add(d);
        }

        Buffer buffer = new Buffer();
        buff_disp = new BufferDispatcher(buffer, config.getBuff_size(), debug, config.getNum_of_source());
        dev_disp = new DeviceDispatcher(device, buff_disp, config.getNum_of_source(), debug);
    }

    public Pair<Double, Integer> generate(double current_time, int req_count){
        for (int i = 0; i < num_of_source; i++){
            if (source.get(i).getCurrent_time() < 0) {
                source.get(i).generate(current_time);
            }
        }
        Optional<Source> min_time = source.stream().min(comparator);
        Request req = min_time.get().getCurrent_req();
        req.setNumber(req_count++);
        if (this.debug != null) debug.add_event(new CustomEvent(req.getGenerate_time(), req, CustomEvent.Event_type.REQUEST_GENERATED, null, null, null));
        buff_disp.put(min_time.get().getCurrent_req());
        min_time.get().setCurrent_time(-1);
        current_time = min_time.get().getCurrent_req().getGenerate_time();
        return new Pair<>(current_time, req_count);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
