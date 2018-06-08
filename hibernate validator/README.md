# hibernate validator


| 常见的注解 						| 作用    |
| --------  					| --------|
|@AssertFalse					|该字段的值只能为false|
|@AssertTrue					|该字段只能为true|
|@DecimalMax					|只能小于或等于该值|
|@DecimalMin					|只能大于或等于该值|
|@Digits(integer=,fraction=)	|检查是否是一种数字的整数、分数,小数位数的数字|
|@Future						|检查该字段的日期是否是属于将来的日期|
|@Max							|该字段的值只能小于或等于该值|
|@Min							|该字段的值只能大于或等于该值|
|@NotNull						|不能为null|
|@Null							|检查该字段为空|
|@NotBlank						|不能为空，检查时会将空格忽略|
|@NotEmpty						|不能为空，这里的空是指空字符串|
|@Past							|检查该字段的日期是在过去|
|@Pattern(regex=,flag=)			|被注释的元素必须符合指定的正则表达式|
|@Size(min=, max=)				|检查该字段的size是否在min和max之间，可以是字符串、数组、集合、Map等|
|@CreditCardNumber				|对信用卡号进行一个大致的验证|
|@Email							|检查是否是一个有效的email地址|
|@Length(min=,max=)				|检查所属的字段的长度是否在min和max之间,只能用于字符串|
|@Range(min=,max=,message=)		|被注释的元素必须在合适的范围内|
|@SafeHtml						|验证用户提供的富文本值，以确保其不包含恶意代码如xss   //就是垃圾 建议使用jsoup
|@URL(protocol=,host,port)		|检查是否是一个有效的URL|



----------------------------
	食用方法          
----------------------------


### 首先在maven中引入hibernate-validator



	<dependency>
	    <groupId>org.hibernate.validator</groupId>
	    <artifactId>hibernate-validator</artifactId>
	    <version>6.0.7.Final</version>
	</dependency>
	



### 全局异常处理返回	
### GlobalExceptionHandler.java
	
	import java.util.Set;
	
	import javax.validation.ConstraintViolation;
	import javax.validation.ConstraintViolationException;
	import javax.validation.ValidationException;
	
	import org.springframework.web.bind.annotation.ControllerAdvice;
	import org.springframework.web.bind.annotation.ExceptionHandler;
	import org.springframework.web.bind.annotation.ResponseBody;
	import org.springframework.web.bind.annotation.ResponseStatus;
	import org.springframework.http.HttpStatus;
	
	@ControllerAdvice
	public class GlobalExceptionHandler {
	
	    @ExceptionHandler
	    @ResponseBody
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public String handle(ValidationException exception) {
	        if(exception instanceof ConstraintViolationException){
	            ConstraintViolationException cve = (ConstraintViolationException) exception;
	            Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
	            violations.forEach(o -> System.out.println(o.getMessage()));
	        }
	        return "小伙子你不要乱来 我做验证了";
	    }
	}
	
	
###  ValidationUtils.java
	
	import java.util.Set;
	import javax.validation.ConstraintViolation;
	import javax.validation.Validation;
	import javax.validation.Validator;
	import org.hibernate.validator.HibernateValidator;

	public class ValidationUtils {
    
	    private static Validator validator = Validation
	            .byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();
	
	    /**
	     * 
	     * 实体类参数验证
	     *
	     */
	    public static <T> void validate(T obj) {
	        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
	        // 抛出检验异常
	        if (constraintViolations.size() > 0) {
	           System.err.println(constraintViolations.iterator().next().getMessage());
	        }
	    }
	}

###  User.java

	public class User implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    private String id; // 用户编号
    
    @Size(min = 6, max = 20,message = "用户名长度只能在 6-20之间")
    private String userName; // 用户名

    @Size(min = 6, max = 20)
    private String passWord; // 密码

    private String phone; // 电话号码
    private String email; // 邮箱
	
	//getter
	//setter
	}
	
###  TestController.java

### @Validated 注解可以校验RequestParam参数是否符合规则
### http://127.0.0.1:1024/test?age=9&email=ojbk
### 就会提示不是一个合法的邮箱地址 
### http://127.0.0.1:1024/user
### 就会提示用户名长度只能在 6-20之间

	@RestController
	@Validated
	public class TestController {
	    
	    @GetMapping(value = "/test")
	    public void demo(@Range(min = 1, max = 120, message = "年龄只能是1-120岁之间")
	                     @RequestParam(value = "age", required = true)
	                     int age,
	                     @Email
	                     @RequestParam(value = "email", required = true)
	                     String email) {
	  			
	        System.err.println(age + "-" + email);
	    }
	    
	    @GetMapping("/user")
		public void demo2(User user) {
			user = new User();
			user.setUserName("wxm");
			user.setEmail("i@ojbk.plus");
			user.setPassWord("1122345");
			user.setPhoneNumber("17666666666");
			ValidationUtils.validate(user);
		}
	}
	
