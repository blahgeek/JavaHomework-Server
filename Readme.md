% Recorder服务器端设计文档
% 2011011262 赵一开
% \today

# 介绍

Recorder是一个记录安卓用户地理位置的服务，分为服务器端和客户端。
客户端安装在安卓手机上，负责定时发送地理位置信息至服务器端，并且允许用户添加照片。
服务器端接收客户端的请求，并将结果通过网页的形式展现出来，展现形式包括列表和Google地图。

主页内容为用户已经上传的地理位置信息，可以通过右上角选择以列表方式或者地图方式显示。
主页左上角可以通过起止时间进行查询。

# 与客户端通信的API

客户端与服务器的通行全部采用HTTP请求，数据以json的形式使用POST方法
发送到服务器端，用户名密码认证方式采用HTTP标准的`basic authorize`认证方式。

## 发送一个记录

    curl -u test:secret \
        -d '{"altitude": 23.456, "latitude": 51.48, "longtitude": 124.9}'\
        -H "Content-Type: application/json" \
        -v localhost:9000/api/postrecord

Json数据中可以包含的字段有：

  字段          含义           数据类型
----------- --------------- -----------------
 altitude     海拔（米）      浮点数
 latitude     维度            浮点数
 longtitude   经度            浮点数
 speed       速度（米每秒）   浮点数
 accuracy    精度（米）       浮点数


服务器将返回以json表示的结果，如下。结果中包含该记录的ID以及是否成功。

    {"result":"ok", "id":42}


## 给一条记录添加图片

图片以base64编码的形式通过json上传，图片格式统一为JPG（`image/jpeg`）。

    curl -u test:secret \
        -d '{"photo": "_base64_here_"}'\
        -H "Content-Type: application/json" \
        -v localhost:9000/api/addphoto/123

地址的最后`123`表示要将图片添加到的记录的ID，由发送记录后服务器返回的json中得到。

该请求返回的结果：

    {"result":"ok"}

错误时将返回结果：

    {"result":"error","message":"invalid data"}


# 使用的框架及设计等

该作业在不同方面基于数个框架、使用了很多开源库来进行快速的开发和部署。

后端：

- Play Framework: 一个MVC模式的Web开发框架
- H2 DataBase: 一个免费的数据库引擎
- jacson提供的json解析库

前端：

- 页面的UI基于bootstrap
- 地图显示调用了Google Maps API v3
- jQuery

