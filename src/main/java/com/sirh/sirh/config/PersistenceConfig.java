/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.config;

/**
 *
 * @author jarellano22
 */
import static cn.hutool.core.date.DateTime.now;
import com.sirh.sirh.entity.AdminLog;
import com.sirh.sirh.entity.Usuario;
import com.sirh.sirh.service.UsuarioService;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class PersistenceConfig {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    addLogService AddLogService;

    private static Logger logger = LoggerFactory.getLogger(PersistenceConfig.class);

    @Pointcut("@annotation(com.sirh.sirh.config.SystemControllerLog)")
    public void logPointCut() {
    }

    @Around(value = "logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        AdminLog adminLog = new AdminLog();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SystemControllerLog systemControllerLog = method.getAnnotation(SystemControllerLog.class);
        if (systemControllerLog != null) {

            String operation = systemControllerLog.operation();
            String type = systemControllerLog.type();
            adminLog.setType(type);//1 TYPE
            adminLog.setOperation(operation);//2 OPERATION

        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestUri = request.getRequestURI();
        String requestMethod = request.getMethod();
        String remoteAddr = this.getIpAddress(request);

        adminLog.setRemote_addr(remoteAddr); //3 REMOTEADDR
        logger.info("La IP del cliente es:" + remoteAddr);
        adminLog.setRequest_uri(requestUri); //4 REQUIES URI
        logger.info("La ruta de la solicitud es:" + requestUri);
        adminLog.setMethod(requestMethod);  // 5 METHOD
        logger.info("El método de solicitud es:" + requestMethod);

        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (Object obj : args) {
                /* System.out.println ("Parámetros pasados" + obj); */
                String params = obj.toString();
                /* System.out.println ("Parámetros pasados" + params); */
                logger.info("Los parámetros de la solicitud son:" + params);
                /* Guardar parámetros de solicitud */
                adminLog.setParams(params); // 6 PARAMS
            }
        }
        Date dtf = now();
        adminLog.setCreatedAt(dtf);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Username = authentication.getName();
        Usuario usuario = usuarioService.findByUsuarioSession(Username);
        Integer userId = usuario.getId();
        String userName = authentication.getName();

        if (authentication.getName() != null) {
            adminLog.setUser_id(userId);     // 8 USERID
            adminLog.setUser_name(userName); // 9 USERNAME
        }

        Object proceed = null;

        try {
            proceed = joinPoint.proceed();
            if (method.isAnnotationPresent(SystemControllerLog.class)) {
                adminLog.setResult_params(proceed.toString());//10 RESULTPARAMS                         
                adminLog.setException_log("Sin anormalidad"); // EXCEPTIONLOG
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            adminLog.setException_log(throwable.getMessage());
            adminLog.setType("Err");
            adminLog.setResult_params(proceed.toString());
        } finally {
            AddLogService.save(adminLog);
        }

        logger.info("El parámetro de retorno es" + proceed);
        return proceed;
    }

    public String getIpAddress(HttpServletRequest request) {

        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                // Según la tarjeta de red para tomar la IP configurada por la máquina
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // Para el caso de varios agentes, la primera IP es la IP real del cliente, y varias IP se dividen según ','
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

}
