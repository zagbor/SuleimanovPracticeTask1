package model;

public class Account {
    private long id;
    private AccountStatus accountStatus;

    public Account() {
    }

    public Account(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public enum AccountStatus {
        ACTIVE,
        BANNED,
        DELETED
    }
}
