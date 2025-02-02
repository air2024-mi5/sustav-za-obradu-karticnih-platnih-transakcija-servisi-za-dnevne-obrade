package foi.air.szokpt.transproc.dtos;

public class Merchant {

    private String oib;

    private String name;

    public Merchant(String name, String oib) {
        this.name = name;
        this.oib = oib;
    }

    public Merchant() {
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


