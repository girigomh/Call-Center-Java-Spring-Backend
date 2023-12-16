package org.comcom.dto;

public record LoginResponse(String token, String refreshToken, UserDto user) {}
