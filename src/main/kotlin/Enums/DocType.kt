package Enums

enum class DocType(val id: UInt) {
    text_doc(1U),
    archive(2U),
    gif(3U),
    image(4U),
    audio(5U),
    video(6U),
    ebook(7U),
    unknown(8U)
}