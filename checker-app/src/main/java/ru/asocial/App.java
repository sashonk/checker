package ru.asocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.asocial.repository")
@ComponentScan(basePackages = {"ru.asocial"})
public class App
{
    public static void main( String[] args )
    {
        System.out.println("============ ======== ============");
        System.out.println("====         STARTING         ====");
        System.out.println("============ ======== ============");
        System.out.println();

        SpringApplication.run(App.class, args);
        System.out.println("============ ======== ============");
        System.out.println("====         WORKING          ====");
        System.out.println("============ ======== ============");
        System.out.println();
    }
}
