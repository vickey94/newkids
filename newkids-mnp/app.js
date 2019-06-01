//app.js
App({
  onLaunch: function() {

  },
  globalData: {
    pHig:0,
    pDrp:0,
    navH : 0,

    wbs: [], //单词书  从服务器
    userInfo: null, //用户信息  //本地

    papers: [], //文章        //本地
    words: [], //当前单词组    //本地
    leftWords: [], //剩余单词  //本地
    cibaWords:[],  //本地

    userData: {
      userNowWb:null,//用户当前单词书
      userWbs: [], //用户所有单词书历史
      hasStudyNum: 0, //用户单词书已经学习个数
      wbWordsNum: 0, //单词书单词个数
      hasStudyDay: 0, //已打卡天数
    },

    conf: {
      cibakey: null, //词霸key
      groupNum: 20, //用户设置每次获取多少个单词  //本地
   
    },
    today: {
      today_words: [], //今日已背诵单词    //本地
      today_checkin: false, //今日是否打卡 //本地
      today_reading: [], //今日阅读历史    //本地
    },
  }
})