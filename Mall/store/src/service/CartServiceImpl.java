package service;

import bean.admin.Product;
import bean.frontEnd.Cart;
import bean.frontEnd.CartItem;
import bean.frontEnd.Order;
import dao.CartDao;
import dao.CartDaoImpl;
import dao.ProductDao;
import dao.ProductDaoImpl;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartServiceImpl implements CartService {

    private CartDao cartDao = new CartDaoImpl();

    private ProductDao productDao = new ProductDaoImpl();

    @Override
    public boolean addToCart(String pid, String uid, int num) {
        return cartDao.addCart(pid,uid,num);
    }

    @Override
    public Map<Object, Object> showShoppingCartByUid(String uid) {
        List<CartItem>cartItemList = new ArrayList<>();
        List <Cart> list = cartDao.showShoppingCartByUid(uid);
        int totalNum = 0;
        double totalPrice = 0;
        for (Cart cart : list) {
            CartItem cartItem = new CartItem();
            cartItem.setNum(cart.getNum());
            Product product = productDao.showProductByPid(cart.getPid());
            cartItem.setProduct(product);
            cartItemList.add(cartItem);
            totalNum += cart.getNum();
            totalPrice += cart.getNum() * product.getEstoreprice();
        }
        HashMap<Object, Object> cartMap = new HashMap<>();
        cartMap.put("cartItemList",cartItemList);
        cartMap.put("totalNum",totalNum);
        cartMap.put("totalPrice",totalPrice);
        return cartMap;
    }

    @Override
    public boolean deleteItem(String uid, String pid) {
        return cartDao.deleteItem(uid,pid);
    }

    @Override
    public int queryProductNum(String uid, String pid) {
        return cartDao.queryProductNum(uid,pid);
    }

    @Override
    public boolean modifyNum(String pid, String uid, int num) {
        return cartDao.modifyNum(pid,uid,num);
    }
}
