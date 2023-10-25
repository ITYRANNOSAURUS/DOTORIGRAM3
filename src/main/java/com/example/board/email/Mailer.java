package com.example.board.email;

import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpSession;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Mailer {
  @Autowired
	HttpSession session;

@GetMapping("/emailcode")
@ResponseBody
  public void sendMail(
    SMTPAuthenticator smtp , @RequestParam String email){
     
        
        String content = Long.toString(Math.abs(new Random().nextLong()),36).substring(0,4);
    Properties p = new Properties();
    p.put("mail.smtp.host", "smtp.gmail.com");
    p.put("mail.smtp.port", "465");
    p.put("mail.smtp.starttls.enable", "true");
    p.put("mail.smtp.auth", "true");
    p.put("mail.smtp.debug", "true");
    p.put("mail.smtp.socketFactory.port", "465");
    p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    p.put("mail.smtp.socketFactory.fallback", "false");
    try {
      Session ses = Session.getInstance(p, smtp);
      ses.setDebug(true);
      MimeMessage msg = new MimeMessage(ses); // 메일의 내용을 담을 객체
      msg.setSubject("pika충전소 회원가입 본인 인증"); // 제목
      Address fromAddr = new InternetAddress("cccsue15@gmail.com");
      msg.setFrom(fromAddr); // 보내는 사람
      Address toAddr = new InternetAddress(email);
      msg.addRecipient(Message.RecipientType.TO, toAddr); // 받는 사람
      msg.setContent(content, "text/html;charset=UTF-8"); // 내용과 인코딩
      Transport.send(msg); // 전송
    } catch (Exception e) {
      e.printStackTrace();
    }
    session.setAttribute("code",content);
  }
  @GetMapping("emailcode-check")
  @ResponseBody
  public String codecheck(@RequestParam String emailcode){
    Object code = session.getAttribute("code");
    System.out.println(code);
    System.out.println(emailcode);
    if(code.equals(emailcode) ){
      System.out.println("yes");
      return "코드일치";
    }else{
      System.out.println("no");
      return "코드불일치";
    }
  }
}