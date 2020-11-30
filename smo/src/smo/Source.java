package smo;

public class Source {
    private int priority;
    private double current_time;
    private double param_a;
    private double param_b;
    private Request current_req;


    public Source(int pri, double param1, double param2){
        this.param_a = param1;
        this.param_b = param2;
        this.priority = pri;
        this.current_time = 0;
    }

    public void generate(double current_time){
        Request new_req = new Request();
        double generate_time = current_time + ((param_b - param_a) * Math.random() + param_a);
        new_req.setGenerate_time(generate_time);
        new_req.setSource_priority(priority);
        this.current_time = generate_time;
        this.current_req = new_req;
    }

    public Request getCurrent_req() {
        return current_req;
    }

    public double getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(double current_time) {
        this.current_time = current_time;
    }

    public int getPriority() {
        return priority;
    }
}
