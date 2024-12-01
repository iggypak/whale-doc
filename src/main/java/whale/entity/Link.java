package whale.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "link")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", unique = true)
    private String Url;
    @Column(name="valid_url")
    private Boolean validUrl;
    @Column(name = "type")
    private String type;
    @Column(name = "metadata")
    private String metadata;
}
