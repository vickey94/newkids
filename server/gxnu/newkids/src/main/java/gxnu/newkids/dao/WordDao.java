package gxnu.newkids.dao;

import gxnu.newkids.entity.Baseword;
import gxnu.newkids.entity.Wordciba;

import java.util.List;
import java.util.Map;

//@Mapper
public interface WordDao {


    List<Baseword> getWordGroup(String open_id,String wb_id,int bw_diff,int size);

    List<Baseword> getHisWordGroup(String open_id,String wb_id,int bw_diff,int size);

    List<Map> getWordcibaGroup(String open_id,String wb_id,int bw_diff,int size);

    List<Map> getWordciba(String word);
}
