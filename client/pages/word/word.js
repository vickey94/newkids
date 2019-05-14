const app = getApp()
const config = require('../../config.js')
const util = require('../../utils/util.js')
const innerAudioContext = wx.createInnerAudioContext();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: null,
    userNowWb: null,
    
    cibakey: "9D579F3386B678CF309BC6AD2B03A376",
    cibaWord: null,
    cibaSents: null,
  
    isPlay_en: false,
    isPlay_am: false,

    word: null,

    id: null,
    bw: null,
    words: null,
    hasStudyWords: null,
    leftWords: null,
    isRem: false,
    hasNum: 0,
    totalNum: 0,
    

    w_score:0,
    w_spend_time:0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(e) {
    var word = e.word;
    word = word.toLowerCase(); //小写
    var that = this;
    that.setData({
      word: word
    })

    //注册播放回调函数
    innerAudioContext.onPlay(() => {
      // console.log('开始播放')
    })

    innerAudioContext.onEnded(() => {
      //  console.log('播放结束')
      var isPlay_am = false;
      var isPlay_en = false;
      var sents = that.data.cibaSents;
      for (var i = 0; i < sents.length; i++) {
        sents[i].isPlay = false;
      }
      that.setData({
        isPlay_am: isPlay_am,
        isPlay_en: isPlay_en,
        cibaSents: sents
      })
    })
    innerAudioContext.onError((res) => {
      console.log(res.errMsg)
      console.log(res.errCode)
    })

    var cibakey = that.data.cibakey;
    wx.request({
      url: config.service.cibaUrl,
      data: {
        w: word,
        key: cibakey,
        type: "json"
      },
      method: 'GET',
      success(res) {
        // console.log(res.data)
        that.setData({
          cibaWord: res.data
        });
      },
    })

    wx.request({
      url: config.service.cibaUrl,
      data: {
        w: word,
        key: cibakey,
        type: "xml"
      },
      method: 'GET',
      success(res) {
        //console.log(res.data)
        var xmlDoc = res.data;
        var cibaSents = util.xml2word(xmlDoc);
        //  console.log(cibaSents)
        that.setData({
          cibaSents: cibaSents
        });
      },
    })

    //如果只是单词展示页面，则设置id为null
    if (e.id != '-1') {
      var userNowWb = wx.getStorageSync('userNowWb');
 
      that.setData({
        id: e.id,
        bw: JSON.parse(e.bw),
        isRem: e.isRem,
        w_score:e.w_score,
        w_spend_time:e.w_spend_time,
        userInfo: app.globalData.userInfo,
        userNowWb:userNowWb,
      })
//预留
      var words = wx.getStorageSync('words');
      var hasStudyWords = wx.getStorageSync('hasStudyWords');
      var leftWords = wx.getStorageSync('leftWords');
      var hasNum = hasStudyWords.length;
      var totalNum = words.length;

      that.setData({
        words: words,
        hasStudyWords: hasStudyWords,
        leftWords: leftWords,
        hasNum: hasNum,
        totalNum: totalNum,
      })
    }
  },




  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  playAudio: function(e) {

    var that = this;
    var id = e.currentTarget.dataset.value;
    var word = that.data.cibaWord;
    var sents = that.data.cibaSents;

    var isPlay_am = false;
    var isPlay_en = false;

    for (var i = 0; i < sents.length; i++) {
      sents[i].isPlay = false;
    }
    var src = "";
    if (id == 'am') {
      isPlay_am = true;
      src = word.symbols[0].ph_am_mp3;
    } else if (id == 'en') {
      isPlay_en = true;
      src = word.symbols[0].ph_en_mp3;
    } else {
      sents[id].isPlay = true;
      src = 'http://dict.youdao.com/dictvoice?audio=' + sents[id].orig;
    }

    innerAudioContext.src = src;
    innerAudioContext.play();

    that.setData({
      isPlay_am: isPlay_am,
      isPlay_en: isPlay_en,
      cibaSents: sents,
    })
  },

  reSetWord:function(){
      this.setData({
        isRem :false
      })
  },

  nextWord: function() {

    this.updateUserWord();

    wx.redirectTo({
      url: '../study/study',
    })
  },

  updateUserWord: function() {
    var d = this.data;
    var isRem = d.isRem;
    var id = d.id;
  
    //如果背下来了，则更新数据库，更新存储，更新单词个数
    if (isRem == 'true') {
    
     //更新本地数据
      var hasStudyWords = wx.getStorageSync('hasStudyWords');
      var leftWords = wx.getStorageSync('leftWords');
  
      var length = leftWords.length;

      var word = leftWords.splice(id, 1); ///从数组中添加/删除项目，然后返回被删除的项目。
      hasStudyWords.push(word);
      console.log('leftWords',leftWords);
      console.log('hasStudyWords',hasStudyWords);
    
      wx.setStorageSync('leftWords', leftWords);
      wx.setStorageSync('hasStudyWords', hasStudyWords);
     
     //更新今日数据
      var today_num = wx.getStorageSync('today_num')
      var today_words = wx.getStorageSync('today_words')
      today_words.push(word);
      today_num++;
      wx.setStorageSync('today_num', today_num);
      wx.setStorageSync('today_words', today_words);
     
     //更新服务器
      var open_id = d.userInfo.openId;
     
      var w = {};
      w.bw_id = d.bw.bw_id;
      w.open_id = open_id;
      w.w_score = d.w_score;
      w.w_spend_time = d.w_spend_time;
      var wb_id = d.userNowWb.wb_id;
      w.wb_id = wb_id;

      wx.request({
        url: config.service.finishOneWordUrl,
        method: 'GET',
        data: {
          open_id: open_id,
          wb_id: wb_id,
          word_type: 1, //单词类型，1是新词，2是历史词汇
          wordLogs: w
        },  
      })
    }
  },


  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {
 
    var word= this.data.cibaWord.word_name
   return{
     title: word+" | newKids",
     path: 'pages/word/word?word=' +word + '&id=-1',
   } 
  }
})