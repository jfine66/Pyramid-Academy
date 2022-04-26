import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MainConfig {
    @Bean
    public List<Phone> getPhone(){
        List<Phone> numbers = new ArrayList<>();
        numbers.add(new Phone("18005557894"));
        numbers.add(new Phone("18006667894"));
        numbers.add(new Phone("18007777894"));
        return numbers;
    }

    @Bean
    public Address getAdd(){
        return new Address("Vinita", "OK", "USA", "74301");
    }

    @Bean
    public Student obj(){
        return new Student(11,"Tim", getPhone(), getAdd());
    }

}
