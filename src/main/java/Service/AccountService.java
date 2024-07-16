package Service;
import DAO.AccountDAO;
import Model.Account;

import java.util.List;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    public Account addAccount(Account user){
        if (user.getUsername().isBlank() || user.getPassword().length() < 4){
            return null;
        }

        List<Account> accountChecker = getAllAccounts();
        for (Account a : accountChecker){
            if(a.getAccount_id() == user.getAccount_id()){
                return null;
            }
        }

        Account persistenceUser = accountDAO.insertUser(user);

        return persistenceUser;
    }

    public Account loginAccount(Account user) {
        Account checker = accountDAO.getAccountByCredentials(user);
        if (checker != null){
            return checker;
        }

        return null;
    }
}
