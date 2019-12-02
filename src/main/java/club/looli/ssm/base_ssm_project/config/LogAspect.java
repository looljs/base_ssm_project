package club.looli.ssm.base_ssm_project.config;

import club.looli.ssm.base_ssm_project.entity.Log;
import club.looli.ssm.base_ssm_project.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @ Aspect :表面这是一个切面类
 */
@Component
@Aspect
public class LogAspect {

    @Autowired
    private LogService logService;

    /**
     * @ Pointcut :切入点
     */
    @Pointcut(
            "execution(* " +//方法返回值：所有
                    "club.looli.ssm.base_ssm_project.controller.*.logout" +//哪个包下的哪个类中的哪个方法
                    "(..))"//方法参数：任意
    )
    public void pc1(){

    }

    @Pointcut(
            "execution(* " +//方法返回值：所有
                    "club.looli.ssm.base_ssm_project.controller.*.loginA*" +//哪个包下的哪个类中的哪个方法
                    "(..))"//方法参数：任意
    )
    public void pc2(){

    }

    @Pointcut(
            "execution(* " +//方法返回值：所有
                    "club.looli.ssm.base_ssm_project.controller.*.editPasswordAct" +//哪个包下的哪个类中的哪个方法
                    "(..))"//方法参数：任意
    )
    public void pc3(){

    }

    /**
     * 通知
     *      前置通知    @Before(value = "pc1()")
     *      返回通知    @AfterReturning(value = "pc1(),returning="result")当方法返回值是result时执行
     *      后置通知    @After(value = "pc1()")
     *      异常通知    @AfterThrowing(value = "pc1(),throwing="e") 当遇到e异常的时候执行
     *      环绕通知    @Around
     * @param joinPoint
     */
    @Before(value = "pc1()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session=attr.getRequest().getSession(true);
        String username = (String) session.getAttribute("username");
        Log log = new Log();
        log.setContent("用户"+username+"登出");
        log.setCreateTime(new Date());
        logService.add(log);
    }

    @After(value = "pc2()")
    public void after(JoinPoint joinPoint){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session=attr.getRequest().getSession(true);
        String username = (String) session.getAttribute("username");
        if (username != null){
            Log log = new Log();
            log.setContent("用户"+username+"登录");
            log.setCreateTime(new Date());
            logService.add(log);
        }
    }

    @After(value = "pc3()")
    public void editPassword(JoinPoint joinPoint){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session=attr.getRequest().getSession(true);
        String username = (String) session.getAttribute("username");
        if (username != null){
            Log log = new Log();
            log.setContent("用户"+username+"修改密码");
            log.setCreateTime(new Date());
            logService.add(log);
        }
    }
}