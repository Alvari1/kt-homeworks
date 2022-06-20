import Exceptions.CommentNotFoundException
import Exceptions.PostNotFoundException

object WallService {
    private var posts = mutableListOf<Post>()
    private var comments = mutableListOf<Comment>()
    private var reports = mutableListOf<Report>()

    private var nextPostId: ULong = 1U
    private var nextCommentId: ULong = 1U
    private var nextReportId: ULong = 1U

    fun getNextPostId(): ULong {
        return nextPostId
    }

    fun getNextCommentId(): ULong {
        return nextCommentId
    }

    fun add(post: Post): Post {
        posts += post.copy(id = nextPostId, date = (System.currentTimeMillis() / 1000).toUInt())
        nextPostId++
        return posts.last()
    }

    fun findById(id: ULong): Post? {
        for ((index, post) in posts.withIndex()) {
            if (post.id == id) {
                return posts[index]
            }
        }
        return null
    }

    fun likeById(id: ULong): Post? {
        for ((index, post) in posts.withIndex()) {
            if (post.id == id) {
                posts[index] = post.copy(likes = post.likes.copy(count = post.likes.count + 1U))
                return posts[index]
            }
        }
        return null
    }

    fun update(post: Post): Boolean {
        for ((index, posted) in posts.withIndex()) {
            if (posted.id == post.id) {
                posts[index] = post.copy(fromId = posted.fromId, date = posted.date)
                return true
            }
        }
        return false
    }

    fun findCommentById(id: ULong): Comment? {
        for ((index, comment) in comments.withIndex()) {
            if (comment.id == id) {
                return comments[index]
            }
        }
        return null
    }

    fun createComment(comment: Comment): Comment {
        var post = findById(comment.postId)
        if (post != null) {
            comments += comment.copy(id = nextCommentId, date = (System.currentTimeMillis() / 1000).toUInt())
            nextCommentId++

            post.comments.count++
            update(post)

            return comments.last()
        } else {
            throw PostNotFoundException("post ${comment.postId} not found")
        }
    }

    fun getNextReportId(): ULong {
        return nextCommentId
    }

    fun findReportById(id: ULong): Report? {
        for ((index, report) in reports.withIndex()) {
            if (report.id == id) {
                return reports[index]
            }
        }
        return null
    }

    fun reportComment(report: Report): UInt {
        var comment = findCommentById(report.commentId)
        if (comment != null) {
            reports += report.copy(id = nextReportId)
            nextReportId++

            return 1U
        } else {
            throw CommentNotFoundException("comment ${report.commentId} not found")
        }
    }
}