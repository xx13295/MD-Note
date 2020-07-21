# repository.save 无效

	在spring的 @Scheduled定时任务，或者在线程池中使用 jpa 的 repository.save 无效 一般情况就是没有配置对应的事务管理器。
	
	
### 1.  配置

	@Configuration
	@EnableTransactionManagement
	public class JpaConfiguration {
	
	  /*
	    @Bean
	    public JPAQueryFactory jpaQuery(EntityManager entityManager) {
	        return new JPAQueryFactory(entityManager);
	    }
	   */
	    
	    @Autowired
	    private EntityManagerFactory entityManagerFactory;
	
	    @Bean(name = "transactionManagerJpa")
	    public PlatformTransactionManager transactionManagerJpa() {
	        return new JpaTransactionManager(entityManagerFactory);
	    }
	}

### 2. 扫描 repository 增加 transactionManagerRef

	@EnableJpaRepositories(basePackages = "com.xxx.server.repository.*", transactionManagerRef = "transactionManagerJpa")
	
### 3. 在对应的save方法上 增加 Transactional 注解

	@Transactional(value = "transactionManagerJpa")