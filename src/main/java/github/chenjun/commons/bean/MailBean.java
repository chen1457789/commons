package github.chenjun.commons.bean;

/**
 * Created by chenjun on 2017/2/3.
 */
public class MailBean {
    private String hostName;
    private int port;
    private String userName;
    private String password;
    private boolean SSLConnect;
    private String from;
    private String subject;
    private String msg;
    private String addTo;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSSLConnect() {
        return SSLConnect;
    }

    public void setSSLConnect(boolean SSLConnect) {
        this.SSLConnect = SSLConnect;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAddTo() {
        return addTo;
    }

    public void setAddTo(String addTo) {
        this.addTo = addTo;
    }
}
