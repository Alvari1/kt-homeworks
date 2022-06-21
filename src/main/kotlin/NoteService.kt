import Enums.CommentNotePrivacy
import Enums.NotePrivacy
import Exceptions.CommentNotFoundException
import Exceptions.NoteNotFoundException

object NoteService {
    private const val ACTION_PASSED: UInt = 1U
    private const val NOT_FOUND: UInt = 180U

    private const val SORT_DESCENDING: UInt = 0U
    private const val SORT_ASCENDING: UInt = 1U

    private val notes = mutableListOf<Note>()
    private val comments = mutableListOf<Comment>()
    private val deletedComments = mutableListOf<Comment>()


    private var nextNoteId: ULong = 1U
    private var nextCommentId: ULong = 1U

    private fun getNextNoteId(): ULong {
        return nextNoteId
    }

    private fun getNextCommentId(): ULong {
        return nextCommentId
    }

    fun add(
        title: String,
        text: String,
        ownerId: UInt,
        privacy: NotePrivacy = NotePrivacy.np0,
        commentNotePrivacy: CommentNotePrivacy = CommentNotePrivacy.cnp0,
        privacyView: String? = null,
        commentPrivacy: String? = null,
    ): ULong {
        notes += Note(
            id = nextNoteId,
            title = title,
            text = text,
            ownerId = ownerId,
            date = (System.currentTimeMillis() / 1000).toUInt(),
            privacy = privacy,
            commentPrivacy = commentNotePrivacy,
            privacyView = privacyView,
            privacyComment = commentPrivacy
        )
        nextNoteId++
        return notes.last().id
    }

    private fun findById(id: ULong): Note? {
        for ((index, note) in notes.withIndex()) {
            if (note.id == id) {
                return notes[index]
            }
        }
        return null
    }

    private fun findCommentById(id: ULong): Comment? {
        for ((index, comment) in comments.withIndex()) {
            if (comment.id == id) {
                return comments[index]
            }
        }
        return null
    }


    fun edit(
        id: ULong,
        title: String,
        text: String,
        privacy: NotePrivacy = NotePrivacy.np0,
        commentNotePrivacy: CommentNotePrivacy = CommentNotePrivacy.cnp0,
        privacyView: String? = null,
        commentPrivacy: String? = null,
    ): UInt {

        for ((index, noted) in notes.withIndex()) {
            if (noted.id == id) {
                notes[index] = noted.copy(
                    title = title,
                    date = noted.date,
                    text = text,
                    privacy = privacy,
                    commentPrivacy = commentNotePrivacy,
                    privacyView = privacyView,
                    privacyComment = commentPrivacy,
                )
                return ACTION_PASSED
            }
        }
        return NOT_FOUND
    }


    private fun updateNote(note: Note): UInt {
        for ((index, noted) in notes.withIndex()) {
            if (noted.id == note.id) {
                notes[index] = noted.copy()
                return ACTION_PASSED
            }
        }
        return NOT_FOUND
    }

    private fun updateComment(comment: Comment): UInt {
        for ((index, commented) in comments.withIndex()) {
            if (commented.id == commented.id) {
                comments[index] = commented.copy()
                return ACTION_PASSED
            }
        }
        return NOT_FOUND
    }

    fun createComment(
        noteId: ULong,
        fromId: UInt,
        ownerId: ULong,
        replyTo: UInt = 0U,
        message: String
    ): ULong {
        var note = findById(noteId)
        if (note != null) {
            comments += Comment(
                id = nextCommentId,
                postId = noteId,
                fromId = fromId,
                ownerId = ownerId,
                replyToUser = replyTo,
                text = message
            )
            val returnCode = nextCommentId
            nextCommentId++

            note.comments++
            updateNote(note)

            return returnCode
        } else {
            throw NoteNotFoundException("note ${noteId} not found")
        }
    }

    fun deleteComment(commentId: ULong, ownerId: ULong): UInt {
        for ((index, comment) in comments.withIndex()) {
            if (comment.id == commentId) {
                deletedComments += comment.copy()
                comments.removeAt(index)
                return ACTION_PASSED
            }
        }
        throw CommentNotFoundException("comment ${commentId} not found")
    }

    fun restoreComment(commentId: ULong, ownerId: ULong): UInt {
        for ((index, comment) in deletedComments.withIndex()) {
            if (comment.id == commentId) {
                comments += comment.copy()
                deletedComments.removeAt(index)
                return ACTION_PASSED
            }
        }
        throw CommentNotFoundException("comment ${commentId} not found")
    }


    fun editComment(commentId: ULong, ownerId: ULong, message: String): UInt {
        val comment = findCommentById(commentId)
        if (comment != null) {
            return updateComment(comment.copy(text = message))
        }

        throw CommentNotFoundException("note ${commentId} not found")
    }

    fun get(
        noteIds: String? = null,
        userId: UInt = 0U,
        offset: ULong = 0U,
        count: ULong = 1U,
        sort: UInt = SORT_DESCENDING
    ): List<Note> {
        val outputList = mutableListOf<Note>()
        var counter: ULong = 0U

        if (noteIds != null) {
            val splitNoteIds = noteIds.trim().split(',')
            for (i in splitNoteIds) {
                val note = findById(i.toULong())
                if (note != null) {
                    outputList += note
                    if (checkCounter(counter++, count)) break
                }
            }
        } else if (!userId.equals(0U)) {
            for (noted in notes) {
                if (noted.ownerId == userId) {
                    outputList += noted.copy()
                    if (checkCounter(counter++, count)) break
                }
            }
        }

        if (outputList.isNotEmpty()) {
            if (sort == SORT_ASCENDING) {
                outputList.sortedBy { it.date }
            } else {
                outputList.sortedByDescending { it.date }
            }
        }

        return outputList
    }

    private fun checkCounter(actualCounter: ULong, limit: ULong): Boolean {
        return actualCounter.equals(limit)
    }

    fun getById(noteId: ULong, ownerId: ULong, needWiki: Boolean): Note {
        return findById(noteId) ?: throw NoteNotFoundException("note ${noteId} not found")
    }

    fun getComments(
        noteId: ULong,
        ownerId: ULong,
        sort: UInt = SORT_DESCENDING,
        offset: ULong = 0U,
        count: ULong = 1U,
    ): List<Comment> {
        val outputList = mutableListOf<Comment>()
        var counter: ULong = 0U

        for (comment in comments) {
            if (comment.postId == noteId) {
                outputList += comment
                if (checkCounter(counter++, count)) break
            }
        }

        if (outputList.isNotEmpty()) {
            if (sort == SORT_ASCENDING) {
                outputList.sortedBy { it.date }
            } else {
                outputList.sortedByDescending { it.date }
            }
        }

        return outputList
    }

}