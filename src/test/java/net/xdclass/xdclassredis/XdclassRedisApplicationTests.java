package net.xdclass.xdclassredis;

import net.xdclass.xdclassredis.model.UserDo;
import net.xdclass.xdclassredis.model.UserPoint;
import net.xdclass.xdclassredis.model.VideoDO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.Set;

@SpringBootTest
class XdclassRedisApplicationTests {


	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	void testStringSet() {
		redisTemplate.opsForValue().set("name","112");

	}

	@Test
	void testStringGet() {
		String name = (String) redisTemplate.opsForValue().get("name");

		String name2 = stringRedisTemplate.opsForValue().get("name");

		System.out.println(name);
		System.out.println(name2);
	}

	@Test
	void testObjSet() {
		UserDo userDo = new UserDo();
		userDo.setName("小明");
		userDo.setAge("18");
		userDo.setSex("男");
		redisTemplate.opsForValue().set("userDo",userDo);


	}

	@Test
	public void saveRank(){
		String DAILY_RANK_KEY="video:rank:daily";

		VideoDO video1 = new VideoDO(3,"PaaS工业级微服务大课","xdclass.net",1099);
		VideoDO video2 = new VideoDO(5,"AlibabaCloud全家桶实战","xdclass.net",59);
		VideoDO video3 = new VideoDO(53,"SpringBoot2.X+Vue3综合实战","xdclass.net",49);
		VideoDO video4 = new VideoDO(15,"玩转23种设计模式+最近实战","xdclass.net",49);
		VideoDO video5 = new VideoDO(45,"Nginx网关+LVS+KeepAlive","xdclass.net",89);
		redisTemplate.opsForList().leftPushAll(DAILY_RANK_KEY,video1,video2,video3,video4,video5);
	}

	/**
	 * 替换榜单第二名
	 */
	@Test
	void replaceRank(){
		String DAILY_RANK_KEY="video:rank:daily";
		VideoDO video = new VideoDO(42,"小滴课堂面试专题第一季高级工程师","xdclass.net",89);
		//在集合的指定位置插入元素,如果指定位置已有元素，则覆盖，没有则新增
		redisTemplate.opsForList().set(DAILY_RANK_KEY

				,1,video);
	}


	//用户画像去重
	@Test
	void userProfile(){
		BoundSetOperations operations = redisTemplate.boundSetOps("user:tags:1");
		operations.add("car","student","rish","dog","rish");

		Set<String> set1 = operations.members();
		System.out.println(set1);


		operations.remove("dog");
		//拿到里面全部成员


		Set<String> set2 = operations.members();
		System.out.println(set2);

	}


	@Test
	public void testSet(){

		BoundSetOperations operationLW  = redisTemplate.boundSetOps("user:lw");

		operationLW.add("A","B","C","D","E");
		System.out.println("老王的粉丝:"+operationLW.members());
		BoundSetOperations operationXD  = redisTemplate.boundSetOps("user:xd");

		operationXD.add("A","B","F","G","H","J");
		System.out.println("小D的粉丝:"+operationXD.members());

		//差集
		Set lwSet = operationLW.diff("user:xd");
		System.out.println("老王的优势:"+lwSet);

		//差集
		Set xdSet = operationXD.diff("user:lw");
		System.out.println("小滴的优势:"+xdSet);

		//交集
		Set interSet =  operationLW.intersect("user:xd");
		System.out.println("共同好友:"+interSet);

		//并集
		Set unionSet = operationLW.union("user:xd");
		System.out.println("两个人的并集:"+unionSet);

		//用户A是否是 老王 的粉丝
		boolean flag = operationLW.isMember("A");
		System.out.println(flag);
	}


	@Test
	void testData() {

		UserPoint p1 = new UserPoint("老王","13113");
		UserPoint p2 = new UserPoint("老A","324");
		UserPoint p3 = new UserPoint("老B","242");
		UserPoint p4 = new UserPoint("老C","542345");
		UserPoint p5 = new UserPoint("老D","235");
		UserPoint p6 = new UserPoint("老E","1245");
		UserPoint p7 = new UserPoint("老F","2356432");
		UserPoint p8 = new UserPoint("老G","532332");

		BoundZSetOperations<String, UserPoint> operations = redisTemplate.boundZSetOps("point:rank:real");


		operations.add(p1,888);



	}
}


