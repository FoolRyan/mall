package bean.frontEnd;

import bean.admin.Product;

public class Cart {
    private int uid;

    private int pid;

    private int num;

    public Cart() {
    }

    public Cart(int uid, int pid, int num) {
        this.uid = uid;
        this.pid = pid;
        this.num = num;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int id) {
        this.uid = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

}
