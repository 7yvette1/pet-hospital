/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.35-log : Database - pet-hospital
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pet-hospital` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `pet-hospital`;

/*Table structure for table `base_carousel` */

DROP TABLE IF EXISTS `base_carousel`;

CREATE TABLE `base_carousel` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `image` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '轮播图',
  `isinuse` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否使用',
  `time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建时间',
  `user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='轮播图信息表';

/*Data for the table `base_carousel` */

insert  into `base_carousel`(`id`,`image`,`isinuse`,`time`,`user`) values (1,'http://localhost:8888/files/20250711105840.jpg','1','2024-07-25','admin'),(4,'http://localhost:8888/files/20250711105834.jpg','1','2024-07-25','admin'),(5,'http://localhost:8888/files/20250711105828.jpg','1','2024-07-25','admin');

/*Table structure for table `base_notice` */

DROP TABLE IF EXISTS `base_notice`;

CREATE TABLE `base_notice` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `time` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建时间',
  `user` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='公告信息表';

/*Data for the table `base_notice` */

insert  into `base_notice`(`id`,`title`,`content`,`time`,`user`) values (8,'智慧宠物综合管理系统，正式上线了，欢迎使用！','智慧宠物综合管理系统，正式上线了，欢迎使用！','2025-07-10','admin');

/*Table structure for table `bus_category` */

DROP TABLE IF EXISTS `bus_category`;

CREATE TABLE `bus_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `img` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '封面',
  `descr` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='资讯分类表';

/*Data for the table `bus_category` */

insert  into `bus_category`(`id`,`name`,`img`,`descr`,`content`) values (3,'猫专科','http://localhost:8888/files/20250714160621-微信图片_20250714160602.png','专业的猫咪诊疗服务，独立诊疗空间，减少应激反应','<p>我们是国内先进的猫专科服务连锁医院，按照国际标准建立猫诊所，以猫为本，致力于减少猫咪到诊所而导致的应激，也让猫主人拜访宠物医生变得更容易更舒适。猫专用诊室和候诊区，“无犬环境”，隔绝噪音打扰，更温和就诊体验。猫专用住院部，单独笼位，复式设计，实现一室一厅一卫，更宽敞舒适的活动空间。猫寄养乐园，分豪华玻璃笼位和散放笼位，与猫乐园无缝连接，活动时间单独出来玩要，满足喵星人上下跳跃的需求。</p>'),(4,'软组织外科','http://localhost:8888/files/20250714160840-微信图片_20250714160830.jpg','针对宠物软组织疾病的诊断与手术治疗','<p>软组织是与其他身体部分和器官相连、支撑或包围的组织。此外，软组织包括腱、韧带、膜、纤维组织、脂肪、滑膜、肌肉、神经和血管。故此，医治软组织疾病可能须要做手术，比如常见的软组织手术、尿道造口术、会阴疝修补术。安安宠医拥有多名专家在软组织外科方面手术成熟。</p>'),(5,'骨科','http://localhost:8888/files/20250714160958-微信图片_20250714160945.png','治疗宠物骨折、关节疾病等骨科相关问题\n\n','<p>伴随着宠物走入人们的身边，宠物已经成为家庭的一员，车祸、咬伤、高空坠落造成的骨折病例在临床上也逐渐增多，骨折发生后，宠物主人愿意让宠物接受好的治疗。安安拥有从事小动物骨科手术多年的骨科宠物专家队伍，掌握高超的诊疗技术，引进先进的仪器设备，所有的患病动物都会在恒温手术台，无菌手术室被细心检查并进行术前的血液生化和血液分析检查，及时发现宠物的各种功能变化，对手术麻醉提供科学的依据，麻醉用药均为进口麻药或呼吸麻醉使用，根据爱宠的实际情况来选用。安安在骨外科手术中有着丰富的经验和成功案例，为您的爱宠提供完善的手术服务和有力保证。</p>'),(6,'内科','http://localhost:8888/files/20250714161204-微信图片_20250714161149.png','涵盖心、肝、脾、肺、肾等脏器的检查与治疗','<p>包括脏器心、肝、脾、肺、肾都在宠物内科体检范围之内，借助完整的专业检测设备，如生化分析仪、内分泌分析仪、超声仪、数位化X射线、核磁共振等，并与其他科室如心脏科、神经科、影像科、肿瘤科的协同会诊提供强大的内科支持。对于呼吸道、消化道、泌尿系统、老年综合性疾病等方面的诊断与治疗有著丰富的经验与专业素养，安安宠医在内科方面已成为疑难杂症的转诊中心医院。</p>'),(7,'内分泌科','http://localhost:8888/files/20250714161658-微信图片_20250714161648.jpg','专门治疗宠物内分泌系统相关疾病','<p>内分泌科室专门助理内分泌疾病的专门科室，主要治疗糖尿病、肥胖症等疾病的临床诊治。</p>'),(8,'心脏科','http://localhost:8888/files/20250714161803-微信图片_20250714161752.png','针对宠物心脏血管系统疾病的专业诊疗','<p>心脏血管系统是维持生命活动的重要器官，它主要参与机体的血液循环代谢，因此与其他系统关系非常为密切。心脏血管系统原发病虽不多，但当发病时必然要引起其他系统机能障碍，其他系统疾病，也常常影响心脏血管系统的机能。某些特定品种的宠物、超过8岁的老年宠物，罹患心脏血管系统的可能性都比较高。 安安宠医心脏病专科配备心脏专用听诊器、血压仪、数字X线诊断系统、大型彩超诊断系统等高端设备，还拥有专业的心脏病专科医疗团队支持，给宠物提供精准的诊疗和贴心的服务。</p>'),(9,'皮肤科','http://localhost:8888/files/20250714162118-微信图片_20250714162108.png','治疗各类宠物皮肤疾病，包括真菌、细菌感染等','<p>宠物易感染皮肤病，包括细菌性皮肤病、真菌性皮肤病、螨虫等皮肤寄生虫感染、过敏性皮肤病、角化性和皮脂溢性皮肤病、自身免疫性皮肤病、内分泌性皮肤病，先天性和后天性脱毛、色素异常等复杂顽固性皮肤病，安安宠医专业医生检查一般犬猫的系统性皮肤病检査包括跳蚤梳、透明胶带、拔毛、刮片涂片、刮片涂片染色、伍德氏灯、细针抽吸、细菌或真菌培养、皮肤活组织检查、过敏检查以及血液学检查，从而精准地给毛孩子做出治疗方案，恢复健康。</p>'),(10,'牙科','http://localhost:8888/files/20250714162231-微信图片_20250714162202.png','宠物口腔检查、洁牙、拔牙等口腔疾病治疗','<p>如果长期不为宠物清洁牙齿，口腔中的细菌、皮屑、食物残渣等，便会积累形成牙菌斑，进而产生牙垢，并逐步演变成坚硬的牙结石。牙结石堆积不仅会压迫牙龈，导致牙龈肿胀出血、牙齿松动、牙龈萎缩等一系列口腔问题;更有可能导致宠物罹患肾病心脏疾病等大病。安安拥有专业的牙科全套，利用超声波帮助宠物既可除污去垢，还可以通过内外按摩对牙齿起到保健的作用。</p>');

/*Table structure for table `bus_comment` */

DROP TABLE IF EXISTS `bus_comment`;

CREATE TABLE `bus_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `user_id` int(11) DEFAULT NULL COMMENT '评论人',
  `pid` int(11) DEFAULT NULL COMMENT '父级ID',
  `root_id` int(11) DEFAULT NULL COMMENT '根节点ID',
  `time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评论时间',
  `fid` int(11) DEFAULT NULL COMMENT '关联ID',
  `module` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模块',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='评论表';

/*Data for the table `bus_comment` */

insert  into `bus_comment`(`id`,`content`,`user_id`,`pid`,`root_id`,`time`,`fid`,`module`) values (1,'智慧宠物综合管理系统是用什么开发的？',2,NULL,NULL,'2025-07-10 14:59:09',0,'forum'),(2,'使用java和vue开发，前端用了elementui框架。',2,1,1,'2025-07-10 14:59:35',0,'forum');

/*Table structure for table `bus_news` */

DROP TABLE IF EXISTS `bus_news`;

