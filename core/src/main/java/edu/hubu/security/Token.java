package edu.hubu.security;

import lombok.Data;

@Data
public class Token {
    private String uid;
    private String rid;
    private String account;
}
