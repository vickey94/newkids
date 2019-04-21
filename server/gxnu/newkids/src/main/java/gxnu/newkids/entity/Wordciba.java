package gxnu.newkids.entity;

public class Wordciba {

    private Integer w_id;
    private String word;
    private String exchange;
    private String voice;
    private Integer w_times;

    public Integer getW_id() {
        return w_id;
    }

    public void setW_id(Integer w_id) {
        this.w_id = w_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public Integer getW_times() {
        return w_times;
    }

    public void setW_times(Integer w_times) {
        this.w_times = w_times;
    }

    @Override
    public String toString() {
        return "Wordciba{" +
                "w_id=" + w_id +
                ", word='" + word + '\'' +
                ", exchange='" + exchange + '\'' +
                ", voice='" + voice + '\'' +
                ", w_times=" + w_times +
                '}';
    }
}
