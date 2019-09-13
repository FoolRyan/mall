package bean;

public class search {

    private int pid;

    private int cid;

    private String pname;

    private String maxprice;

    private String minprice;

    public search() {
    }

    public search(int pid, int cid, String pname, String maxprice, String minprice) {
        this.pid = pid;
        this.cid = cid;
        this.pname = pname;
        this.maxprice = maxprice;
        this.minprice = minprice;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(String maxprice) {
        this.maxprice = maxprice;
    }

    public String getMinprice() {
        return minprice;
    }

    public void setMinprice(String minprice) {
        this.minprice = minprice;
    }

    @Override
    public String toString() {
        return "search{" +
                "pid=" + pid +
                ", cid=" + cid +
                ", pname='" + pname + '\'' +
                ", maxprice='" + maxprice + '\'' +
                ", minprice='" + minprice + '\'' +
                '}';
    }
}
