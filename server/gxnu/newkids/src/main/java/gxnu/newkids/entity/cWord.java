package gxnu.newkids.entity;

import java.util.List;

public class cWord {
    private String word;
    private Baseword bw;
    private CibaWord cibaWord;
    private WordLogs wLogs = null;
    private List<Means> means = null;
    private List<Sent> sents = null;

    public CibaWord getCibaWord() {
        return cibaWord;
    }

    public void setCibaWord(CibaWord cibaWord) {
        this.cibaWord = cibaWord;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Baseword getBw() {
        return bw;
    }

    public void setBw(Baseword bw) {
        this.bw = bw;
    }

    public WordLogs getwLogs() {
        return wLogs;
    }

    public void setwLogs(WordLogs wLogs) {
        this.wLogs = wLogs;
    }

    public List<Means> getMeans() {
        return means;
    }

    public void setMeans(List<Means> means) {
        this.means = means;
    }

    public List<Sent> getSents() {
        return sents;
    }

    public void setSents(List<Sent> sents) {
        this.sents = sents;
    }
}
