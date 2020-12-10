package smo;

import smo.statistics.CustomEvent;
import smo.statistics.Debug;

import java.util.ArrayList;
import java.util.List;

public class DeviceDispatcher {
    private List<Device> devices;
    private BufferDispatcher buf_disp;
    private List<List<Double>> time_buffer = new ArrayList<>();
    private List<Double> time_system = new ArrayList<>();
    private List<List<Double>> av_time_device = new ArrayList<>();
    private Debug debug;

    public DeviceDispatcher(List<Device> d, BufferDispatcher b, int source_num, Debug debug){
        this.devices = d;
        this.buf_disp = b;
        this.debug = debug;
        for (int i = 0 ; i < source_num; i++){
            time_buffer.add(i, new ArrayList<>());
            time_system.add(i, 0.0);
            av_time_device.add(i, new ArrayList<>());
        }
    }

    public void put(double current_time){
        for (Device d: devices) {
            Request next = buf_disp.getBuffer().next_req();
            if (next == null) return;
            if (d.isFree()){
                execute(current_time, d, next);
            } else {
                  if (current_time >= d.getFree_time()){
                    d.setFree(true);
                      if (debug != null) debug.add_event(new CustomEvent(current_time, d.getCurr_req(), CustomEvent.Event_type.DEVICE_FREE, buf_disp.getBuffer(), d, devices));
                    d.setFree_time(buf_disp.getCurr_req().getGenerate_time());
                    if (next.getGenerate_time() < d.getFree_time()) {
                        continue;
                    }
                      execute(current_time, d, next);
                  }
            }
        }
    }

    private void execute(double current_time, Device d, Request next) {
        d.processing(next, current_time);
        time_buffer.get(next.getSource_priority()).add(d.getCurrent_time() - next.getGenerate_time());
        int index = buf_disp.getBuffer().getBuf().indexOf(next);
        buf_disp.getBuffer().getBuf().set(index, null);
        time_system.set(next.getSource_priority(), time_system.get(next.getSource_priority()) + d.getFree_time() - next.getGenerate_time());
        av_time_device.get(next.getSource_priority()).add(d.getFree_time() - d.getCurrent_time());
        if (debug != null) debug.add_event(new CustomEvent(current_time, next, CustomEvent.Event_type.REQUEST_ON_DEVICE, buf_disp.getBuffer(), d, devices));
    }

    public List<List<Double>> getTime_buffer() {
        return time_buffer;
    }


    public List<List<Double>> getAv_time_device() {
        return av_time_device;
    }

    public double getTime_system(int num_source) {
        return time_system.get(num_source);
    }
}