CREATE TABLE `bus_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `type` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型',
  `descr` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `cover` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '封面',
  `user` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布人',
  `time` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='新闻资讯表';

/*Data for the table `bus_news` */

insert  into `bus_news`(`id`,`title`,`type`,`descr`,`content`,`cover`,`user`,`time`) values (6,'猫癃闭的中兽医诊治','康复信息','中兽医认为尿闭归属于癃闭，指尿量少、点滴而出，甚至闭塞不通为主证的一种疾病。','<p>中兽医认为尿闭归属于癃闭，指尿量少、点滴而出，甚至闭塞不通为主证的一种疾病。一例猫2022年11月26日入院就诊，表现排便意识丧失，大便失禁，小便蓄积，尾部不能自主运动，中兽医诊断为脾肾两虚，肾气受损，精血亏耗，命门火衰以致膀胱气化功能失常。笔者应用中兽医针灸调节阴阳、舒筋活络、活血化瘀、止痛，四级镭射激光理疗促进炎症缓解，减轻疼痛，改善微循环，增加细胞携氧能力。最终达到补脾肾，助气化，使气化得行，大小便自通的效果确切，未见复发。<br/><strong>1.临床病例</strong><br/>薯片，10M，雌性，体重2.7kg，BCS4/9，已绝育。<br/><strong>2.诊察检查方法</strong><br/><strong>2.1 中兽医四诊</strong><br/>望诊&nbsp;&nbsp;患猫消瘦，骨盆位置有明显术后伤口，右侧腹部有绝育后伤口。舌色淡粉，鼻尖偏白，可视黏膜粉偏白。食欲差，粪便干燥正常，无排尿意识，肛周有粪便欲出，但动物无排便意识，需辅助排尿。尾巴自然下垂，梳理尾部被毛，尾巴无任何反应。<br/><strong>闻诊</strong>：&nbsp; 心率178次/分，呼吸30次/分，身上有粪臭味。<br/><strong>问诊&nbsp;</strong>：&nbsp;猫咪自幼比较消瘦，3月龄时与同窝猫咪相比体重不足，且挑食。绝育术后不幸摔伤，随之走路异常，未见排便、排尿。摔伤第3天施以骨盆手术，术后走路姿势改善，基本恢复正常，但没有自主排便、排尿的意识，需人工辅助，尾垂吊不能摆动。采食量少，爱吃冻干粮。<br/><strong>切诊：</strong>触诊耳尖偏凉，体温38.2℃，脉细弱，骨盆区域及尾根部触诊敏感。<br/><strong>3.辩证论治</strong><br/><strong>3.1辩证</strong>&nbsp;&nbsp;<br/>3.1.1脾气虚 &nbsp;患猫从小与同窝猫咪相比消瘦明显，可谓先天之精不足。肾为先天之本，脾为后天之本，脾肾之间存在着“先天温养后天，后天滋养先天”的关系，故二者在生理上相互资助、相互促进，在病理上相互影响、互为因果。患猫先天之精不足不能温养后天，所以患猫消瘦、肌肉薄弱，食欲不佳，挑食，长期食少导致脾气虚。<br/>3.1.2肾气虚 &nbsp;脾主运化，化生水谷精微为机体提供营养，患猫脾气虚，肾气需要脾化生的气血来提供营养，故患猫肾气不足，长期互相影响，导致脾肾两虚的体况。肾主骨、生髓、利关节、司二阴，故患猫表现鼻尖发白,耳尖不温，脉细弱等脾肾两虚症状而易发生骨折或大小便不利。<br/>3.1.3局部气滞血瘀 &nbsp;患猫有骨折外伤和手术病史，触诊腰背疼痛明显，此为局部实证，存在局部气滞血瘀，经络阻滞不通的情况。<br/>3.1.4虚实夹杂导致“癃闭”&nbsp;患猫总体辩证为虚实夹杂，脾肾两虚兼局部气滞血淤。脾主运化水湿，肾主水液与膀胱相表里，脾肾两虚，则脾气不足，肾气受损，使中气下陷或命门火衰，不能蒸腾气化，导致膀胱气化功能减退，开合失司，出现小便困难或尿失禁而形成“癃闭”之“闭证”。<br/><strong>3.2论治</strong><br/><a href=\"https://m.18zhongyao.com/post/2034.html\">癃闭</a>常因膀胱湿热、肺热壅盛、肝郁气滞、瘀血内阻、肾阳虚衰、中气不足等，患猫脾肾两虚，肾气受损，精血亏耗，命门火衰以致膀胱气化功能失常。治则以补脾肾、助气化，使气化得行，大小便自通。<br/><strong>3.3治疗方案</strong><br/><strong>3.3.1针灸治疗</strong><br/>针灸：取命门、肾俞、腰百会、小肠俞、膀胱俞、次髎、中髎、委中、昆仑透太溪、滴水、后三里、尾尖穴，行电针术（命门交替连委中/滴水穴；小肠俞连中髎穴；命门连尾尖穴；后三里连后三里穴），留针30min，其中15min连续波，15min断续波，频率40-80HZ，强度根据动物适应情况调节。<br/><strong>4.结果</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>针灸2次后，患猫尾部可以自主轻微扭动，刺激腰部区域，尾巴运动明显；针灸3次后，患猫自然运动时尾部不完全耷拉，可以轻微上抬，至此患猫仍不能自主排便、排尿；针灸6次后，患猫尾部可以抬高超过躯体；针灸7次后，患猫能自主排便到猫砂盆；针灸8次后，患猫连续2天可以自主排尿，至此，患猫能够自主大小便，未再出现排便、排尿困难现象。</p>','http://localhost:8888/files/20250714151145-微信图片_20250714151130.jpg','admin','2025-07-14 15:11:11'),(7,'一例英国短毛猫肠道梗阻的诊治','康复信息','一例英国短毛猫肠道梗阻的诊治','<p><strong>摘要</strong><strong>：</strong>胃肠道梗阻常导致顽固性呕吐，其后果可能危及生命的症状，包括电解质和酸碱紊乱、脱水、可能的返流误吸，甚至可能出现套叠、穿孔、内毒素血症、低血容量性休克。通过X光和超声准确的诊断非常重要，为手术争取最佳时机。本病例是异物引起的肠道梗阻并发功能性梗阻，术后及时恢复消化道营养和促进肠道蠕动对于恢复也尤为重要。<br/><strong>病例介绍</strong><br/><strong>1.1基本信息</strong><br/>英国短毛猫，奶茶，2Y8M，4.3 kg，已绝育，疫苗驱虫全。</p><p><strong>主诉</strong><br/>体重4.8kg，在家已有两天呕吐，有洗过澡，舔毛厉害，有吐毛球，触诊腹部有疼痛反应，但仅做了血检和要求医生开了3天胃溃宁回家观察。期间六天时间，每天有呕吐，化毛膏不吃，猫条闻到有呕吐动作，仅排过一次大便，食欲差，有脱水。<br/>另外该猫从小排便习惯是每两天一次大便。<br/><strong>临床检查</strong><br/><strong>体格检查</strong><br/>T:38.5℃，&nbsp;R:35次/min，P:160bpm，CRT:&lt;2s，BCS：5/9。口色粉，听诊心音、呼吸音未见明显异常。皮肤回弹差，脱水大约9%，触诊腹部比较敏感，有疼痛表现，双侧股动脉微弱。&nbsp;&nbsp;<br/><strong>血常规</strong><strong>和SAA</strong><br/>检查血常规和SAA，显示白细胞指标较高，以中性粒细胞升高为主，RBC、HGB、HCT升高，提示机体存在炎症和脱水。<br/><strong>超声检查</strong><br/>腹部超声检查显示，胃内空虚，十二指肠近端轻度积气，胃肠道蠕动功能非常差，小肠段有后方声影的影像，长度约1.58cm，伴有肠壁分层不清晰，前端未见明显的肠腔扩张表现，另外膀胱壁欠光滑，有点状高回声和絮状等回声内容物，胆囊壁回声增强。超声提示肠道存在异物，轻度胆囊炎，膀胱结晶/膀胱炎。</p><p><strong>X光影像学检查</strong></p><p>X光显示未见明显异常（5月14日），经过以上检查初步诊断为肠道异物，主人不能接受手术，选择先输液缓解脱水，之后决定于5月15日进一步钡餐造影诊断，5月15日下午14点开始喂钡餐后分别于14:15（15min）、15:00（1h）、18:00（4h）、24:00（10h）及5月16日10:00（20h）多次拍X光发现，钡餐始终停留在幽门十二指肠部，考虑到同时存在严重胃肠功能性梗阻，遂主人同意决定开腹探查。</p><p><strong>术后状态</strong><br/>术后第1-2天（5月17-18日）：精神好转，人工喂食，股动脉仍然较弱，血压较低，无大便；<br/>术后第3-5天（5月19-21日）：精神明显好转，人工喂食，股动脉已较强，血压恢复，术后第3天（5月19日）长久存积在结直肠的便便开始排出，较硬，5月20日便便稀软，5月21日成型软便。<br/>术后第6-9天（5月22-26日）：状态很不错，主动进食，成型软便；<br/>术后第10天（5月27日），回家。</p>','http://localhost:8888/files/20250714151342-微信图片_20250714151326.jpg','admin','2025-07-14 15:13:15'),(8,'CT检查肿瘤切除术','康复信息','12岁的亨利，是一只老年犬。有一个非常爱他的主人，一直陪伴着彼此。但近亨利过得很不开心，因为下颌居然长了一个恶性肿瘤。','<p>因为亨利已经很老了，所以牙齿很不好了主人看他近胃口不好，就带着去安安宠物医院检查并洁牙，安安宠医·竹辉路分院的王剑松院长是主人信任的医生，也是朋友，在洗牙过程中，细心的王院长发现了亨利的口腔有一个异常的团块。在通过采样证实是口腔黑色素瘤，口腔的黑色素瘤是非常恶性的肿瘤，而且有很高的转移几率（80%），王院长建议做更精准的检查，随后主人带着亨利转诊到了安安宠医·苏州中心医院进行治疗，这里有苏州专业的诊疗设备，还有来自台湾的影像学专家张安琦医生。</p><p>通过CT断层扫描亨利头部，骨窗可以明显看到其右侧下颌骨有骨溶解与骨膜反应，这是明显的异常现象；经过软组织造影之后可以看到团块已经跨越中线但尚未影响对侧的下颌；与肿瘤同一侧的下颌淋巴结相对较大，即使大小正常也仍有40%发生转移。因为CT相较于X光对肺脏结节的侦探更加敏感，所以医生进行胸腔的CT扫描，确定没有远端转移到肺脏器官的小团块。CT断层扫描确诊亨利病状后，王院长与主人沟通确认了局部切除下颌骨手术，手术由王院长亲自操刀并顺利完成，后配合药物治疗加上医助对其细心的看护，亨利恢复很好，现在又是一只吃嘛嘛香的好狗啦。</p>','http://localhost:8888/files/20250714151508-微信图片_20250714151455.jpg','admin','2025-07-14 15:15:10'),(9,'一例田园犬腰椎粉碎性骨折的诊治','康复信息','动物发生脊椎骨折一般是由大的外力所致：如咬伤、车祸伤或高楼坠伤等，这种脊椎损伤若没有对神经产生大的损伤，及时通过外科手段对患处减压固定一般恢复良好；若是出现严重的神经损伤，预后不良。','<p><strong>本病例为一只4岁田园犬，患犬于2023年5月1日就诊，就诊三天前即4月27日外出玩耍时发生车祸后出现后肢瘫痪，初步影像学检查发现L5粉碎性骨折，于5.2进行全面检查后，采用螺钉和骨水泥(Pin-PMMA) 固定。患犬术后一周即可行走自如。</strong><br/><strong>患犬基本资料&nbsp;</strong>品种：田园犬；年龄：4岁；性别：♀（未绝育）；体重：17.7KG；免疫不全，未驱虫；既往史：不详；现病史：患犬于4月27日 出现车祸，双后肢无法站立行走；4.30到医院检查发现L5出现骨折（图一二），5.1转到本院进一步检查。<br/><strong>临床检查</strong>T：38.5℃,P：约140次/分, R：40次/分，精神良好，双后肢疼痛反射明显，左后肢本体反射较差，脊椎压痛明显，腹部皮肤大面积淤血。<br/><strong>辅助检查</strong><br/>CBC检查：WBC轻度升高，其他未见异常<br/>血液生化：ALT升高，提示肝脏损伤<br/>超声检查结果：腹部超声检查未见异常；心超发现二尖瓣和三尖瓣轻度闭锁不全，未见明显结构异常， 收缩功能与舒张功能良好 &nbsp;<br/><strong>术前DR</strong>：L5骨折</p><p><strong>检查所见</strong><br/>骨窗下见L2-3椎间隙减小，椎体腹侧可见矿物质衰减阴影，L3椎间盘内可见一矿物质衰减阴影，椎管右腹侧内可见一线性矿物质衰减阴影，脊髓轻度向左侧偏移。L5椎体不连续，可见数个骨碎片，其中一个向右背侧偏移，并进入椎管内。 软组织窗和脑窗下见L5水平处的脊髓显著向左背侧偏移，脊髓连续性良好。<br/>结果：L3椎间盘钙化，部分椎间盘向右侧突出 L2-3退行性关节病 ，L5椎体粉碎性骨折，显著压迫脊髓，脊髓向左背侧位移<br/><strong>初步诊断</strong><br/>&nbsp;L5粉碎性骨折，L2-3退行性关节病<br/><strong>治疗计划</strong><br/>&nbsp;&nbsp;手术：半椎板切除+骨水泥固定（结合阳纹克氏针固定）<br/>&nbsp;&nbsp;过程描述：先镇静后用两个5ml的注射器针头在X光下定位骨折的腰椎位置（保证全过程无菌）；麻醉后消毒，切开皮肤、肌肉等组织，找到棘突，并用咬骨钳咬掉L5的棘（如图），再用磨钻小心地将椎板切开并磨平（如图），暴露神经（去除嵌入椎板内的碎骨片）；在L4L5L6左右两侧下椎板均打入两根阳纹柯氏针，同时在L5的左侧骨折处椎板打入贯穿柯氏针企图固定两个大的碎片（见术后DR），充分止血后剪少量的脂肪垫在脊椎开窗的部位，最后将准备好的骨水泥均匀地倒入开窗部位；等待骨水泥冷却后常规缝合伤口。</p>','http://localhost:8888/files/20250714151611-微信图片_20250714151543.jpg','admin','2025-07-14 15:16:41'),(10,'千亿宠物诊疗市场遭遇看病贵宠物险理赔控费如何破局','资讯信息','不少民众在给宠物治疗时发现，不同宠物医院对同一个症状开出的治疗费用大相径庭，甚至有些宠物医院的治疗费用价差达到千元，让他们感觉“宠物治疗费用像是在开盲盒”。','<p>不少民众在给宠物治疗时发现，不同宠物医院对同一个症状开出的治疗费用大相径庭，甚至有些宠物医院的治疗费用价差达到千元，让他们感觉“宠物治疗费用像是在开盲盒”。</p><p>这背后，是宠物治疗正遭遇一系列“看病贵”、“收费标准不统一”、“误诊多”、“过度诊疗频繁”等问题，也给正在兴起的宠物险构成巨大的理赔控费压力。</p><p>蚂蚁保宠物险运营负责人张艺琦向记者透露，当前由诊疗不规范与欺诈挤占所带来的赔付成本超过20%，由此带来一系列恶性循环，一是宠物险赔付率高企，部分渠道与保险公司亏损；二是保险公司不得不抬高保费与阻碍宠物险保障力提升而“维持”宠物险业务运营，无形间挤压广大投保人的权益。</p><p>为了破解这项“恶性循环”，业界开始积极探索宠物险行业在就医理念、诊疗标准等方面的应用规范，推动行业规范发展。</p><p>张艺琦告诉记者，蚂蚁保旗下宠物险业务正联合南京农业大学、新瑞鹏医疗集团、瑞派宠物医院集团等，共同探索宠物险在就医理念、诊疗标准等方面的应用规范，且相关规范将融入与蚂蚁保合作的定点宠物医院管理、就医推荐和理赔审核环节，通过医险联动确立标准化诊疗体系、科学用药指南及透明化服务标准，让宠物就医变得更加透明和公开，也让宠物主少花“冤枉钱”，切实保护消费者权益。</p><p>多位保险公司宠物险业务人士认为，要将这些规范更有效地落实到宠物诊疗与理赔审核环节，还需人工智能（AI）技术的赋能。</p><p>“要彻底有效地解决宠物看病贵问题，可以在事前、事中、事后三个环节引入AI技术，比如在事前，陪伴式的AI智能体能迅速根据宠物病症，向民众提供相应的疾病类型与专业科学诊疗建议，并推荐优质宠物医院，令民众及时对过度医疗或不规范治疗、以及黑心宠物医院说不；在事中，宠物险可以通过AI技术建立针对不同宠物疾病类型的疾病诊断相关分组（Diagnosis Related Groups, DRG），从而规范宠物诊疗费用开支的合理性，从而更大范畴缓解看病贵问题同时实现更好的理赔控费效果，也能显著降低民众的宠物诊疗负担；在事后，AI智能体还可以对一些看似不合理的诊疗案例进行诊疗流程追溯分析，并形成一些规范优化宠物诊疗方式，进一步解决某些宠物疾病看病贵问题。”一位保险公司宠物险业务负责人陈强向记者指出。</p><p><strong>宠物缘何“看病贵”</strong><br/></p><p>随着近年养宠家庭日益增多，“宠物经济”迅速火热。</p><p>数据显示，去年国内城镇宠物猫犬数量突破1.2亿只，相关宠物经济市场规模达到约3000亿元。其中，25%与宠物医疗相关。与此对应的是，宠物医疗产业规模复合增长率超过20%，单只宠物年均医疗支出持续提升。</p><p>这也带动宠物险规模水涨船高。</p><p>张艺琦告诉记者，预计2025年宠物险规模将达到约32亿元，在保渗透率也随之上升至逾5%。</p><p>但是，如何让宠物险迈过盈亏平衡点，正成为众多保险公司相当头疼的挑战。</p><p>多位保险公司宠物险业务人士直言，当前不少保险公司与渠道都面临宠物险业务亏损窘境，其中一个原因是“宠物看病贵”、“收费标准不统一”、“误诊多”、“过度诊疗频繁”等状况导致宠物险赔付率居高不下。</p><p>以常见的猫肠胃炎为例，轻度的猫肠胃炎只需进行血常规及粪便检测，再服用止吐药、益生菌等药物即可缓解症状，治疗费用通常不超过500元。但部分宠物医疗机构为了“多赚钱”，要么将轻度肠胃炎当成猫瘟治疗，导致单次诊疗费用高达2000—3000元；要么干脆给猫咪来个“全身系统性诊疗检查”，仅诊疗前检查检验费用就突破千元。这导致保险公司不但面临“昂贵”的宠物治疗理赔费用，还容易因猫肠胃炎诊疗不规范，与宠物医院、投保人就诊疗方式与理赔费用产生纠纷。</p><p>一位熟悉宠物医院经营状况的宠物诊疗专家向记者透露，这背后，是宠物医院正面临无序竞争所带来的生存压力。</p><p>“尽管宠物医疗产业市场规模突破千亿，但国内各类宠物医院数量达到约3万家，导致单个宠物医院普遍面临生存压力。尤其在某些一二线城市社区街道，一条马路就出现至少2—3家宠物医院，他们的生存环境更严峻，其中部分宠物医院只能靠抬高宠物诊疗费用维系经营。”他告诉记者。</p><p>记者获悉，当前“宠物看病贵”、“收费标准不统一”、“误诊多”、“过度诊疗频繁”等状况频发，还与宠物医院运营成本持续加大有着密切关系。除了不菲的房租与获客引流开支，宠物治疗仪器与药物的投入也相当昂贵，比如一套宠物超声仪器设备的采购价格超过10万元，可能没用多长时间就要设备迭代升级，宠物医院只能通过“过度诊疗”增加这项设备的使用率，尽快收回设备成本。与此同时，某些宠物治疗药物采购价格也相当高，若没有及时使用就面临药物过期失效风险，导致部分宠物医院只能“想尽办法”，通过误诊或夸大病症等手段将这些药物赶紧销售出去。</p><p>甚至个别宠物医院为了“增加营收”，还虚构宠物的治疗记录，或将宠物治疗记录“张冠李戴”，作为向保险公司申请更多理赔金额的手段。</p><p>这也令保险公司的理赔控费压力进一步上升。</p><p>上述保险公司宠物险业务负责人陈强告诉记者，围绕宠物险合作，他们与渠道方主要签订盈亏对赌模式，即围绕剩余利润进行分成。通过一段时间的实践，他发现不少渠道与保险公司同行都遭遇诊疗不规范与欺诈挤占所带来的赔付成本居高不下，导致赔付金额超过保费收入（承保亏损）的状况，在保险公司与渠道方都拿不到预期利润分成的情况下，彼此在宠物险的合作力度与意愿都相应下滑。</p><p>值得注意的是，为了有效压低欺诈行为所带来的赔付费用，也有渠道方通过前沿技术找到了有效解决方案。2019年起，蚂蚁保平台先后推出了宠物鼻纹识别技术、宠物生物信息识别系统等，帮助保险公司解决宠物身份唯一性的问题，并将这些技术应用在宠物险的投保与理赔流程，有效降低部分宠物医院通过“张冠李戴”套取宠物诊疗理赔款的风险。</p><p>新瑞鹏医务部负责人魏仁生向记者透露，若要彻底解决宠物治疗不规范、收费标准不统一与看病贵等问题，还需保险公司与宠物治疗业界共同努力，设立标准化诊疗体系、科学用药指南及透明化服务标准，让宠物就医更加透明公开，令保险公司理赔有章可循，也让民众获得更专业科学且费用合理的宠物诊疗服务。</p><p>要做好这项工作，难度也不小。</p><p>张艺琦告诉记者，目前部分宠物治疗机构为了增加营收，会对宠物险用户采取过度诊疗、阴阳收费的方式，且他们还与部分用户相互配合，通过篡改诊疗材料套取理赔资金，导致相关规范要在所有诊疗机构得到有效实施，仍有相当长的路要走。</p><p>在她看来，要杜绝这些宠物治疗不规范乱象，仍需保险公司与宠物治疗机构通力合作，引导民众前往治疗方式科学专业、治疗流程透明规范、治疗费用合理的优质诊疗医院，推动整个宠物医院产业的优胜劣汰，令标准化诊疗体系、科学用药指南及透明化服务标准得到更好地普及。</p><p><strong>诊疗标准规范“落地”挑战</strong><br/></p><p>记者获悉，围绕宠物险在就医理念、诊疗标准等方面的应用规范，保险公司与宠物医疗机构、专业学术机构已形成初步的研究方向：一是致力于宠物诊疗标准的制定与更新，尤其是针对猫犬常见疾病，系统性地梳理涵盖疾病分级、临床表现、治疗方案及用药剂量等核心环节的标准细则，为宠物医院提供规范化临床指导。二是宠物险合作平台基于对自身庞大理赔案例的大数据分析、宠物长期发病跟踪，从更广泛的数据视角洞察宠物发病规律、行业诊疗规范和收费标准，实现理赔审核标准的持续优化，长期推动宠物诊疗费用的合理化下降，推动整个宠物医疗行业规范发展同时实现更佳的理赔控费效果。</p><p>张艺琦告诉记者，蚂蚁保期待和生态各方共建规范、有序的宠物预防及诊疗标准。目前各方需做好大量工作，比如在宠物诊疗行业规范方面，需建立疾病图谱、疾病分级、定义指征、处置方案、用药用量、收费参考；在宠物险行业规范方面，也需建立宠物大病表、宠物重疾定义、精算模型、产品定价、理赔审核标准、宠物医院评级分层。</p><p>她直言，宠物险从产品设计、运营、理赔、到生态建设方面也将随之面临一系列新变革——保险产品设计需根据宠物诊疗标准做好产品改进；保险产品精算也需跟踪诊疗成本迭代清算模型；理赔审核标准需从平台视角优化审核尺度；保险公司在宠物医院经营洞察方面需尽快建立全面专业的医院评价及分层体系。</p><p>前述保险公司宠物险业务负责人陈强认为，要让宠物险在推广普及宠物治疗标准规范方面发挥更大的作用，还需保险公司引导更多投保人前往规范经营、诊疗方式合理专业、费用合理、用户体验良好的宠物医院给宠物看病，这不但有助于保险公司做好理赔控费工作并实现宠物险业务的可持续发展，还能驱动宠物医院行业全面执行宠物治疗标准规范，加快宠物医院的优胜劣汰进程。</p><p>他认为，无论是保险公司、还是蚂蚁保等合作平台，都应根据数据智能与行业专家知识，建立全面的宠物行业诊疗因子数据库与医院推荐模型，按照总理赔量、重疾理赔量、诊疗费用、费用区间、治愈率、用户评价等因子，将宠物医院分成“三好医院”、“常态医院”与“黑产医院”，一方面引导投保人前往“三好医院”与“常态医院”给宠物看病，另一方面对黑产医院进行识别、严打与清退，从而让宠物治疗标准规范实现更好的市场效果，带动宠物险与宠物治疗产业更好发展。</p><p>张艺琦向记者表示，目前蚂蚁保正基于数据智能与行业专家知识，结合20多种因子构建“找医院”产品，基于民众的宠物症状、疾病、科室与医院等就医场景精准匹配“三好医院”与“常态医院”，引导民众前往规范经营、诊疗方式专业科学与费用合理的宠物医院，既获得有效的宠物治疗方案又避免多花冤枉钱。</p><p>南京农业大学动物医学院预防兽医系副教授杨丹晨表示，宠物治疗标准规范的设立，将对宠物险带来新的发展机遇。它不仅有助于持续优化保险产品的条款与定价，还能缓解宠物看病难看病贵问题，推动宠物险与宠物医院共建良好行业生态。</p><p>张艺琦认为，一旦宠物诊疗市场形成治疗标准规范，未来宠物险产品设计将更具灵活性与实用性，包括提高保险保障杠杆、取消单次赔付限额等，有助于进一步降低民众的宠物诊疗财务负担。此外，这还将推动民众在宠物患病时主动寻求规范专业的宠物诊疗服务，进一步驱动宠物诊疗行业生态完善，实现宠物医院资源的合理筛选与分层。</p>','http://localhost:8888/files/20250714152100-微信图片_20250714152048.jpg','admin','2025-07-14 15:21:03'),(11,'宠物药店兴起,“毛孩子”小病不用跑医院了','资讯信息','随着国内养宠规模的日益壮大，养宠人对宠物日常备药的需求及对购药便捷性的要求不断增长。','<p>“深夜十二点多，猫咪突然呕吐，但家里没有常备的药品，不得不赶紧带它去24小时动物医院”“我的猫牙龈红肿、牙疼，医生开了包括X光、腹部B超等在内的6项检查单，费用近1500元”……对于家有宠物的人来说，大多经历过没有常备药、不得不深夜带宠物去医院，以及就医检查项目多、费用较高的情况。</p><p>随着国内养宠规模的日益壮大，养宠人对宠物日常备药的需求及对购药便捷性的要求不断增长。在此背景下，聚焦宠物轻医疗消费场景，提供宠物药品销售及基础诊疗服务的宠物药店，其市场潜力和发展空间正逐步显现。未来，给毛孩子买药会越来越方便。</p><h2><strong>宠物买药新渠道来了</strong></h2><p>5月22日，毛球药局深圳南山店开业。这是继红岭北店、车公庙店之后，毛球药局在深圳的第三家店。据其小红书运营人员回复网友称，目前还有几家新店在筹划中，但尚未官宣具体信息。</p><p><img src=\"https://pic.rmb.bdstatic.com/bjh/news/f78e2ee6fef301f6d8975f98caa00c81.jpeg?x-bce-process=image/auto-orient,o_1/resize,w_1242,limit_1/quality,Q_86/format,f_auto\"/></p><p>毛球药局是前京东宠物健康CEO佟文博的新创业项目，<strong>以线上门店的形式，通过无人仓模式，提供宠物药品的24小时即时配送服务</strong>。佟文博4月在接受读特新闻采访时称，毛球药局开业一个月以来，线上售卖超1000单，20:00后订单占比达70%，主要为应急药品。</p><p>由于缺乏宠物医疗知识，很多养宠人在毛孩子出现健康问题时常常手足无措。而带着宠物到线下医院问诊、买药，不仅要耗费大量的时间、精力，医疗费也不菲。更关键的是，在许多情况下，他们仅是单纯需要购买一些非处方药，对医院提供的诸多检查项目，及其检查必要性并不了解。</p><p>“轻医疗”模式下的宠物药店，为养宠人提供了更便捷、更经济的宠物健康管理选择。如在云南某县城开宠物药房的温睿（化名）所称，她开宠物药房的初衷，是希望能让宠物患病后，能像人类一样，实现“小病自己治”。</p><p>2024年底，拥有执业兽医资格的温睿回到云南老家开了一家宠物药房。“之前在宠物医院上班时，常常遇到觉得检查、治疗费太高，但又不得不为宠物进行治疗的宠物主。加上周围的朋友猫猫狗狗生病时，经常问我病因、需要吃什么药之类的问题，我就萌生了开个药房的想法。”她在社交平台上如是分享道。</p><p>而无论是线上还是线下宠物药店，提供基础的问诊服务都是其能否成功的关键因素。线上宠物药店更是大多主打“7×24小时”服务。例如，毛球药局宣称“24小时营业、资深执业宠物医生在线咨询”。《消费者报道》记者以养宠人身份联系了其微信客服，对方表示，早上8点至晚上24点医生均在线，24点之后如需紧急问诊，可以拨打门店电话。</p><p>值得注意的是，<strong>宠物药店的兴起或将进一步推动宠物药品的“去医院化”趋势，尤其在即时零售、轻处方药、高频急需等多重需求驱动下，宠物健康服务将更加贴近家庭场景。</strong></p><h2><strong>不止卖药的宠物药店</strong></h2><p>当下，宠物不再仅仅是养宠人的玩伴，而是提供“陪伴感”的家人。这种情感上的转变，带动宠物消费“防治养”的全面升级，宠物经济前景广阔。据艾媒咨询数据预测，中国宠物经济产业规模将保持积极增长态势，预计到2028年市场规模有望达到1.15万亿元。</p><p><strong>宠物健康消费也从“被动医疗”向“主动防护”转型</strong>，宠物药店的市场想象空间被充分打开。沙利文今年年初发布的《2025年中国宠物药物白皮书》显示，中国宠物药品市场规模从2020年的93.9亿元增长至2024年的209.5亿元。预计到2029年，市场规模将增至354.1亿元。</p><p>这吸引了多家连锁药店先后跨界入局宠物赛道。今年3月，国内知名医药零售连锁企业——山东立健药店连锁有限公司（简称“山东立健”）变更了经营范围，新增“兽药经营”业务。在山东立健之前，老百姓（603883.SH）于2024年12月新增“兽药经营”业务，第一医药连锁有限公司（下称“第一医药”）于2024年 6 月在上海开设了首家宠物友好药房，专门设置宠物专区，售卖宠物药品。</p><p>据《第一药店财智》报道，“药店+宠物”成为药品零售行业在探索优化存量、寻找增量路径中的新选择，除山东立健、老百姓之外，益丰、一心堂、大参林、健之佳、漱玉平民等上市连锁药店也已入局宠物赛道。</p><p>但值得注意的是，部分涉足宠物领域的连锁药店尚未涉及宠物药品销售，或其业务范围不仅限于宠物药品。例如，漱玉平民2023年5月上架的仅为宠物食品，而第一医药上述宠物友好药房还销售宠物护理产品、宠物食品和专用洗护产品，老百姓新增的业务中还包括“宠物食品及用品批发”。</p><p>专门的宠物药店也不仅局限于销售宠物药品。在回乡创业之前，温睿就已计划，除了宠物药品外，还将销售宠物洗护用品，并准备提供宠物医疗、宠物培训等服务。如今，她的宠物药店不仅提供宠物诊疗、药品、零食，还涵盖宠物寄养、洗护、绝育、疫苗注射等服务。</p><p>线上宠物药店同样如此。例如，京东健康宠物大药房除常见宠物药品外，还提供益生菌、鱼油等营养品，以及滴眼液、洁齿粒等护理用品。毛球药局则涵盖感冒、肠胃、皮肤等常见病症药品及驱虫药、营养补充剂，以及猫犬主粮、猫砂、水族异宠用品等。</p><p>这也是宠物药店在竞争激烈的宠物消费市场中的差异化发展路径。<strong>对宠物药店而言，除了基础问诊、轻医疗外，其竞争优势或更在亚健康预防、体验式服务及多元化经营、供应链整合</strong>。未来，随着宠物经济的持续升温，宠物药店有望成为宠物健康服务领域的重要力量。</p>','http://localhost:8888/files/20250714152254-微信图片_20250714152244.jpg','admin','2025-07-14 15:22:55'),(12,'狗狗肠胃不好吃什么药？','资讯信息','狗狗肠胃不好吃什么药？宠物医院告诉你们，狗狗肠胃不好时，用药要谨慎。','<p>狗狗肠胃不好吃什么药？<a href=\"http://www.ananpet.com/\" target=\"_blank\">宠物医院</a>告诉你们，狗狗肠胃不好时，用药要谨慎。若症状持续不缓解或加重，应及时带狗狗去宠物医院，准确诊断病因，在宠物医生指导下科学用药。<br/>&nbsp; &nbsp; &nbsp; 狗狗肠胃不好是常见问题，针对不同原因，可选择相应药物来调理。<br/>&nbsp; &nbsp; &nbsp;&nbsp;<strong>消化不良</strong><br/>&nbsp; &nbsp; &nbsp; 1.益生菌：当狗狗因进食过多、过快或食物不易消化导致消化不良时，益生菌是不错选择。像含有双歧杆菌、嗜酸乳杆菌等成分的宠物专用益生菌，能调节肠道菌群平衡，增强肠道消化功能。用法是拌在狗狗食物中，按产品说明剂量使用，一般小型犬每次 1 - 2 克，中大型犬每次 3 - 5 克，每天 1 - 2 次。持续使用一段时间，帮助狗狗恢复正常消化。<br/>&nbsp; &nbsp; &nbsp; 2.消食片：含有山楂、麦芽等成分的宠物消食片，可促进食物消化。山楂能增加胃酸分泌，麦芽富含淀粉酶，有助于淀粉类食物消化。狗狗消化不良时，按体重适量喂食，小型犬每次 1 - 2 片，中大型犬每次 3 - 4 片，可直接喂食或碾碎拌入食物，每天 2 - 3 次。<br/>&nbsp; &nbsp; &nbsp;&nbsp;<strong>肠胃炎症</strong><br/>&nbsp; &nbsp; &nbsp; 1.抗生素：若狗狗肠胃炎症由细菌感染引起，需使用抗生素治疗。常用的有阿莫西林，它能抑制细菌细胞壁合成，从而杀灭细菌。按每千克体重 10 - 20 毫克的剂量，每天 2 - 3 次口服。但使用抗生素要严格遵医嘱，避免滥用导致狗狗肠道菌群失调。<br/>&nbsp; &nbsp; &nbsp; 2.胃肠黏膜保护剂：如硫糖铝，可在狗狗肠胃黏膜表面形成一层保护膜，减轻炎症刺激，促进黏膜修复。剂量为每千克体重 10 - 25 毫克，每天 3 - 4 次，饭前空腹服用效果更佳。<br/>&nbsp; &nbsp;&nbsp;<strong>&nbsp; 呕吐腹泻</strong><br/>&nbsp; &nbsp; &nbsp; 1.止吐药：狗狗频繁呕吐时，可使用止吐药，如胃复安。它能抑制延髓催吐化学感受区，起到止吐作用。按每千克体重 0.2 - 0.5 毫克的剂量，每天 2 - 3 次口服。但止吐药只是缓解症状，需同时找出呕吐原因进行针对性治疗。<br/>&nbsp; &nbsp; &nbsp; 2.止泻药：对于腹泻症状，蒙脱石散较为常用。它能吸附肠道内病菌和毒素，同时保护肠道黏膜，起到止泻作用。小型犬每次 1 - 2 克，中大型犬每次 3 - 5 克，每天 3 次，用温水冲服。</p>','http://localhost:8888/files/20250714152406-微信图片_20250714152355.jpg','admin','2025-07-14 15:24:07');

/*Table structure for table `chat_list` */

DROP TABLE IF EXISTS `chat_list`;

CREATE TABLE `chat_list` (
  `id` int(19) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` varchar(19) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业务标识（可以为商品或者其他）',
  `product_image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业务图片（可以为商品或者其他）',
  `from_user_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发送者用户标识',
  `from_user_avatar` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送者用户头像',
  `from_user_nick` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送者用户昵称',
  `to_user_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接受者用户标识',
  `to_user_nick` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接受者用户昵称',
  `to_user_avatar` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接受者用户头像',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='聊天记录关联表';

/*Data for the table `chat_list` */

/*Table structure for table `chat_message` */

DROP TABLE IF EXISTS `chat_message`;

CREATE TABLE `chat_message` (
  `id` int(19) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `chat_list_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '聊天记录关联ID',
  `from_user_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发起者标识',
  `to_user_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接受者标识',
  `from_user_nick` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发起者昵称',
  `to_user_nick` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接受者昵称',
  `content` longtext COLLATE utf8mb4_unicode_ci COMMENT '聊天内容',
  `send_time` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送时间',
  `is_read` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '1已读0未读',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='聊天记录表';

