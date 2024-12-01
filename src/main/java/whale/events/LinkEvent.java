package whale.events;

import org.springframework.context.ApplicationEvent;

public class LinkEvent extends ApplicationEvent {
    private final String link;

    public LinkEvent(Object source, String link) {
        super(source);
        this.link = link;
    }

    public String getLink() {
        return link;
    }

}
