package Enums

enum class CommentNotePrivacy(val text: String) {
    cnp0("все пользователи"),
    cnp1("только друзья"),
    cnp2("друзья и друзья друзей"),
    cnp3("только пользователь"),
}