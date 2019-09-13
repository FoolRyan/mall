package dao;

import bean.admin.Product;

import java.util.List;

public interface ProductDao {
    int addProduct(Product product);

    int updateProduct(Product product);


    int queryTotalCount();

    List<Product> queryPageProduct(int number);

    int deleteProduct(String pid);

    Product findProductByPid(int pid);

    List<Product> conditionSearch(String sql, List list);

    List<Product> productTop();

    List<Product> hotProducts();

    List<Product> findProductByCid(String cid);

    Product findProductById(String pid);

    List<Product> findProByName(String keyword,int i);

    Product showProductByPid(int pid);

}
