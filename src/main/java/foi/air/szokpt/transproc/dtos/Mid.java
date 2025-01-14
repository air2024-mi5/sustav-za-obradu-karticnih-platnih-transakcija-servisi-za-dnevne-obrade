package foi.air.szokpt.transproc.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Mid {
    @JsonProperty("pos_mid")
    private String posMid;

    @JsonProperty("sale_point_name")
    private String salePointName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state_code")
    private String stateCode;

    @JsonProperty("type_code")
    private String typeCode;

    @JsonProperty("location_code")
    private String locationCode;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("security_code")
    private String securityCode;

    @JsonProperty("merchant")
    private Merchant merchant;

    public Mid(String posMid,
               String salePointName,
               String city,
               String stateCode,
               String typeCode,
               String locationCode,
               String postalCode,
               String countryCode,
               String securityCode,
               Merchant merchant) {
        this.posMid = posMid;
        this.salePointName = salePointName;
        this.city = city;
        this.stateCode = stateCode;
        this.typeCode = typeCode;
        this.locationCode = locationCode;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
        this.securityCode = securityCode;
        this.merchant = merchant;
    }

    public Mid() {
    }

    public String getPosMid() {
        return posMid;
    }

    public void setPosMid(String posMid) {
        this.posMid = posMid;
    }

    public String getSalePointName() {
        return salePointName;
    }

    public void setSalePointName(String salePointName) {
        this.salePointName = salePointName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

}

