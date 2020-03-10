# 谷歌验证码![image](https://github.com/xx13295/wxm/blob/master/images/o.png?raw=true)


	我想大家肯定都见过这样的验证码挺烦的 还得疯狂选图片才能继续下一步。
	
![image](https://github.com/xx13295/MD-Note/blob/master/reCAPTCHA/img/yanzhengma-1.png?raw=true)
	
![image](https://github.com/xx13295/MD-Note/blob/master/reCAPTCHA/img/yanzhengma-2.png?raw=true)
	
	
	这是谷歌的reCAPTCHA v2 验证码
	现在已经有 V3 了 V3对我们来说是无感的优于V2
	


### 文档资料

	
	https://developers.google.com/recaptcha/docs/display

	由于防火墙的原因 谷歌被墙了
	
	因此下述的代码示例中使用的 是 https://www.recaptcha.net
	
	原版地址为  https://www.google.com 
	
	后缀均相同 
	

	
#### 申请验证码 

	首先要登录谷歌账户就不用说了
	
	https://www.google.com/recaptcha/admin/create 
	
![image](https://github.com/xx13295/MD-Note/blob/master/reCAPTCHA/img/yanzhengma-3.png?raw=true)

![image](https://github.com/xx13295/MD-Note/blob/master/reCAPTCHA/img/yanzhengma-4.png?raw=true)

	2个密钥，一个是在客户端（HTML）使用，一个是在服务端使用

#### 前端代码

例子1.

```

	<!DOCTYPE html>
	<html>
	<head>
	    <title>reCAPTCHA demo</title>
	    <meta charset="UTF-8">
	</head>
	<body>
	    <div id="google-reCaptcha"></div>
	    <button>验证后提交</button>
	</body>
	<script src="https://www.recaptcha.net/recaptcha/api.js?onload=onloadCallback&render=explicit" async defer> </script>
	<script type="text/javascript">
	    var verifyCallback = function(token) {
	        document.querySelector('button').addEventListener('click', () => {
	            console.log('客户端token:' + token);
	        fetch('/validate?token=' + token, {
	            method: 'GET'
	        }).then(response => {
	            if (response.ok){
	            response.json().then(message => {
	                console.log('服务端验证');
	            console.log(message);
	            alert(message);
	        });
	        }
	    });
	    });
	
	    };
	    var onloadCallback = function() {
	        grecaptcha.render('google-reCaptcha', {
	            'sitekey' : '6Ldctd****jU4******IGRs',
	            'callback' : verifyCallback,
	            'theme' : 'light'
	        });
	    };
	</script>
	</html>
	
```	
 例子2.

```

	<html>
		<head>
			<meta charset="UTF-8">
			<title>谷歌ReCaptcha</title>
	        <script src="https://www.recaptcha.net/recaptcha/api.js" defer></script>
		</head>
		<body>
	         <div class="g-recaptcha" data-sitekey="6Ldctd****jU4******IGRsh"></div>
	         <button style="width: 200px; height: 50px;">点击我完成验证</button>
	         <h3>验证结果</h3>
	         <div>
	            <pre id="response">
	
	            </pre>
	         </div>
		<script type="text/javascript">
	        window.onload = () => {
	            document.querySelector('button').addEventListener('click', () => {
	                // 获取验证码的token
	                const token = grecaptcha.getResponse();
	                if (!token){
	                    alert('请先点击，“进行人机身份验证”');
	                    return;
	                }
	                fetch('/validate?token=' + token, {
	                    method: 'GET'
	                }).then(response => {
	                    if (response.ok) {
	                        response.text().then(message => {
	                           // 请求成功，重置验证码
	                           grecaptcha.reset();
	                           document.querySelector('#response').innerHTML = message;
	                        });
	                    }else {
	                        alert('请求异常');
	                    }
	                })
	            });
	        }
		</script>
		</body>
	</html>

```
#### 服务端代码

```
	
	@Value("${google.recaptcha.validate-api:https://www.recaptcha.net/recaptcha/api/siteverify}")
	private String validateApi;
	@Value("${google.recaptcha.server-secret:6L**********tY**V****YG}")
	private String captchaServerSecret;
	
	@RequestMapping("/validate")
	public Object validate (HttpServletRequest request, @RequestParam("token")String token) {
		RestTemplate restTemplate= new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("secret", this.captchaServerSecret);
		requestBody.add("response", token);
		requestBody.add("remoteip", request.getRemoteAddr()); // 客户的ip地址，不是必须的参数。
		ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(this.validateApi, new HttpEntity<>(requestBody,httpHeaders), JSONObject.class);
		return responseEntity.getBody();
	}

```

