package com.yinjie.controller;

import com.yinjie.domin.Job;
import com.yinjie.form.JobSuccess;
import com.yinjie.form.ReceiveEmailDetail;
import com.yinjie.service.JobService;
import com.yinjie.service.MessageService;
import com.yinjie.service.impl.SendEmailService;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/sendEmail")
public class sendEmail {
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private JobService jobService;

    @PostMapping("/sendResume")
    public ResponseEntity sendResume(MultipartFile file, @ModelAttribute ReceiveEmailDetail receiveEmailDetail) {
        System.out.println("邮件发送处理开始");
        //短信消息处理
        messageService.sendMessage(receiveEmailDetail.getId());//下单发送消息，生产消息
        Job job = jobService.sendEmailFind(receiveEmailDetail.getId(), receiveEmailDetail.getJobName());

        String fileName = file.getOriginalFilename();
        fileName = UUID.randomUUID() + "_" + fileName;
        System.out.println(fileName);

        String dirPath = "E:\\桌面\\pure\\upload\\";
        File filePath = new File(dirPath); //便于使用file里的方法
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        try {

            file.transferTo(new File(dirPath + fileName));//将上传的文件保存到指定路径

//                //发送邮件
            String to = "3462194490@qq.com";
            String subject = "求职信";
            StringBuilder text = new StringBuilder();
            text.append("<html><head></head>");
            text.append("<style> #a{\n" +
                    "            width: 200px;\n" +
                    "            height: 200px;\n" +
                    "            position: absolute;\n" +
                    "            right: 10px;\n" +
                    "        }" +
                    "#b{" +
                    "color:red}" +
                    "</style>");
            text.append("<body><h3>来自【<span id='b'>" + job.getApartment().getDepartment() + "</span>】的【<span id='b'>"
                    + job.getName() + "</span>】职位的邮件</h3>\n" +
                    "<h1>求职信</h1>");
            String textContext = receiveEmailDetail.getTextContext(); //邮件信息
            text.append("<p> " + textContext + "</p></body>");
            text.append(" <span id='a'>" + receiveEmailDetail.getUsername() + "</span><br/>\n" +
                    "        <span id='a'>" + receiveEmailDetail.getTelephone() + "</span><br/>\n" +
                    "        <span id='a'>" + receiveEmailDetail.getCreatedDate() + "</span><br/>" +
                    "</html>");
            String filePath2 = dirPath + fileName;

            sendEmailService.sendComplexEmail(to, subject, text.toString(),
                    filePath2, textContext);
            System.out.println("邮件发送处理结束");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
