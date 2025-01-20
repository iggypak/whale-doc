package whale.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;

@Data
@RedisHash("Link")
@AllArgsConstructor
@NoArgsConstructor
public class Link implements Serializable {
    @Id
    private String url;
    private boolean validUrl;
    private String resource;
    private String comment;

    public Link(String url){
        this.url = url;
    }

    public static Link of(String link, String resource) {
        var currentLink = new Link();
        currentLink.setUrl(link);
        currentLink.setResource(resource);
        return currentLink;
    }
}
