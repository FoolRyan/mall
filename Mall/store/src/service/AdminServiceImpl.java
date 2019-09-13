package service;

import bean.admin.Admin;
import bean.admin.Page;
import dao.AdminDao;
import dao.AdminDaoImpl;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao = new AdminDaoImpl();

    @Override
    public int addAdmin(Admin admin) {
        return adminDao.addAdmin(admin);
    }

    @Override
    public Page findPageAdmin(int number) {
        int totalCount = adminDao.queryTotalCount();
        List<Admin> admins = adminDao.queryPageAdmin(number);
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
    public int updateAdmin(Admin admin) {
        return adminDao.updateAdmin(admin);
    }

    @Override
    public int deleteAdmin(String adminId) {
        return adminDao.deleteAdmin(adminId);
    }

    @Override
    public boolean isExist(Admin admin) {
        return adminDao.isExist(admin);
    }


}
