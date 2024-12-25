package com.yinjie;

import com.yinjie.service.impl.SendEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@RunWith(SpringRunner.class)
@SpringBootTest
public class sendEmailTest {
    @Autowired
    private SendEmailService sendEmailService;

    @Test
    public void sendSimpleMailTest() {

        String to = "3462194490@qq.com";
        String subject = "【纯文本邮件】标题";
        String text = "Spring Boot纯文本邮件发送内容测试.....";
        sendEmailService.sendSimpleEmail(to, subject, text);
    }

    @Test
    public void sendComplexEmailTest() {
        String to = "3462194490@qq.com";
        String subject = "【复杂邮件】标题";
        StringBuilder text = new StringBuilder();
        text.append("<html><head></head>");
        text.append("<style>span{\n" +
                "            width: 200px;\n" +
                "            height: 200px;\n" +
                "            position: absolute;\n" +
                "            right: 0px;\n" +
                "        }</style>");
        text.append("<body><h1>求职简历\n！</h1>");
        String rscId = "img001";
        text.append("<p>我叫[您的姓名]，是一名对[目标职位或行业]充满热情的求职者。在浏览贵公司的招聘信息时，我深深被[公司名]的[公司文化、愿景、或具体职位]所吸引，因此我希望能有机会加入贵公司，共同实现目标。\n" +
                "\n" +
                "    我的教育背景与[目标职位]密切相关。我拥有[您的学历]学位，主修[您的专业]，在校期间，我不仅系统学习了[相关专业课程]，还积极参与了[相关项目或实践活动]，这些经历让我对[目标职位]有了更深入的了解和认识。\n</p></body>");
        text.append(" <span th:text=\"${username}\">[您的姓名]</span><br/>\n" +
                "        <span th:text=\"${telephone}\">[您的联系方式]</span><br/>\n" +
                "        <span th:text=\"${createdDate}\">[日期]</span><br/>" +
                "</html>");

        String rscPath = "E:\\1java\\springBoot\\23gbsrj01-3-4\\截图\\email\\email素材.png";
        String filePath = "E:\\1java\\springBoot\\23gbsrj01-3-4\\截图\\email\\email文本.txt";
        sendEmailService.sendComplexEmail(to, subject, text.toString(),
                filePath, rscId);
    }

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendTemplateEmailTest() {
        String to = "3462194490@qq.com";
        String subject = "求职信";
        Context context = new Context();
        context.setVariable("username", "尹杰");
        context.setVariable("telephone", "456123");
        context.setVariable("createdDate", "2024-4-5");
        String filePath = "E:\\1java\\springBoot\\23gbsrj01-3-4\\截图\\email\\email文本.txt";
        String emailContent = templateEngine.process("code", context);
        sendEmailService.sendTemplateEmail(to, subject, emailContent);


    }


}