/*Data for the table `chat_message` */

/*Table structure for table `front_user` */

DROP TABLE IF EXISTS `front_user`;

CREATE TABLE `front_user` (
  `user_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `status` varchar(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `sex` varchar(1) DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(500) DEFAULT NULL COMMENT '头像',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` varchar(64) DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='前端用户表';

/*Data for the table `front_user` */

insert  into `front_user`(`user_id`,`user_name`,`nick_name`,`password`,`status`,`email`,`phone`,`sex`,`avatar`,`create_by`,`create_time`,`remark`) values (2,'xzj','许某某','123456','0','18522149254@163.com','18522149254','0','http://localhost:8888/files/20250710142126-头像.jpg','xzj','2024-08-15 18:37:00',NULL),(3,'liuwei','刘玮','123456','0','13155438761@163.com','13155438761','1','http://localhost:8888/files/20250718114145-lunbo1.jpg','liuwei','2025-05-28 16:37:32',NULL);

/*Table structure for table `medical_case` */

DROP TABLE IF EXISTS `medical_case`;

CREATE TABLE `medical_case` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `registerId` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '挂号ID',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '宠物名称',
  `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '宠物性别',
  `age` varchar(3) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '宠物年龄',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `visitDate` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '就诊日期',
  `doctorId` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接诊医生',
  `medicalInfo` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '病因',
  `diagnosis` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '诊断记录',
  `adviceInfo` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '医嘱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='病例信息表';

/*Data for the table `medical_case` */

insert  into `medical_case`(`id`,`registerId`,`name`,`gender`,`age`,`phone`,`visitDate`,`doctorId`,`medicalInfo`,`diagnosis`,`adviceInfo`) values (1,'4','周周','0','3岁','15320087762','2025-07-21','3','呕吐','肚内有毛球','需要排毛，开药');

/*Table structure for table `register_info` */

DROP TABLE IF EXISTS `register_info`;

CREATE TABLE `register_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `clinic_id` int(10) DEFAULT NULL COMMENT '预约诊所服务ID',
  `user_id` int(10) DEFAULT NULL COMMENT '用户ID',
  `doc_id` int(10) DEFAULT NULL COMMENT '预约医生ID',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预约状态',
  `time` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '时间',
  `receive_id` int(10) DEFAULT NULL COMMENT '接诊ID',
  `receive_time` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接诊时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预约挂号表';

/*Data for the table `register_info` */

insert  into `register_info`(`id`,`clinic_id`,`user_id`,`doc_id`,`status`,`time`,`receive_id`,`receive_time`) values (2,3,2,NULL,'已接诊','2025-07-18 11:40:59',1,'2025-07-18 16:40:59'),(3,4,3,NULL,'已预约','2025-07-18 11:41:53',NULL,NULL),(4,NULL,2,3,'已完成','2025-07-18 13:57:47',3,'2025-07-18 16:13:56');

/*Table structure for table `sys_icon` */

DROP TABLE IF EXISTS `sys_icon`;

CREATE TABLE `sys_icon` (
  `icon_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'icon编号',
  `icon_name` varchar(50) DEFAULT NULL COMMENT 'icon名称',
  PRIMARY KEY (`icon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=281 DEFAULT CHARSET=utf8mb4 COMMENT='系统图标表';

/*Data for the table `sys_icon` */

insert  into `sys_icon`(`icon_id`,`icon_name`) values (1,'el-icon-platform-eleme'),(2,'el-icon-eleme'),(3,'el-icon-delete-solid'),(4,'el-icon-delete'),(5,'el-icon-s-tools'),(6,'el-icon-setting'),(7,'el-icon-user-solid'),(8,'el-icon-user'),(9,'el-icon-phone'),(10,'el-icon-phone-outline'),(11,'el-icon-more'),(12,'el-icon-more-outline'),(13,'el-icon-star-on'),(14,'el-icon-star-off'),(15,'el-icon-s-goods'),(16,'el-icon-goods'),(17,'el-icon-warning'),(18,'el-icon-warning-outline'),(19,'el-icon-question'),(20,'el-icon-info'),(21,'el-icon-remove'),(22,'el-icon-circle-plus'),(23,'el-icon-success'),(24,'el-icon-error'),(25,'el-icon-zoom-in'),(26,'el-icon-zoom-out'),(27,'el-icon-remove-outline'),(28,'el-icon-circle-plus-outline'),(29,'el-icon-circle-check'),(30,'el-icon-circle-close'),(31,'el-icon-s-help'),(32,'el-icon-help'),(33,'el-icon-minus'),(34,'el-icon-plus'),(35,'el-icon-check'),(36,'el-icon-close'),(37,'el-icon-picture'),(38,'el-icon-picture-outline'),(39,'el-icon-picture-outline-round'),(40,'el-icon-upload'),(41,'el-icon-upload2'),(42,'el-icon-download'),(43,'el-icon-camera-solid'),(44,'el-icon-camera'),(45,'el-icon-video-camera-solid'),(46,'el-icon-video-camera'),(47,'el-icon-message-solid'),(48,'el-icon-bell'),(49,'el-icon-s-cooperation'),(50,'el-icon-s-order'),(51,'el-icon-s-platform'),(52,'el-icon-s-fold'),(53,'el-icon-s-unfold'),(54,'el-icon-s-operation'),(55,'el-icon-s-promotion'),(56,'el-icon-s-home'),(57,'el-icon-s-release'),(58,'el-icon-s-ticket'),(59,'el-icon-s-management'),(60,'el-icon-s-open'),(61,'el-icon-s-shop'),(62,'el-icon-s-marketing'),(63,'el-icon-s-flag'),(64,'el-icon-s-comment'),(65,'el-icon-s-finance'),(66,'el-icon-s-claim'),(67,'el-icon-s-custom'),(68,'el-icon-s-opportunity'),(69,'el-icon-s-data'),(70,'el-icon-s-check'),(71,'el-icon-s-grid'),(72,'el-icon-menu'),(73,'el-icon-share'),(74,'el-icon-d-caret'),(75,'el-icon-caret-left'),(76,'el-icon-caret-right'),(77,'el-icon-caret-bottom'),(78,'el-icon-caret-top'),(79,'el-icon-bottom-left'),(80,'el-icon-bottom-right'),(81,'el-icon-back'),(82,'el-icon-right'),(83,'el-icon-bottom'),(84,'el-icon-top'),(85,'el-icon-top-left'),(86,'el-icon-top-right'),(87,'el-icon-arrow-left'),(88,'el-icon-arrow-right'),(89,'el-icon-arrow-down'),(90,'el-icon-arrow-up'),(91,'el-icon-d-arrow-left'),(92,'el-icon-d-arrow-right'),(93,'el-icon-video-pause'),(94,'el-icon-video-play'),(95,'el-icon-refresh'),(96,'el-icon-refresh-right'),(97,'el-icon-refresh-left'),(98,'el-icon-finished'),(99,'el-icon-sort'),(100,'el-icon-sort-up'),(101,'el-icon-sort-down'),(102,'el-icon-rank'),(103,'el-icon-loading'),(104,'el-icon-view'),(105,'el-icon-c-scale-to-original'),(106,'el-icon-date'),(107,'el-icon-edit'),(108,'el-icon-edit-outline'),(109,'el-icon-folder'),(110,'el-icon-folder-opened'),(111,'el-icon-folder-add'),(112,'el-icon-folder-remove'),(113,'el-icon-folder-delete'),(114,'el-icon-folder-checked'),(115,'el-icon-tickets'),(116,'el-icon-document-remove'),(117,'el-icon-document-delete'),(118,'el-icon-document-copy'),(119,'el-icon-document-checked'),(120,'el-icon-document'),(121,'el-icon-document-add'),(122,'el-icon-printer'),(123,'el-icon-paperclip'),(124,'el-icon-takeaway-box'),(125,'el-icon-search'),(126,'el-icon-monitor'),(127,'el-icon-attract'),(128,'el-icon-mobile'),(129,'el-icon-scissors'),(130,'el-icon-umbrella'),(131,'el-icon-headset'),(132,'el-icon-brush'),(133,'el-icon-mouse'),(134,'el-icon-coordinate'),(135,'el-icon-magic-stick'),(136,'el-icon-reading'),(137,'el-icon-data-line'),(138,'el-icon-data-board'),(139,'el-icon-pie-chart'),(140,'el-icon-data-analysis'),(141,'el-icon-collection-tag'),(142,'el-icon-film'),(143,'el-icon-suitcase'),(144,'el-icon-suitcase-1'),(145,'el-icon-receiving'),(146,'el-icon-collection'),(147,'el-icon-files'),(148,'el-icon-notebook-1'),(149,'el-icon-notebook-2'),(150,'el-icon-toilet-paper'),(151,'el-icon-office-building'),(152,'el-icon-school'),(153,'el-icon-table-lamp'),(154,'el-icon-house'),(155,'el-icon-no-smoking'),(156,'el-icon-smoking'),(157,'el-icon-shopping-cart-full'),(158,'el-icon-shopping-cart-1'),(159,'el-icon-shopping-cart-2'),(160,'el-icon-shopping-bag-1'),(161,'el-icon-shopping-bag-2'),(162,'el-icon-sold-out'),(163,'el-icon-sell'),(164,'el-icon-present'),(165,'el-icon-box'),(166,'el-icon-bank-card'),(167,'el-icon-money'),(168,'el-icon-coin'),(169,'el-icon-wallet'),(170,'el-icon-discount'),(171,'el-icon-price-tag'),(172,'el-icon-news'),(173,'el-icon-guide'),(174,'el-icon-male'),(175,'el-icon-female'),(176,'el-icon-thumb'),(177,'el-icon-cpu'),(178,'el-icon-link'),(179,'el-icon-connection'),(180,'el-icon-open'),(181,'el-icon-turn-off'),(182,'el-icon-set-up'),(183,'el-icon-chat-round'),(184,'el-icon-chat-line-round'),(185,'el-icon-chat-square'),(186,'el-icon-chat-dot-round'),(187,'el-icon-chat-dot-square'),(188,'el-icon-chat-line-square'),(189,'el-icon-message'),(190,'el-icon-postcard'),(191,'el-icon-position'),(192,'el-icon-turn-off-microphone'),(193,'el-icon-microphone'),(194,'el-icon-close-notification'),(195,'el-icon-bangzhu'),(196,'el-icon-time'),(197,'el-icon-odometer'),(198,'el-icon-crop'),(199,'el-icon-aim'),(200,'el-icon-switch-button'),(201,'el-icon-full-screen'),(202,'el-icon-copy-document'),(203,'el-icon-mic'),(204,'el-icon-stopwatch'),(205,'el-icon-medal-1'),(206,'el-icon-medal'),(207,'el-icon-trophy'),(208,'el-icon-trophy-1'),(209,'el-icon-first-aid-kit'),(210,'el-icon-discover'),(211,'el-icon-place'),(212,'el-icon-location'),(213,'el-icon-location-outline'),(214,'el-icon-location-information'),(215,'el-icon-add-location'),(216,'el-icon-delete-location'),(217,'el-icon-map-location'),(218,'el-icon-alarm-clock'),(219,'el-icon-timer'),(220,'el-icon-watch-1'),(221,'el-icon-watch'),(222,'el-icon-lock'),(223,'el-icon-unlock'),(224,'el-icon-key'),(225,'el-icon-service'),(226,'el-icon-mobile-phone'),(227,'el-icon-bicycle'),(228,'el-icon-truck'),(229,'el-icon-ship'),(230,'el-icon-basketball'),(231,'el-icon-football'),(232,'el-icon-soccer'),(233,'el-icon-baseball'),(234,'el-icon-wind-power'),(235,'el-icon-light-rain'),(236,'el-icon-lightning'),(237,'el-icon-heavy-rain'),(238,'el-icon-sunrise'),(239,'el-icon-sunrise-1'),(240,'el-icon-sunset'),(241,'el-icon-sunny'),(242,'el-icon-cloudy'),(243,'el-icon-partly-cloudy'),(244,'el-icon-cloudy-and-sunny'),(245,'el-icon-moon'),(246,'el-icon-moon-night'),(247,'el-icon-dish'),(248,'el-icon-dish-1'),(249,'el-icon-food'),(250,'el-icon-chicken'),(251,'el-icon-fork-spoon'),(252,'el-icon-knife-fork'),(253,'el-icon-burger'),(254,'el-icon-tableware'),(255,'el-icon-sugar'),(256,'el-icon-dessert'),(257,'el-icon-ice-cream'),(258,'el-icon-hot-water'),(259,'el-icon-water-cup'),(260,'el-icon-coffee-cup'),(261,'el-icon-cold-drink'),(262,'el-icon-goblet'),(263,'el-icon-goblet-full'),(264,'el-icon-goblet-square'),(265,'el-icon-goblet-square-full'),(266,'el-icon-refrigerator'),(267,'el-icon-grape'),(268,'el-icon-watermelon'),(269,'el-icon-cherry'),(270,'el-icon-apple'),(271,'el-icon-pear'),(272,'el-icon-orange'),(273,'el-icon-coffee'),(274,'el-icon-ice-tea'),(275,'el-icon-ice-drink'),(276,'el-icon-milk-tea'),(277,'el-icon-potato-strips'),(278,'el-icon-lollipop'),(279,'el-icon-ice-cream-square'),(280,'el-icon-ice-cream-round');

/*Table structure for table `sys_login` */

DROP TABLE IF EXISTS `sys_login`;

CREATE TABLE `sys_login` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `login_addr` varchar(64) DEFAULT NULL COMMENT '登录IP地址',
  `login_time` varchar(64) DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COMMENT='用户登录表';

/*Data for the table `sys_login` */

insert  into `sys_login`(`id`,`user_name`,`login_addr`,`login_time`) values (1,'admin','2.0.0.1','2024-08-08 18:04:27'),(2,'admin','2.0.0.1','2024-08-12 09:52:50'),(3,'admin','2.0.0.1','2024-08-12 10:18:52'),(4,'admin','2.0.0.1','2024-08-12 10:53:24'),(5,'admin','2.0.0.1','2024-08-12 15:05:54'),(6,'admin','2.0.0.1','2024-08-12 15:16:07'),(7,'admin','2.0.0.1','2024-08-12 15:16:44'),(8,'admin','2.0.0.1','2024-08-12 15:17:47'),(9,'admin','2.0.0.1','2024-08-12 15:32:33'),(11,'admin','2.0.0.1','2024-08-12 16:27:37'),(12,'admin','2.0.0.1','2024-08-12 17:05:59'),(13,'admin','2.0.0.1','2024-08-13 15:19:57'),(14,'admin','2.0.0.1','2024-08-13 15:34:55'),(15,'admin','2.0.0.1','2024-08-13 15:35:35'),(16,'admin','2.0.0.1','2024-08-13 16:02:23'),(17,'admin','2.0.0.1','2024-08-13 16:28:27'),(18,'admin','2.0.0.1','2024-08-13 16:46:51'),(19,'admin','2.0.0.1','2024-08-13 16:57:19'),(20,'admin','2.0.0.1','2024-08-14 15:28:29'),(21,'admin','2.0.0.1','2024-08-14 17:51:04'),(22,'admin','2.0.0.1','2024-08-19 11:19:08'),(23,'admin','2.0.0.1','2024-08-19 14:00:06'),(24,'admin','2.0.0.1','2024-08-19 14:07:14'),(25,'admin','2.0.0.1','2024-08-20 15:23:32'),(26,'admin','2.0.0.1','2024-08-27 16:18:08'),(27,'admin','2.0.0.1','2024-08-28 10:28:45'),(28,'admin','2.0.0.1','2024-08-28 14:58:38'),(29,'admin','2.0.0.1','2024-08-29 09:54:58'),(30,'admin','2.0.0.1','2024-09-02 18:01:19'),(31,'admin','2.0.0.1','2024-09-03 14:17:11'),(32,'admin','2.0.0.1','2024-11-12 11:11:08'),(33,'admin','192.168.110.88','2024-11-12 22:17:46'),(34,'admin','192.168.110.88','2024-11-12 22:20:19'),(35,'admin','192.168.110.88','2024-11-12 22:35:12'),(36,'admin','192.168.110.88','2025-02-17 22:45:32'),(37,'admin','192.168.110.88','2025-02-17 22:55:22'),(38,'admin','192.168.110.88','2025-03-01 11:38:54'),(39,'admin','172.31.26.208','2025-07-10 15:00:50'),(40,'admin','172.31.26.208','2025-07-11 10:57:41'),(41,'admin','172.31.26.208','2025-07-14 15:06:33'),(42,'admin','172.31.26.208','2025-07-17 11:35:41'),(43,'admin','172.31.26.208','2025-07-18 14:03:47'),(44,'zhang','172.31.26.208','2025-07-18 15:56:59'),(45,'xu','172.31.26.208','2025-07-18 15:57:17'),(46,'admin','172.31.26.208','2025-07-18 15:57:38'),(47,'xu','172.31.26.208','2025-07-18 16:13:49'),(48,'admin','172.31.26.208','2025-07-18 16:17:12'),(49,'admin','172.31.26.208','2025-07-21 09:48:00'),(50,'xu','172.31.26.208','2025-07-21 10:10:44'),(51,'admin','172.31.26.208','2025-07-21 10:11:02'),(52,'xu','172.31.26.208','2025-07-21 10:11:46'),(53,'admin','172.31.26.208','2025-07-21 10:19:20'),(54,'xu','172.31.26.208','2025-07-21 10:46:40'),(55,'admin','172.31.26.208','2025-07-21 10:46:54'),(56,'xu','172.31.26.208','2025-07-21 10:47:12'),(57,'zhang','172.31.26.208','2025-07-21 10:47:39'),(58,'admin','172.31.26.208','2025-07-21 10:47:55'),(59,'admin','172.31.26.208','2025-07-21 11:46:00'),(60,'admin','172.31.26.208','2025-07-21 12:29:00'),(61,'admin','172.31.26.208','2025-07-21 12:34:29');

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menu_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `route_name` varchar(50) DEFAULT NULL COMMENT '路由名称',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `label` varchar(50) DEFAULT NULL COMMENT '菜单名称（页面菜单显示）',
  `parent_id` int(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(255) DEFAULT NULL COMMENT '路由地址（页面访问的路径）',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径（对应的vue位置）',
  `menu_type` varchar(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单）',
  `status` varchar(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` varchar(64) DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menu_id`,`route_name`,`menu_name`,`label`,`parent_id`,`order_num`,`path`,`component`,`menu_type`,`status`,`icon`,`create_by`,`create_time`,`remark`) values (1,'home','首页','首页',0,0,'/home','views/manage/Home','C','0','el-icon-s-home','admin','2024-05-17 18:27:58','首页'),(2,NULL,'系统管理','系统管理',0,1,NULL,NULL,'M','0','el-icon-setting','admin','2024-05-17 18:27:58','系统管理'),(9,'SysUser','用户管理','用户管理',2,1,'/sysuser','views/manage/system/SysUser','C','0','el-icon-user','admin','2024-05-17 18:27:58','用户管理'),(10,'SysRole','角色管理','角色管理',2,2,'/sysrole','views/manage/system/SysRole','C','0','el-icon-s-custom','admin','2024-05-17 18:27:58','角色管理'),(11,'SysMenu','菜单管理','菜单管理',2,3,'/sysmenu','views/manage/system/SysMenu','C','0','el-icon-menu','admin','2024-05-17 18:27:58','菜单管理'),(12,'SysIcon','图标管理','图标管理',14,1,'/sysicon','views/manage/system/SysIcon','C','0','el-icon-picture','admin','2024-05-17 18:27:58','图标管理'),(14,NULL,'系统工具','系统工具',0,2,NULL,NULL,'M','0','el-icon-s-cooperation','admin','2024-07-04 18:27:58','系统工具'),(15,NULL,'系统信息','系统信息',0,3,NULL,NULL,'M','0','el-icon-info','admin','2024-07-23 11:38:02','信息管理'),(16,'BaseNotice','公告管理','公告管理',15,2,'/basenotice','views/manage/base/BaseNotice','C','0','el-icon-s-comment','admin','2024-07-23 11:40:35','公告管理'),(17,'SysCarousel','轮播图管理','轮播图管理',15,1,'/basecarousel','views/manage/base/BaseCarousel','C','0','el-icon-picture','1','2024-07-23 11:52:22','轮播图管理'),(21,'SysChart','图表管理','图表管理',14,2,'/syschart','views/manage/system/SysChart','C','0','el-icon-pie-chart','1','2024-07-26 09:59:43','图表管理'),(22,'SysLogin','登录日志','登录日志',15,3,'/syslogin','views/manage/system/SysLogin','C','0','el-icon-s-custom','1','2024-08-08 17:52:24','登录日志'),(23,NULL,'诊所管理','诊所管理',0,4,NULL,NULL,'M','0','el-icon-office-building','1','2024-08-19 11:30:32','诊所管理'),(24,'BusNews','诊所信息','诊所信息',15,5,'/busnews','views/manage/bus/BusNews','C','0','el-icon-info','1','2024-08-19 11:34:06','诊所资讯、康复信息'),(25,'BusCategory','服务分类','服务分类',23,1,'/buscategory','views/manage/bus/BusCategory','C','0','el-icon-s-grid','1','2024-08-19 14:01:30','服务分类'),(26,'BusComment','论坛信息','论坛信息',15,4,'/buscomment','views/manage/bus/BusComment','C','0','el-icon-s-comment','1','2024-08-20 15:26:34','论坛信息'),(27,'FrontUser','前端用户','前端用户',2,4,'/foreuser','views/manage/base/FrontUser','C','0','el-icon-user','1','2024-11-12 11:11:50','前端用户'),(28,'RegisterInfo','服务接诊','服务接诊',23,2,'/registerInfo','views/manage/bus/RegisterInfo','C','0','el-icon-loading','1','2025-07-18 14:15:10','服务接诊'),(29,'RegisterInfo2','医生接诊','医生接诊',23,3,'/registerInfo2','views/manage/bus/RegisterInfo2','C','0','el-icon-date','1','2025-07-18 14:18:17','医生接诊'),(30,'DocMake','诊疗管理','诊疗管理',0,5,'/docMake','views/manage/bus/DocMake','C','0','el-icon-s-marketing','1','2025-07-18 16:22:46','诊疗管理'),(31,'MedicalCase','病例管理','病例管理',0,6,'/medicalCase','views/manage/bus/MedicalCase','C','0','el-icon-s-grid','1','2025-07-21 10:20:21','病例管理'),(32,NULL,'统计管理','统计管理',0,7,NULL,NULL,'M','0','el-icon-s-data','1','2025-07-21 10:30:41','统计管理'),(33,'RegisterCount','预约统计','预约统计',32,1,'/registerCount','views/manage/bus/RegisterCount','C','0','el-icon-postcard','1','2025-07-21 10:31:58','预约统计');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(128) DEFAULT NULL COMMENT '角色名称',
  `status` varchar(6) DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` varchar(64) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`role_name`,`status`,`create_by`,`create_time`,`remark`) values (1,'超级管理员','0','admin','2024-06-07 18:27:58','系统超级管理员'),(2,'医生','0','admin','2024-06-07 18:27:58','医生');

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` int(20) NOT NULL COMMENT '角色ID',
  `menu_id` int(20) NOT NULL DEFAULT '0' COMMENT '菜单id',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,1),(2,12),(2,14),(2,21),(2,23),(2,25),(2,28),(2,29),(2,30),(2,31);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(64) DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `status` varchar(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `sex` varchar(1) DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(500) DEFAULT NULL COMMENT '头像',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` varchar(64) DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `job` varchar(100) DEFAULT NULL COMMENT '职位',
  `descr` varchar(500) DEFAULT NULL COMMENT '描述',
  `beGoodAt` varchar(100) DEFAULT NULL COMMENT '擅长',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`user_name`,`nick_name`,`password`,`status`,`email`,`phone`,`sex`,`avatar`,`create_by`,`create_time`,`remark`,`job`,`descr`,`beGoodAt`) values (1,'admin','超级管理员','123456','0','admin@163.com','15320085772','0','http://localhost:8888/files/1.png','admin','2024-06-11 19:10:37','系统超级管理员用户',NULL,NULL,NULL),(3,'xu','许医生','123456','0','xu@163.com','15320085772','1','http://localhost:8888/files/20250717113618-用户头像.png','1','2024-07-09 14:05:58','普通用户','成都区域技术总监 | 成都中心医院院长','从事宠物医疗工作20余年，擅长小动物外科手术及疑难病症诊治，临床经验丰富。曾发表多篇专业论文。','外科手术,疑难病症,急救'),(4,'zhang','张医生','123456','0',NULL,NULL,'0','http://localhost:8888/files/20250717153047-微信图片_20250717152958.jpg','1','2025-07-17 15:31:24',NULL,'广州分公司副总经理 | 技术总监','专注于宠物内科疾病研究，尤其在消化系统疾病诊疗方面有深厚造诣。带领团队完成多项科研项目，医疗技术精湛。','内科疾病,消化系统,临床诊断'),(5,'li','李医生','123456','0',NULL,NULL,'1','http://localhost:8888/files/20250717153146-微信图片_20250717153026.jpg','1','2025-07-17 15:32:23',NULL,'两湖技术总监 | 长沙安文分院院长','擅长猫科疾病诊疗，对猫咪行为学有深入研究，提倡人性化治疗。曾赴美国进修猫科专科，带回先进的诊疗理念和技术。','猫科疾病,急救'),(6,'zhao','赵医生','123456','0',NULL,NULL,NULL,'http://localhost:8888/files/20250717153306-微信图片_20250717153031.jpg','1','2025-07-17 15:33:08',NULL,'技术主任','擅长宠物的各种疑难病症。','疑难病症,临床诊断');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `role_id` int(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`) values (1,1),(3,2),(4,2),(5,2),(6,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
