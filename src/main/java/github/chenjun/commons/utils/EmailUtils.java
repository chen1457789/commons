package github.chenjun.commons.utils;

import github.chenjun.commons.bean.MailBean;
import org.apache.commons.mail.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * send email
 * Created by chenjun on 2017/2/3.
 */
public class EmailUtils {
    private static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);

    public static void sendEmail(MailBean bean) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(bean.getHostName());
            email.setSmtpPort(bean.getPort());
            email.setAuthenticator(new DefaultAuthenticator(bean.getUserName(), bean.getPassword()));
            email.setSSLOnConnect(bean.isSSLConnect());
            email.setFrom(bean.getFrom());
            email.setSubject(bean.getSubject());
            email.setMsg(bean.getMsg());
            email.addTo(bean.getAddTo());
            email.send();
        } catch (EmailException e) {
            logger.error("send email error", e);
        }
    }

    public static void sendEmail(MailBean bean, File... files) {
        try {
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName(bean.getHostName());
            email.setSmtpPort(bean.getPort());
            email.setAuthenticator(new DefaultAuthenticator(bean.getUserName(), bean.getPassword()));
            email.setSSLOnConnect(bean.isSSLConnect());
            email.setFrom(bean.getFrom());
            email.setSubject(bean.getSubject());
            email.setMsg(bean.getMsg());
            email.addTo(bean.getAddTo());
            for (File f : files) {
                email.attach(f);
            }
            email.send();
        } catch (EmailException e) {
            logger.error("send email error", e);
        }
    }
}
