package whale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import whale.crawlers.FileLinkCrawler;
import whale.crawlers.HttpLinkCrawler;

import java.net.MalformedURLException;

@Configuration
@ComponentScan("whale.crawlers")
public class AppConfig {

    @Value("${default.filepath}")
    private String filepath;
    @Value("${default.url}")
    private String url;
    @Bean
    public FileLinkCrawler fileLinkCrawler() {
        return new FileLinkCrawler(filepath);
    }

    @Bean
    @Primary
    public HttpLinkCrawler httpLinkCrawler() {
        try {
            return new HttpLinkCrawler(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
