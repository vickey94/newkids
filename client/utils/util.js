var Parser = require('../lib/dom-parser.js')

const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}


/*登录验证流程
先检查，app.globalData.userInfo ,如果不存在，则
然后检查Storage里的登录状态
返回false时，页面开始调用登录服务，同时存储到Storage中
*/
const isLogged = app => {
  if (app.globalData.userInfo != null) return true;
  
  var userInfo = wx.getStorageSync('userInfo');
  if (userInfo != null && userInfo != '' && userInfo != undefined) {
    app.globalData.userInfo = userInfo;
    return true;
  } else
    return false;
}

const xml2word = xml => {
  var word = {};
  var XMLParser = new Parser.DOMParser();
  var doc = XMLParser.parseFromString(xml)
  var key = doc.getElementsByTagName('key')['0'];
  var sents= doc.getElementsByTagName('sent'); 
  
  var temp =[];
  for(var i = 0; i < sents.$$length;i++){
     var sent = {};
    var ss = sents[i];
    ss.index = i;
    sent.orig = ss.firstChild.firstChild.nodeValue;
    sent.trans = ss.lastChild.firstChild.nodeValue;
    sent.isPlay = false;
     temp[i] = sent;
  }

 // word.word = key.firstChild.nodeValue;
 // word.sents = temp;

return temp;
}


//插入一组新单词时，会重置单词组
const setWordsGroup = wg =>{
  wx.setStorageSync('words', wg);
  wx.setStorageSync('hasStudyWords', []);
  wx.setStorageSync('leftWords',wg)
}

const getRandomInt =max=> {
  return Math.floor(Math.random() * Math.floor(max));
}

const refreshTodayData = ()=>{
  var date = wx.getStorageSync("date");
  var nowDate = new Date();
  if (date != "") {
    if (nowDate.getDate() == date.getDate() && nowDate.getMonth() == date.getMonth() && nowDate.getFullYear() == date.getFullYear()) {
      return;
    }
  }
  wx.setStorageSync("date", new Date());
  wx.setStorageSync("today_num", 0);
  wx.setStorageSync("today_words", []);
  wx.setStorageSync("today_checkin", false);
}

const refreshData = () => {
  
   //初始化设置一些数据
  var gn = wx.getStorageSync('groupNum')
  if (gn==""){
    wx.setStorageSync('groupNum', 10);
  }

  //初始化背单词模块
  var w = wx.getStorageSync('words')

  if (w == "") {

    wx.setStorageSync('words', []);
  } 
  var hw= wx.getStorageSync('hasStudyWords')
  if (hw == "") {
    wx.setStorageSync('hasStudyWords', []);
  }
  var lw = wx.getStorageSync('leftWords')
  if (lw == "") {
    wx.setStorageSync('leftWords', []);
  }

 

}

module.exports = {
  formatTime,
  isLogged,
  xml2word,
  setWordsGroup,
  getRandomInt,
  refreshTodayData,
  refreshData
}