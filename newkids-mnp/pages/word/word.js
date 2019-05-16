// pages/word/word.js
const d = getApp().globalData;
const config = require('../../config.js')
const util = require('../../utils/util.js')
const func = require('../../utils/func.js')
const innerAudioContext = wx.createInnerAudioContext();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    pHig: 0,
    pDrp: 1,

    nowYView: 1,

    type: -1, //1背单词，2复习，-1查单词

    wb_name: '',
    percent: 0,

    isPlay_en: false,
    isPlay_am: false,
    isPlay_sents: false,

    w_score: 0,
    w_spend_time: 0,

    userInfo: null,
    userNowWb: null,
    cibakey: "9D579F3386B678CF309BC6AD2B03A376",
    cibaWord: null,

    word: null,
    words: [],
    nowWord: null,
    leftWords: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(e) {
    let type = parseInt(e.type);
    let word = e.word;
  
    //设置本次全局数据
    let that = this;

    this.setData({
      type :  type,
      pHig: d.pHig,
      pDrp: d.pDrp,
    })

    //如果只是单词展示页面，则设置id为0
    if (type == 1 ) {
      let userNowWb = d.userData.userNowWb;
      let wbs = d.wbs;
      let wb_name = '';

      for (let i = 0; i < wbs.length; i++) {
        if (wbs[i].wb_id == userNowWb.wb_id) {
          wb_name = wbs[i].wordbook
          break;
        }
      }

      that.setData({
        userInfo: d.userInfo,
        userNowWb: userNowWb,
        wb_name: wb_name,
      })

      that.getOneWord();

    } else if (type == -1 ) {
      //that.getOlinecibaWord(word, that.data.cibakey);
      console.log(word);
      func.getFromciba(word, that.cibaCallback);
    }


    //注册播放回调函数
    innerAudioContext.onPlay(() => {
      // console.log('开始播放')
    })

    innerAudioContext.onEnded(() => {
      //  console.log('播放结束')
      let isPlay_am = false;
      let isPlay_en = false;
      let isPlay_sents = false;
      let sents = that.data.cibaSents;
      /*    for (let i = 0; i < sents.length; i++) {
            sents[i].isPlay = false;
          }*/
      that.setData({
        isPlay_am: isPlay_am,
        isPlay_en: isPlay_en,
        isPlay_sents: isPlay_sents,
        // cibaSents: sents,
      })
    })
    innerAudioContext.onError((res) => {
      console.log(res.errMsg)
      console.log(res.errCode)
    })
  },


  onShow: function() {
    this.setData({
      nowYView: 0,
    })
  },



  getOneWord: function() {
    let that = this;

    func.studyStart();

    console.log(d.leftWords)
    //func.studyStart(); //开始计时

    let w_num = d.words.length;
    let lw_num = d.leftWords.length;

    that.setData({
      percent: (((w_num - lw_num) / w_num) * 100).toFixed(2),
    })

    if (lw_num > 0) { //剩余单词大于0
      let nowWord = d.leftWords[lw_num - 1]; //获取最后一个单词
      that.setData({
        nowWord: nowWord
      })
      that.getcibaWord(nowWord.word);
    } else {
      that.endTip();
    }
  },

  isRem: function() {
    let that = this;

    d.leftWords.pop();
  
    d.today.today_words.push(that.data.nowWord);

    wx.setStorageSync('leftWords', d.leftWords);
    wx.setStorageSync('today_words', d.today.today_words);

   
    let w = {};
   
    w.bw_id = that.data.nowWord.bw_id;
    w.open_id = d.userInfo.openId;
    w.w_score = that.data.w_score;
    w.w_spend_time = func.studyEnd();
    w.wb_id = d.userData.userNowWb.wb_id;

    console.log(w)
    console.log(d.userData)
    //单词背诵完成
    wx.request({
      url: config.service.finishOneWordUrl,
      data: {
        open_id: d.userInfo.openId,
        wb_id: d.userData.userNowWb.wb_id,
        word_type: 1, //单词类型，1是新词，2是历史词汇
        wordLogs: w
            
      },
      method: 'GET',
      success(res) {},
    })
  },
  isNotRem: function() {
    let that = this;
  
    d.leftWords.pop();
    d.leftWords.splice(0, 0, that.data.nowWord);
    wx.setStorageSync('leftWords', d.leftWords);
  },


  getcibaWord: function (word) {
    word = word.toLowerCase(); //小写
   
    let that = this;
    for(let i = 0 ; i < d.cibaWords.length;i++){
    
      if (d.cibaWords[i].word_name == word){
        that.setData({
          cibaWord : d.cibaWords[i]
        })
     
        break;
      }
    }

  },
