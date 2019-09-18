package net.socialhub.service.action.request;

import net.socialhub.define.action.ActionType;
import net.socialhub.model.Account;
import net.socialhub.model.service.Pageable;
import net.socialhub.model.service.Paging;
import net.socialhub.model.service.User;

import java.util.function.Function;

public class UsersRequestImpl implements UsersRequest {

    private Function<Paging, Pageable<User>> usersFunction;
    private ActionType actionType;
    private Account account;

    /**
     * {@inheritDoc}
     */
    @Override
    public Pageable<User> getUsers(Paging paging) {
        return usersFunction.apply(paging);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionType getActionType() {
        return actionType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account getAccount() {
        return account;
    }

    //region // Getter&Setter
    public void setAccount(Account account) {
        this.account = account;
    }

    public void setUsersFunction(Function<Paging, Pageable<User>> usersFunction) {
        this.usersFunction = usersFunction;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
    //endregion
}