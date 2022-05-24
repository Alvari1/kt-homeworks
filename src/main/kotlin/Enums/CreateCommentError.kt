package Enums

enum class CreateCommentError(val text: String) {
    cce212("Access to post comments denied"),
    cce213("Нет доступа к комментированию записи"),
    cce222("Запрещено размещение ссылок в комментариях"),
    cce223("Превышен лимит комментариев на стене")
}