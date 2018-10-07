### Simple retry with Spring AOP (AspectJ annotation)

LinkedIn
One of my blog follower sends an email asking me to show an example of “RealWorld Usage of Spring AOP”. He mentioned that in most of the examples the usage of Spring AOP is demonstrated for logging method entry/exit or Transaction management or Security checks. He wanted to know how Spring AOP is being used in “Real Project for Real Problems”.

So I would like to show how I have used Spring AOP for one of my project to handle a real problem.

We won’t face some kind of problems in development phases and only come to know during Load Testing or in production environments only.
For example:

- Remote WebService invocation failures due to network latency issues
- Database query failures because of Lock exceptions etc

In most of the cases just retrying the same operation is sufficient to solve these kind of failures.
Let us see how we can use Spring AOP to automatically retry the method execution if any exception occurs.

We can use Spring AOP @Around advice to create a proxy for those objects whose methods needs to be retried and implement the retry logic in Aspect.

Before jumping on to implementing these Spring Advice and Aspect, first let us write a simple utility to execute a “Task” which automatically retry for N times ignoring the given set of Exceptions.

```java
public interface Task<T> {
  T execute();
}
```
```java
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class TaskExecutionUtil 
{
   
  private static Logger logger = LoggerFactory.getLogger(TaskExecutionUtil.class);
 
  @SafeVarargs
  public static <T> T execute(Task<T> task, 
                int noOfRetryAttempts, 
                long sleepInterval, 
                Class<? extends Throwable>... ignoreExceptions) 
  {
     
    if (noOfRetryAttempts < 1) {
      noOfRetryAttempts = 1;
    }
    Set<Class<? extends Throwable>> ignoreExceptionsSet = new HashSet<Class<? extends Throwable>>();
    if (ignoreExceptions != null && ignoreExceptions.length > 0) {
      for (Class<? extends Throwable> ignoreException : ignoreExceptions) {
        ignoreExceptionsSet.add(ignoreException);
      }
    }
     
    logger.debug("noOfRetryAttempts = "+noOfRetryAttempts);
    logger.debug("ignoreExceptionsSet = "+ignoreExceptionsSet);
     
    T result = null;
    for (int retryCount = 1; retryCount <= noOfRetryAttempts; retryCount++) {
      logger.debug("Executing the task. Attemp#"+retryCount);
      try {
        result = task.execute();
        break;
      } catch (RuntimeException t) {
        Throwable e = t.getCause();
        logger.error(" Caught Exception class"+e.getClass());
        for (Class<? extends Throwable> ignoreExceptionClazz : ignoreExceptionsSet) {
          logger.error(" Comparing with Ignorable Exception : "+ignoreExceptionClazz.getName());
           
          if (!ignoreExceptionClazz.isAssignableFrom(e.getClass())) {
            logger.error("Encountered exception which is not ignorable: "+e.getClass());
            logger.error("Throwing exception to the caller");
             
            throw t;
          }
        }
        logger.error("Failed at Retry attempt :" + retryCount + " of : " + noOfRetryAttempts);
        if (retryCount >= noOfRetryAttempts) {
          logger.error("Maximum retrial attempts exceeded.");
          logger.error("Throwing exception to the caller");
          throw t;
        }
        try {
          Thread.sleep(sleepInterval);
        } catch (InterruptedException e1) {
          //Intentionally left blank
        }
      }
    }
    return result;
  }
 
}
```
I hope this method is self explanatory. It is taking a Task and retries noOfRetryAttempts times in case method task.execute() throws any Exception and ignoreExceptions indicates what type of exceptions to be ignored while retrying.

Now let us create a Retry annotation as follows:

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public  @interface Retry {
   
  public int retryAttempts() default 3;
   
  public long sleepInterval() default 1000L; //milliseconds
   
  Class<? extends Throwable>[] ignoreExceptions() default { RuntimeException.class };
   
}
```
We will use this @Retry annotation to demarcate which methods needs to be retried.

Now let us implement the Aspect which applies to the method with @Retry annotation.
```java
import java.lang.reflect.Method;
 
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
 
@Component
@Aspect
public class MethodRetryHandlerAspect {
   
  private static Logger logger = LoggerFactory.getLogger(MethodRetryHandlerAspect.class);
   
  @Around("@annotation(com.sivalabs.springretrydemo.Retry)")
  public Object audit(ProceedingJoinPoint pjp) 
  {
    Object result = null;
    result = retryableExecute(pjp);
      return result;
  }
   
  protected Object retryableExecute(final ProceedingJoinPoint pjp)
  {
    MethodSignature signature = (MethodSignature) pjp.getSignature();
    Method method = signature.getMethod();
    logger.debug("-----Retry Aspect---------");
    logger.debug("Method: "+signature.toString());
 
    Retry retry = method.getDeclaredAnnotation(Retry.class);
    int retryAttempts = retry.retryAttempts();
    long sleepInterval = retry.sleepInterval();
    Class<? extends Throwable>[] ignoreExceptions = retry.ignoreExceptions();
     
    Task<Object> task = new Task<Object>() {
      @Override
      public Object execute() {
        try {
          return pjp.proceed();
        } catch (Throwable e) {
          throw new RuntimeException(e);
        }
      }
    };
    return TaskExecutionUtil.execute(task, retryAttempts, sleepInterval, ignoreExceptions);
  }
}
```


Reference: 
- [java retry(重试) spring retry, guava retrying 详解](https://juejin.im/post/5b6ac0a06fb9a04f8a21b192)
- [Java实现几种简单的重试机制](https://my.oschina.net/u/566591/blog/1526551)
- [Retrying Method Execution using Spring AOP](https://sivalabs.in/2016/01/retrying-method-execution-using-spring-aop/)