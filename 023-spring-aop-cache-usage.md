### Simple caching with Spring AOP (AspectJ annotation)

This page and source code are roughly from the reference page, just take a node.

Caching hasn’t been never so easy to implement before. AspectJ is one of the best (probably the best) technologies that can be used for implementing caching system.

There are several ways how to do caching. Object can directly set values to cache, can implement some caching interface or can be fancy and use annotations that sounds to me as a most elegant solution. With annotation you can annotate any method that should be cached. Could be something easier?

Let’s create @SimpleDataCacheable annotation:
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleDataCacheable {
}
```
ith this annotation we can mark any expensive, time consuming methods whose results should be cached. 
For example let’s create `ComplicatedCalculationService` class that has one very expensive and time consuming method. Every time we call this method with same input it will be executed. It’s a huge wasting of resources. So we will use our `@SimpleDataCacheable` annotation on it’s function to prevent executing it when it’s not necessary.

```java
@Component
public class ComplicatedCalculationService {

    private static final Logger logger = LoggerFactory.getLogger(ComplicatedCalculationService.class);

    @SimpleDataCacheable
    public CalculationResult calculate(int a, int b) {
        logger.info("Calculating " + a + " + " + b);
        try {
            // pretend this is an expensive and complicated operation
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.error("Something went wrong...", e);
        }
        return new CalculationResult(a, b, a + b);
    }
}
```

Next we need to create an aspect called `SimpleCacheAdvice` that will handle caching. 
A function that wraps calling of cacheable methods. 
This function will be actually called every time empty function cache() is invoked. 
Advise aroundCachedMethods will take a look into cache for a previously cached result and return it. 
If it couldn’t find anything then it will call our time consuming function and store result for next reuse.

```java
@Aspect
@Component
public class SimpleCacheAdvice {

    private Logger logger = LoggerFactory.getLogger(SimpleCacheAdvice.class);
    private Map<String, Object> cache = Maps.newConcurrentMap();

    @Around(value = "within(com.github.fangming.springboot..*) && @annotation(SimpleDataCacheable)")
    public Object aroundCachedMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        logger.debug("Execution of Cacheable method catched");

        // generate the key under which cached value is stored
        // will look like caching.aspectj.Calculator.sum(Integer=1;Integer=2;)
        StringBuilder keyBuff = new StringBuilder();

        // append name of the class
        keyBuff.append(proceedingJoinPoint.getTarget().getClass().getName());

        // append name of the method
        keyBuff.append(".").append(proceedingJoinPoint.getSignature().getName());

        keyBuff.append("(");
        // loop through cacheable method arguments
        for (final Object arg : proceedingJoinPoint.getArgs()) {
            // append argument type and value
            keyBuff.append(arg.getClass().getSimpleName() + "=" + arg + ";");
        }
        keyBuff.append(")");
        String key = keyBuff.toString();

        logger.debug("Key = " + key);
        Object result = cache.get(key);
        if (result == null) {
            logger.debug("Result not yet cached. Must be calculated...");
            result = proceedingJoinPoint.proceed();
            logger.info("Storing calculated value '" + result + "' to cache");
            cache.put(key, result);
        } else {
            logger.debug("Result '" + result + "' was found in cache");
        }

        return result;
    }
}
```

Reference: 
> [Simple caching with AspectJ](https://urmincek.wordpress.com/2009/09/06/simple-caching-with-aspectj/)

> [Simple caching with Spring AOP](https://urmincek.wordpress.com/2009/09/18/simple-caching-with-spring-aop/)

> [github demo-caching-with-spring-aop](https://github.com/igo/demo-caching-with-spring-aop)
