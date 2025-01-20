package whale.dto;

public record LinkDTO(String url, Boolean validUrl, String resource, String comment) {

    public static LinkDTO of(String url){
        return new LinkDTO(url, true, url, null);
    }

    public static LinkDTO of(String url, String resource) {
        return new LinkDTO(url, true, resource, null);
    }
}
