package kg.bilim_app.admin_api.request;
/**
 * Request to create a new city.
 *
 * @param nameRu   name in Russian
 * @param nameKy   name in Kyrgyz
 * @param regionId identifier of the region
 */
public record NewCityRequest(String nameRu,
                             String nameKy,
                             Long regionId) {
}
