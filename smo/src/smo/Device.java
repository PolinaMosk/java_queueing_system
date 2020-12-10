package smo;

public class Device {
    private int priority;
    private boolean free;
    private double free_time;
    private double param_lambda;
    private double time_on_device;
    private int req_count;
    private double current_time;
    private Request curr_req;

    public Device(int priority, double lambda){
        this.param_lambda = lambda;
        this.priority = priority;
        this.free_time = 0;
        this.free = true;
        this.time_on_device = 0;
        this.req_count = 0;
    }

    public void processing(Request request, double current_time){
        this.curr_req = request;
        this.free = false;
        if (this.free_time < request.getGenerate_time()) current_time = request.getGenerate_time();
        this.current_time = current_time;
        this.free_time = current_time + this.param_lambda * Math.exp(Math.random());
        time_on_device += this.free_time - current_time;
        req_count++;
    }

    public boolean isFree() {
        return free;
    }

    public double getCurrent_time() {
        return current_time;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public double getFree_time() {
        return free_time;
    }

    public void setFree_time(double free_time) {
        this.free_time = free_time;
    }

    public int getPriority() {
        return priority;
    }

    public double getTime_on_device() {
        return time_on_device;
    }

    public int getReq_count() {
        return req_count;
    }

    public Request getCurr_req() {
        return curr_req;
    }
}
