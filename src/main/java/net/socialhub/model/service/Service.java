package net.socialhub.model.service;

import net.socialhub.define.ServiceEnum;
import net.socialhub.model.Account;

/**
 * SNS サービス情報
 * SNS Service Info
 */
public class Service {

    private Account account;

    private ServiceEnum service;
    private RateLimit rateLimit;

    /** Use Only Mastodon */
    private String apiHost;
    /** Use Only Mastodon */
    private String streamApiHost;

    /**
     * For Twitter
     */
    public Service(ServiceEnum service, Account account) {
        this.service = service;
        this.account = account;
        this.rateLimit = new RateLimit();
    }

    //region // Getter&Setter
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ServiceEnum getService() {
        return service;
    }

    public void setService(ServiceEnum service) {
        this.service = service;
    }

    public RateLimit getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(RateLimit rateLimit) {
        this.rateLimit = rateLimit;
    }

    public String getApiHost() {
        return apiHost;
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

    public String getStreamApiHost() {
        return streamApiHost;
    }

    public void setStreamApiHost(String streamApiHost) {
        this.streamApiHost = streamApiHost;
    }
    //endregion
}
