import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.ls.LSOutput;

public class Main {
    static ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    static Student obj = (Student) context.getBean("Student");


    public static void main(String[] args) {
        System.out.println(obj);
    }
}
