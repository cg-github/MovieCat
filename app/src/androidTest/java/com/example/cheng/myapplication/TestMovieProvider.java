package com.example.cheng.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.cheng.myapplication.data.MovieContract;
import com.example.cheng.myapplication.util.JsonParser;

import org.json.JSONException;

import java.util.Vector;

/**
 * Created by cheng on 2017/1/5.
 */

public class TestMovieProvider extends AndroidTestCase {

    long movieId = 297761;
    static final String movieTitle = "X特遣队";

    private final static String TEST_JSON_STRING="{\"page\":1,\"results\":[{\"poster_path\":\"\\/vD2XUVUm9enYhp232oL6R0TaCPL.jpg\",\"adult\":false,\"overview\":\"赛琳娜，面对狼人和吸血鬼两族的背叛。依靠唯一的盟友，戴维和他的父亲托马斯，她努力终结狼人和吸血鬼之间的永恒战斗，即使需要付出极大的牺牲。\",\"release_date\":\"2016-12-01\",\"genre_ids\":[28,27],\"id\":346672,\"original_title\":\"Underworld: Blood Wars\",\"original_language\":\"en\",\"title\":\"黑夜传说5：血战\",\"backdrop_path\":\"\\/PIXSMakrO3s2dqA7mCvAAoVR0E.jpg\",\"popularity\":45.020986,\"vote_count\":274,\"video\":false,\"vote_average\":4},{\"poster_path\":\"\\/4W9ejAfUnqbBAD5wmfGxC04wFp8.jpg\",\"adult\":false,\"overview\":\"“X特遣队”又称“自杀小队”，成员都是面对严重牢狱之灾的超级罪犯，包括死亡射手、鳄鱼杀手、回旋镖队长和哈莉·奎茵等，他们为美国政府干一些极其危险的脏活，以此来为自己减刑。每个成员脖子里都被装有纳米炸弹，如果不乖乖执行任务就会被炸死。影片由大卫·阿耶执导，已于 2016年上映。\",\"release_date\":\"2016-08-02\",\"genre_ids\":[28,80,14],\"id\":297761,\"original_title\":\"Suicide Squad\",\"original_language\":\"en\",\"title\":\"X特遣队\",\"backdrop_path\":\"\\/34dxtTxMHGKw1njHpTjDqR8UBHd.jpg\",\"popularity\":40.567701,\"vote_count\":3180,\"video\":false,\"vote_average\":5.9},{\"poster_path\":\"\\/qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg\",\"adult\":false,\"overview\":\"\",\"release_date\":\"2016-12-14\",\"genre_ids\":[28,12,14,878,53,10752],\"id\":330459,\"original_title\":\"Rogue One: A Star Wars Story\",\"original_language\":\"en\",\"title\":\"Rogue One: A Star Wars Story\",\"backdrop_path\":\"\\/tZjVVIYXACV4IIIhXeIM59ytqwS.jpg\",\"popularity\":38.016694,\"vote_count\":334,\"video\":false,\"vote_average\":7.6},{\"poster_path\":\"\\/aHhGjO3jaxUKiWXiXwJCVt3icjC.jpg\",\"adult\":false,\"overview\":\"　　根据星云奖以及雨果奖得主、华裔科幻作家姜峯楠的同名短篇小说改编，讲述了外星人飞船来到地球，艾米·亚当斯饰演的语言学家受雇于政府，来与外星人沟通了解它们此行的目的。然而当用外星语言“七肢桶”与这些来客交流时，她眼前突然浮现了 她从出生到死亡、已知或未知的完整一生。杰瑞米·雷纳将饰演一名物理学教授，他与亚当斯饰演的语言学家一同受雇于政府。\",\"release_date\":\"2016-11-10\",\"genre_ids\":[18,878],\"id\":329865,\"original_title\":\"Arrival\",\"original_language\":\"en\",\"title\":\"降临\",\"backdrop_path\":\"\\/yIZ1xendyqKvY3FGeeUYUd5X9Mm.jpg\",\"popularity\":32.107515,\"vote_count\":728,\"video\":false,\"vote_average\":6.5},{\"poster_path\":\"\\/nc2EerGCmdmENaBGRhMiEiXrEA9.jpg\",\"adult\":false,\"overview\":\"《神奇动物在哪里》把我们带进了J·K·罗琳笔下一个崭新的魔法世界，一段早于《哈利·波特》几十年，发生在地球另一端的故事。\\r 1926年纽约的魔法世界，危机四伏。某个神秘的力量在街头制造了一连串的破坏，并扬言要向反魔法师狂热组织第二塞勒姆'揭露魔法社会的存在，并借他们的手斩草除根。而强大的黑魔法师盖勒特·格林德沃，在欧洲制造了浩劫后消身匿迹，至今没人能寻得其踪。\\r 魔法动物学家纽特·斯卡曼德（埃迪·雷德梅恩 Eddie Redmayne 饰）带着一个魔法皮箱抵达纽约，里头保护着不少神奇魔法动物。只是箱子里的部分神奇动物阴差阳错地跑了出来。本打算短暂停留的纽特，不得不和新认识的三个小伙伴蒂娜（凯瑟琳·沃特斯顿 Katherine Waterston 饰）、雅各布（丹·福勒 Dan Fogler 饰）和奎妮（艾莉森·萨多尔 Alison Sudol 饰），在纽约展开一场精彩的冒险之旅。\",\"release_date\":\"2016-11-16\",\"genre_ids\":[10751,12,14],\"id\":259316,\"original_title\":\"Fantastic Beasts and Where to Find Them\",\"original_language\":\"en\",\"title\":\"神奇动物在哪里\",\"backdrop_path\":\"\\/6I2tPx6KIiBB4TWFiWwNUzrbxUn.jpg\",\"popularity\":24.391866,\"vote_count\":1211,\"video\":false,\"vote_average\":7},{\"poster_path\":\"\\/6YRdgYyT6qZHcRhR6qKedY3qS7R.jpg\",\"adult\":false,\"overview\":\"《佩小姐的奇幻城堡》由好莱坞风格独树一帜的鬼才名导提姆·波顿执导，改编自畅销奇幻小说。影片主要讲述雅格布的爷爷自雅格布还小的时候，就会常说床边故事给他听，故事中有许多拥有特殊能力的小孩，爷爷过世后，留下了关于这个属于另一个时空世界种种谜团的线索，雅格布循着这些线索，来到了一个住着许多拥有特殊能力孩子的城堡，他逐渐认识这里的人，并跟他们成为了朋友。同时，他也发现了爷爷的故事里所说的可怕怪物，而他的到来，也使得这个地方变得更加危险。他必须运用自己与众不同的奇特特质，才能解救他的新朋友们。\",\"release_date\":\"2016-09-28\",\"genre_ids\":[14],\"id\":283366,\"original_title\":\"Miss Peregrine's Home for Peculiar Children\",\"original_language\":\"en\",\"title\":\"佩小姐的奇幻城堡\",\"backdrop_path\":\"\\/qXQinDhDZkTiqEGLnav0h1YSUu8.jpg\",\"popularity\":21.732455,\"vote_count\":630,\"video\":false,\"vote_average\":6.1},{\"poster_path\":\"\\/bfr35DYREc4M6sVbjokjdtCs7v8.jpg\",\"adult\":false,\"overview\":\"2009年1月15日，萨利（汤姆·汉克斯）在全美航空1549号班担任机长，飞机起飞两分钟后遭到飞鸟攻击，两架发动机全部熄火，萨利决定在哈德逊河上迫降，155人全数生还。但之后的调查显示他做了错误的抉择，认为大可选择返回拉瓜地亚机场。机内到底发生了什么呢？\",\"release_date\":\"2016-08-16\",\"genre_ids\":[18,36],\"id\":363676,\"original_title\":\"Sully\",\"original_language\":\"en\",\"title\":\"萨利机长\",\"backdrop_path\":\"\\/vC9H1ZVdXi1KjH4aPfGB54mvDNh.jpg\",\"popularity\":21.209326,\"vote_count\":474,\"video\":false,\"vote_average\":6.6},{\"poster_path\":\"\\/jRhTFJ7RT46OzIN95LGrBr6gx2L.jpg\",\"adult\":false,\"overview\":\"世界已崩坏，人性已殆尽，这是个血和火的世界，为了生存所有人都必须残酷的斗争。在无尽的荒芜沙漠中，两个逃亡的反抗份子有可能给这世界建立秩序。麦克斯，一个沉默寡言，用行动说话的男人，他在混乱中失去了家人，如今他追寻的只有平静。弗瑞奥萨，一个用行动说话的强悍女人，她相信穿越沙漠回到儿时故乡是生存的唯一途径。当两人相遇，公路大战也随之爆发。\",\"release_date\":\"2015-05-13\",\"genre_ids\":[28,12,878,53],\"id\":76341,\"original_title\":\"Mad Max: Fury Road\",\"original_language\":\"en\",\"title\":\"疯狂的麦克斯：狂暴之路\",\"backdrop_path\":\"\\/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg\",\"popularity\":20.814926,\"vote_count\":6021,\"video\":false,\"vote_average\":7.1},{\"poster_path\":\"\\/xZ6qp3VPyD5YM5VN1EydbDBG2JM.jpg\",\"adult\":false,\"overview\":\"美国队长史蒂夫·罗杰斯（克里斯·埃文斯 Chris Evans 饰）带领着全新组建的复仇者联盟，继续维护世界和平。然而，一次执行任务时联盟成员不小心造成大量平民伤亡，从而激发政治压力，政府决定通过一套监管系统来管理和领导复仇者联盟。联盟内部因此分裂为两派：一方由史蒂夫· 罗杰斯领导，他主张维护成员自由，在免受政府干扰的情况下保护世界；另一方则追随托尼·斯塔克（小罗伯特·唐尼 Robert Downey Jr. 饰），他令人意外地决定支持政府的监管和责任制体系。神秘莫测的巴基（塞巴斯蒂安·斯坦 Sebastian Stan 饰）似乎成为内战的关键人物……\",\"release_date\":\"2016-04-27\",\"genre_ids\":[28],\"id\":271110,\"original_title\":\"Captain America: Civil War\",\"original_language\":\"en\",\"title\":\"美国队长3\",\"backdrop_path\":\"\\/m5O3SZvQ6EgD5XXXLPIP1wLppeW.jpg\",\"popularity\":18.291024,\"vote_count\":3724,\"video\":false,\"vote_average\":6.8},{\"poster_path\":\"\\/zSZZLGiMDCSo8trlFe5Diw22rHS.jpg\",\"adult\":false,\"overview\":\"《豪勇七蛟龙\\/七侠荡寇志》灵感来自黑泽明（Akira Kurosawa）著名的《七武士》（Seven Samurai）。故事讲述墨西哥一个小村庄不堪强盗们的侵扰，于是花钱从美国请来七位身怀绝技的枪手来对抗强盗。\\r 　　新版《豪勇七蛟龙\\/七侠荡寇志》剧本先后由尼克·皮佐莱托（Nic Pizzolatto）、约翰·李·汉考克（John Lee Hancock）等创作，其故事设定在美国内战结束之后，西部大开发的末期。\",\"release_date\":\"2016-09-14\",\"genre_ids\":[28,12,37],\"id\":333484,\"original_title\":\"The Magnificent Seven\",\"original_language\":\"en\",\"title\":\"豪勇七蛟龙\",\"backdrop_path\":\"\\/T3LrH6bnV74llVbFpQsCBrGaU9.jpg\",\"popularity\":17.848578,\"vote_count\":874,\"video\":false,\"vote_average\":4.9},{\"poster_path\":\"\\/hxGAYQDrvOH1kTlCGKlP0NE4Gar.jpg\",\"adult\":false,\"overview\":\"世界在历经极端气候与粮食危机，地球濒临末日之际，一位电机工程师与一群专业研究者还有顶尖太空人，扛起人类史上最重要的任务，越过已知的银河，在星际间寻找未来出路。同时必须先割舍无法与家人见面的亲情牵绊，在爱与勇气、生存与挑战中，跨越星际拯救人类……\",\"release_date\":\"2014-11-05\",\"genre_ids\":[12,18,878],\"id\":157336,\"original_title\":\"Interstellar\",\"original_language\":\"en\",\"title\":\"星际穿越\",\"backdrop_path\":\"\\/xu9zaAevzQ5nnrsXN6JcahLnG4i.jpg\",\"popularity\":16.583983,\"vote_count\":6268,\"video\":false,\"vote_average\":8},{\"poster_path\":\"\\/qUsAlQ7nx80SXKYxQI612eL9Jk4.jpg\",\"adult\":false,\"overview\":\"　　在后斯诺登时代，中情局系统被黑客入侵，多项特工计划可能泄露。与此同时，中情局还发现了杰森·伯恩（马特·达蒙饰）和好搭档尼基·帕森斯（朱丽娅·斯蒂尔斯饰）的踪迹。年轻的网络专家海瑟·李（艾丽西亚·维坎德饰）自告奋勇追踪杰森·伯恩，而杰森·伯恩也在寻找着关于自己身世的惊天黑幕。中情局高官罗伯特·杜威（汤米·李·琼斯饰）是知晓一切幕后秘密的人。罗伯特·杜威、海瑟·李和杰森·伯恩之间的角力悬念，不到最后一刻，就无法了解真正的结局  。\",\"release_date\":\"2016-07-27\",\"genre_ids\":[28,53],\"id\":324668,\"original_title\":\"Jason Bourne\",\"original_language\":\"en\",\"title\":\"谍影重重5\",\"backdrop_path\":\"\\/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg\",\"popularity\":15.788295,\"vote_count\":1282,\"video\":false,\"vote_average\":5.6},{\"poster_path\":\"\\/adqL6JyooWGK6xyHkWPjVO4rI7b.jpg\",\"adult\":false,\"overview\":\"侏罗纪公园惨剧发生的22年后。如今公园的所在地“努布拉岛”已经被重新建立成为了一个崭新的恐龙主题乐园，并更名为“侏罗纪世界”。当初的开办者约翰·哈蒙德所创办的“国际基因科技公司”已经被“马斯拉尼国际企业”收购。\\r 　　欧文（克里斯·帕拉特 Chris Pratt 饰）是一名退役军人以及动物行为专家，在主园区的外围的迅猛龙研究基地进行隐密的工作。欧文多年来训练了一批具侵略性的迅猛龙，和它们建立起主从关系，勉勉强强让它们得以压抑住掠食者的天性、不情愿的听从指示。久而久之，当公园的游客开始对恐龙的表演开始感到厌倦时，在公司的要求下，公园所有的基因科学家夜以继日地创造出了一只基因混合恐龙——D-Rex掠食者恐龙，专家一开始只是为了增加游客数量才动用这个计划，但未预料到野蛮和聪明程度都仍是未知数的恐龙想出了逃脱方法，而消失在丛林深处。侏罗纪世界里的所有生物，包括人类和恐龙的性命都受到威胁。这时内部的命令让园区陷入混乱，游客成为了恐龙的猎物。\\r 　　恐龙们相继逃出，在大地、空中和水里对人类进行猎杀，整个园区已经没有任何一个角落是安全的，22年前的惨剧再度上演……\",\"release_date\":\"2015-06-09\",\"genre_ids\":[28,12,878,53],\"id\":135397,\"original_title\":\"Jurassic World\",\"original_language\":\"en\",\"title\":\"侏罗纪世界\",\"backdrop_path\":\"\\/dkMD5qlogeRMiEixC4YNPUvax2T.jpg\",\"popularity\":15.730726,\"vote_count\":5417,\"video\":false,\"vote_average\":6.5},{\"poster_path\":\"\\/tBrssQ4PksKC5sBBWOITyyDgflZ.jpg\",\"adult\":false,\"overview\":\"大家最喜爱的、健忘的多莉（艾伦·德杰尼勒斯 Ellen DeGeneres 配音）回来了！这次她将与好朋友尼莫（海登·罗兰斯 Hayden Rolence 配音）与马林（艾伯特·布鲁克斯 Albert Brooks 配音）一起，寻找她的神秘过去和她遗失的家庭。她能记起来什么？她的父母是谁？还有，她是从哪儿学会了说鲸语？多莉与马林、尼莫一起开启了这场改变“鱼”生的大冒险，穿越大洋至加州著名的海洋生物研究所。 在这里，多莉获得了三位有趣居民的鼎力支持：一只脾气暴躁、经常让研究所雇员找得团团转的章鱼汉克（艾德·奥尼尔 Ed O'Neill 配音）；一头坚信自己的生物声呐技能出问题了的白鲸贝利（泰·布利尔 Ty Burrell 配音）；以及一头严重近视眼的鲸鲨运儿（凯特琳·奥尔森 Kaitlin Olson 配音）。当多莉和朋友们在研究所错综复杂的内部水道中辗转前行时，她也逐渐对友谊、亲情和家庭的奥义有了更深刻的认识……\",\"release_date\":\"2016-06-16\",\"genre_ids\":[16,10751],\"id\":127380,\"original_title\":\"Finding Dory\",\"original_language\":\"en\",\"title\":\"海底总动员2：多莉去哪儿\",\"backdrop_path\":\"\\/iWRKYHTFlsrxQtfQqFOQyceL83P.jpg\",\"popularity\":15.373285,\"vote_count\":1591,\"video\":false,\"vote_average\":6.7},{\"poster_path\":\"\\/cyyTjHW0oemFgJYE7Gyx5yxbFbs.jpg\",\"adult\":false,\"overview\":\"“奇异博士”斯特兰奇是漫威第三阶段的重要角色，曾经是一位外科医生的他在车祸之后失去了赖以为生的本领，为了治疗双手，斯特兰奇踏上前往异国他乡的旅程，旅途中的奇遇让他成为了拥有超凡魔力的至尊法师，并运用自己的本领对抗黑暗力量，捍卫世界。\",\"release_date\":\"2016-10-25\",\"genre_ids\":[28,12,14,878],\"id\":284052,\"original_title\":\"Doctor Strange\",\"original_language\":\"en\",\"title\":\"奇异博士\",\"backdrop_path\":\"\\/tFI8VLMgSTTU38i8TIsklfqS9Nl.jpg\",\"popularity\":15.301267,\"vote_count\":1436,\"video\":false,\"vote_average\":6.6},{\"poster_path\":\"\\/xkB6WiNg93DyDhOLHHhWfsJsI5e.jpg\",\"adult\":false,\"overview\":\"银河帝国皇帝帕尔帕廷及其徒弟达斯·维德死后三十年，银河系又崛起了一个黑暗政权——第一秩序（First Order）。他们制造了一件比死星更强大的超级武器——弑星者基地（Starkiller Base）。弑星者基地具有摧毁一颗恒星的威力。与此同时，贾库星球的拾荒者蕾伊、第一秩序的逃兵芬恩、“千年隼号”的船长汉·索洛、抵抗组织的飞行员波·达默龙、抵抗组织的领导人莱娅·奥加纳将军等新老英雄将齐心协力，粉碎第一秩序的邪恶阴谋。他们还有另一个目标——找到失踪的卢克·天行者。\",\"release_date\":\"2015-12-15\",\"genre_ids\":[28,12,878,14],\"id\":140607,\"original_title\":\"Star Wars: The Force Awakens\",\"original_language\":\"en\",\"title\":\"星球大战7：原力觉醒\",\"backdrop_path\":\"\\/c2Ax8Rox5g6CneChwy1gmu4UbSb.jpg\",\"popularity\":15.095122,\"vote_count\":5130,\"video\":false,\"vote_average\":7.5},{\"poster_path\":\"\\/oXV2ayQYUQfHwpuMdWnZF0Geng5.jpg\",\"adult\":false,\"overview\":\"《幽冥》讲述了一个美国国防部高级研究计划局科学家接受一项致命任务，带领一队精挑细选的三角洲部队士兵进驻一个被战争摧毁的城市，这里游荡着被称为“幽冥”的神秘幽灵，它们无形无色，能在不经意间造成大规模的伤亡\",\"release_date\":\"2016-12-09\",\"genre_ids\":[53,28,878],\"id\":324670,\"original_title\":\"Spectral\",\"original_language\":\"en\",\"title\":\"幽冥\",\"backdrop_path\":\"\\/4Q6rr41mOqxor4yknHPgHs33AEF.jpg\",\"popularity\":14.61824,\"vote_count\":58,\"video\":false,\"vote_average\":6.7},{\"poster_path\":\"\\/AgzX7mmCrQcSozvqWGwSpFAsEXj.jpg\",\"adult\":false,\"overview\":\"讲述了在纽约一幢热闹的公寓大楼里，有一群宠物，每天主人出门后、回家前这里就变成了它们的乐园：有的和其他宠物一起出去玩；有的聚在一起交流主人的糗事；还有的在不停捯饬自己的外貌，使自己看上去更可爱以便从主人那里要来更多的零食……总之，宠物们每天的“朝九晚五”是他们一天中最自由、最惬意的时光。  　　在这群宠物中，有一只小猎犬是当仁不让的领袖，他叫麦克斯（Max），机智可爱，自认为是女主人生活的中心——直到她从外带回家一只懒散、没有家教的杂种狗“公爵”（Duke）。  　　麦克斯和公爵人生观价值观都不一样，自然很难和平共处。但当它们一起流落纽约街头后，两人又必须抛弃分歧、共同阻止一只被主人抛弃的宠物兔“雪球”（Snowball）——后者为了报复人类，准备组织一支遭弃宠物大军在晚饭前向人类发起总攻……\",\"release_date\":\"2016-06-18\",\"genre_ids\":[12,16,35,10751],\"id\":328111,\"original_title\":\"The Secret Life of Pets\",\"original_language\":\"en\",\"title\":\"爱宠大机密\",\"backdrop_path\":\"\\/lubzBMQLLmG88CLQ4F3TxZr2Q7N.jpg\",\"popularity\":14.234831,\"vote_count\":1293,\"video\":false,\"vote_average\":6},{\"poster_path\":\"\\/3FDBlVlOogbGuhjcQzviOLdRrUx.jpg\",\"adult\":false,\"overview\":\"凯特尼斯·伊夫狄恩，燃烧的女孩，虽然她的家被毁了，可她却活了下来。盖尔也逃了出来，凯特尼斯的家人也安全了，皮塔被凯匹特抓走了。十三区并不真的存在，出现了反抗，出现了新的领导者，一个革命的序幕正在缓缓拉开。  　　凯特尼斯从噩梦般的竞技场逃出来是已经设计好的，她是反抗运动的参与者，也是设计好的，而她对此并不知情。十三区从隐蔽处出来了，并计划推翻凯匹特的统治。似乎每个人都参与了这项精心策划的行动，而只有凯特尼斯并不知情。  　　反抗运动将凯特尼斯卷入了漩涡，她被迫成为棋子，她被迫为许多人的使命负责，不得不肩负起改变帕纳姆国的未来的负责。为了做到这一切，她必须抛却愤怒和不信任，她必须要成为反抗者的嘲笑鸟――不管要付出多大的代价。\",\"release_date\":\"2014-11-18\",\"genre_ids\":[878,12,53],\"id\":131631,\"original_title\":\"The Hunger Games: Mockingjay - Part 1\",\"original_language\":\"en\",\"title\":\"饥饿游戏3：嘲笑鸟（上）\",\"backdrop_path\":\"\\/83nHcz2KcnEpPXY50Ky2VldewJJ.jpg\",\"popularity\":14.186305,\"vote_count\":3494,\"video\":false,\"vote_average\":6.6},{\"poster_path\":\"\\/kPO21FHKWr8mXvzvJpTCewpBoYj.jpg\",\"adult\":false,\"overview\":\"彼得·奎尔（克里斯·帕拉特 Chris Pratt 饰）是一名从小被劫持到外太空的地球人，在义父勇度（迈克尔·鲁克 Michael Rooker 饰）的培养下成了一个终极混混，自称“星爵”。一次行动中他偷了一块神秘球体，便成为了赏金猎人火箭浣熊（布莱德利·库珀 B radl ey Cooper 配音）、树人格鲁特（范·迪塞尔 Vin Diesel 配音）的绑架目标，而神秘的卡魔拉（佐伊·索尔达娜 Zoe Saldana 饰）也对神秘球体势在必得。经过笑料百出的坎坷遭遇，星爵被迫和这三人，以及复仇心切的“毁灭者”德拉克斯（戴夫·巴蒂斯塔 Dave Batista 饰）组成小分队逃避“指控者”罗南（李·佩斯 Lee Pace 饰）的追杀。然而这个神秘球体拥有无穷的力量，小分队必须团结一致对付罗南，才有可能解救整个银河系，银河护卫队由此诞生。 ©豆瓣\",\"release_date\":\"2014-07-30\",\"genre_ids\":[28,878,12],\"id\":118340,\"original_title\":\"Guardians of the Galaxy\",\"original_language\":\"en\",\"title\":\"银河护卫队\",\"backdrop_path\":\"\\/bHarw8xrmQeqf3t8HpuMY7zoK4x.jpg\",\"popularity\":14.060025,\"vote_count\":5458,\"video\":false,\"vote_average\":7.9}],\"total_results\":19596,\"total_pages\":980}";



