package gxnu.newkids.service;

import gxnu.newkids.AC.ACADT;
import gxnu.newkids.dao.PaperDao;
import gxnu.newkids.dao.WordDao;
import gxnu.newkids.entity.Paper;
import gxnu.newkids.entity.PaperEntity;
import gxnu.newkids.entity.ReadingLogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PaperService {

    protected static Logger logger= LoggerFactory.getLogger(PaperService.class);

    @Resource
    private PaperDao paperDao;
    @Resource
    private WordDao wordDao;


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

    //获取一组文章
    public Map getACPapersByWords(String open_id,String[] words){

        Map map = new HashMap();

        //根据words 查出word相关词，难度小于3的词抛弃
        List<String> w_list = new ArrayList<>(Arrays.asList(words));

        HashMap<String,String> wordsMap = new HashMap<>();

      //  long st0=System.currentTimeMillis();
        List<?> wordsList = wordDao.getW2W(w_list);

        Iterator itw = wordsList.iterator();
        while (itw.hasNext()){
            HashMap<String,String> w2w = (HashMap<String, String>) itw.next();
            wordsMap.put(w2w.get("now_word"),w2w.get("raw_word"));
        }
       // System.out.println(wordsList.size());
      //  long en0=System.currentTimeMillis();
      //  System.out.println(en0-st0);

   //     long st1=System.currentTimeMillis();
        //随机拿一组文章
        List<PaperEntity> pList = paperDao.getACPapers(open_id,1000);

        //AC自动机 根据所有相关词，匹配出相关文章
        TreeMap<String, String> treeMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : wordsMap.entrySet()) {
            //  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            treeMap.put(entry.getKey(),entry.getKey());
        }
      //  long en1=System.currentTimeMillis();
     //   System.out.println(en1-st1);

     //   long st2=System.currentTimeMillis();

        ACADT ACADT = new ACADT();
        //int level = w_list.size()/5;
        int level = 1;
        List<PaperEntity> recommendList =  ACADT.getRecommendList(treeMap,pList,wordsMap,level);

       /* for (PaperEntity p : recommendList){
            System.out.println("推荐文章P_id为:"+p.getpId()+" ac level is "+p.getAc());
        }*/

        map.put("papers",getHighAC(recommendList,10));
   //     long en2=System.currentTimeMillis();

      //  System.out.println(en2 - st2 +"ms");

        map.put("status", 1);

        return map;
    }

    //获取高AC匹配值的文章
    private List<PaperEntity> getHighAC(List<PaperEntity> rList,int size){
        List<PaperEntity> pList = new ArrayList<PaperEntity>();
        int length  = rList.size();
        int num = 0;
        int maxAC = 1 ;

        for (int i = 0; i < length; i++) {
            if(maxAC < rList.get(i).getAc()) maxAC = rList.get(i).getAc();
        }

        for(int i = maxAC ; i > 0 ; i--){
            for (int j = 0; j < length; j++) {
                if(num == size)   return pList;

                if(rList.get(j).getAc() == i){

                    pList.add(rList.get(j));
                    rList.remove(j);
                    length--;
                    num++;
                }
            }
        }

        return pList;
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

    public Map getReadingLogs(String open_id,String p_id){
        Map map = new HashMap();
        map.put("status", 1);
        if(p_id == "-1" ||p_id == "null" ){
            p_id = null;
        }
        List<ReadingLogs> rlList = paperDao.getReadingLogs(open_id,p_id);

        map.put("res",rlList);

        return map;
    }


}
