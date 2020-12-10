package smo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Buffer {
    private List<Request> buf;

    public Buffer(){
        this.buf = new ArrayList<>();
    }

    public List<Request> getBuf() {
        return buf;
    }

    public void setBuf(List<Request> buf) {
        this.buf = buf;
    }

    public Request next_req(){
        Comparator<Request> comparator = Comparator.comparing(Request::getSource_priority);
        comparator.thenComparing(Request::getGenerate_time).reversed();
        List<Request> bf = new ArrayList<>(this.buf);
        bf.sort(Comparator.nullsLast(comparator));
        if (this.buf.size() > 0) return bf.get(0);
        else return null;
    }

    public int check_buff(int limit){
        for (int i = 0; i < limit; i++){
            if (this.buf.get(i) == null) return i;
        }
        return -1;
    }


}
