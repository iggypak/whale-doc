package whale;

import whale.crawlers.FileLinkCrawler;
import whale.crawlers.HttpLinkCrawler;
import whale.facades.Facade;
import whale.facades.Terminal;

import java.net.MalformedURLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    public static void main(String[] args) throws MalformedURLException {
        Facade facade = null;
        if(args.length != 0){
            if (args[0].contains("-") && args[0].contains("f")){
                facade = new Terminal(new FileLinkCrawler(args[1]));
                facade.postChoosing();
                facade.exit();
            } else if (args[0].contains("-") && args[0].contains("i")) {
                facade = new Terminal(new HttpLinkCrawler("https://pokeapi.co"));
                facade.postChoosing();
                facade.exit();
            }
        }
        facade = new Terminal(
                new FileLinkCrawler(
                        new Scanner(System.in).nextLine()
                )
        );
        facade.showMenu();
        while (true){
            try {
                facade.menu();
            } catch (IllegalArgumentException e) {
                facade.showError(e.getMessage());
                facade.showMenu();
            }
        }

    }

}