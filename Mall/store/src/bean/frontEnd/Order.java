package bean.frontEnd;

import bean.admin.User;

import java.util.List;

public class Order {
    //订单的编号
    private String oid;
    //订单的金额
    private String money;

    private String recipient;

    private String tel;

    private String address;
    //订单的状态，state ，0表示未付款，1表示待发货，2表示待签收，3表示待评价
    private String state;

    private String ordertime;

    private String username;

    private int uid;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String  money) {
        this.money = money;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String  getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid='" + oid + '\'' +
                ", money=" + money +
                ", recipient='" + recipient + '\'' +
                ", tel=" + tel +
                ", address='" + address + '\'' +
                ", state=" + state +
                ", ordertime='" + ordertime + '\'' +
                ", username='" + username + '\'' +
                ", uid=" + uid +
                ", user=" + user +
                '}';
    }
}
