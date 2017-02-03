package github.chenjun.commons.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by chenjun on 2017/2/3.
 */
public class FtpUtil {
    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * 连接ftp服务器
     *
     * @param ftpClient
     * @param host
     * @param port
     * @return
     */
    public static boolean connect(FTPClient ftpClient, String host, int port) {
        if (ftpClient == null) ftpClient = new FTPClient();
        if (ftpClient.isConnected()) return true;
        try {
            ftpClient.connect(host, port);
            return ftpClient.isConnected();
        } catch (IOException e) {
            logger.error("connect ftp server fail", e);
            return false;
        }
    }

    /**
     * 登录
     *
     * @param ftpClient
     * @param userName
     * @param password
     * @return
     */
    public static boolean login(FTPClient ftpClient, String userName, String password) {
        if (ftpClient == null) ftpClient = new FTPClient();
        try {
            boolean flag = ftpClient.login(userName, password);
            if (flag) ftpClient.changeWorkingDirectory("/");
            return flag;
        } catch (IOException e) {
            logger.error("login ftp server fail", e);
            return false;
        }
    }

    /**
     * 上传文件
     *
     * @param ftpClient
     * @param file
     * @param dirs
     * @return
     */
    public static boolean upload(FTPClient ftpClient, File file, String... dirs) {
        return upload(ftpClient, file, file.getName(), dirs);
    }

    /**
     * 上传文件
     *
     * @param ftpClient
     * @param file
     * @param fileName
     * @param dirs
     * @return
     */
    public static boolean upload(FTPClient ftpClient, File file, String fileName, String... dirs) {
        try {
            return upload(ftpClient, new FileInputStream(file), fileName, dirs);
        } catch (FileNotFoundException e) {
            logger.error("upload file to ftp fail", e);
            return false;
        }
    }

    /**
     * 上传文件
     *
     * @param ftpClient
     * @param inputStream
     * @param fileName
     * @param dirs
     * @return
     */
    public static boolean upload(FTPClient ftpClient, InputStream inputStream, String fileName, String... dirs) {
        try {
            ftpClient.changeWorkingDirectory("/");
            for (String dir : dirs) {
                if (!ftpClient.changeWorkingDirectory(dir)) {
                    ftpClient.makeDirectory(dir);
                    ftpClient.changeWorkingDirectory(dir);
                }
            }
            boolean flag = ftpClient.storeFile(fileName, inputStream);
            ftpClient.changeWorkingDirectory("/");
            return flag;
        } catch (IOException e) {
            logger.error("upload file to ftp fail", e);
            return false;
        }
    }

    /**
     * 删除ftp上的文件或文件夹
     *
     * @param ftpClient
     * @param path
     * @return
     */
    public static boolean delete(FTPClient ftpClient, String path) {
        try {
            FTPFile[] listFiles = ftpClient.listFiles(path);
            for (FTPFile ftpFile : listFiles) {
                String filePath = path + "/" + ftpFile.getName();
                if (ftpFile.isDirectory()) {
                    delete(ftpClient, filePath);
                } else {
                    ftpClient.deleteFile(filePath);
                }
            }
            return ftpClient.deleteFile(path);
        } catch (IOException e) {
            logger.error("delete path fail", e);
            return false;
        }
    }

    /**
     * 注销
     *
     * @param ftpClient
     * @return
     */
    public static boolean logout(FTPClient ftpClient) {
        try {
            return ftpClient == null || ftpClient.logout();
        } catch (IOException e) {
            logger.error("logout ftp server fail", e);
            return false;
        }
    }

    /**
     * 断开连接
     *
     * @param ftpClient
     * @return
     */
    public static boolean disConnect(FTPClient ftpClient) {
        try {
            if (ftpClient != null) ftpClient.disconnect();
            return true;
        } catch (IOException e) {
            logger.error("disConnect ftp server fail", e);
            return false;
        }
    }
}
