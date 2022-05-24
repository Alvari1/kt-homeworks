import Enums.ReportCommentReason

fun main() {
    val post1 = WallService.add(Post(fromId = 1U, text = "London is the capital of US."))
    val post2 = WallService.add(Post(fromId = 2U, text = "test post2"))

    if (post1 != null) {
        WallService.likeById(post1.id)
    }

    if (post2 != null) {
        WallService.update(post2.copy(text = "test post2 updated"))
    }

    val comment1 = WallService.createComment(
        Comment(
            id = 1U,
            fromId = 1U,
            ownerId = 1U,
            postId = post1.id,
            text = "ty nesesh' kakuyu-to ditch"
        )
    )

    val report1 = WallService.reportComment(
        Report(
            id = 1U,
            ownerId = 1U,
            commentId = comment1.id,
            reason = ReportCommentReason.rcr2
        )
    )
}