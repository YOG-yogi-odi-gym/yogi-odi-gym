package com.health.yogiodigym.member.service;

public interface EmailService {

    void sendCodeToMail(String recipient, String code);

    String makeCode();
}
