package dao;

import bean.admin.Admin;
import bean.admin.Page;

import java.util.List;

public interface AdminDao {
    int addAdmin(Admin admin);

    int queryTotalCount();

    List<Admin> queryPageAdmin(int number);

    int updateAdmin(Admin admin);

    int deleteAdmin(String adminId);

    boolean isExist(Admin admin);


}
