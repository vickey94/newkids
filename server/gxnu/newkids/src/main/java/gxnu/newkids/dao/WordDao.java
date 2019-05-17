package gxnu.newkids.dao;

import gxnu.newkids.entity.Baseword;

import gxnu.newkids.entity.WordLogs;

import java.util.List;
import java.util.Map;

//@Mapper
public interface WordDao {

    List<Baseword> getWordGroup(String open_id,String wb_id,int bw_diff,int size);

    List<Baseword> getHisWordGroup(String open_id,int bw_diff,int w_sorce,int size);

    List<Map> getWordcibaGroup(String open_id,String wb_id,int bw_diff,int size);

    List<Map> getWordciba(String word);

    void insertWordLogs(List<WordLogs> wordLogs);

    void updateUserWbRate(int rate ,String open_id,String wb_id);

    void updateWordLogs(List<WordLogs> wordLogs);

    void updateBaseWordsCount(List<WordLogs> wordLogs);

    List<String> getW2W(String raw_word);

    List<Map> searchWords(String word);

}
