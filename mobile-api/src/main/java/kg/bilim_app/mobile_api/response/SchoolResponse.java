package kg.bilim_app.mobile_api.response;

/**
 * School data returned by the API.
 *
 * @param id      identifier
 * @param nameRu  name in Russian
 * @param nameKy  name in Kyrgyz
 * @param cityId  identifier of the city
 */
public record SchoolResponse(Long id, String nameRu, String nameKy, Long cityId) {
}
