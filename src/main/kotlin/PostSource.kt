import Enums.PostSourceData
import Enums.PostSourcePlatform
import Enums.PostSourceType

data class PostSource(
    val type: PostSourceType,
    val platform: PostSourcePlatform? = null,
    val data: PostSourceData? = null,
    val url: String? = null
)
