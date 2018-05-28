CREATE DATABASE IF NOT EXISTS day14;
use day14;

CREATE TABLE product(
  pid VARCHAR(96),
  pname VARCHAR(150),
  markey_price DOUBLE,
  shop_price DOUBLE,
  pimage VARCHAR(600),
  pdate DATE,
  pdec VARCHAR(765)
);


INSERT INTO `product` VALUES('1','小米 4c 标准版','1399','1299','products/1/c_0001.jpg','2015-11-02','小米 4c 标准版 全网通 白色 移动联通电信4G手机 双卡双待');
INSERT INTO `product` VALUES('10','华为 Ascend Mate7','2699','2599','products/1/c_0010.jpg','2015-11-02','华为 Ascend Mate7 月光银 移动4G手机 双卡双待双通6英寸高清大屏，纤薄机身，智能超八核，按压式指纹识别！!选择下方“移动老用户4G飞享合约”，无需换号，还有话费每月返还！');
INSERT INTO `product`  VALUES('11','vivo X5Pro','2399','2298','products/1/c_0014.jpg','2015-11-02','移动联通双4G手机 3G运存版 极光白【购机送蓝牙耳机+蓝牙自拍杆】新升级3G运行内存·双2.5D弧面玻璃·眼球识别技术');
INSERT INTO `product`  VALUES('12','努比亚（nubia）My 布拉格','1899','1799','products/1/c_0013.jpg','2015-11-02','努比亚（nubia）My 布拉格 银白 移动联通4G手机 双卡双待【嗨11，下单立减100】金属机身，快速充电！布拉格相机全新体验！');
INSERT INTO `product`  VALUES('13','华为 麦芒4','2599','2499','products/1/c_0012.jpg','2015-11-02','华为 麦芒4 晨曦金 全网通版4G手机 双卡双待金属机身 2.5D弧面屏 指纹解锁 光学防抖');
INSERT INTO `product`  VALUES('14','vivo X5M','1899','1799','products/1/c_0011.jpg','2015-11-02','vivo X5M 移动4G手机 双卡双待 香槟金【购机送蓝牙耳机+蓝牙自拍杆】5.0英寸大屏显示·八核双卡双待·Hi-Fi移动KTV');
INSERT INTO `product`  VALUES('15','Apple iPhone 6 (A1586)','4399','4288','products/1/c_0015.jpg','2015-11-02','Apple iPhone 6 (A1586) 16GB 金色 移动联通电信4G手机长期省才是真的省！点击购机送费版，月月送话费，月月享优惠，畅享4G网络，就在联通4G！');