/**
 * Created by IntelliJ IDEA.
 * service.User: Administrator
 * Date: 2019/7/10
 * Time: 10:30
 */
package service;

import bean.admin.Category;
import bean.admin.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CategoryService {
    List<Category> findAllCategory();

    int addCategory(Category category);

    int updateCategory(Category category);

    boolean deleteCategory(String cid);

    Page findPageCategory(int number);



    // boolean queryCategoryByName(Category category);
}
