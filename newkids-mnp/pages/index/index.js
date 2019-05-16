// pages/index/index.js
const config = require('../../config.js');
const util = require('../../utils/util.js');
const func = require('../../utils/func.js')
const d = getApp().globalData;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    isOnShow:true,
    pHig: 0,
    pDrp: 0,
    navH: 0,

    hasWord: false, //单词未加载完成禁止点击
    papersLoading :false,

    nowYView: 1,

    wbs: [],
    userInfo: null,

    groupNum: 10,

    leftWords: [],
    papers: [],
    
    userNowWb:null,
    userWbs: [],
    wbWordsNum: 0,
    hasStudyDay: 0,
    hasStudyNum: 0,

    today_words: [],
    today_reading: [],
    today_checkin: false,

    //动画
    aniBox1 : null,
    isSetGroupNum: false,

    onShowAni1: null, //box1
    onShowAni2: null, //box2
    onShowAni3: null, //box3

    sWords : [],
    sWord :null,
    sWordTxt : "",

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    if(d.userInfo == null){
      wx.reLaunch({
        url: '../pre/pre',
      })
    }


    //只加载一次数据 
    let that = this;

    that.setData({
      pHig: d.pHig,
      pDrp: d.pDrp,
      navH: d.navH,

      userInfo: d.userInfo,
      wbs: d.wbs,
    })
  },


  onShow: function () {
    var that = this;
    
    that.getStudyData();
    that.setData({
      nowYView: 1
    });
  
    if (that.data.isOnShow){
      that.onShowAni();

      setTimeout(function () {
        that.setData({
          isOnShow: false
        })
      }, 800)
     
    }
   
  },

  refreshData: function () {
    var that = this;
    
    //自动打卡
    if (!d.today.today_checkin && d.today.today_words.length >= d.conf.groupNum ){

      d.today.today_checkin = true,

      wx.request({
        url: config.service.checkInUrl,
        data:{openId:d.userInfo.openId},
      })

      wx.setStorageSync('today_checkin', true)

    }

    that.setData({
   
      groupNum: d.conf.groupNum,

      leftWords: d.leftWords,
      papers: d.papers,

      userNowWb: d.userData.userNowWb,
      userWbs: d.userData.userWbs,
      wbWordsNum: d.userData.wbWordsNum,
      hasStudyDay: d.userData.hasStudyDay,
      hasStudyNum: d.userData.hasStudyNum,

      today_words: d.today.today_words,
      today_reading: d.today.today_reading,
      today_checkin: d.today.today_checkin,
  
    })
  },

    //获取一组单词，如果文章为空，获取一组文章  
  getOneGroupWord: function () {
  
    console.log("获取一组单词")
    
    let that = this;
    that.setData({hasWord:false})

    wx.request({
      url: config.service.getWordGroupUrl,
      data: {
        open_id: d.userInfo.openId,
        wb_id: d.userData.userNowWb.wb_id,
        size: d.conf.groupNum,
      },
      method: 'GET',
      success(res) {
        console.log(res.data);
        //插入一组新单词时，会重置单词组
        let words = res.data.res;

        d.words = words.slice(0);
        d.leftWords = words.slice(0);
        that.getFromciba();

        that.setData({
          word: words.slice(0),
          leftWords: words.slice(0),
        });
        wx.setStorageSync('words', words);
        wx.setStorageSync('leftWords', words)

        let papers = d.papers;

        if (papers.length == 0) {
          that.getPapers();
        } 
      },
    })
  },

  getPapers: function () {
    console.log("获取一组文章")

    let that = this;
    that.setData({
      papersLoading: true
    })

    let open_id = d.userInfo.openId;
    let words = d.words;
    let t_words = [];

    for (let i = 0; i < words.length; i++) {
      t_words[i] = words.word;
    }

    wx.request({
      url: config.service.getPaperGroupUrl,
      data: {
        open_id: open_id,
        words: t_words,
      },
      method: 'GET',
      success(res) {
        console.log(res.data)
        let papers = res.data.papers;
        d.papers = papers.slice(0);
        wx.setStorageSync("papers", papers);

        that.setData({
          papers: papers,
          papersLoading : false,
        })

      },
      fail(res) { },
    })
  },

  getOnePaper :function(e){
  
    wx.navigateTo({
      url: '../paper/paper?p_id='+e.currentTarget.dataset.value,
    })
  },


  groupNumchange: function (e) {
    let that = this;
    let gn = e.detail.value;

    d.conf.groupNum = gn,

    wx.setStorage({
      key: 'groupNum',
      data: gn,
    })  
   
    that.setData({
      groupNum: gn,
      leftWords: [],
    })
    // d.leftWords = [];
    //wx.setStorageSync('leftWords', [])
    //重新获取单词
    that.getOneGroupWord();
  },


 
  setUserWb: function (e) {
    let that = this;
    let wb_id = e.currentTarget.dataset.value;
    let open_id = d.userInfo.openId;
    wx.request({
      url: config.service.updateWordbookUrl,
      data: {
        open_id: open_id,
        wb_id: wb_id,
      },
      method: 'GET',
      success(res) {
        console.log('更新用户单词',res)

        d.leftWords = [],
        //获取一组新单词
        that.getStudyData();
      
      },
      fail(err) {
        console.log(err)
      }
    })
  },

  

  getStudyData:function(){ 

    let that = this;
    that.setData({hasWord:false})
    //获取用户学习信息，
    let initSync = new Promise((resolve, reject) => {
      wx.request({
        url: config.service.getUserStudyUrl,
        data: {
          open_id: d.userInfo.openId,
        },
        method: 'GET',
        success(res) {
          resolve(res);
        },
        fail(err) {
          console.log(err)
          reject(err)
        },
       
      })
    })

    //在同步结束后
    initSync.then((res) => {

      console.log('用户学习数据', res.data);
   //   d.conf.cibakey = res.data.cibakey;
      d.userData.userNowWb = res.data.userWb;
      d.userData.userWbs = res.data.userWbs;
      d.userData.hasStudyNum = res.data.hasStudyNum;
      d.userData.wbWordsNum = res.data.wordsNum;
      d.userData.hasStudyDay = res.data.hasStudyDay;

      //刷新数据
      that.refreshData();
     

      if (d.leftWords.length == 0) {
        that.getOneGroupWord();
      }else{
        if(d.cibaWords.length ==0)
        that.getFromciba()
        that.setData({
         
            hasWord: true
       
        })
      }

    })

  },

  studyWords :function(){
    wx.navigateTo({
      url: '../word/word?type=1&word=null',
    })
  },

  getFromciba(){

    let that = this;
    that.setData({
      hasWord: false,
    })
    
    d.cibaWords = [];
    for(let i = 0 ; i < d.leftWords.length;i++){
      func.getFromciba(d.leftWords[i].word, that.cibaCallback);
    }
  },

  cibaCallback: function (cibaWord) {
  let that = this;
 
   d.cibaWords.push(cibaWord);
   
   if(d.cibaWords.length == d.leftWords.length){
     that.setData({
       hasWord:true,
     })
     wx.setStorageSync("cibaWord", cibaWord);
     console.log('已从ciba获取所有单词');
   }
  },


  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },



  /**动画部分 */
  
  setGroupNum: function () {
    let that = this;
    let animation1 = wx.createAnimation({
      duration: 500, //动画时长
      timingFunction: 'ease', //动画的效果 
      delay: 100 //动画延迟时间，单位 ms
    });
    animation1.opacity(0).step()

    let animation2 = wx.createAnimation({
      duration: 700, //动画时长
      timingFunction: 'ease', //动画的效果 
      delay: 10 //动画延迟时间，单位 ms
    });
    animation2.opacity(1).step()

    this.setData({
      aniBox1: animation1.export(),
    })

    setTimeout(function () {
      that.setData({
        isSetGroupNum: !that.data.isSetGroupNum,
      })
    }, 450)

    setTimeout(function () {
      that.setData({
        aniBox1: animation2.export(),
      })
    }, 550)
  },

  onShowAni: function () {
    let that = this;
    let aniHidden1 = func.getHiddenAni();
    aniHidden1.opacity(0).step()

    let aniHidden2 = func.getHiddenAni();
    aniHidden2.opacity(0).step()

    let aniHidden3 = func.getHiddenAni();
    aniHidden3.opacity(0).step()

    that.setData({
      onShowAni1: aniHidden1.export(), //box1
      onShowAni2: aniHidden2.export(), //box2
      onShowAni3: aniHidden3.export(), //box3
    })

    let aniShow1 = func.getShowAni(1000);
    aniShow1.opacity(1).step()

    let aniShow2 = func.getShowAni(1000);
    aniShow2.opacity(1).step()

    let aniShow3 = func.getShowAni(1000);
    aniShow3.opacity(1).step()

    setTimeout(function () {
      that.setData({
        onShowAni1: aniShow1.export(), //box1
      })
    }, 200)

    setTimeout(function () {
      that.setData({
        onShowAni2: aniShow2.export(), //box1
      })
    }, 800)

    setTimeout(function () {
      that.setData({
        onShowAni3: aniShow3.export(), //box1
      })
    }, 1400)
  },


  touchStart: function (e) {
    func.touchStart(e)
  },
  touchEnd: function (e) {
    let that = this;
    let d = this.data;
    let moveY = 200 / d.pDrp;
    let moveX = 300 / d.pDrp;

    let y = d.nowYView;

    //如果滑动长度大于pTop,则 up,down,left,right,
    let act = func.touchEnd(e, moveX, moveY, 3,20)
    //  console.log(act)

    //下拉进设置，上滑进主页，右划背单词，左滑看文章

    if (y == 1) {
      if (act == "down") {
        y = 0;
      } 
    }
    if (y == 0) {
      if (act == "up") {
        y = 1;
      }
    }
    that.setData({
      nowYView: y
    })
  },


  searchWord:function(e){
    
    let that = this;
    let sWord = e.detail.value;

    //查词
    wx.request({
      url: config.service.searchwordsUrl,
      data:{
        w:sWord,
      },
      success(res){
      //  console.log(res.data.res);
        that.setData({
          sWords : res.data.res
        })
      }

    })
  },

  searchWordFromCiba1: function (e) {
    let that = this;
    let w = e.detail.value;

    if(that.data.sWords.length>0)
     that.toWord(w);
     else{
      wx.showToast({
        title: '查无此单词',
        icon: 'none',
        duration: 2000
      })
      that.setData({
        sWords: [],
        sWordTxt: "",
      })
     }

  },

  searchWordFromCiba2: function(e){
    let that = this;
    let w = e.currentTarget.dataset.value;
    if (that.data.sWords.length > 0)
    that.toWord(w);
  },

  toWord:function(w){
    let that = this;
    that.clearSearch();

    wx.navigateTo({
      url: '../word/word?word=' + w + '&type=-1',
    })
  },

  clearSearch:function(e){
    let that = this;
    that.setData({
      sWords: [],
      sWordTxt: "",
    })
  },

})