/*
  getOlinecibaWord: function(word, cibakey) {

    let that = this;
    word = word.toLowerCase(); //小写
    let cibaWord = {};
    //请求单词
    wx.request({
      url: config.service.cibaUrl,
      data: {
        w: word,
        key: cibakey,
        type: "json"
      },
      method: 'GET',
      success(res) {
       
       console.log(res.data)
      
        cibaWord = res.data;
 
        //请求例句
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
            let xmlDoc = res.data;
            let cibaSents = util.xml2word(xmlDoc);
            //  console.log(cibaSents)
            cibaWord.sents = cibaSents
            that.setData({
              cibaWord: cibaWord
            });
          },
        })
      },
    })
  },*/

  cibaCallback:function(res){
    console.log(res)
    this.setData({
      cibaWord: res
    });
  },

  endTip: function() {
    let that = this;
    wx.showModal({
      title: 'Tips',
      content: '本组单词已经背诵完成',
      success(res) {
        if (res.confirm) {

          //that.getOneGroupWord();
          wx.reLaunch({
            url: '../index/index',
          })

        } else if (res.cancel) {

          wx.reLaunch({
            url: '../index/index',
          })

        }
      }
    })
  },


  playAudio: function (e) {

    let that = this;
    let id = e.currentTarget.dataset.value;
    let word = that.data.cibaWord;
    let sents = that.data.cibaSents;

    let isPlay_am = false;
    let isPlay_en = false;
    let isPlay_sents = false;

    /*   for (let i = 0; i < sents.length; i++) {
         sents[i].isPlay = false;
       }*/
    let src = "";
    if (id == 'am') {
      isPlay_am = true;
      src = word.symbols[0].ph_am_mp3;
    } else if (id == 'en') {
      isPlay_en = true;
      src = word.symbols[0].ph_en_mp3;
    } else {
      // sents[id].isPlay = true;
      isPlay_sents = true;
      src = 'http://dict.youdao.com/dictvoice?audio=' + sents[id].orig;
    }

    innerAudioContext.src = src;
    innerAudioContext.play();

    that.setData({
      isPlay_am: isPlay_am,
      isPlay_en: isPlay_en,
      //    cibaSents: sents,
      isPlay_sents: isPlay_sents,
    })
  },

  touchStart: function (e) {
  
    if(this.data.type == -1) return;
    func.touchStart(e)
  },
  touchEnd: function (e) {
    if (this.data.type == -1) return;
    let that = this;
  
    let moveY = 100 / d.pDrp;
    let moveX = 60 / d.pDrp;

    let y = that.data.nowYView;

    //如果滑动长度大于pTop,则 up,down,left,right,
    let act = func.touchEnd(e, moveX, moveY, 1,0)

    /**
     * 如果是页面y0
     * 下滑,则返回主页，
     * 上滑，则到页面1
     * 右划认识，
     * 左滑不认识
     */
    if (y == 0) {
      if (act == "down" ) {
        wx.navigateBack({
          delta: 1,
        });
      } else if (act == "up") {
        y = 1;
      } else if (act == "right") {
        that.isNotRem();
        that.getOneWord();

      } else if (act == "left") {
        that.isRem();
        that.getOneWord();
      }
    }
    if(y == 1){
      if(act == "down"){
        y = 0;
      }
    }

    that.setData({
      nowYView: y
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {
    var word = this.data.cibaWord.word_name
    return {
      title: word + " | newKids",
      path: 'pages/word/word?word=' + word + '&type=-1',
    } 
  }
})