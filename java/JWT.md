## jwt 简易代码

	引入依赖
	
	 	<dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>3.7.0</version>
        </dependency>


#### 代码


```

	import com.auth0.jwt.algorithms.Algorithm;
	import com.auth0.jwt.exceptions.JWTCreationException;
	import com.auth0.jwt.exceptions.JWTVerificationException;
	import com.auth0.jwt.exceptions.TokenExpiredException;
	import com.auth0.jwt.interfaces.DecodedJWT;
	import com.auth0.jwt.interfaces.JWTVerifier;
	
	import java.util.Date;
	
	public final class Jwt {
	
	    private static final String[] jwtClaims = {"ojbk.plus"};
	
	    private static final String secret = "pI4642dg2b7VGbs6";
	
	
	    public static void main(String[] args) throws Exception {
	        Jwt obj = new Jwt();
	
	        String token = obj.getToken( "王小明");
	
	        // 打印token
	        System.out.println("token: " + token);
	        // 解密token
	        DecodedJWT jwt = obj.decoded(token);
	        System.out.println("issuer: " + jwt.getIssuer());
	        System.out.println("name: " + jwt.getClaim("name").asString());
	        System.out.println("过期时间：      " + jwt.getExpiresAt());
	
	    }
	
	
	    /**
	     * 生成加密后的token
	     * @param name
	     * @return 加密后的token
	     */
	    public String getToken(String name) {
	        String token = null;
	        try {
	            Date expiresAt = new Date(System.currentTimeMillis() + 30L * 24L * 3600L * 1000L);
	            token = com.auth0.jwt.JWT.create()
	                    .withIssuer(jwtClaims[0])
	                    .withClaim("name", name)
	                    .withExpiresAt(expiresAt)
	                    // 使用了HMAC256加密算法。
	                    // secret 是用来加密数字签名的密钥。
	                    .sign(Algorithm.HMAC256(secret));
	        } catch (JWTCreationException e){
	            //Invalid Signing configuration / Couldn't convert Claims.
	        } catch (IllegalArgumentException e) {
	            e.printStackTrace();
	        }
	        return token;
	    }
	
	    /**
	     * 先验证token是否被伪造，然后解码token。
	     * @param token 字符串token
	     * @return 解密后的DecodedJWT对象，可以读取token中的数据。
	     */
	    public DecodedJWT decoded(String token) {
	        DecodedJWT jwt = null;
	        try {
	            JWTVerifier verifier = com.auth0.jwt.JWT.require(Algorithm.HMAC256(secret))
	                    .withIssuer(jwtClaims[0])
	                    .build(); //Reusable verifier instance
	            jwt = verifier.verify(token);
	        } catch (JWTVerificationException e){
	            //Invalid signature/claims /TokenExpired
	            if(e instanceof TokenExpiredException){
	                System.err.println("说明该token已经过期");
	            }
	            e.printStackTrace();
	        }
	        return jwt;
	    }
	}

```