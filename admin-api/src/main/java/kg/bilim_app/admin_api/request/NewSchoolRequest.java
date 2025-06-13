package kg.bilim_app.admin_api.request;
/**
 * Request to create a new school.
 *
 * @param nameRu name in Russian
 * @param nameKy name in Kyrgyz
 * @param cityId identifier of the city
 */
public record NewSchoolRequest(String nameRu,
                               String nameKy,
                               Long cityId) {
}
