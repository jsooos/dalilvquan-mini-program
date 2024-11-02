package com.jsoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动程序
 * 
 * @author jsoo
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableCaching
public class JsooApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(JsooApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  大荔绿泉小程序服务端启动成功   ლ(´ڡ`ლ)ﾞ ");
    }
}
