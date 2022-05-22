class Graffiti(
    override val id: ULong,
    override val ownerId: ULong,

    val photo130: String? = null,
    val photo604: String? = null
) : AttachmentItem {
}