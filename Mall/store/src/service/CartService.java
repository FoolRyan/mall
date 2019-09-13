package service;

import java.util.Map;

public interface CartService {


    boolean addToCart(String pid, String uid, int num);


    Map<Object,Object> showShoppingCartByUid(String uid);

    boolean deleteItem(String uid, String pid);

    int queryProductNum(String uid, String pid);

    boolean modifyNum(String pid, String uid, int num);
}
