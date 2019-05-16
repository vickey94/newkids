var host = 'https://nk.aaaaaaa.club';
//var host = 'http://localhost:8000'

var config = {
  service: {
    host,
    // 登录地址，用于建立会话
    loginUrl: `${host}/login/valid`,
    //访问
    visitUrl: `${host}/login/visit`,

    indexInitUrl: `${host}/func/init`,
    //获取所有单词书
    getAllWordbookUrl: `${host}/func/getallwb`,
    //获取用户学习状态
    getUserStudyUrl: `${host}/func/getuserstudy`,
    //用户设置单词书
    updateWordbookUrl: `${host}/func/usersetwb`,
    //用户获取一组单词
    getWordGroupUrl: `${host}/func/getwordgroup`,
    
    //用户获取一组历史单词
    getHisWordGroupUrl: `${host}/func/gethiswordgroup`,
    //用户获取一个单词详情
    getWordcibaUrl: `${host}/func/getwordciba`,
    
    //从词霸获取单词/例句
    cibaUrl: `${host}/func/getcibaword`,

    //从有道获取发音
    ydttsUrl:'https://dict.youdao.com/dictvoice',
    //背诵完成
    finishOneWordUrl: `${host}/func/finishoneword`,
    //背诵完成
    finishWordsUrl: `${host}/func/finishwords`,

    //用户获取一组文章
    getPaperGroupUrl: `${host}/func/getpapergroup`,
    //用户获取一篇文章
    getOnePaperUrl: `${host}/func/getonepaper`,    
    //用户文章打卡
    finishPaperUrl: `${host}/func/finishpaper`,
    //用户阅读记录
    getreadinglogsUrl: `${host}/func/getreadinglogs`,

    //用户打卡
    checkInUrl: `${host}/func/checkin`,

    //搜索单词
    searchwordsUrl: `${host}/func/searchwords`,

  }
}

module.exports = config;