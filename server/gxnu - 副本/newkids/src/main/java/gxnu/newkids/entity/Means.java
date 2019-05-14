package gxnu.newkids.entity;

//t_means 单词含义表(对应t_words)
public class Means {

    private Integer w_id ;
    private Integer pos_id;
    private String  W_means;

    @Override
    public String toString() {
        return "Means{" +
                "w_id=" + w_id +
                ", pos_id=" + pos_id +
                ", W_means='" + W_means + '\'' +
                '}';
    }

    public Integer getW_id() {
        return w_id;
    }

    public void setW_id(Integer w_id) {
        this.w_id = w_id;
    }

    public Integer getPos_id() {
        return pos_id;
    }

    public void setPos_id(Integer pos_id) {
        this.pos_id = pos_id;
    }

    public String getW_means() {
        return W_means;
    }

    public void setW_means(String w_means) {
        W_means = w_means;
    }
}
