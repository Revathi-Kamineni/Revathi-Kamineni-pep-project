package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public List<Account> getAllAccounts() {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> users = new ArrayList<>();

        try {

            String sql = "SELECT * FROM account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account user = new Account(rs.getInt("account_id"), 
                rs.getString("username"), rs.getString("password"));
                users.add(user);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return users;
    }

    public Account getAccountByCredentials(Account user){

        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()){
                Account loggedInUser = new Account(rs.getInt("account_id"), 
                rs.getString("username"), rs.getString("password"));
                return loggedInUser;
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account insertUser(Account user) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account(username, password) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, user.getUsername(), user.getPassword());
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
    
}
