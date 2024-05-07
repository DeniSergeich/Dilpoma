package ru.sergeich.diploma.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;

//    @Value("${spring.mail.username}")
//    private String from;

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
        bouquetsMap.forEach((key, value) -> body[0] += key.getName()
                + " | " + value + " | " + key.getPrice() + " руб."
                + (value*key.getPrice() != key.getPrice() ? " ("+value*key.getPrice()
                + " руб.)\n" : "\n"));
        body[0] += "Итого: " + user.getCart().getTotalPrice() + " руб.";
        return body[0];
    }

}
