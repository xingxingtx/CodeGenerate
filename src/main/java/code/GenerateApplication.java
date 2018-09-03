package code;

import code.config.ConfigurationValue;
import code.gen.MyGenerator;
import code.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenerateApplication {

	public static void main(String[] args) throws Exception{

		SpringApplication.run(GenerateApplication.class, args);
	}

}
