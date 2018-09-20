# guideMachine

#项目基本信息
1、项目采用SpringBoot框架进行开发
2、ORM框架采用Hibernate，默认使用数据库为MySQL，若使用其他数据库请修改application-dev.yml文件
3、项目数据源采用阿里Druid，若更换数据源，请修改pom文件，增加对应依赖jar包，并修改applicationContext.xml中数据源配置
4、项目已集成ActiveMQ，项目启动前请先保证activemq服务启动
5、项目已集成webSocket，可实现服务器主动向客户端推送消息
6、项目已集成swagger，可使用swagger-ui.html测试接口

#项目待集成技术
1、定时任务Quartz
2、微信授权登录、第三方支付、以及微信消息推送
3、Excel模板数据导入，POI获取其他，待敲定
4、短信验证，腾讯云短信服务或阿里大于
