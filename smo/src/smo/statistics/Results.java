package smo.statistics;

public class Results {
    private double av_time_buf;
    private double av_time_device;
    private double time_device;
    private double time_sim;
    private double av_time_system;
    private double p_reject;
    private int proceed_num;
    private int reject_num;
    private int source_num;
    private int device_num;
    private double disp_buff;
    private double disp_device;

    private double ratio_k;

    public void setP_reject() {
        this.p_reject = ((double) reject_num) / (proceed_num + reject_num);
    }

    public void setAv_time_buf(Double times) {
        this.av_time_buf = times / proceed_num;
        this.disp_buff = this.av_time_buf / this.av_time_system;
    }

    public void setProceed_num(int proceed_num) {
        this.proceed_num = proceed_num;
    }

    public void setReject_num(int reject_num) {
        this.reject_num = reject_num;
    }

    public void setSource_num(int source_num) {
        this.source_num = source_num;
    }

    public void setAv_time_system(double av_time_system) {
        this.av_time_system = av_time_system / proceed_num;
    }

    public void setTime_sim(double time_sim) {
        this.time_sim = time_sim;
    }


    public void setAv_time_device(double av_time_device) {
        this.av_time_device = av_time_device / proceed_num;
        this.disp_device = this.av_time_device / this.av_time_system;
    }

    public void setDevice_num(int device_num) {
        this.device_num = device_num;
    }

    public void setTime_device(double time_device) {
        this.time_device = time_device;
        this.ratio_k = time_device / time_sim;
    }

    public double getAv_time_buf() {
        return Math.round(av_time_buf * 100000) / 100000.0;
    }

    public double getAv_time_device() {
        return  Math.round(av_time_device * 100000) / 100000.0;
    }

    public double getTime_device() {
        return  Math.round(time_device * 100000) / 100000.0;
    }

    public double getAv_time_system() {
        return  Math.round(av_time_system * 100000) / 100000.0;
    }

    public double getP_reject() {
        return  Math.round(p_reject * 100000) / 100000.0;
    }

    public int getProceed_num() {
        return proceed_num;
    }

    public int getReject_num() {
        return reject_num;
    }

    public int getSource_num() {
        return source_num;
    }

    public int getDevice_num() {
        return device_num;
    }

    public double getDisp_buff() {
        return  Math.round(disp_buff * 100000) / 100000.0;
    }

    public double getDisp_device() {
        return  Math.round(disp_device * 100000) / 100000.0;
    }

    public double getRatio_k() {
        return  Math.round(ratio_k * 100000) / 100000.0;
    }
}
