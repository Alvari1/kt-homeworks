class Video(
    override val id: ULong,
    override val ownerId: ULong,

    val albumId: ULong = 0U,
    val userId: ULong = 0U,
    val title: String? = null,
    val description: String? = null,
    val duration: UInt = 0U,
    val date: UInt = 0U
) : AttachmentItem {
}