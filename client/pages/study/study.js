// pages/study/study.js
const app = getApp()
const config = require('../../config.js')
const util = require('../../utils/util.js')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    word:null,
    words: null,
    hasStudyWords: null,
    leftWords: null,
    cibakey: null,

    id:null,
    bw:null,
    hasNum: 0,
    totalNum: 0,

    isRem : false, //记得还是不记得

    w_score:0,
    w_spend_time:0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(e) {
    var that = this;

    /*
    console.log(e.word)
    var word = e.word;
    var cibakey = wx.getStorageSync('cibakey');
    that.setData({
      cibakey: cibakey,
    });
 

    wx.request({
      url: config.service.cibaUrl,
      data:{
        w:word,
        key:cibakey,
        type:'json'
      },
      method: 'GET',
      success(res) {
        console.log(res.data)
      },
    })*/

    var words = wx.getStorageSync('words');
    var hasStudyWords = wx.getStorageSync('hasStudyWords');
    var leftWords = wx.getStorageSync('leftWords');
    var hasNum = hasStudyWords.length;
    var totalNum = words.length;

    this.setData({
      words: words,
      hasStudyWords: hasStudyWords,
      leftWords: leftWords,
      hasNum:hasNum,
      totalNum:totalNum,
    })

    var length = leftWords.length;
    console.log(length)
    if (length > 0) {

      var id = util.getRandomInt(length); //随机一个单词
     
      var word = leftWords[id];

      that.setData({
        word : word.word,
        bw:word,
        id:id,
      })
      
    } else {
      wx.reLaunch({
        url: '../index/index',
      })
    }
  },

  worddetail:function(){
    var d= this.data;
    wx.redirectTo({
      url: '../word/word?word=' + d.word + '&id=' + d.id + '&bw=' + JSON.stringify(d.bw) + '&isRem=' + d.isRem + '&w_score=' + d.w_score + '&w_spend_time=' + d.w_spend_time,
    })
  },

  isRem :function(e){
    var r = e.currentTarget.dataset.value;
    this.setData({
      isRem:r
    })
    this.worddetail();
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
     //向服务器更新单词
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