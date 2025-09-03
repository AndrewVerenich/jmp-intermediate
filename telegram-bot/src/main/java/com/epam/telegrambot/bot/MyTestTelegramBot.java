package com.epam.telegrambot.bot;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyTestTelegramBot extends TelegramLongPollingBot {

  private final String botUsername;
  private final String botToken;

  public MyTestTelegramBot(String botUsername, String botToken) {
    this.botUsername = botUsername;
    this.botToken = botToken;
  }

  public MyTestTelegramBot(DefaultBotOptions options, String botUsername, String botToken) {
    super(options);
    this.botUsername = botUsername;
    this.botToken = botToken;
  }

  @Override
  public String getBotUsername() {
    return botUsername;
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasCallbackQuery()) {
      String data = update.getCallbackQuery().getData();
      Long chatId = update.getCallbackQuery().getMessage().getChatId();
      Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

      if ("ECHO_BTN".equals(data)) {
        EditMessageText edit = new EditMessageText();
        edit.setChatId(String.valueOf(chatId));
        edit.setMessageId(messageId);
        edit.setText("Кнопка нажата ✅");
        try {
          execute(edit);
        } catch (TelegramApiException e) {
          // noop for template
        }
      }
      return;
    }

    if (update.getMessage() != null && update.getMessage().hasText()) {
      Long chatId = update.getMessage().getChatId();
      String text = update.getMessage().getText();
      if ("/start".equals(text)) {
        sendText(chatId, "Бот запущен через прокси/без него. Напишите что-нибудь.");
      } else {
        sendText(chatId, "Вы написали: " + text);
      }
    }
  }

  private void sendText(Long chatId, String text) {
    SendMessage message = new SendMessage();
    message.setChatId(String.valueOf(chatId));
    message.setText(text);

    InlineKeyboardButton button = new InlineKeyboardButton();
    button.setText("Нажми меня");
    button.setCallbackData("ECHO_BTN");

    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rows = new ArrayList<>();
    List<InlineKeyboardButton> row = new ArrayList<>();
    row.add(button);
    rows.add(row);
    markup.setKeyboard(rows);
    message.setReplyMarkup(markup);
    try {
      execute(message);
    } catch (TelegramApiException e) {
      // noop for template
    }
  }
}

