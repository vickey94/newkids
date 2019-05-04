package gxnu.newkids.entity;
//t_paper_wb 文章-单词书表
public class PaperWb {

    private Integer p_wb_id;
    private Integer p_id;
    private String wb_id;
    private float p_wb_score;

    @Override
    public String toString() {
        return "PaperWb{" +
                "p_wb_id=" + p_wb_id +
                ", p_id=" + p_id +
                ", wb_id=" + wb_id +
                ", p_wb_score=" + p_wb_score +
                '}';
    }

    public Integer getP_wb_id() {
        return p_wb_id;
    }

    public void setP_wb_id(Integer p_wb_id) {
        this.p_wb_id = p_wb_id;
    }

    public Integer getP_id() {
        return p_id;
    }

    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }

    public void setWb_id(String wb_id) {
        this.wb_id = wb_id;
    }

    public float getP_wb_score() {
        return p_wb_score;
    }

    public void setP_wb_score(float p_wb_score) {
        this.p_wb_score = p_wb_score;
    }
}
