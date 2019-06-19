
/**
 * 新框架使用规范帮助文档 Restful Api 风格 2.1.0
 * 
 * @author shenguohao
 *   --------------------
 *   正规统一环境 >=Tomcat 8.0 / JDK >=1.8
 *   
 *   Java 方法执行逻辑的基本注释 注释注释 一定要写！！！
 *   Java 代码严禁中文命名，其次拼音，最好骆驼命名法  英文不熟可参考  https://fanyi.baidu.com
 *   RestFul 命名严禁拼音，大写，可用下划线 (特殊命名不会或者纠结请直接问我-慎国浩)
 *   RestFul 参考文档 https://blog.csdn.net/chenxiaochan/article/details/73716617  
 *   
 *   com.mall.common 包下为公共封装常用的操作类
 *   utils 包下为常用的调用工具类
 *   com.mall.common.yunos 包下为云服务:阿里云服务相关/七牛云服务相关操作
 *   com.mall.common.interceptor 包下为拦截请求验证操作，日后如需项目接口路径需要拦截或者不拦截  ，请根据业务需求自行修改如下操作
 *   参考applicationContext-MVC.xml文件 <mvc:interceptors> 需求配置
 *   
 *   --------------------
 *   
 *   com.mall.controller.api 包下为  前端、app->安卓、IOS 提供接口服务
 *   com.mall.controller.console 包下为 后台管理系统接口服务 （如果不是前后分离模式，页面后台框架自行添加，数据自行处理）
 *   com.mall.vo 包是接口 的返回对象参数
 *   com.mall.in 包是接口的输入对象参数
 *   
 *   基本的增删改查请参照 UserInfoService 类中的方法操作 
 *   
 */  
package com.zcf.controller;