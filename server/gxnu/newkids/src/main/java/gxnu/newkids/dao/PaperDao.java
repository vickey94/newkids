package gxnu.newkids.dao;

import gxnu.newkids.entity.Paper;
import gxnu.newkids.entity.PaperEntity;
import gxnu.newkids.entity.ReadingLogs;

import java.util.List;

public interface PaperDao {

    List<Paper> getPapers(String open_id,int size);

    List<PaperEntity> getACPapers(String open_id ,int size);

    Paper getOnePaper(String p_id);

    void insertReadingLog(ReadingLogs readingLogs);

    void updatePaperTimes(String p_id ,float p_score);

    List<ReadingLogs> getReadingLogs(String open_id, String p_id);
}
