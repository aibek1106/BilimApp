package kg.bilim_app.mobile_api.response;

/**
 * City data returned by the API.
 *
 * @param id       identifier
 * @param nameRu   name in Russian
 * @param nameKy   name in Kyrgyz
 * @param regionId identifier of the region
 */
public record CityResponse(Long id, String nameRu, String nameKy, Long regionId) {
}
