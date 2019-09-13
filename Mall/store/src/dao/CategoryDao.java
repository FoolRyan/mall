package dao;

import bean.admin.Category;

import java.util.List;

public interface CategoryDao {


    // boolean queryCategoryByName(Category category);

    int addCategory(Category category);

    boolean deleteCategory(String cid);

    int updateCategory(Category category);

    int queryTotalCount();

    List<Category> queryPageCategory(int number);

    List<Category> findAllCategory();

    Category findCategoryById(int cid);

}
