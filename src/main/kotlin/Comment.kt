data class Comment(
    val id: ULong = 0U,
    val fromId: UInt, // Идентификатор автора комментария
    val ownerId: ULong,
    val postId: ULong,
    val date: UInt = 0U,
    val text: String,
    val donut: Donut = Donut(),
    val replyToUser: UInt = 0U,
    val replyToComment: ULong = 0U,
    val attachments: List<Attachment>? = null,
    val parentStack: Set<ULong>? = null,
    val thread: CommentThread? = null
)
