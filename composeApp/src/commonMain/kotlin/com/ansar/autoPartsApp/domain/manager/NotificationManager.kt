package com.ansar.autoPartsApp.domain.manager

import kotlinx.coroutines.channels.Channel


sealed class Notification(val header: String, val message: String) {
    class Success(header: String = "Уведомление", message: String) : Notification(header, message)
    class Failure(header: String = "Упс, ошибка", message: String) : Notification(header, message)
}

interface NotificationManager {

    val notification: Channel<Notification>

    fun success(message: String, header: String = "Уведомление")
    fun error(message: String, header: String = "Упс, ошибка")

    fun notification(notification: Notification)
}


class NotificationManagerImpl : NotificationManager {

    override val notification: Channel<Notification> = Channel(Channel.BUFFERED)

    override fun success(message: String, header: String) {
        notification.trySend(Notification.Success(message, header))
    }

    override fun error(message: String, header: String) {
        notification.trySend(Notification.Failure(message, header))
    }

    override fun notification(notification: Notification) {
        this.notification.trySend(notification)
    }

}