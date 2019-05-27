package gxnu.newkids.AC;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import gxnu.newkids.entity.PaperEntity;

import java.util.*;

public class ACADT {
    public  List<PaperEntity> getRecommendList(TreeMap<String, String> treeMap, List<PaperEntity> listText, HashMap<String,String> wordMap, Integer listLength){

        List<PaperEntity> recommendList = new ArrayList<>();
        for(PaperEntity p : listText){
            int level = isPaperByAC(treeMap,p.getPaper(),wordMap,listLength);
            if (level != 0){
                p.setAc(level);
                recommendList.add(p);
            }
        }
        return recommendList;
    }

    public static int isPaperByAC(TreeMap<String, String> treeMap, String text, HashMap<String,String> wordMap,Integer listLength){
        AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
        acdat.build(treeMap);
        Set<String> set = new HashSet<>();
        acdat.parseText(text, (begin, end, value) -> {
            set.add(wordMap.get(value));
          //  System.out.printf("[%d:%d]=%s\n", begin, end, value);
        });

        if (set.size() >= listLength) {

            return set.size();
        }else {
            return 0;
        }

    }

    public static void ACtest(TreeMap<String, String> wordMap, String text){
        AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
        acdat.build(wordMap);

        acdat.parseText(text, (begin, end, value) -> {
//            if (wordMap.containsKey(""))
            System.out.printf("[%d:%d]=%s\n", begin, end, value);
        });
    }
   /* public static void main(String[] arfs){
        TreeMap<String, String> wordMap = new TreeMap<>();
        String text = "BRUSSELS, April 26 (Xinhua) -- Ambassador Zhang Ming, head of the Chinese Mission to the European Union (EU), has dismissed the accusations regarding the China-proposed Belt and Road Initiative (BRI), saying the so-called China's debt trap \"is not a suitable hat for China, nor for the BRI.\"\n" +
                "\"Debt is a global problem with a long history. The BRI is just six years old. Do you think it makes sense to blame a young initiative for an old problem?\" he said on Thursday in an interview with the BBC, replying to a question on the issue of the trap regarding debt that some countries which have signed up to the initiative can get into.\n" +
                "\"No one is in a better position to tell the true story than the BRI participants themselves,\" he said, adding that many leading officials, business communities and the general public of the BRI countries have spoken up and refuted the argument with facts and figures.\n" +
                "Zhang cited the finance secretary of the Philippines as stating recently in public that the debts owed to China account for only 0.65 percent of the country's total debt.\n" +
                "\"The BRI countries not only borrow from China, but also from other countries. It's not fair to say that someone else's money is a sweet pie, while China's money is a trap,\" he noted.\n" +
                "Commenting on criticisms of the motivations of Beijing with this initiative, the Ambassador said: \"The logic of the BRI is not about seeking supremacy. In history, we had the ancient Silk Road, a symbol for openness and mutual learning. The BRI aims to revive that spirit...The BRI aims to offer a solution to global challenges and an open platform for global cooperation. It focuses on connectivity, and would hopefully give a boost to global growth.\"\n" +
                "He reiterated that BRI cooperation is not government directed, but market driven. Enterprises are the major players.\n" +
                "\"We welcome all the members of the world community to take part in the BRI in different ways. We welcome constructive criticisms, and also the contribution of ideas, suggestions and advice to advance the cooperation in the framework of BRI,\" Zhang added.";
        wordMap.put("bett","bett");
        wordMap.put("criticisms","criticisms");
        wordMap.put("world","world");
        ACtest(wordMap,text);
    }*/
}
