package io.bitjob.domain;

/**
 * Created by yangkuan on 15/8/5.
 */
public class CompanyConfig {
    private String payEnum;
    private String merchantNo;

    public String getPayEnum() {
        return payEnum;
    }

    public void setPayEnum(String payEnum) {
        this.payEnum = payEnum;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
}
