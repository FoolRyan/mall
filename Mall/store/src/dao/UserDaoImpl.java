package dao;

import bean.admin.Page;
import bean.admin.Product;
import bean.admin.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DruidUtils;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());

    @Override
    public int addUser(User user) {
        try {
            runner.update("insert into user values(?,?,?,?,?,?)",
                    user.getUid(),
                    user.getUsername(),
                    user.getNickname(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getBirthday());
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public int queryTotalCount() {
        Long count = 0L;
        try {
            count = runner.query("select count(uid) from user",new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count.intValue();
    }

    @Override
    public List<User> queryPageUser(int number) {
        List<User> users = null;
        try {
            users = runner.query("select * from user limit ? offset ?",
                    new BeanListHandler<User>(User.class),
                    Page.PAGE_SIZE,(number - 1) * Page.PAGE_SIZE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean deleteUser(String uid) {
        try {
            runner.update("delete from user where uid = ? ",uid);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public User isExist(User user) {
        User query = null;
        try {
            query = runner.query("select * from user where username = ? and password = ? ",
                    new BeanHandler<User>(User.class),
                    user.getUsername(),
                    user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    @Override
    public User isUsernameExist(String username) {
        User user = null;
        try {
            user = runner.query("select * from user where username = ? ",new BeanHandler<User>(User.class),username);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return user;
    }

    @Override
    public User isEmailExist(String email) {
        User user = null;
        try {
            user = runner.query("select * from user where email = ? ",new BeanHandler<User>(User.class),email);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return user;
    }

    @Override
    public boolean activatedUser(String uid) {
        try {
            runner.update("update user set activate = 'true' where uid = ?",uid);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUserInfo(User user) {
        try {
            runner.update("update user set nickname = ?,password = ?,email = ?,birthday = ? where uid = ?",
                    user.getNickname(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getBirthday(),
                    user.getUid());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
