package gxnu.newkids.entity;

//t_basewords 基础词汇表（此表包含各个单词书的所有单词）
public class BasewordWlogs {

    private String bw_id;
    private String word;
    private Integer bw_freq;
    private Integer bw_diff;
    private Integer bw_count;
    private Integer bw_ack_rate;
    private Integer bw_score;
    private WordLogs wordLogs;

    @Override
    public String toString() {
        return "Baseword{" +
                "bw_id='" + bw_id + '\'' +
                ", word='" + word + '\'' +
                ", bw_freq=" + bw_freq +
                ", bw_diff=" + bw_diff +
                ", bw_count=" + bw_count +
                ", bw_ack_rate=" + bw_ack_rate +
                ", bw_score=" + bw_score +
                '}';
    }




    public String getBw_id() {
        return bw_id;
    }

    public void setBw_id(String bw_id) {
        this.bw_id = bw_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getBw_freq() {
        return bw_freq;
    }

    public void setBw_freq(Integer bw_freq) {
        this.bw_freq = bw_freq;
    }

    public Integer getBw_diff() {
        return bw_diff;
    }

    public void setBw_diff(Integer bw_diff) {
        this.bw_diff = bw_diff;
    }

    public Integer getBw_count() {
        return bw_count;
    }

    public void setBw_count(Integer bw_count) {
        this.bw_count = bw_count;
    }

    public Integer getBw_ack_rate() {
        return bw_ack_rate;
    }

    public void setBw_ack_rate(Integer bw_ack_rate) {
        this.bw_ack_rate = bw_ack_rate;
    }

    public Integer getBw_score() {
        return bw_score;
    }

    public void setBw_score(Integer bw_score) {
        this.bw_score = bw_score;
    }

    public WordLogs getWordLogs() {
        return wordLogs;
    }

    public void setWordLogs(WordLogs wordLogs) {
        this.wordLogs = wordLogs;
    }
}
