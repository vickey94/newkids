package gxnu.newkids.service;

import gxnu.newkids.dao.PaperDao;
import gxnu.newkids.entity.Paper;
import gxnu.newkids.entity.ReadingLogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

@Service
public class PaperService {

    protected static Logger logger= LoggerFactory.getLogger(PaperService.class);

    @Resource
    private PaperDao paperDao;

    //获取一组文章
    public Map getPapersByWords(String open_id,String[] words){
        Map map = new HashMap();

        //根据words 查出word相关词，难度小于3的词抛弃

        //随机拿一组文章

        //根据所有相关词，匹配出相关文章

        List<Paper> pList = paperDao.getPapers(open_id,10);
        map.put("papers",pList);

        map.put("status", 1);


        return map;
    }

    //获取一篇文章详情
    public Map getOnePaper(String p_id){
        Map map = new HashMap();

        map.put("status", 1);

        Paper paper = paperDao.getOnePaper(p_id);
        map.put("paper",paper);


        return map;
    }

    //文章打卡
    public Map finishPaper(ReadingLogs readingLogs,String p_id,float p_score){
        Map map = new HashMap();

        map.put("status", 1);

        //文章打卡时，插入p_logs,更新文章阅读次数，评分

        paperDao.insertReadingLog(readingLogs);
        paperDao.updatePaperTimes(p_id,p_score);

        return map;

    }
}
