create table t_member
(
    id int(11) not null auto_increment,
    loginacct varchar(255) not null, userpswd char(200) not null, username varchar(255), email varchar(255), authstatus int(4) comment '实名认证状态 0 - 未实名认证， 1 - 实名认证申
请中， 2 - 已实名认证', usertype int(4) comment ' 0 - 个人， 1 - 企业', realname varchar(255), cardnum varchar(255), accttype int(4) comment '0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府', primary key (id)
);
alter table t_member add unique index (loginacct);

create table t_type
(
    id int(11) not null auto_increment, name varchar(255) comment '分类名称', remark varchar(255) comment '分类介绍', primary key (id)
);
create table t_project_type
(
    id int not null auto_increment, projectid int(11), typeid int(11), primary key (id)
);
create table t_tag
(
    id int(11) not null auto_increment, pid int(11), name varchar(255), primary key (id)
);

create table t_project_tag
(
    id int(11) not null auto_increment, projectid int(11), tagid int(11), primary key (id)
);

create table t_project
(
    id int(11) not null auto_increment, project_name varchar(255) comment '项目名称', project_description varchar(255) comment '项目描述', money bigint (11) comment '筹集金额', day int(11) comment '筹集天数', status int(4) comment '0-即将开始，1-众筹中，2-众筹成功，3-众筹失败
', deploydate varchar(10) comment '项目发起时间', supportmoney bigint(11) comment '已筹集到的金额', supporter int(11) comment '支持人数', completion int(3) comment '百分比完成度', memberid int(11) comment '发起人的会员 id', createdate varchar(19) comment '项目创建时间', follower int(11) comment '关注人数', header_picture_path varchar(255) comment '头图路径', primary key (id)
);

create table t_project_item_pic
(
    id int(11) not null auto_increment, projectid int(11),
    item_pic_path varchar(255), primary key (id)
);

create table t_member_launch_info
(
    id int(11) not null auto_increment, memberid int(11) comment '会员 id', description_simple varchar(255) comment '简单介绍', description_detail varchar(255) comment '详细介绍', phone_num varchar(255) comment '联系电话', service_num varchar(255) comment '客服电话', primary key (id)
);

create table t_return
(
    id int(11) not null auto_increment, projectid int(11), type int(4) comment '0 - 实物回报， 1 虚拟物品回报', supportmoney int(11) comment '支持金额', content varchar(255) comment '回报内容', count int(11) comment '回报产品限额，“0”为不限回报数量', signalpurchase int(11) comment '是否设置单笔限购', purchase int(11) comment '具体限购数量', freight int(11) comment '运费，“0”为包邮',
    invoice int(4) comment '0 - 不开发票， 1 - 开发票', returndate int(11) comment '项目结束后多少天向支持者发送回报', describ_pic_path varchar(255) comment '说明图片路径', primary key (id)
);

create table t_member_confirm_info
(
    id int(11) not null auto_increment, memberid int(11) comment '会员 id', paynum varchar(200) comment '易付宝企业账号', cardnum varchar(200) comment '法人身份证号', primary key (id)
);

CREATE TABLE t_address (
                                             `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
                                             `receive_name` CHAR(100) COMMENT '收件人', `phone_num` CHAR(100) COMMENT '手机号', `address` CHAR(200) COMMENT '收货地址', `member_id` INT COMMENT '用户 id', PRIMARY KEY (`id`)
);
CREATE TABLE t_order_project (
                                                   `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键', `project_name` CHAR(200) COMMENT '项目名称', `launch_name` CHAR(100) COMMENT '发起人', `return_content` CHAR(200) COMMENT '回报内容', `return_count` INT COMMENT '回报数量', `support_price` INT COMMENT '支持单价', `freight` INT COMMENT '配送费用', `order_id` INT COMMENT '订单表的主键', PRIMARY KEY (`id`)
);

CREATE TABLE t_order (
                                           `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键', `order_num` CHAR(100) COMMENT '订单号', `pay_order_num` CHAR(100) COMMENT '支付宝流水号', `order_amount` DOUBLE(10,5) COMMENT '订单金额', `invoice` INT COMMENT '是否开发票（0 不开，1 开）', `invoice_title` CHAR(100) COMMENT '发票抬头',
                                           `order_remark` CHAR(100) COMMENT '订单备注', `address_id` CHAR(100) COMMENT '收货地址 id', PRIMARY KEY (`id`)
);
