class Audio(
    override val id: ULong,
    override val ownerId: ULong,

    val albumId: ULong = 0U,
    val userId: ULong = 0U,
    val artist: String? = null,
    val title: String? = null,
    val duration: UInt = 0U,
    val url: String? = null,
    val date: UInt = 0U
) : AttachmentItem {
}