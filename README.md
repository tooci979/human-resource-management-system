# 基于springboot的人力资源管理系统
## 一、系统介绍
人力资源管理系统是一款专为学校设计的管理工具，旨在帮助学校实现员工信息的集中管理和高效沟通。该系统具备员工信息的录入、查询、修改、删除等功能，同时支持用户的邮件发送，极大地提升了学校人力资源管理的效率和便捷性。
### 用户
登录、注册、查询职位信息，投递简历，邮件管理，个人信息管理
### 管理员
职员管理、岗位管理、角色管理、部门管理、邮件管理
## 二、所用技术
后端技术栈：
  Spring、SpringMVC、SpringBoot、redis、mybatis、mybatis-plus、任务管理、消息服务、邮件任务
前端技术
  Vue-pure-element、pinia、axios
## 三、环境介绍
基础环境IDEA，JDK1.8 Mysql 5.7及以上, Maven3.9
所有项目以及源代码本人均调试运行无问题 可支持远程调试运行
## 四、页面截图
### 1.管理员界面
![整合前端框架](https://github.com/user-attachments/assets/175c2147-b668-4b14-b1ef-f891caf9bd0d)
![岗位管理](https://github.com/user-attachments/assets/ab9fdbc7-f545-45bf-9d25-4919501b55ad)
![部门管理](https://github.com/user-attachments/assets/1b2d181f-a861-4d0e-a275-8ca4b52842ce)
![新增用户管理-新增](https://github.com/user-attachments/assets/78220396-425a-4b93-9f4b-35a422dd3315)
### 2.员工管理
![用户求职投递简历界面](https://github.com/user-attachments/assets/3d2db7b9-c31e-4854-b873-74b5d19d0c73)
![发送邮件（投递简历）](https://github.com/user-attachments/assets/f2e3ad0e-5ac5-4ecf-b7bd-f4efc3120fc8)
## 五、访问地址
访问地址：http://localhost:8081
管理员：admin/123456
普通用户：李四/123456
## 六、安装教程
1.使用Navicat或者其它工具，在mysql中创建对应名称的数据库，并执行项目的sql
2.使用IDEA导入后端项目；使用vscode导入前端项目，并使用pnpm i下载依赖
3.使用pnpm run dev 启动前端项目，并运行后端，访问http://localhost:8081
