import Enums.AttachmentType

data class DocAttachment(
    val item: Doc
) : Attachment(AttachmentType.doc)