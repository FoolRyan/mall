package service;

import bean.admin.Admin;
import bean.admin.Page;
import bean.admin.User;
import dao.UserDao;
import dao.UserDaoImpl;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public int addUser(User user) {

        return userDao.addUser(user);
    }

    @Override
    public Page findPageUser(int number) {
        int totalCount = userDao.queryTotalCount();
        List<User> admins = userDao.queryPageUser(number);
        Page page = new Page();
        page.setCurrentPage(number);
        page.setNextPage(number + 1);
        page.setPreviousPage(number - 1);
        page.setTotalCount(totalCount);
        page.setList(admins);
        int totalPageCount = (int) Math.ceil(totalCount * 1.0 / Page.PAGE_SIZE);
        page.setTotalPageCount(totalPageCount);
        return page;
    }

    @Override
    public boolean deleteUser(String uid) {
        return userDao.deleteUser(uid);
    }

    @Override
    public User isExist(User user) {
        return userDao.isExist(user);
    }

    @Override
    public User isUsernameExist(String username) {
        return userDao.isUsernameExist(username);
    }

    @Override
    public User isEmailExist(String email) {
        return userDao.isEmailExist(email);
    }

    @Override
    public boolean activatedUser(String uid) {
        return userDao.activatedUser(uid);
    }

    @Override
    public boolean updateUserInfo(User user) {
        return userDao.updateUserInfo(user);
    }
}
