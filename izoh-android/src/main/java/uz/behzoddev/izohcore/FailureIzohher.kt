package uz.behzoddev.izohcore

import uz.behzoddev.izoh_common.date.DateUtils
import java.text.DateFormat

class FailureIzohher : Izohher {

  override fun log(level: Level, tag: String, message: String, throwable: Throwable?) {
    val currentMessage = composedAndPrint(level, tag, message)
    val finalMessage = failureMessage(throwable, currentMessage)
    sanitizeAndLogPriority(level, finalMessage)
  }

  private fun sanitizeAndLogPriority(level: Level, message: String) {
    when (level) {
      Level.FAILURE -> System.err.println(message)
      else -> println(message)
    }
  }

  private fun failureMessage(failure: Throwable?, currentMessage: String): String {
    return failure?.let {
      "$currentMessage\n${it.stringify()}"
    } ?: currentMessage
  }

  private fun composedAndPrint(level: Level, tag: String, message: String): String {
    val now = formatAndCurrentTime()
    val currentThread = runCurrentThread()
    return "$now ($currentThread) [${level.asString()}/$tag]: $message"
  }

  private fun formatAndCurrentTime(): String {
    val dateFormat: DateFormat = DateUtils.simpleCurrentTime()
    return dateFormat.format(DateUtils.now())
  }

  private fun runCurrentThread(): String {
    return Thread.currentThread().run { "$name:$id" }
  }
}