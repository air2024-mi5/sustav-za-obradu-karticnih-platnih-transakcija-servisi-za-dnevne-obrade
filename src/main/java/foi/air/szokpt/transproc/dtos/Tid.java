package foi.air.szokpt.transproc.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tid {

    @JsonProperty("pos_tid")
    private String posTid;

    private Mid mid;

    public Tid(String posTid, Mid mid) {
        this.posTid = posTid;
        this.mid = mid;
    }

    public Tid() {
    }

    public String getPosTid() {
        return posTid;
    }

    public void setPosTid(String pos_tid) {
        this.posTid = pos_tid;
    }

    public Mid getMid() {
        return mid;
    }

    public void setMid(Mid mid) {
        this.mid = mid;
    }

}
