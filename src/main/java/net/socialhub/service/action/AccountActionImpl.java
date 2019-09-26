package net.socialhub.service.action;

import net.socialhub.model.Account;
import net.socialhub.model.service.User;

public abstract class AccountActionImpl implements AccountAction {

    private Account account;

    protected User me;

    @SuppressWarnings("unchecked")
    public <T extends AccountActionImpl> T account(Account account) {
        this.account = account;
        return (T) this;
    }

    public interface ActionCaller<T, E extends Throwable> {
        T proceed() throws E;
    }

    public interface ActionRunner<E extends Throwable> {
        void proceed() throws E;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestAction request() {
        return new RequestActionImpl(account);
    }

    /**
     * Get User me with cache.
     * キャッシュ付きで自分のユーザーを取得
     */
    protected User getUserMeWithCache() {
        return (me != null) ? me : getUserMe();
    }


    //region // Getter&Setter
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    //endregion
}
