// pages/pre/pre.js
const config = require('../../config.js');
const util = require('../../utils/util.js');
const d = getApp().globalData;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    isLoading: true,
    wbs: [],
    wb_id: null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

    //获取单词书
    //判断用户是否登录
    let that = this;
    wx.getSystemInfo({
      success: function(res) {
        d.pHig= 750 * res.windowHeight / res.windowWidth
        // let t = 750 / res.windowWidth * 150;
        d.pDrp = 750 / res.windowWidth;
        d.navH = res.statusBarHeight
        
        that.getWbs();
      }
    })
  },


  onShareAppMessage: function() {

  },


  getWbs: function() {
    let that = this;
    wx.request({
      url: config.service.getAllWordbookUrl,
      data: {},
      method: 'GET',
      success(res) {
        console.log('获取单词书', res.data.res);
        if (res.data.res == undefined){
          that.lostConnect();
          return;
        }

        let wbs = res.data.res;
        d.wbs = wbs;
        that.isLogged();
      },
      fail(err) {
        console.log(err)
        that.lostConnect(err);
      },
    })
  },


  isLogged: function() {
    /**
     * 登录流程
     * --引导页
     * 获取登录态，如果
     */
    let that = this;

    let userInfo = wx.getStorageSync('userInfo') || null;

    if (userInfo == null) {

      console.log("用户未登录：-1");
      that.toLogin();

    } else {
     
      //服务器验证
      wx.request({
        url: config.service.visitUrl,
        data: {
          open_id: userInfo.openId
        },
        method: 'GET',
        success(res) {
          that.toIndex();
        }
      })
    }
  },

  /**
   * 注册流程
   * 尝试登录
   * 登录成功，跳转本页,
   * 登录失败，再次尝试
   */
  toLogin: function() {
    wx.clearStorageSync();
    var that = this;
    that.setData({
      isLoading: false,
      wbs: d.wbs,
      wb_id: d.wbs[0].wb_id,
    })
  },

  /**
   * 登录
   * 刷新今日数据
   * 获取本地数据
   * 获取网络数据
   * 跳转index
   */
  toIndex: function() {
    var that = this;
    that.initLoaclData();
    that.initOnlineData();
  },

  initLoaclData: function() {
    //刷新今日数据
    let dat = wx.getStorageSync("dat") || null;
    let nowDate = new Date();
    dat = new Date(dat);
    if (dat != null && nowDate.getDate() == dat.getDate() && nowDate.getMonth() == dat.getMonth() && nowDate.getFullYear() == dat.getFullYear()){
    
    }else{
      wx.setStorageSync("dat", new Date());
      wx.setStorageSync("today_words", []);
      wx.setStorageSync("today_checkin", false);
      wx.setStorageSync("today_reading", []);
    }

    //获取本地数据
    d.userInfo = wx.getStorageSync('userInfo');

    d.today.today_words = wx.getStorageSync('today_words'); //今日单词
    d.today.today_checkin = wx.getStorageSync('today_checkin'); //今日是否打卡
    d.today.today_reading = wx.getStorageSync('today_reading');
    //以上已经全部初始化
    

    d.words = wx.getStorageSync('words') || [];
    d.papers = wx.getStorageSync('papers') || []; //文章
    d.leftWords = wx.getStorageSync('leftWords') || [];

    d.conf.groupNum = wx.getStorageSync('groupNum') || 20;
    d.cibaWords = wx.getStorageSync('cibaWords') || [];


  },

  initOnlineData: function() {

    //获取用户学习信息，
  /*  let initSync = new Promise((resolve, reject) => {
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
        complete() {

        }
      })
    })

    //在同步结束后
    initSync.then((res) => {

      console.log('用户学习数据', res.data);
      d.conf.cibakey = res.data.cibakey;
      d.userData.userNowWb = res.data.userWb;
      d.userData.userWbs = res.data.userWbs;
      d.userData.hasStudyNum = res.data.hasStudyNum;
      d.userData.wbWordsNum = res.data.wordsNum;
      d.userData.hasStudyDay = res.data.hasStudyDay;
*/
      wx.reLaunch({
        url: '../index/index',
      })

  //  })

  },


  bingGetUserInfo: function(e) {
    let that = this;
    console.log(e.detail);

    that.setData({
      isLoading: true
    })

    wx.login({
      success: res => {
        //  console.log(config.service.loginUrl)
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        wx.request({
          url: config.service.loginUrl,
          data: {
            code: res.code,
            rawData: e.detail.rawData, //用户非敏感信息                  
            signature: e.detail.signature, //签名                  
            encryptedData: e.detail.encryptedData, //用户敏感信息                  
            iv: e.detail.iv, //解密算法的向量
          },
          method: 'GET',
          success(res) {
            console.log(res.data)
            if (res.data.status == 1) {
              let userInfo = res.data.userInfo;
              wx.setStorageSync('userInfo', userInfo);
              console.log('登陆成功', userInfo);

              //获取用户信息以后，向后台设置wordbook
              let wb_id = that.data.wb_id;
              let open_id = userInfo.openId;

              wx.request({
                url: config.service.updateWordbookUrl,
                data: {
                  open_id: open_id,
                  wb_id: wb_id,
                },
                method: 'GET',
                success(res) {
                  console.log(res.data);
                  wx.redirectTo({
                    url: '../pre/pre'
                  })

                },
                fail(err) {
                  console.log(err)

                }
              })
            } else {
              wx.showModal({
                content: '登录失败，是否再次尝试？',
                success: function(res) {
                  if (res.cancel) {
                    //点击取消,默认隐藏弹框
                    wx.navigateBack({
                      delta: 1
                    })
                  } else {
                    //点击确定
                    wx.reLaunch({
                      url: '../pre/pre',
                    })

                  }
                }
              })
            }
          },
          fail(err) {
            console.log(err)
            that.lostConnect(err);
          },
          complete() {}
        })
      }
    })
  },

  setUserWb: function(e) {
    // console.log(e.currentTarget.dataset.value)
    this.setData({
      wb_id: e.currentTarget.dataset.value
    })

  },
  lostConnect: function(err) {
    wx.showModal({
      title: 'fail',
      content: '访问服务器失败，可能是网络错误或服务器错误,是否重试',
      success: function(res) {
        if (res.cancel) {
          //点击取消,默认隐藏弹框
          wx.navigateBack({
            delta: 1
          })
        } else {
          //点击确定
          wx.reLaunch({
            url: '../pre/pre',
          })

        }
      }
    })
  }


})