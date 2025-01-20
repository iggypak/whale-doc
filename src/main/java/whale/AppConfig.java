package whale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import whale.crawlers.FileLinkCrawler;
import whale.crawlers.HttpLinkCrawler;
import whale.services.CompositeStorageService;
import whale.services.LinkStorageService;
import whale.services.RedisStorageService;

import java.net.MalformedURLException;

@Configuration
@ComponentScan("whale.crawlers")
public class AppConfig {

    @Value("${default.filepath}")
    private String filepath;
    @Value("${default.url}")
    private String url;
    @Value("${default.redis.url}")
    private String redisUrl;
    @Value("${default.redis.port}")
    private int redisPort;

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

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory
                = new JedisConnectionFactory();
        jedisConFactory.setHostName(redisUrl);
        jedisConFactory.setPort(redisPort);
        return jedisConFactory;
    }

    @Bean
    @Primary
    public LinkStorageService linkStorageService() {
        return new CompositeStorageService();
    }

}
