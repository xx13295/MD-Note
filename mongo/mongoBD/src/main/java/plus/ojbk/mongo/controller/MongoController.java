package plus.ojbk.mongo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import plus.ojbk.mongo.entity.User;
import plus.ojbk.mongo.service.UserService;

@RestController
public class MongoController {

	@Autowired
	private UserService userService;

	@GetMapping("/newuser")
	public Object a() {
		User user = new User();
		user.setUserName("wxm");
		user.setEmail("i@ojbk.plus");
		user.setPassWord("12346");
		user.setPhoneNumber("17666666666");
		userService.createUser(user);
		return "ojbk";
	}
	
	@GetMapping("/getuser")
	public Object b() {
		List<User> user = userService.getAllUser();
		String str = JSON.toJSONString(user);  
		return str;
	}
	
	@GetMapping("/getwxm")
	public Object c() {
		User user = userService.getUserByUserName("wxm");
		String json =JSON.toJSONString(user);  
		return json;
	}
	
	@GetMapping("/updatewxm")
	public Object d() {
		User user = new User();
		user.setUserName("wxm"); 
		user.setPassWord("6666");
		user.setPhoneNumber("18000000000");
		long u = userService.updateUser(user);
		return u;
	}
	
	@GetMapping("/delwxm")
	public Object e() {
		User user = userService.getUserByUserName("wxm");
		long num = userService.deleteUserById(user.getId());
		return num;
	}
}
