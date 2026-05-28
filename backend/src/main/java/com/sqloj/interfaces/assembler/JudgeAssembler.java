package com.sqloj.interfaces.assembler;

import com.sqloj.application.judge.dto.JudgeResultDTO;
import com.sqloj.interfaces.response.JudgeResponse;
import org.springframework.stereotype.Component;

@Component
public class JudgeAssembler {

    public JudgeResponse toResponse(JudgeResultDTO dto) {
        return new JudgeResponse(
                dto.submissionId(),
                dto.status(),
                dto.score(),
                dto.message(),
                dto.detail(),
                dto.executeTimeMs(),
                dto.judgedAt()
        );
    }
}
