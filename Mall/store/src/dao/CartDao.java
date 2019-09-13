package dao;

import bean.frontEnd.Cart;

import java.util.List;

public interface CartDao {
    int queryCartNumberByUser(int pid, int uid);

    boolean addCart(String pid, String num, int uid);

    void updateCart(int pid, int num, int uid);

    List<Cart> queryCartByUser(int uid);

    boolean deleteItem(String uid,String pid);

    List<Cart> showShoppingCartByUid(String uid);

    int queryProductNum(String uid, String pid);

    boolean modifyNum(String pid, String uid, int num);
}
