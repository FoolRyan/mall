package service;

import bean.admin.Admin;
import bean.admin.Category;
import bean.admin.Page;
import bean.admin.Product;
import dao.CategoryDao;
import dao.CategoryDaoImpl;
import dao.ProductDao;
import dao.ProductDaoImpl;
import util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductDao productDao = new ProductDaoImpl();

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public int updateProduct(Product product) {
        return productDao.updateProduct(product);
    }

    @Override
    public int addProduct(Product product) {
        return productDao.addProduct(product);
    }

    @Override
    public Page findPageProduct(int number) {
        int totalCount = productDao.queryTotalCount();
        List<Product> products = productDao.queryPageProduct(number);
        Iterator iterator = products.iterator();
        while (iterator.hasNext()) {
            Product next = (Product) iterator.next();
            int cid = next.getCid();
            Category categoryById = categoryDao.findCategoryById(cid);
            next.setCategory(categoryById);
        }
        Page page = new Page();
        page.setCurrentPage(number);
        page.setNextPage(number + 1);
        page.setPreviousPage(number - 1);
        page.setTotalCount(totalCount);
        page.setList(products);
        int totalPageCount = (int) Math.ceil(totalCount * 1.0 / Page.PAGE_SIZE);
        page.setTotalPageCount(totalPageCount);
        return page;
    }

    private Product findCategoryByCid(int pid) {
        Product productByPid = productDao.findProductByPid(pid);
        Category categoryById = categoryDao.findCategoryById(productByPid.getCid());
        productByPid.setCategory(categoryById);
        return productByPid;
    }

    @Override
    public int deleteProduct(String pid) {
        return productDao.deleteProduct(pid);
    }

    @Override
    public Product findProductByPid(int pid) {
        return productDao.findProductByPid(pid);
    }

    @Override
    public List<Product> findProByName(String keyword,int i) {
        return productDao.findProByName(keyword,i);
    }
    @Override
    public List<Product> conditionSearch(String pid, String cid, String pname, String maxprice, String minprice) {
        // 模糊查询
        String sql = "select * from product where 1 = 1";
        List list = new ArrayList();
        if (!StringUtils.isEmpty(pid)) {
            sql += " and pid = ? ";
            list.add(pid);
        }
        if (!StringUtils.isEmpty(cid)) {
            sql += " and cid = ? ";
            list.add(cid);
        }
        if (!StringUtils.isEmpty(pname)) {
            sql += " and pname like ? ";
            list.add("%" + pname + "%");
        }
        if (!StringUtils.isEmpty(maxprice)) {
            double max = Double.parseDouble(maxprice);
            sql += " and estoreprice <= ? ";
            list.add(max);
        }
        if (!StringUtils.isEmpty(minprice)) {
            double min = Double.parseDouble(minprice);
            sql += " and estoreprice >= ? ";
            list.add(min);
        }
        return productDao.conditionSearch(sql,list);
    }

    @Override
    public List<Product> productTop() {
        return productDao.productTop();
    }

    @Override
    public List<Product> hotProducts() {
        return productDao.hotProducts();
    }

    @Override
    public List<Product> findProductByCid(String cid) {
        return productDao.findProductByCid(cid);
    }

    @Override
    public Product findProductById(String pid) {
        return productDao.findProductById(pid);
    }



}
