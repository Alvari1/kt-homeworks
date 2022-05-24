import Enums.ReportCommentReason

data class Report(
    val id: ULong = 0U,
    val ownerId: ULong,
    val commentId: ULong,
    val reason: ReportCommentReason = ReportCommentReason.rcr0
)
