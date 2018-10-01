## SpringBoot AOP 记录日志实例

通过AOP统一处理web请求日志，只需要添加spring-boot-starter-aop即可：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

### 1.先定义一个注解,用来定义在哪些方法上应用。
```java
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggerManager {

    String description();
}
```

### 2.我们定义一个Web Controller 来应用这些注解。
```java
@RestController
public class PersonController {

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);
    private static Map<Integer, Person> dataStore = Maps.newConcurrentMap();

    static {
        dataStore.put(1, new Person(1, "jack"));
        dataStore.put(2, new Person(2, "lily"));
    }

    @GetMapping("/")
    @LoggerManager(description = "Index Page")
    public String index(){
        return "Hello, welcome!, click <a href='list'>list </a> to list all data";
    }

    @GetMapping(value = "/list",produces = "application/json")
    @LoggerManager(description = "List all data ")
    public List<Person> list() throws InterruptedException {
        logger.info("sleep 2s to mock the actual list operation.");
        Thread.sleep(2000L);
        return Lists.newArrayList(dataStore.values());
    }

    @PostMapping(value = "/add", produces = "application/json")
    @LoggerManager(description = "add new person")
    public ResponseEntity addPerson(@RequestBody Person person){
        dataStore.put(person.getId(), person);
        return new ResponseEntity("Person saved successfully", HttpStatus.OK);
    }

    @GetMapping(value = "getById/{id}", produces = "application/json")
    @LoggerManager(description = "get person by id")
    public Person getById(@PathVariable Integer id){
        return dataStore.get(id);
    }
}
```

### 3.我们来定义日志切面的实现细节。
- 使用`@Aspect`注解将一个java类定义为切面类
- 使用`@Pointcut`定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。
- 使用`@Before`在切入点开始处切入内容
- 使用`@After`在切入点结尾处切入内容
- 使用`@AfterReturning`在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
- 使用`@Around`在切入点前后切入内容，并自己控制何时执行切入点自身的内容
- 使用`@AfterThrowing`用来处理当切入内容部分抛出异常之后的处理逻辑
- 使用`ThreadLocal`对象来记录请求处理的时间（直接在使用基本类型会有同步问题，所以我们可以引入ThreadLocal对象）

```java
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggerAdvice {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    //Should use the target class instead of the aspect class
    private Logger getAcrualLogger(Class targetClass){
        return LoggerFactory.getLogger(targetClass);
    }

    @Before("within(com.github.fangming.springboot..*) && @annotation(loggerManager)")
    public void addBeforeLogger(JoinPoint joinPoint, LoggerManager loggerManager){
        Class className = joinPoint.getTarget().getClass();
        Logger logger = getAcrualLogger(className);

        logger.info("execute [{}] start", loggerManager.description());
        logger.info("Method signature [{}].", joinPoint.getSignature().toLongString());
        logger.info("Input parameters [{}].", parseParames(joinPoint.getArgs()));
        startTime.set(System.currentTimeMillis());
    }

    @AfterReturning("within(com.github.fangming.springboot..*) && @annotation(loggerManager)")
    public void addAfterRetruningLogger(JoinPoint joinPoint, LoggerManager loggerManager){
        Class className = joinPoint.getTarget().getClass();
        Logger logger = getAcrualLogger(className);
        logger.info("execute [{}] end.", loggerManager.description());
    }

    //Take care about the @annotation parameter should the same to the method parameter
    @AfterThrowing(value = "within(com.github.fangming.springboot..*) && @annotation(loggerManager2)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, LoggerManager loggerManager2, Exception ex){
        Class className = joinPoint.getTarget().getClass();
        Logger logger = getAcrualLogger(className);
        logger.info("execute [{}] throw [{}] exception.", loggerManager2.description(), ex);
    }

    //if the method don't have LoggerManager type parameter, the annotation should be the class name
    @Around(value = "within(com.github.fangming.springboot..*) && @annotation(LoggerManager)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Class className = joinPoint.getTarget().getClass();
        Logger logger = getAcrualLogger(className);
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime.get();
        logger.info("[{}] executed in [{}] ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }

    private String parseParames(Object[] parames){
        if (null == parames || parames.length <= 0){
            return "";
        }
        StringBuffer param = new StringBuffer();
        Arrays.stream(parames).forEach((Object obj) -> param.append(ToStringBuilder.reflectionToString(obj)));
        return param.toString();
    }
}
```

Spring AOP支持在切入点表达式中使用如下的切入点指示符：

- execution - 匹配方法执行的连接点，这是你将会用到的Spring的最主要的切入点指示符。
- within - 限定匹配特定类型的连接点（在使用Spring AOP的时候，在匹配的类型中定义的方法的执行）。
- this - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中bean reference（Spring AOP 代理）是指定类型的实例。
- args - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中参数是指定类型的实例。
- @target - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中正执行对象的类持有指定类型的注解。
- @args - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中实际传入参数的运行时类型持有指定类型的注解。
- @within - 限定匹配特定的连接点，其中连接点所在类型已指定注解（在使用Spring AOP的时候，所执行的方法所在类型已指定注解）。
- @annotation - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中连接点的主题持有指定的注解。

参考资料：
[SpringBoot 使用AOP统一处理web请求日志](https://www.jianshu.com/p/15798e19bc12)
[Introduction to Spring AOP](https://www.baeldung.com/spring-aop)
[Implementing a Custom Spring AOP Annotation](https://www.baeldung.com/spring-aop-annotation)
[demo2_aop_logging](https://github.com/KotlinSpringBoot/demo2_aop_logging)