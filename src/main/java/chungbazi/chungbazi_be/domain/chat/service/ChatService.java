package chungbazi.chungbazi_be.domain.chat.service;

import chungbazi.chungbazi_be.domain.chat.converter.ChatConverter;
import chungbazi.chungbazi_be.domain.chat.dto.ChatRequestDTO;
import chungbazi.chungbazi_be.domain.chat.dto.ChatResponseDTO;
import chungbazi.chungbazi_be.domain.chat.entity.ChatRoom;
import chungbazi.chungbazi_be.domain.chat.entity.Message;
import chungbazi.chungbazi_be.domain.chat.repository.ChatRoomRepository;
import chungbazi.chungbazi_be.domain.chat.repository.MessageRepository.MessageRepository;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.community.repository.PostRepository;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.domain.user.utils.UserHelper;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.GeneralException;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import chungbazi.chungbazi_be.global.config.RabbitConfig;
import chungbazi.chungbazi_be.global.utils.PaginationResult;
import chungbazi.chungbazi_be.global.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final UserHelper userHelper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

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
        simpMessagingTemplate.convertAndSend("/topic/chat.room." + chatRoomId, response);

        return response;
    }

    @Transactional
    public ChatResponseDTO.chatRoomResponse getChatRoomDetail(Long chatRoomId,Long cursor, int limit){
        ChatRoom chatRoom=chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_CHATROOM));

        if (!isParticipant(chatRoom,userHelper.getAuthenticatedUser())){
            throw new GeneralException(ErrorStatus.ACCESS_DENIED_CHATROOM);
        }

        User receiver = chatRoom.getSender().equals(userHelper.getAuthenticatedUser())
                ? chatRoom.getReceiver() : chatRoom.getSender();

        markMessagesAsRead(chatRoomId,userHelper.getAuthenticatedUser().getId());

        List<Message> messages = messageRepository.findMessagesByChatRoomId(chatRoomId,cursor,limit+1);
        PaginationResult<Message> paginationResult = PaginationUtil.paginate(messages,limit);

        List<ChatResponseDTO.chatDetailMessage> messageList = paginationResult.getItems().stream()
                .map(message -> ChatConverter.toChatDetailMessageDTO(message, userHelper.getAuthenticatedUser().getId()))
                .collect(Collectors.toList());

        return ChatResponseDTO.chatRoomResponse.builder()
                .chatRoomId(chatRoomId)
                .postTitle(chatRoom.getPost().getTitle())
                .messageList(messageList)
                .receiverId(receiver.getId())
                .receiverName(receiver.getName())
                .build();
    }

    public void markMessagesAsRead(Long chatRoomId, Long userId){
        ChatRoom chatRoom=chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_CHATROOM));

        long updatedCount = messageRepository.markAllAsRead(chatRoom.getId(),userId);

        if (updatedCount>0){
            User receiver = chatRoom.getSender().getId().equals(userId)
                    ? chatRoom.getReceiver() : chatRoom.getSender();

            simpMessagingTemplate.convertAndSend(
                    receiver.getId().toString(),
                    "/queue/message-read",
                    Map.of("chatRoomId",chatRoom.getId(),"readCount",updatedCount));
        }
    }

    private boolean isParticipant (ChatRoom chatRoom,User user){
        return chatRoom.getSender().equals(user) || chatRoom.getReceiver().equals(user);
    }
}
