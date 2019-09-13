package dao;

import bean.admin.Page;
import bean.admin.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DruidUtils;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());

    @Override
    public int addProduct(Product product) {
        try {
            runner.update("insert into product values(?,?,?,?,?,?,?,?)",
                    product.getPid(),
                    product.getPname(),
                    product.getEstoreprice(),
                    product.getMarkprice(),
                    product.getPnum(),
                    product.getCid(),
                    product.getImgurl(),
                    product.getDesc());
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public int updateProduct(Product product) {
        int update;
        try {
            update = runner.update("update product set cid = ?,pname = ?,estoreprice = ?,markprice = ?,pnum = ?,imgurl = ?,`desc` = ? where pid = ? ",
                    product.getCid(),
                    product.getPname(),
                    product.getEstoreprice(),
                    product.getMarkprice(),
                    product.getPnum(),
                    product.getImgurl(),
                    product.getDesc(),
                    product.getPid());
        } catch (SQLException e) {
            e.printStackTrace();
            return  -1;
        }
        return update;
    }

    @Override
    public int queryTotalCount() {
        Long count = 0L;
        try {
            count = runner.query("select count(pid) from product",new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count.intValue();
    }

    @Override
    public List<Product> queryPageProduct(int number) {
        List<Product> products = null;
        try {
            products = runner.query("select * from product limit ? offset ?",
                    new BeanListHandler<Product>(Product.class),
                    Page.PAGE_SIZE,(number - 1) * Page.PAGE_SIZE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public int deleteProduct(String pid) {
        int delete;
        try {
            delete = runner.update("delete from product where pid = ?",pid);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return delete;
    }

    @Override
    public Product findProductByPid(int pid) {
        Product product = null;
        try {
            product = runner.query("select * from product where pid = ?",
                    new BeanHandler<Product>(Product.class),pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> conditionSearch(String sql, List list) {
        List<Product> query = null;
        try {
            query = runner.query(sql, new BeanListHandler<Product>(Product.class), list.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    @Override
    public List<Product> productTop() {
        List<Product> query = null;
        try {
            query = runner.query("select * from producttop",new BeanListHandler<Product>(Product.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    @Override
    public List<Product> hotProducts() {
        List<Product> query = null;
        try {
            query = runner.query("select * from product_copy1", new BeanListHandler<Product>(Product.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    @Override
    public List<Product> findProductByCid(String cid) {
        List<Product> query = null;
        try {
            query = runner.query("select * from product where cid = ? ", new BeanListHandler<Product>(Product.class),cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    @Override
    public Product findProductById(String pid) {
        Product query = null;
        try {
            query = runner.query("select * from product where pid = ? ",new BeanHandler<Product>(Product.class),pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    @Override
    public List<Product> findProByName(String keyword,int i) {
        String word = null;
        keyword = "'%" + keyword + "%'";
        // Product product = new Product();
        switch (i) {
            case 0:
                word = "pid";
                break;
            case 1:
                word = "pname";
                break;
            case 2:
                word = "estoreprice";
                break;
            case 3:
                word = "`desc`";
                break;
        }
        String sql = "select * from product where " + word + " like " + keyword;
        List<Product> list = null;
        try {
            list = runner.query(sql, new BeanListHandler<Product>(Product.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Product showProductByPid(int pid) {
        Product query = null;
        try {
            query = (Product)runner.query("select * from product where pid = ?",new BeanHandler<Product>(Product.class),pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }
}
