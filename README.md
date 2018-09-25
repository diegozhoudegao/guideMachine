 <h1>guideMachine</h1>

<h2>项目基本信息</h2>
1、项目采用SpringBoot框架进行开发<br/>
2、ORM框架采用Hibernate，默认使用数据库为MySQL，若使用其他数据库请修改application-dev.yml文件<br/>
3、项目数据源采用阿里Druid，若更换数据源，请修改pom文件，增加对应依赖jar包，并修改applicationContext.xml中数据源配置<br/>
4、项目已集成ActiveMQ，项目启动前请先保证activemq服务启动<br/>
5、项目已集成webSocket，可实现服务器主动向客户端推送消息<br/>
6、项目已集成swagger，可使用swagger-ui.html测试接口<br/>
7、项目已集成阿里大于的短信发送，可直接使用DysmsUtil工具类发送消息<br/>
8、Excel模板数据导入，POI<br/>
9、微信授权登录、微信消息推送<br/>
10、项目采用Redis作为缓存<br/>

<h2>项目待集成技术</h2>
1、定时任务Quartz<br/>
2、第三方支付<br/>
