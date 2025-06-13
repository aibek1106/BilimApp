package kg.bilim_app.admin_api.request;
/**
 * Request to create a new region.
 *
 * @param nameRu name in Russian
 * @param nameKy name in Kyrgyz
 */
public record NewRegionRequest(String nameRu,
                               String nameKy) {
}
