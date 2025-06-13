package kg.bilim_app.admin_api.response;

/**
 * Region data returned by the API.
 *
 * @param id     identifier
 * @param nameRu Russian name
 * @param nameKy Kyrgyz name
 */
public record RegionResponse(Long id, String nameRu, String nameKy) {
}
