package whale.dto;

public record LinkDTO(Long id, String url, Boolean validUrl, String type, String metadata) {

    public static LinkDTO of(String url){
        return new LinkDTO(null, url, true, null, null);
    }

    public static LinkDTO of(String url, String type, Boolean isValid) {
        return new LinkDTO(null, url, isValid, type, null);
    }
}
