package gxnu.newkids.entity;

//t_pos 词性表(对应t_words)
public class Pos {

    private Integer pos_id;

    private String pos_name;

    private String pos_means;

    @Override
    public String toString() {
        return "Pos{" +
                "pos_id=" + pos_id +
                ", pos_name='" + pos_name + '\'' +
                ", pos_means='" + pos_means + '\'' +
                '}';
    }

    public Integer getPos_id() {
        return pos_id;
    }

    public void setPos_id(Integer pos_id) {
        this.pos_id = pos_id;
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
