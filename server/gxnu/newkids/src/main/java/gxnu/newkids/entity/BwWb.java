package gxnu.newkids.entity;

//t_bw_wb 单词单词书关联表（单词对应的单词书）
public class BwWb {

    private String bw_wb_id;

    private String bw_id;

    private String wb_id;

    private float bw_wb_score;

    @Override
    public String toString() {
        return "BwWb{" +
                "bw_wb_id='" + bw_wb_id + '\'' +
                ", bw_id='" + bw_id + '\'' +
                ", wb_id='" + wb_id + '\'' +
                ", bw_wb_score=" + bw_wb_score +
                '}';
    }

    public String getBw_wb_id() {
        return bw_wb_id;
    }

    public void setBw_wb_id(String bw_wb_id) {
        this.bw_wb_id = bw_wb_id;
    }

    public String getBw_id() {
        return bw_id;
    }

    public void setBw_id(String bw_id) {
        this.bw_id = bw_id;
    }

    public String getWb_id() {
        return wb_id;
    }

    public void setWb_id(String wb_id) {
        this.wb_id = wb_id;
    }

    public float getBw_wb_score() {
        return bw_wb_score;
    }

    public void setBw_wb_score(float bw_wb_score) {
        this.bw_wb_score = bw_wb_score;
    }
}
