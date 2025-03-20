package com.example.buysell.controllers;

import com.example.buysell.models.Message;
import com.example.buysell.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Отправить сообщение
    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        // Извлекаем текущего аутентифицированного пользователя
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Присваиваем текущего пользователя как отправителя
        message.setSender(user.getUsername());  // Или email, в зависимости от вашей логики

        // Сохраняем сообщение с привязанным отправителем
        return messageService.saveMessage(message);
    }

    // Получить все сообщения для получателя
    @GetMapping("/{recipient}")
    public List<Message> getMessages(@PathVariable String recipient) {
        return messageService.getMessages(recipient);
    }

    // Удалить сообщение
    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }
}
