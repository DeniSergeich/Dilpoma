package ru.sergeich.diploma.services;

import ru.sergeich.diploma.domain.User;

public interface MailSenderService {
    void sendMail(String to, long subject, String text);
    String createBody(User user);
}
