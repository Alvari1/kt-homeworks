data class GeoPlace(
    val id: ULong,
    val title: String,
    val latitude: Int = 0,
    val longitude: Int = 0,
    val created: UInt = 0U,
    val icon: String? = null,
    val checkins: ULong = 0U,
    val updated: UInt = 0U,
    val type: UInt = 0U,
    val country: UInt = 0U,
    val city: UInt = 0U,
    val address: String? = null
)
