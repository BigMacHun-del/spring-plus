package org.example.expert.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    // REQUIRES_NEW: 기존 트랜잭션과 별개로 독립적인 트랜잭션에서 로그를 저장
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(Long todoId, Long requestUserId, Long targetManagerUserId, String status, String message) {
        Log log = new Log(todoId, requestUserId, targetManagerUserId, status, message);
        logRepository.save(log);
    }
}
