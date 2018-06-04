package plus.ojbk.mongo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import static org.springframework.data.mongodb.core.query.Criteria.where; 
import static org.springframework.data.mongodb.core.query.Query.query; 
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import plus.ojbk.mongo.entity.User;

/**
 * 
 * @author 王小明  
 * 			             示例代码 偷懒啦  
 */

@Service
public class UserService {
	
	@Autowired 
    private MongoTemplate mongoTemplate;
	
	/**
	 * 查询用户
	 */
	public User getUserByUserName(String username) {
		Query query = Query.query(Criteria.where("name").is(username));
		return this.mongoTemplate.findOne(query, User.class);
	}
	
	/**
	 * 创建用户
	 */
	public void createUser(User u) {
		User user = new User();
		user.setUserName(u.getUserName());
		user.setPassWord(DigestUtils.md5DigestAsHex(DigestUtils.md5DigestAsHex(u.getPassWord().getBytes()).getBytes()));
		user.setPhoneNumber(u.getPhoneNumber());
		user.setEmail(u.getEmail());
		user.setAddDate(new Date());
		user.setEnabled(u.getEnabled());;
		this.mongoTemplate.save(user);
	}
	
	/**
	 * 编辑 用户
	 */
	public long updateUser(User user) {
		Query query = new Query(Criteria.where("name").is(user.getUserName()));  //正常应该用 _id 来进行编辑用户这里偷懒了 
		Update update = new Update();
		if (user.getEmail() != null) {
			update.set("email", user.getEmail());
		}
		if (user.getPassWord() != null) {
			update.set("password", DigestUtils.md5DigestAsHex(DigestUtils.md5DigestAsHex(user.getPassWord().getBytes()).getBytes()));
		}
		if (user.getEnabled() != null) {
			update.set("phone", user.getPhoneNumber());
		}
		if (user.getEnabled() != null) {
			update.set("enabled", user.getEnabled());
		}
		update.set("modify_date", new Date());
		update.set("modify_user", "admin");
		return this.mongoTemplate.updateFirst(query, update, User.class).getN();  //getN()获取执行条数  
	}
	
	/**
	 * 删除用户
	 */
	public long deleteUserById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return this.mongoTemplate.remove(query, User.class).getN();
	}
	
	/**
	 * 查询全部用户
	 */
	public List<User> getAllUser() {
		Query query = new Query();
		return this.mongoTemplate.find(query, User.class);
	}
	
	/**
	 * 这里使用静态导包 
	 * import static org.springframework.data.mongodb.core.query.Criteria.where; 
	 * import static org.springframework.data.mongodb.core.query.Query.query; 
	 * 
	 * 一下示例 给出 查询   num大于 某一个值的 全部用户
	 *   gt  相当于       〉
	 *   gte 相当于       〉=
	 *   lt  相当于      〈
	 *   lte 相当于      〈 =
	 *   is  相当于       ==
	 *   ne  相当于       !=
	 *   in  相当于      sql中的in
	 *   nin 相当于       not in
	 *   orOperator 接受多个条件，组成or逻辑  
	 */
	public List<User> getUser(Integer num) {
		Criteria criteria = where("num").gt(num);
		Query query = query(criteria);
		// 上面两句可以简写 成下面这句   
 		//Query query = query(where("num").gt(num));
		return this.mongoTemplate.find(query, User.class);
	}
	
	/**
	 * 模糊查询    （通过邮箱模糊查询用户）  
	 *  这里是 示例仅仅是个小栗子qwq
	 */
	public List<User> getUserByEmailKey(String key){
		Query query = query(where("email").regex(".*"+key+".*"));
		return this.mongoTemplate.find(query, User.class);
	}
	
	/**
	 * 倒叙查询  加入 query.with(new Sort(Direction.DESC, "add_date"));
	 */
	public List<User> getSortUserByEmailKey(String key){
		Query query = query(where("email").regex(".*"+key+".*"));
		query.with(new Sort(Direction.DESC, "add_date"));
		return this.mongoTemplate.find(query, User.class);
	}
	
	/**
	 * 分页查询 加入       
	 * query.skip(0);    从第几条开始 【版本不同  有的版本是 表示第几页】
	 * query.limit(10);  每页显示的条数
	 *  
	 */
	public List<User> getSortUserByEmailKeys(String key){
		Query query = query(where("email").regex(".*"+key+".*"));
		query.skip(0);
		query.limit(10);
		query.with(new Sort(Direction.DESC, "add_date"));
		return this.mongoTemplate.find(query, User.class);
	}
}
