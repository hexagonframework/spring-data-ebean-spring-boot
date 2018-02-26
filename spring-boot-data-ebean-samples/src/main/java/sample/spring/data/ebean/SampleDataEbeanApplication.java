package sample.spring.data.ebean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sample.spring.data.ebean.domain.User;
import sample.spring.data.ebean.domain.UserRepository;

/**
 * @author Xuegui Yuan
 */
@SpringBootApplication
public class SampleDataEbeanApplication implements CommandLineRunner {

  @Autowired
  UserRepository userRepository;

  public static void main(String[] args) {
    SpringApplication.run(SampleDataEbeanApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    User user = new User("Xuegui", "Yuan", "yuanxuegui@163.com");
    user.setAge(29);
    user = userRepository.save(user);
    System.out.println(user);
  }
}
