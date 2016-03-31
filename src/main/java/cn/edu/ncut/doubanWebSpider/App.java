package cn.edu.ncut.doubanWebSpider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Configuration
@Controller
@EnableAutoConfiguration
//@ComponentScan
public class App {
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }


    public static void main(String[] args) throws Exception {
        //		RedisUtil.init();
        SpringApplication.run(new String[]{"classpath*:ssm/spring-mvc.xml","classpath*:ssm/spring-mybatis.xml",}, args);
    }

}
