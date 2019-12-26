package woowacrew.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {
    }

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {
    }

    @Pointcut("execution(* *.*(..))")
    protected void allMethod() {
    }

    @Around("(restController() || controller()) && allMethod()")
    public Object doLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.debug("{}, Arguments : {}", proceedingJoinPoint.getSignature(), proceedingJoinPoint.getArgs());
        Object retVal = proceedingJoinPoint.proceed();
        log.debug("{}, Return Value : {}", proceedingJoinPoint.getSignature(), retVal);
        return retVal;
    }
}
