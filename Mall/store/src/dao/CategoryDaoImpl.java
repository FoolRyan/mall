/**
 * Created by IntelliJ IDEA.
 * service.User: Administrator
 * Date: 2019/7/10
 * Time: 10:32
 */
package dao;

import bean.admin.Category;
import bean.admin.Page;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DruidUtils;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private static QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());

//    @Override
//    public boolean queryCategoryByName(Category category) {
//        try {
//            Long number = (Long) runner.query("select count(cid) from category where cname = ?",
//                    new ScalarHandler(), category.getCname());
//            if(number.intValue() == 0){
//                return false;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }

    @Override
    public int addCategory(Category category) {
        try {
            runner.update("insert into category values (null,?)", category.getCname());
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public boolean deleteCategory(String cid) {
        try {
            runner.update("delete from category where cid = ?", cid);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public int updateCategory(Category category) {
        int update;
        try {
            update = runner.update("update category set cname = ? where cid = ?",
                    category.getCname(),category.getCid());
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return update;
    }

    @Override
    public int queryTotalCount() {
        Long count = 0L;
        try {
            count = runner.query("select count(cid) from category", new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count.intValue();
    }

    @Override
    public List<Category> queryPageCategory(int number) {
        List<Category> categories = null;
        try {
            categories = runner.query("select * from category limit ? offset ?",
                    new BeanListHandler<Category>(Category.class),
                    Page.PAGE_SIZE,(number - 1) * Page.PAGE_SIZE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public List<Category> findAllCategory() {
        List<Category> query = null;
        try {
            query = runner.query("select * from category", new BeanListHandler<Category>(Category.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    @Override
    public Category findCategoryById(int cid) {
        String query = null;
        try {
            query = runner.query("select cname from category where cid = ? ", new ScalarHandler<>(), cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Category(cid,query);
    }


}
