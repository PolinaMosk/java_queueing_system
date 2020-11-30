package smo;

public class Request {
    private int source_priority;
    private double generate_time;
    private int number;

    public Request(){};

    public int getSource_priority() {
        return source_priority;
    }

    public void setSource_priority(int source_priority) {
        this.source_priority = source_priority;
    }

    public double getGenerate_time() {
        return generate_time;
    }

    public void setGenerate_time(double generate_time) {
        this.generate_time = generate_time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
