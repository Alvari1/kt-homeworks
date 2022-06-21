import Exceptions.CommentNotFoundException
import Exceptions.NoteNotFoundException
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NoteServiceTest {
    @Before
    fun setUp() {
    }

    @Test
    fun `adding note to notelist`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)

        Assert.assertNotNull(note1)
    }

    @Test
    fun `edit existed note`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val editResult = NoteService.edit(id = note1, title = "Some new note", text = "some new text here")

        Assert.assertEquals(1U, editResult)
    }

    @Test
    fun `edit non-existed note`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val editResult = NoteService.edit(id = note1 + 1U, title = "Some new note", text = "some new text here")

        Assert.assertEquals(180U, editResult)
    }

    @Test
    fun `create comment for existed note`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val createCommentResult =
            NoteService.createComment(noteId = note1, fromId = 1U, ownerId = 2U, message = "some comment here")

        Assert.assertTrue(createCommentResult > 0U)
    }

    @Test(expected = NoteNotFoundException::class)
    fun `create comment for non-existed note should throw NoteNotFoundException exception`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val createCommentResult =
            NoteService.createComment(noteId = note1 + 1U, fromId = 1U, ownerId = 2U, message = "some comment here")
    }

    @Test
    fun `delete existed comment`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val comment1 =
            NoteService.createComment(noteId = note1, fromId = 1U, ownerId = 2U, message = "some comment here")
        val deleteCommentResult = NoteService.deleteComment(commentId = comment1, ownerId = 1U)

        Assert.assertEquals(1U, deleteCommentResult)
    }

    @Test(expected = CommentNotFoundException::class)
    fun `delete non-existed comment should throw CommentNotFoundException exception`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val comment1 =
            NoteService.createComment(noteId = note1, fromId = 1U, ownerId = 2U, message = "some comment here")
        val deleteCommentResult = NoteService.deleteComment(commentId = comment1 + 1U, ownerId = 1U)
    }

    @Test
    fun `restore deleted comment`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val comment1 =
            NoteService.createComment(noteId = note1, fromId = 1U, ownerId = 2U, message = "some comment here")
        val deleteCommentResult = NoteService.deleteComment(commentId = comment1, ownerId = 1U)
        val restoreDeletedCommentResult = NoteService.restoreComment(commentId = comment1, ownerId = 1U)

        Assert.assertEquals(1U, restoreDeletedCommentResult)
    }

    @Test(expected = CommentNotFoundException::class)
    fun `restore non-existed comment should throw CommentNotFoundException exception`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val comment1 =
            NoteService.createComment(noteId = note1, fromId = 1U, ownerId = 2U, message = "some comment here")
        val deleteCommentResult = NoteService.deleteComment(commentId = comment1, ownerId = 1U)
        val restoreDeletedCommentResult = NoteService.restoreComment(commentId = 2U, ownerId = 1U)
    }

    @Test
    fun `edit existed comment`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val createComment1 =
            NoteService.createComment(noteId = note1, fromId = 1U, ownerId = 2U, message = "some comment here")


        val editCommentResult =
            NoteService.editComment(commentId = createComment1, ownerId = 2U, message = "some new comment here")

        Assert.assertEquals(1U, editCommentResult)
    }

    @Test(expected = CommentNotFoundException::class)
    fun `edit non-existed comment should throw CommentNotFoundException exception`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val createComment1 =
            NoteService.createComment(noteId = note1, fromId = 1U, ownerId = 2U, message = "some comment here")

        val editCommentResult =
            NoteService.editComment(commentId = createComment1 + 1U, ownerId = 2U, message = "some new comment here")
    }

    @Test
    fun `get noteLIst by noteIds`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val note2 = NoteService.add(title = "Some note 2", text = "some note text here 2", ownerId = 2U)
        val note3 = NoteService.add(title = "Some note 3", text = "some note text here 3", ownerId = 2U)
        val note4 = NoteService.add(title = "Some note 4", text = "some note text here 4", ownerId = 2U)

        val notesListResult = NoteService.get(noteIds = "$note1,$note3")

        Assert.assertTrue(notesListResult.isNotEmpty())
    }

    @Test
    fun `get notesList by userId`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val note2 = NoteService.add(title = "Some note 2", text = "some note text here 2", ownerId = 2U)

        val notesListResult = NoteService.get(userId = 2U)

        Assert.assertTrue(notesListResult.isNotEmpty())
    }

    @Test
    fun `get notesList by userId sorted ascending`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val note2 = NoteService.add(title = "Some note 2", text = "some note text here 2", ownerId = 2U)
        val note3 = NoteService.add(title = "Some note 3", text = "some note text here 3", ownerId = 2U)
        val note4 = NoteService.add(title = "Some note 4", text = "some note text here 4", ownerId = 2U)

        val notesListResult = NoteService.get(userId = 2U, sort = 1U)

        Assert.assertTrue(notesListResult.isNotEmpty())
    }


    @Test
    fun `get note by id for existed note`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val note2 = NoteService.getById(noteId = note1, ownerId = 1U, needWiki = true)

        Assert.assertNotNull(note2)
    }

    @Test(expected = NoteNotFoundException::class)
    fun `get note by id for non-existed note should throw NoteNotFoundException exception`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val note2 = NoteService.getById(noteId = note1 + 1U, ownerId = 1U, needWiki = true)
    }

    @Test
    fun `get commentsList`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val note2 = NoteService.add(title = "Some note 2", text = "some note text here 2", ownerId = 1U)

        var createCommentResult =
            NoteService.createComment(noteId = note1, fromId = 1U, ownerId = 2U, message = "some comment here")
        createCommentResult =
            NoteService.createComment(noteId = note1, fromId = 1U, ownerId = 2U, message = "some comment here")
        createCommentResult =
            NoteService.createComment(noteId = note2, fromId = 1U, ownerId = 2U, message = "some comment here")

        val commentsListResult = NoteService.getComments(noteId = note1, ownerId = 1U)

        Assert.assertTrue(commentsListResult.isNotEmpty())
    }

    @Test
    fun `get commentsList sorted ascending`() {
        val note1 = NoteService.add(title = "Some note", text = "some note text here", ownerId = 1U)
        val note2 = NoteService.add(title = "Some note 2", text = "some note text here 2", ownerId = 1U)

        var createCommentResult =
            NoteService.createComment(noteId = note1, fromId = 1U, ownerId = 2U, message = "some comment 1 here")
        createCommentResult =
            NoteService.createComment(noteId = note1, fromId = 1U, ownerId = 2U, message = "some comment 2 here")
        createCommentResult =
            NoteService.createComment(noteId = note2, fromId = 1U, ownerId = 2U, message = "some comment 3 here")

        val commentsListResult = NoteService.getComments(noteId = note1, ownerId = 1U, sort = 1U)

        Assert.assertTrue(commentsListResult.isNotEmpty())
    }
}