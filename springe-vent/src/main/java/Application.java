import com.luzhiqing.learn.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description:
 * @version:
 * @Author: 陆志庆
 * @CreateDate: 2019/9/27 18:31
 */
@SpringBootApplication
@ComponentScan("com.luzhiqing.learn")
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class,args);
        Publisher publisher = context.getBean(Publisher.class);
        publisher.publishEvent();
        System.out.println("ok");
    }
}
