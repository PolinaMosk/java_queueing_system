package smo;

public class Config {
    private int num_of_source;
    private int num_of_device;
    private double a;
    private double b;
    private double lambda;
    private int buff_size;
    private boolean debug;
    private double time_finish;

    public Config(int num_of_source, int num_of_device, double a, double b, double lambda, int buff_size, boolean debug, double time_finish) {
        this.num_of_source = num_of_source;
        this.num_of_device = num_of_device;
        this.a = a;
        this.b = b;
        this.lambda = lambda;
        this.buff_size = buff_size;
        this.debug = debug;
        this.time_finish = time_finish;
    }

    public int getNum_of_source() {
        return num_of_source;
    }

    public int getNum_of_device() {
        return num_of_device;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getLambda() {
        return lambda;
    }

    public int getBuff_size() {
        return buff_size;
    }

    public boolean isDebug() {
        return debug;
    }

    public double getTime_finish() {
        return time_finish;
    }
}
