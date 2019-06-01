package gxnu.newkids.entity;

//t_means 单词含义表(对应t_words)
public class Means {

    private Integer w_id ;
    private Integer pos_id;
    private String w_means;
    private String pos_name;
    private String pos_means;

    @Override
    public String toString() {
        return "Means{" +
                "w_id=" + w_id +
                ", pos_id=" + pos_id +
                ", w_means='" + w_means + '\'' +
                ", pos_name='" + pos_name + '\'' +
                ", pos_means='" + pos_means + '\'' +
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
        return w_means;
    }

    public void setW_means(String w_means) {
        this.w_means = w_means;
    }

    public String getPos_name() {
        return pos_name;
    }

    public void setPos_name(String pos_name) {
        this.pos_name = pos_name;
    }

    public String getPos_means() {
        return pos_means;
    }

    public void setPos_means(String pos_means) {
        this.pos_means = pos_means;
    }
}
