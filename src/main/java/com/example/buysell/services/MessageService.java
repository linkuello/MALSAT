package com.example.buysell.services;

import com.example.buysell.models.Message;
import com.example.buysell.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    // Сохранить сообщение с авторизованным пользователем
    public Message saveMessage(Message message) {
        // Извлекаем текущего аутентифицированного пользователя
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Присваиваем отправителя текущего пользователя
        message.setSender(user.getUsername());

        // Сохраняем сообщение в репозиторий
        return messageRepository.save(message);
    }

    // Получить все сообщения для получателя
    public List<Message> getMessages(String recipient) {
        return messageRepository.findByRecipient(recipient);
    }

    // Удалить сообщение
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}
