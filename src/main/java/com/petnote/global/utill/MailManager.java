package com.petnote.global.utill;


import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailManager {

    private final JavaMailSender javaMailSender;

    public void codeSendMail(String to, String code) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

        helper.setTo(to);
        helper.setSubject("[PetNote] 인증코드 안내");
        helper.setFrom("PetNote <petNoteG@gmail.com>");

        String html = """
            <div style="font-family: Pretendard, Arial, sans-serif; max-width:480px; margin:auto;
                        border:1px solid #eee; border-radius:12px; padding:24px;">
              <h2 style="color:#FF7A00; text-align:center;">🐾 PetNote 인증코드</h2>
              <p style="font-size:15px; color:#333; text-align:center;">
                아래 인증코드를 입력해 주세요.<br/>
                <b style="font-size:22px; color:#FF7A00; letter-spacing:3px;">%s</b>
              </p>
              <p style="font-size:13px; color:#888; text-align:center; margin-top:24px;">
                이 코드는 10분 동안만 유효합니다.<br/>
                만약 본인이 요청한 것이 아니라면 이 메일을 무시하세요.
              </p>
            </div>
            """.formatted(code);

        helper.setText(html, true);
        javaMailSender.send(message);
    }

    public void resetPwSendMail(String to, String code) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

        helper.setTo(to);
        helper.setSubject("[PetNote] 임시 비밀번호 안내");
        helper.setFrom("PetNote <petNoteG@gmail.com>");

        String html = """
            <div style="font-family: Pretendard, Arial, sans-serif; max-width:480px; margin:auto;
                        border:1px solid #eee; border-radius:12px; padding:24px;">
              <h2 style="color:#FF7A00; text-align:center;">🐾 PetNote 임시 비밀번호</h2>
              <p style="font-size:15px; color:#333; text-align:center;">
                아래 임시 비밀번호를 통해 로그인 후 새로운 비밀번호로 변경해 주세요.<br/>
                <b style="font-size:22px; color:#FF7A00; letter-spacing:3px;">%s</b>
              </p>
              <p style="font-size:13px; color:#888; text-align:center; margin-top:24px;">
                만약 본인이 요청한 것이 아니라면 petNote 메일로 연락해 주세요.
              </p>
            </div>
            """.formatted(code);

        helper.setText(html, true);
        javaMailSender.send(message);
    }
}