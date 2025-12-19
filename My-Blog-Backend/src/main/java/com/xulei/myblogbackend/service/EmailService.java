package com.xulei.myblogbackend.service;

import com.xulei.myblogbackend.Excpetion.BaseException;
import com.xulei.myblogbackend.dto.EmailVerifyDto;
import org.springframework.stereotype.Service;

/**
 * @author xulei
 */

public interface EmailService {
    void getEmailCode(EmailVerifyDto emailVerifyDto) throws BaseException;

    void sendMessage(String emailAddress) throws BaseException;
}
