package service;

import bean.admin.Admin;
import bean.admin.Page;

public interface AdminService {

    int addAdmin(Admin admin);

    Page findPageAdmin(int number);

    int updateAdmin(Admin admin);

    int deleteAdmin(String adminId);

    boolean isExist(Admin admin);
}
