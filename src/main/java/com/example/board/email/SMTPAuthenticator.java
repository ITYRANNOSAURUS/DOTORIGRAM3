package com.example.board.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
  @Override
  protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(
        "cccsue15@gmail.com", "pgkpyubkjxjtazhb");
  }
}