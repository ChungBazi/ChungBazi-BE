package chungbazi.chungbazi_be.domain.chat.service;

import chungbazi.chungbazi_be.domain.chat.converter.ChatConverter;
import chungbazi.chungbazi_be.domain.chat.dto.ChatRequestDTO;
import chungbazi.chungbazi_be.domain.chat.dto.ChatResponseDTO;
import chungbazi.chungbazi_be.domain.chat.entity.ChatRoom;
import chungbazi.chungbazi_be.domain.chat.entity.Message;
import chungbazi.chungbazi_be.domain.chat.repository.ChatRoomRepository;
import chungbazi.chungbazi_be.domain.chat.repository.MessageRepository;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.community.repository.PostRepository;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.domain.user.utils.UserHelper;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import chungbazi.chungbazi_be.global.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final UserHelper userHelper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public ChatResponseDTO.createChatRoomResponse createChatRoom(Long postId){
        User sender = userHelper.getAuthenticatedUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_POST));

        ChatRoom chatRoom=ChatRoom.builder()
                .sender(sender)
                .receiver(post.getAuthor())
                .post(post)
                .isActive(true)
                .build();

        chatRoomRepository.save(chatRoom);

        return ChatConverter.toChatRoomDTO(chatRoom);
    }


    public ChatResponseDTO.messageResponse sendMessage(Long chatRoomId,ChatRequestDTO.messagedto dto){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_CHATROOM));

        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        Message message = Message.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(dto.getContent())
                .isRead(false)
                .build();

        ChatResponseDTO.messageResponse response=ChatConverter.from(message);

        messageRepository.save(message);
        rabbitTemplate.convertAndSend(RabbitConfig.CHAT_EXCHANGE,RabbitConfig.CHAT_ROUTING_KEY+chatRoom.getId(),response);

        return response;
    }
}
