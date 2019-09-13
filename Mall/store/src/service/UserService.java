package service;

import bean.admin.Page;
import bean.admin.User;

public interface UserService {
    int addUser(User user);

    Page findPageUser(int number);

    boolean deleteUser(String uid);

    User isExist(User user);


    User isUsernameExist(String username);

    User isEmailExist(String email);

    boolean activatedUser(String uid);

    boolean updateUserInfo(User user);

}
