import Enums.DocType

class Doc(
    override val id: ULong,
    override val ownerId: ULong,

    val title: String? = null,
    val size: ULong = 0U,
    val ext: String? = null,
    val url: String? = null,
    val date: UInt = 0U,
    val type: DocType? = DocType.unknown
) : AttachmentItem {
}