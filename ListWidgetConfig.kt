package my.first.widget

data class ListWidgetConfig(
    val name: String,
    val surname: String
)

object ListDataGenerator {

    fun getListData(instanceId: String): List<ListWidgetConfig> {
        return when (instanceId) {
            "movies" -> listOf(
                ListWidgetConfig("Iron", "Man"),
                ListWidgetConfig("Batman", "Returns"),
                ListWidgetConfig("World" , "War Z")
            )
            "shows" -> listOf(
                ListWidgetConfig("Breaking", "Bad"),
                ListWidgetConfig("Game", "of Thrones"),
                ListWidgetConfig("Stranger" , "Things"),
                ListWidgetConfig("Walking" , "Dead")
            )
            else -> emptyList()
        }
    }
}
