package smo;

import smo.statistics.CustomEvent;
import smo.statistics.Debug;
import smo.statistics.Results;

import java.util.ArrayList;
import java.util.List;

public class BufferDispatcher {
    private List<Integer> reject_res;
    private Buffer buffer;
    private int limit;
    private Request curr_req;
    private Debug debug;
    private List<Integer> proceed_res;

    public BufferDispatcher(Buffer b, int limit, Debug debug, int source_count){
        this.buffer = b;
        this.limit = limit;
        proceed_res = new ArrayList<>();
        reject_res = new ArrayList<>();
        for (int i = 0; i < source_count; i++){
            proceed_res.add(0);
            reject_res.add(0);
        }
        for (int i = 0; i < this.limit; i++){
            this.buffer.getBuf().add(null);
        }
        this.debug = debug;
    }


    public void put(Request req){
        this.curr_req = req;
        if (buffer.check_buff(this.limit) == -1) {
            reject_res.set(req.getSource_priority(), reject_res.get(req.getSource_priority()) + 1);
            if (debug != null) debug.add_event(new CustomEvent(req.getGenerate_time(), req, CustomEvent.Event_type.REQUEST_REJECTED, buffer, null));
        } else {
            proceed_res.set(req.getSource_priority(), proceed_res.get(req.getSource_priority()) + 1);
            List<Request> b = buffer.getBuf();
            b.set(buffer.check_buff(this.limit), req);
            buffer.setBuf(b);
            if (debug != null) debug.add_event(new CustomEvent(req.getGenerate_time(), req, CustomEvent.Event_type.REQUEST_IN_BUFFER, buffer, null));
        }
    }

    public Results form_stats(int source_num){
            Results res = new Results();
            res.setProceed_num(proceed_res.get(source_num));
            res.setReject_num(reject_res.get(source_num));
            res.setSource_num(source_num);
            res.setP_reject();
            return res;
    }



    public Buffer getBuffer(){
        return buffer;
    }

    public Request getCurr_req() {
        return curr_req;
    }
}
