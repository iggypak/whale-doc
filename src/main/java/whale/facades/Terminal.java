package whale.facades;

import whale.Command;
import whale.crawlers.LinkCrawler;

public class Terminal implements Facade{

    private final LinkCrawler linkCrawler;
    private boolean hasParams;

    public Terminal(LinkCrawler linkCrawler) {
        this.linkCrawler = linkCrawler;
    }

    @Override
    public void showMenu() {
        System.out.println(TEXT_MENU);
    }

    @Override
    public void menu() {
        String result = scanner.nextLine();
        Command command = result.matches(INT_PATTERN) ? Command
                .getCommandByCode(
                        Integer.parseInt(result)
                ) : Command.valueOf(result.toUpperCase());
        switch (command) {
            case EXIT -> {
                exit();
            }
            case START -> {
                System.out.println(NEXT_STEP);
                postChoosing();
            }
            default -> {
                throw new IllegalArgumentException("Некорректное значение");
            }
        }
    }

    @Override
    public void postChoosing() {
        linkCrawler.getLinks().forEach(System.out::println);
        printSuccess();
    }

    @Override
    public void exit() {
        Runtime.getRuntime().exit(0);
    }

    @Override
    public void printSuccess() {
        System.out.println("Завершено!");
    }

    @Override
    public void showError(String errorMsg) {
        System.err.println(errorMsg);
    }

    @Override
    public boolean isParamsSet() {
        return hasParams;
    }

    public void setParam(boolean isSetted){
        this.hasParams = isSetted;
    }

}
