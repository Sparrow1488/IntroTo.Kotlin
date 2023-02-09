package routes

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import constants.BotCommands

fun Dispatcher.configureNavigation() {
    text(BotCommands.Nav.CREATE_NEW_ARTICLE) {
        bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = "Данный функционал еще не реализован"
        )
    }

    text(BotCommands.Nav.TELL_ABOUT_BOT) {
        bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = "На данный момент я нахожусь в режиме разработки и не могу рассказать о своих возможностях"
        )
    }
}