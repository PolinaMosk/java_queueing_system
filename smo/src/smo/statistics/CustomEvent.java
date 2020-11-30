package smo.statistics;

import smo.Buffer;
import smo.Device;
import smo.Request;

import java.util.ArrayList;
import java.util.List;

public class CustomEvent {
    public enum Event_type {
        REQUEST_GENERATED, REQUEST_REJECTED, REQUEST_IN_BUFFER, REQUEST_ON_DEVICE
    }
    private String event_time;
    private String request_num;
    private String request_priority;
    private Event_type event_type;
    private String device_num;
    private String device_free_time;
    private List<Request> buffer;


    public CustomEvent(Double event_time, Request req, Event_type event_type, Buffer buffer, Device device) {
        this.event_time = event_time.toString();
        this.event_type = event_type;
        if (buffer != null) {
            this.buffer = new ArrayList<>();
            this.buffer.addAll(buffer.getBuf());
        }
        this.request_num = String.valueOf(req.getNumber());
        this.request_priority = String.valueOf(req.getSource_priority());
        if (device == null) {
            this.device_num = "-";
            this.device_free_time = "-";
        } else {
            this.device_num = String.valueOf(device.getPriority());
            this.device_free_time = String.valueOf(device.getFree_time());
        }
    }

    public String getEvent_time() {
        return event_time;
    }

    public Event_type getEvent_type() {
        return event_type;
    }

    public List<Request> getBuffer() {
        return buffer;
    }


    public String getRequest_num() {
        return request_num;
    }

    public String getRequest_priority() {
        return request_priority;
    }

    public String getDevice_num() {
        return device_num;
    }

    public String getDevice_free_time() {
        return device_free_time;
    }
}
