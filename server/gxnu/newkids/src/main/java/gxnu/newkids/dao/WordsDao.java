package gxnu.newkids.dao;

import gxnu.newkids.entity.Wordciba;
import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface WordsDao {

    Wordciba getWordcibaByWord(String word);
}
