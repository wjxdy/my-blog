package com.xulei.myblogbackend.service;

import com.xulei.myblogbackend.excpetion.BaseException;
import com.xulei.myblogbackend.dto.EmailVerifyDto;

/**
 * @author xulei
 */

public interface EmailService {
    void getEmailCode(EmailVerifyDto emailVerifyDto) throws BaseException;

    void sendMessage(String emailAddress) throws BaseException;
}
