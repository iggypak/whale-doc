package whale.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import whale.crawlers.LinkCrawler;

@RestController
@RequestMapping("/api/crawler")
public class SpringBootRest implements Facade{

    private LinkCrawler linkCrawler;

    @Autowired
    public SpringBootRest(LinkCrawler linkCrawler){
        this.linkCrawler = linkCrawler;
    }

    @Override
    public String availableActions(String param) {
        return "";
    }

    @Override
    public String actions(String param) {
        return linkCrawler.fetchLinks().toString();
    }

    @Override
    public String links(String param) {
        return "";
    }

    @Override
    public String exit(String param) {
        return "";
    }

    @Override
    public String submit(String param) {
        return "";
    }

    @Override
    public String infoMessage(String param) {
        return "";
    }

    public boolean isParamsSet() {
        return false;
    }

    @GetMapping("/actions")
    public String getOutput(@RequestParam String param) {
        return actions(param);
    }
}
