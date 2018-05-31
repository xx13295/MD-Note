package plus.ojbk.mongo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
}
