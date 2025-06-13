package kg.bilim_app.admin_api.response;

/**
 * School data returned by the API.
 *
 * @param id     identifier
 * @param nameRu Russian name
 * @param nameKy Kyrgyz name
 * @param cityId identifier of the city
 */
public record SchoolResponse(Long id, String nameRu, String nameKy, Long cityId) {
}
