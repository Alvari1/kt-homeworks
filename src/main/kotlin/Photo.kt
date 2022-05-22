class Photo(
    override val id: ULong,
    override val ownerId: ULong,

    val albumId: ULong = 0U,
    val userId: ULong = 0U,
    val text: String? = null,
    val date: UInt = 0U,
    val width: UInt? = null,
    val height: UInt? = null
) : AttachmentItem {
}