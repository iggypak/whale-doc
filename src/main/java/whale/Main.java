package whale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.net.MalformedURLException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws MalformedURLException {
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        /*Facade facade = initializeFacade(args);
        if (facade == null) {
            facade = setupDefaultFacade();
        }

        facade.showMenu();

        while (true) {
            handleMenu(facade);
        }*/
    }


}