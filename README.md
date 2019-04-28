newkids
====
newkids weixin mini program

****
## 目录
* [功能设计](#功能设计)
  * [文章预处理](#文章预处理)
  * [背单词流程](#背单词流程)
* [单词书、文章评级](#单词书、文章评级)
* [数据库设计](#数据库设计)


****
# 功能设计

## 单词预处理

我们需要对单词进行统一划分，同一个单词的不同时态，算为同一个单词。

最好能拓展出其形容词等，(待定)

## 文章预处理

我们会对一篇入库的文章进行预处理，包括：

对文章进行评分，决定文章所对应的单词书，

对文章进行分析，挖掘出对应的重点单词，作为单词组，用于单词背诵。

## 背单词流程

用户单词背诵主要为以下流程

1. 用户选择单词书
2. 单词为组的形式，一组10-20个单词，外加一个附属组10-20个词(或多个附属组)，为新单词或历史旧单词
3. 为用户推荐文章，用户阅读文章
4. 阅读文章完毕后
   1. 用户开始背诵单词，单词第一轮引入例句，用户猜测单词含义
   2. 用户猜出单词含义，第二轮则为无例句用户猜测单词含义
   3. 用户没有猜出单词含义，则再次出现例句进行背诵
   4. 当一轮单词数量不足5个时，引入附属组作为混淆词，继续背诵，直到本组单词全部背诵完成，
5. 单词背诵结束以后，再次阅读文章


****
# 单词书、文章评级

用户单词背诵主要是按照单词书选择，如CET-4单词书，CET-6单词书，同时对单词书进行打分，如CET-4为55分，CET-6为65分
_单词预留了评分，这里可以考虑作为单词书打分依据，但待定_

我们采用对文章进行评打分级，这里，一般大学英语，对应50-70分之间

通过单词书的分，和文章的分数，可以把单词书和文章进行关联起来

这里如果涉及到小学英语，中学英语，则对应相应分数即可

```
附 中国英语等级划分

1、一、二级大致对应小学水平。

2、三级对应初中。

3、四级对应高中。

4、五、六级对应大学。

5、七级对应英语专业。

6、八、九级对应高端外语人才。

在考试中英语等级划分：

一、《高等学校英语应用能力考试》面向专科生，分为A、B级（也被称为大学英语二、三级）；

二、《成人高等教育本科英语考试》（学位英语）面向成人本科生（也被称为成人英语三级）；

三、《大学英语等级考试》面向非英语专业本科生，分为四、六级；

四、《专业英语等级考试》面向英语专业本科生，分为四、八级；

五、《全国英语等级考试》（PETS考试）面向全体社会，分为一至五级，一级B相当于小学毕业，一级相当于初中毕业，二级相当于高中毕业，三级相当于大学英语四级，四级相当于大学英语六级，五级相当于专业英语四。
```

****
# 数据库设计

说明1：

_为兼容微信数据，这里数据库格式为utfmb4，后台设置为UTF-8(兼容utfmb8)_

说明2：

_命名规则：表名 t\_表名称s  关联表t\_关联表表名1(缩写)\_关联表表名2(缩写)_

_字段名  id命名规则为 表名缩写\_id （如t\_basewords bw\_id）主字段名一般和表名一致，其他附加字段则用表名缩写，涉及到其他表的字段，命名和其主键一样_


**t_usersession**  (utf8mb4)微信小程序数据

|字段|数据类型|说明|
|---|---|---
|open_id|varchar(100)|PK NN 也是用户在这个小程序中的唯一标识
|uuid|varchar(100)|微信统一ID 预留字段
|skey|varchar(100)|可用于session 预留字段
|create_time|timestamp|创建时间
|last_visit_time|timestamp|最近登录时间
|session_key|varchar(100)|用于微信小程序加密解密信息
|user_info|varchar(6000)|用户信息/解密出的用户信息

**t_basewords**  基础词汇表（此表包含各个单词书的所有单词）

|字段|数据类型|说明|
|---|---|---
|bw_id|varchar(255)|PK NN 此主键是单词在系统中的唯一标识，
|word|varchar(255)||NN 只包括基础词汇
|bw_freq|double(50)|单词出现频率
|bw_diff|int(11)|单词难度
|bw_count|int(11)|单词背诵次数
|bw_ack_rate|double(10)|单词平均背诵速率
|bw_score|float(5,2)| 单词评级,预留字段

**t_wordbooks**  单词书表

|字段|数据类型|说明|
|---|---|---
|wb_id|varchar(255)|PK NN
|wordbook |varchar(255)|单词书名,如CET-4,CET-6
|wb_num|int(11)|单词书单词个数
|wb_status |tinyint(1)|状态 1显示 0停用
|wb_order|int(11)|排序 预留字段
|wb_score|float(5,2)|用于评级，范围为0-100之间
|wb_parent_id|int(11)|预留字段，单词书父级

**t_user_wb**  用户单词书表

|字段|数据类型|说明|
|---|---|---
|u_wb_id|int(11)|PK NN
|open_id|varchar(100)|NN 
|wb_id|varchar(255)|NN 单词书ID
|u_wb_status|tinyint(1)|NN 单词书状态，默认为0，1为已经背诵完成，每次只能选择一本单词书
|u_wb_rate|float(5,2)|用户完成百分比，预留字段
|u_wb_time|timestamp|添加时间


**t_bw_wb**  单词单词书关联表（单词对应的单词书）

|字段|数据类型|说明|
|---|---|---
|bw_wb_id|varchar(255)|PK NN
|bw_id|varchar(255)| NN 基础表中id
|wb_id|varchar(255)| NN 单词书id
|bw_wb_score|float(5,2)| 单词书下，单词重要程度,预留字段


**t_words**  总词汇表，源自BBC 13W单词

|字段|数据类型|说明|
|---|---|---
|w_id|int(20)|PK NN
|word|varchar(255)|单词
|w_exchange|varchar(1500)|拓展词，其他形态
|w_voice|varchar(1500)|发音
|w_times|int(20)|出现次数


**t_pos**  词性表(对应t_words)

|字段|数据类型|说明|
|---|---|---
|pos_id|int(2)|PK NN
|pos_name|varchar(100)|缩写
|pos_means|varchar(255)|中文

**t_means**  单词含义表(对应t_words)

|字段|数据类型|说明|
|---|---|---
|w_id|int(20)|NN 一个单词可能有多个释义
|pos_id|int(2)|NN
|w_means|varchar(1000)|

**t_papers**  文章表

|字段|数据类型|说明|
|---|---|---
|p_id|int(11)|PK NN
|p_abstract|varchar(255)|摘要，预留字段
|paper|varchar(6000)|文章，带样式
|paper_raw|varchar(6000)|原文章
|p_score |float(5,2)|文章评级0-100分
|p_times |int(11)|阅读次数
|p_update_time |timestamp|文章更新日期

**t_paper_wb**  文章-单词书表

|字段|数据类型|说明|
|---|---|---
|p_wb_id|int(11)|PK NN
|p_id|int(11)|NN 文章ID
|wb_id|varchar(255)|NN 单词书ID 
|p_wb_score|float(5,2)|文章单词书相关度，预留字段

**t_paper_bw** 文章单词表

|字段|数据类型|说明|
|---|---|---
p_bw_id|int(11)|PK NN
p_id|int(11)|NN 文章id
bw_id|varchar(255)|NN 基础词汇id
p_bw_score|float(5,2)|文章单词相关性评分


**t_reading_logs**  阅读记录表

|字段|数据类型|说明|
|---|---|---
|r_logs_id|int(20)|PK NN
|p_id|int(11)|NN 文章ID
|open_id|varchar(100)|用户ID
|r_score |tinyint(1)|用户对文章阅读评分1-10分
|r_start_time|timestamp|阅读开始时间
|r_end_time|timestamp|阅读结束时间

**t_word_logs**  背单词记录

|字段|数据类型|说明|
|---|---|---
|w_logs_id|int(20)|PK NN
|bw_id|varchar(255)|NN 单词ID
|open_id|varchar(100)|用户ID
|w_score|tinyint(1)|单词记忆评分,预留字段
|w_start_time|timestamp|背单词开始时间
|w_end_time|timestamp|背单词结束时间

**t_missing**   查询无词表

|字段|数据类型|说明|
|---|---|---
|miss_id|int(11)|PK NN
|miss_word|varchar(255)|查无单词
|s_time|timestamp|查询时间
|miss_times|int(11)|出现次数