    public void testBulkInsert() throws JSONException {
        Vector<ContentValues> cVVector = JsonParser.StoreHotMovies(TEST_JSON_STRING);
        ContentValues[] cValues = new ContentValues[cVVector.size()];
        cVVector.toArray(cValues);
        getContext().getContentResolver().bulkInsert(MovieContract.MovieEntry.CONTENT_URI,cValues);
        assertFalse(false);
    }

    public void testQuery(){
        Cursor corAll = getContext().getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                MovieContract.MOVIE_PROJECTION,
                null,
                null,
                MovieContract.SORT_BY_VOTE
        );
        assertTrue(corAll.moveToFirst());
        assertEquals(20,corAll.getCount());
        corAll.close();

        Uri uri = MovieContract.MovieEntry.buildUriWithMovieId(movieId);
        Log.i("cheng",uri.toString());
        Cursor corSingle = getContext().getContentResolver().query(
                uri,
                MovieContract.MOVIE_PROJECTION,
                null,
                null,
                MovieContract.SORT_BY_VOTE
        );
        assertTrue(corSingle.moveToFirst());
        assertEquals(movieTitle,corSingle.getString(MovieContract.COL_TITLE));

    }

    public void testDelete(){
        Uri uri = MovieContract.MovieEntry.buildUriWithMovieId(movieId);
        int rowDeleted;
        rowDeleted = getContext().getContentResolver().delete(
               uri,
                null,
                null
        );
        assertEquals(rowDeleted,0);
    }

    public void testUpdate(){
        final long updateMovieId = 330459;
        final String updateTitle = "星战";

        int rowUpdated;

        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE,updateTitle);
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,"this is a test movie");

        Uri uri = MovieContract.MovieEntry.buildUriWithMovieId(updateMovieId);

        rowUpdated = getContext().getContentResolver().update(
                uri,
                contentValues,
                null,
                null
        );
        assertEquals(1,rowUpdated);
    }
}
