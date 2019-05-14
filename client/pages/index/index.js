// pages/index/index.js
const app = getApp()
const config = require('../../config.js')
const util = require('../../utils/util.js')


Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: null,
    wbs: null, //单词书
    papers: null, //当前文章
    //用户的单词书
    userWbs: null,
    userNowWb: null,
    //已经背诵单词个数，
    hasStudyNum: 0,
    //用户打卡天数
    hasStudyDay: 0,
    //单词书单词个数
    wordsNum: 0,
    groupNum: 0,

    words: [],
    hasWord: false, //单词未加载完成禁止点击

    //今日数据
    today_num: 0,
    today_words: [],
    today_checkin: false,

    reading_his: [], //阅读纪录
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.showLoading({
      title: '',
      mask: true,
    })
    var that = this;

    that.setData({
      userInfo: app.globalData.userInfo,
      groupNum: wx.getStorageSync('groupNum'),
    })

    /**
     * 用户信息，app.js已经从缓存中获取，这里直接调用全局变量就行
     * 打开主页后，会先向后台获取基本数据，包括
     * 加载所有单词单词书，
     * 加载用户单词书进度，
     * 如果没有本地单词获取一组单词，
     * 根据获取的单词，加载一组文章
     */

    wx.request({
      url: config.service.indexInitUrl,
      data: {
        open_id: that.data.userInfo.openId,
      },
      method: 'GET',
      success(res) {
        console.log(res.data);
        that.setData({
          wbs: res.data.wbs,
          hasStudyDay: res.data.hasStudyDay,
        });
        wx.setStorage({
          key: 'cibakey',
          data: res.data.cibakey,
        })
      },
      fail(err) {
        console.log(err)
        wx.showModal({
          title: 'fail',
          content: '访问服务器失败，可能是网络错误或服务器错误',
        })
      },
      complete() {
        wx.hideLoading();
      }
    })
  },

  setTodayData: function() {
    //设置缓存有效期，今日已背单词数量，今日单词复习
    util.refreshTodayData();
    var today_num = wx.getStorageSync('today_num');
    var today_words = wx.getStorageSync('today_words');
    var today_checkin = wx.getStorageSync('today_checkin');

    //如果今天没有打卡,且背诵超过20单词，则自动打卡
    if (!today_checkin && today_num >= 20) {
      today_checkin = true;
      wx.setStorageSync('today_checkin', true);
    }
    //获取缓存数据
    this.setData({
      today_num: today_num,
      today_words: today_words,
      today_checkin: today_checkin,
    })
  },


  refresh: function() {
    var that = this;

    that.setTodayData();

    var words = wx.getStorageSync("words");
    var reading_his = wx.getStorageSync("reading_his");
    that.setData({
      words: words,
      reading_his: reading_his,
    })

    var papers = wx.getStorageSync('papers');
   
    if(papers == ""){
      that.getPapers();
    }else{
      that.setData({
        papers:papers,
      })
    }

    //获取用户学习信息，
    let initSync = new Promise((resolve, reject) => {
      wx.request({
        url: config.service.getUserStudyUrl,
        data: {
          open_id: that.data.userInfo.openId,
        },
        method: 'GET',
        success(res) {
          resolve(res);
        },
        fail(err) {
          console.log(err)

          reject(err)
        },
        complete() {

        }
      })
    })

    //在同步结束后
    initSync.then((res) => {
      console.log(res.data);
      //设置首页参数
      wx.setStorageSync('userNowWb', res.data.userWb);

      that.setData({
        userNowWb: res.data.userWb,
        userWbs: res.data.userWbs,
        hasStudyNum: res.data.hasStudyNum,
        wordsNum: res.data.wordsNum,
      });

      //获取一组单词
      var leftwords = wx.getStorageSync('leftWords');
      if (leftwords.length == 0) {
        that.setData({
          hasWord: false
        })
        that.getOneGroupWord();
      } else {
        that.setData({
          hasWord: true
        })
      }

    })
  },

  getOneGroupWord: function() {
    var that = this;
    wx.request({
      url: config.service.getWordGroupUrl,
      data: {
        open_id: that.data.userInfo.openId,
        wb_id: that.data.userNowWb.wb_id,
        size: that.data.groupNum,
      },
      method: 'GET',
      success(res) {
        console.log(res.data);
        util.setWordsGroup(res.data.res);
        that.setData({
          words: res.data.res,
          hasWord: true,
        });
      },
    })
  },

  toStudy: function() {
    wx.navigateTo({
      url: '../study/study',
    })

  },

  getPapers: function() {
    var that = this;
    var open_id = that.data.userInfo.openId;

    wx.setStorageSync('reading_his', []);
    that.setData({
      reading_his: [],
    })

    var t_words = [];
    var words = that.data.words;
    for (var i = 0; i < words.length; i++) {
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
        wx.setStorageSync("papers", res.data.papers)
        that.setData({
          papers: res.data.papers
        })
   
      },
      fail(res) {},

    })

  },

  toPaper: function(e) {
    var p_id = e.currentTarget.dataset.value;
    wx.navigateTo({
      url: '../paper/paper?p_id=' + p_id + '&hasRead=' + false,
    })
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
    var that = this;
    that.refresh();

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

  }
})