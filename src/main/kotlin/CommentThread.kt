data class CommentThread(
    val count: UInt,
    val items: Set<Comment>? = null,
    val canPost: Boolean = true,
    val showReplyButton : Boolean = true,
    val groupsCanPost: Boolean = true
)
