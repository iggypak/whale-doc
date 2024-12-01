package whale.crawlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whale.dto.LinkDTO;
import whale.repositories.LinkRepository;

import java.util.List;

@Service
public class LinkService implements CRUDService<LinkDTO>{

    private LinkCrawler linkCrawler;
    private LinkRepository linkRepository;

    @Autowired
    public LinkService(LinkCrawler linkCrawler, LinkRepository linkRepository){
        this.linkCrawler = linkCrawler;
        this.linkRepository = linkRepository;
    }

    @Override
    public List<LinkDTO> getAllLinks(String source) {

        return List.of();
    }

    @Override
    public void saveAll(List<LinkDTO> links) {

    }

    @Override
    public void save(LinkDTO link) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public LinkDTO findByUrl(String url) {
        return null;
    }

    @Override
    public LinkDTO findByid(Long id) {
        return null;
    }
}
