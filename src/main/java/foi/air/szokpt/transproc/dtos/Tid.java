package foi.air.szokpt.transproc.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tid tid = (Tid) o;
        return Objects.equals(posTid, tid.posTid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posTid);
    }

}
