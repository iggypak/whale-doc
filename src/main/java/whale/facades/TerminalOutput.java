package whale.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import whale.crawlers.LinkCrawler;
import whale.entity.Link;
import whale.services.LinkStorageService;

import java.util.Scanner;
//TODO add terminalInput
@ShellComponent
public class TerminalOutput implements Facade{
    static final Scanner SCANNER = new Scanner(System.in);

    private final LinkCrawler linkCrawler;
    private final ApplicationEventPublisher applicationEventPublisher;
    private boolean hasParams;
    private final LinkStorageService linkStorageService;

    @Autowired
    public TerminalOutput(
            LinkCrawler linkCrawler,
            ApplicationEventPublisher applicationEventPublisher,
            LinkStorageService linkStorageService
    ) {
        this.linkCrawler = linkCrawler;
        this.applicationEventPublisher = applicationEventPublisher;
        this.linkStorageService = linkStorageService;
    }

    @ShellMethod
    @Override
    public String availableActions(String param) {
        return TEXT_MENU;
    }

    @ShellMethod
    @Override
    public String actions(String param) {
        return links(param);
    }

    @ShellMethod
    public String scan(String path) {
        linkCrawler.setResourceAddress(new Link(path));
        return links(path);
    }

    @Override
    public String links(String param) {
        var links = linkCrawler.fetchLinks();
        linkStorageService.saveLinkBatch(links.stream().toList());
        return submit(null);
    }

    @ShellMethod
    @Override
    public String exit(String param) {
        try {
            return "Завершение работы";
        } finally {
            Runtime.getRuntime().exit(0);
        }
    }

    @ShellMethod
    @Override
    public String submit(String param) {
        return "Завершено!";
    }

    @Override
    public String infoMessage(String param) {
        return "";
    }

    @ShellMethod
    public boolean isParamsSet() {
        return hasParams;
    }

    public void setParam(boolean isSetted){
        this.hasParams = isSetted;
    }

}
