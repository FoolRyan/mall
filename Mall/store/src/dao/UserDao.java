package dao;

import bean.admin.User;

import java.util.List;

public interface UserDao {
    int addUser(User user);

    int queryTotalCount();


    List<User> queryPageUser(int number);

    boolean deleteUser(String uid);

    User isExist(User user);

    User isUsernameExist(String username);

    User isEmailExist(String email);

    boolean activatedUser(String uid);

    boolean updateUserInfo(User user);

}
