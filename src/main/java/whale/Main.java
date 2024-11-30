package whale;

import whale.crawlers.FileLinkCrawler;
import whale.crawlers.HttpLinkCrawler;
import whale.facades.Facade;
import whale.facades.Terminal;

import java.net.MalformedURLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws MalformedURLException {
        Facade facade = initializeFacade(args);
        if (facade == null) {
            facade = setupDefaultFacade();
        }

        facade.showMenu();

        while (true) {
            handleMenu(facade);
        }
    }

    private static Facade initializeFacade(String[] args) throws MalformedURLException {
        if (args.length == 0) {
            return null;
        }

        String mode = args[0];

        if (mode.contains("-f") && args.length > 1) {
            return new Terminal(new FileLinkCrawler(args[1]));
        }

        if (mode.contains("-i")) {
            return new Terminal(new HttpLinkCrawler(args[1]));
        }

        return null;
    }

    private static Facade setupDefaultFacade() {
        return new Terminal(
                new FileLinkCrawler(
                        new Scanner(System.in).nextLine()
                )
        );
    }

    private static void handleMenu(Facade facade) {
        try {
            facade.menu();
        } catch (IllegalArgumentException e) {
            facade.showError(e.getMessage());
            facade.showMenu();
        }
    }
}