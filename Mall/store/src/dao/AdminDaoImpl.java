package dao;

import bean.admin.Admin;
import bean.admin.Page;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DruidUtils;

import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl implements AdminDao {
    private static QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());

    @Override
    public int addAdmin(Admin admin) {
        int add;
        try {
            add = runner.update("insert into admin values (null,?,?)", admin.getAdminName(), admin.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return add;
    }

    @Override
    public int queryTotalCount() {
        Long count = 0L;
        try {
            count = runner.query("select count(adminId) from admin", new ScalarHandler<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count.intValue();
    }

    @Override
    public List<Admin> queryPageAdmin(int number) {
        List<Admin> admins = null;
        try {
            admins = runner.query("select * from admin limit ? offset ?",
                    new BeanListHandler<Admin>(Admin.class),
                    Page.PAGE_SIZE, (number - 1) * Page.PAGE_SIZE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    @Override
    public int updateAdmin(Admin admin) {
        int update;
        try {
            update = runner.update("update admin set password = ? where adminName = ?",
                    admin.getPassword(), admin.getAdminName());
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return update;
    }

    @Override
    public int deleteAdmin(String adminId) {
        int delete;
        try {
            delete = runner.update("delete from admin where adminId = ?", adminId);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return delete;
    }

    @Override
    public boolean isExist(Admin admin) {
        Admin query = null;
        try {
            query = runner.query("select * from admin where adminName = ? and password = ? ",
                    new BeanHandler<Admin>(Admin.class),
                    admin.getAdminName(),
                    admin.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if(query != null){
            return true;
        } else{
            return false;
        }

    }


}


