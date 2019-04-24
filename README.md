newkids
====
newkids weixin mini program

****
# 数据库设计

说明1：

_为兼容微信数据，这里数据库格式为utfmb4，后台设置为UTF-8(兼容utfmb8)_

说明2：

_命名规则：表名 t_表名称s  关联表t_关联表表名1(缩写)_关联表表名2(缩写)_

_字段名  id命名规则为 表名缩写_id （如t_basewords bw_id）主字段名一般和表名一致，其他附加字段则用表名缩写，涉及到其他表的字段，命名和其主键一样_


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
|bw_id|int(11)|PK NN 此主键是单词在系统中的唯一标识，
|word|varchar(255)||NN 只包括基础词汇
|bw_score|float(5,2)| 单词评级,预留字段

**t_wordbooks**  单词书表

|字段|数据类型|说明|
|---|---|---
|wb_id|int(11)|PK NN
|wordbook |varchar(255)|单词书名,如CET-4,CET-6
|wb_status |tinyint(1)|状态 1显示 0停用
|wb_order|int(11)|排序 预留字段
|wb_score|float(5,2)|用于评级，范围为0-100之间

**t_user_wb**  用户单词书表

|字段|数据类型|说明|
|---|---|---
|u_wb_id|int(11)|PK NN
|open_id|varchar(100)|NN 
|wb_id|int(11)|NN 单词书ID
|u_wb_rate|float(5,2)|用户完成百分比，预留字段
|u_wb_time|timestamp|添加时间


**t_bw_wb**  单词单词书关联表（单词对应的单词书）

|字段|数据类型|说明|
|---|---|---
|bw_wb_id|int(11)|PK NN
|bw_id|int(11)| NN 基础表中id
|wb_id|int(11)| NN 单词书id
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
|wb_id|int(11)|NN 单词书ID 
|p_wb_score|float(5,2)|文章单词书相关度，预留字段

**t_paper_bw** 文章单词表

|字段|数据类型|说明|
|---|---|---
p_bw_id|int(11)|PK NN
p_id|int(11)|NN 文章id
bw_id|int(11)|NN 基础词汇id
p_bw_score|float(5,2)|文章单词相关性评分


**t_reading_logs**  阅读记录表

|字段|数据类型|说明|
|---|---|---
|r_logs_id|int(20)|PK NN
|p_id|int(11)|NN 文章ID
|open_id|varchar(100)|用户ID
|r_sorce |tinyint(1)|用户对文章阅读评分1-10分
|r_start_time|timestamp|阅读开始时间
|r_end_time|timestamp|阅读结束时间

**t_word_logs**  背单词记录

|字段|数据类型|说明|
|---|---|---
|w_logs_id|int(20)|PK NN
|bw_id|int(11)|NN 单词ID
|open_id|varchar(100)|用户ID
|w_sorce|tinyint(1)|单词记忆评分,预留字段
|w_start_time|timestamp|背单词开始时间
|w_end_time|timestamp|背单词结束时间

**t_missing**   查询无词表

|字段|数据类型|说明|
|---|---|---
|miss_id|int(11)|PK NN
|miss_word|varchar(255)|查无单词
|s_time|timestamp|查询时间
|miss_times|int(11)|出现次数
