package ru.sergeich.diploma.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.MailSenderService;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender mailSender;

    @Async
    public void sendMail(String to, long subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Ваш заказ № " + subject  + " выполнен");
        message.setText(text);
        message.setFrom("tsvetochki.mag@yandex.ru");
        mailSender.send(message);
    }

    public String createBody(User user) {
        final String[] body = {"Спасибо за Ваш заказ в нашем магазине.\nВаш заказ: \n"};
        Map<Bouquet, Long> bouquetsMap = user.getCart().getBouquets().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        bouquetsMap.forEach((bouquet, amount) -> body[0] += bouquet.getName()
                + " | " + amount + " | " + bouquet.getPrice() + " руб."
                + (amount*bouquet.getPrice() != bouquet.getPrice() ? " ("+amount*bouquet.getPrice()
                + " руб.)\n" : "\n"));
        body[0] += "Итого: " + user.getCart().getTotalPrice() + " руб.";
        return body[0];
    }
}
