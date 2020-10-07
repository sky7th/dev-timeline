package com.sky7th.devtimeline.chat.service;

import com.sky7th.devtimeline.chat.model.ChatSession;
import com.sky7th.devtimeline.chat.model.ChatUser;
import com.sky7th.devtimeline.chat.repository.ChatSessionRepository;
import com.sky7th.devtimeline.chat.service.exception.NotFoundChatSessionException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatSessionService {

  private final ChatSessionRepository chatSessionRepository;

  @Transactional(readOnly = true)
  public ChatSession findById(String id) {
    return chatSessionRepository.findById(id).orElseThrow(NotFoundChatSessionException::new);
  }

  @Transactional(readOnly = true)
  public ChatSession findByRoomIdAndUserId(Long roomId, Long userId) {
    return chatSessionRepository.findByRoomIdAndUserId(roomId, userId).orElseThrow(NotFoundChatSessionException::new);
  }

  @Transactional(readOnly = true)
  public List<ChatSession> findByRoomId(Long roomId) {
    return chatSessionRepository.findAllByRoomId(roomId);
  }

  public int countByRoomId(Long roomId) {
    return findByRoomId(roomId).size();
  }

  public ChatSession save(Long roomId, Long userId) {
    return chatSessionRepository.save(new ChatSession(roomId, userId));
  }

  public void delete(String id) {
    chatSessionRepository.deleteById(id);
  }

  public ChatSession addSessionId(Long roomId, ChatUser chatUser) {
    ChatSession chatSession;
    try {
      chatSession = findByRoomIdAndUserId(roomId, chatUser.getUserId());
    } catch (NotFoundChatSessionException e) {
      chatSession = save(roomId, chatUser.getUserId());
    }
    Set<String> sessionIds = chatSession.getSessionIds();
    sessionIds.add(chatUser.getSessionId());
    chatSessionRepository.updateSessionIds(chatSession.getId(), sessionIds);

    return chatSession;
  }

  public ChatSession removeSessionId(Long roomId, ChatUser chatUser) {
    ChatSession chatSession = findByRoomIdAndUserId(roomId, chatUser.getUserId());
    Set<String> sessionIds = chatSession.getSessionIds();
    sessionIds.remove(chatUser.getSessionId());

    if (sessionIds.size() == 0) {
      delete(chatSession.getId());
    } else {
      chatSessionRepository.updateSessionIds(chatSession.getId(), sessionIds);
    }

    return chatSession;
  }
}
