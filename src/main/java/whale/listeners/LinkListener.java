package whale.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import whale.events.LinkEvent;

@Component
public class LinkListener {
    @EventListener
    public void addLinkToScan(LinkEvent linkEvent) {
        System.out.println("Scanning link {}".formatted(linkEvent.getLink()));
    }
}
