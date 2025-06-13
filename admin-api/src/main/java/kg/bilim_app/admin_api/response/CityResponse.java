package kg.bilim_app.admin_api.response;

/**
 * City data returned by the API.
 *
 * @param id       identifier
 * @param nameRu   Russian name
 * @param nameKy   Kyrgyz name
 * @param regionId identifier of the region
 */
public record CityResponse(Long id, String nameRu, String nameKy, Long regionId) {
}
