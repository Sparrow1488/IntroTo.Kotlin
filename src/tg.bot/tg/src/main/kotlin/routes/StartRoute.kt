package routes

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import constants.BotCommands
import org.koin.core.context.GlobalContext.get
import templates.HelloText

fun Dispatcher.configureStartCommand() {
    val koin = get()
    val helloText = HelloText(koin.get()).toString()

    command("start") {
        val navButtons = KeyboardReplyMarkup(keyboard = generateNavigationButton(), resizeKeyboard = true)
        bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = helloText,
            replyMarkup = navButtons
        )
    }
}

private fun generateNavigationButton(): List<List<KeyboardButton>> {
    return listOf(
        listOf(KeyboardButton(BotCommands.Nav.TELL_ABOUT_BOT)),
        listOf(KeyboardButton(BotCommands.Nav.CREATE_NEW_ARTICLE))
    )
}