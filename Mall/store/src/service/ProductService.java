package service;

import bean.admin.Page;
import bean.admin.Product;

import java.util.List;

public interface ProductService {

    int updateProduct(Product product);

    int addProduct(Product product);

    Page findPageProduct(int number);


    int deleteProduct(String checkbox);

    Product findProductByPid(int pid);

    List<Product> conditionSearch(String pid, String cid, String pname, String maxprice, String minprice);

    List<Product> productTop();

    List<Product> hotProducts();

    List<Product> findProductByCid(String cid);

    Product findProductById(String pid);

    List<Product> findProByName(String keyword,int i);

}
