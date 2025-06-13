package kg.bilim_app.mobile_api.response;

/**
 * Region data returned by the API.
 *
 * @param id      identifier
 * @param nameRu  name in Russian
 * @param nameKy  name in Kyrgyz
 */
public record RegionResponse(Long id, String nameRu, String nameKy) {
}
