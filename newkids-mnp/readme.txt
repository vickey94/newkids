//初始化获取
  wbs:[] //单词书  从服务器
  userInfo:null //用户信息  从本地

//index获取
    /**以下从服务器获取**/

userData{
    userWbs: [], //用户所有单词书历史
    hasStudyNum: 0 ,//用户单词书已经学习个数
    wbWordsNum: 0, //单词书单词个数
    
    hasStudyDay: 0,  //已打卡天数
}
conf{
    cibakey: null,//词霸key
}

   /**以下从本地获取**/
today{
    today_num: 0, //今日背单词个数
    today_words: [], //今日单词
    today_checkin: false, //今日是否打卡

    today_reading: [], //今日阅读历史
    today_study:[] //今日学习的单词
}
conf{
    groupNum: 10, //用户设置每次获取多少个单词
}

    papers: [],//文章

    words:[],//当前单词组
    leftWords:[],//剩余单词


全局变量



