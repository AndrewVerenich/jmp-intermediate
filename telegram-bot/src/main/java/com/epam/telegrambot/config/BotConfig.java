package com.epam.telegrambot.config;

import com.epam.telegrambot.bot.MyTestTelegramBot;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.DefaultBotOptions.ProxyType;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@EnableConfigurationProperties(BotProperties.class)
public class BotConfig {

  @Bean
  public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
    return new TelegramBotsApi(DefaultBotSession.class);
  }

  @Bean
  public MyTestTelegramBot telegramBot(BotProperties properties, TelegramBotsApi botsApi) throws TelegramApiException {
    DefaultBotOptions options = new DefaultBotOptions();
//    options.setProxyHost(properties.getProxy().getHost());
//    options.setProxyPort(properties.getProxy().getPort());
//    options.setProxyType(ProxyType.HTTP);
    MyTestTelegramBot bot = new MyTestTelegramBot(options, properties.getName(), properties.getToken());
    botsApi.registerBot(bot);
    return bot;
  }
}

