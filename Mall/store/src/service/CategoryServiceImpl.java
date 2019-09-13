
package service;

import bean.admin.Category;
import bean.admin.Page;
import dao.CategoryDao;
import dao.CategoryDaoImpl;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();


    @Override
    public List<Category> findAllCategory() {
        return categoryDao.findAllCategory();
    }

    /**
     * 增加分类，首先判断是否存在该分类
     *
     * */
    @Override
    public int addCategory(Category category) {
        //service层做具体的逻辑
        //数据库该字段设置unique，如果重复，直接sql层报错，会被try catch捕获，直接在catch里返回对应的字段。
        /*boolean result = categoryDao.queryCategoryByName(category);
        if(result){
           return -1;
        }*/
        return categoryDao.addCategory(category);
    }

    @Override
    public int updateCategory(Category category) {
        return categoryDao.updateCategory(category);
    }

    @Override
    public boolean deleteCategory(String cid) {
        return categoryDao.deleteCategory(cid);
    }

    @Override
    public Page findPageCategory(int number) {
        int totalCount = categoryDao.queryTotalCount();
        List<Category> categories = categoryDao.queryPageCategory(number);
        Page page = new Page();
        page.setCurrentPage(number);
        page.setNextPage(number + 1);
        page.setPreviousPage(number - 1);
        page.setTotalCount(totalCount);
        page.setList(categories);
        int totalPageCount = (int) Math.ceil(totalCount * 1.0 / Page.PAGE_SIZE);
        page.setTotalPageCount(totalPageCount);
        return page;
    }



    /*@Override
    public boolean queryCategoryByName(Category category) {
        return categoryDao.queryCategoryByName(category);
    }*/


}
