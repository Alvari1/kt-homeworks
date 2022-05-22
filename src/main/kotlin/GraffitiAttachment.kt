import Enums.AttachmentType

data class GraffitiAttachment(
    val item: Graffiti
) : Attachment(AttachmentType.graffiti)