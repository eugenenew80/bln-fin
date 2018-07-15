package bln.fin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;
import javax.xml.datatype.DatatypeConfigurationException;

@EntityScan(
    basePackageClasses = { App.class, Jsr310JpaConverters.class }
)
@EnableScheduling
@SpringBootApplication
public class App  {
    public static void main(String[] args) throws DatatypeConfigurationException {
        SpringApplication.run(App.class, args);
    }
}
