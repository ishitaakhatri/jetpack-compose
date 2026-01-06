package my.first.widget

data class BannerConfig(
    val title: String,
    val subtitle: String
)

object BannerDataGenerator {

    fun getBanner(instanceId: String): List<BannerConfig> {
        return when (instanceId) {
            "pokemon" -> listOf(
                BannerConfig("Pokemon", "Catch them all"),
                BannerConfig("Pikachu", "Electric power"),
                BannerConfig("sai duck", "Favourite pokemon")
            )
            "cars" -> listOf(
                BannerConfig("Cars", "Fast & Furious"),
                BannerConfig("Porshe 911" , "legend"),
                BannerConfig("BMW 3.0 CSL" , "Batmobile")
            )
            "bikes" -> listOf(
                BannerConfig("Bikes", "Ride Free"),
                BannerConfig("Harley Davidson" , "in hawaii")
            )
            else -> emptyList()
        }
    }
}
