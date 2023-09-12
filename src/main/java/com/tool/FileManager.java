package com.tool;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.tool.common.resource.AppConfig;
import com.tool.db.sqlite.SqliteSession;
import com.tool.document.DocumentManager;
import com.tool.http.LoginHttpUtil;
import com.tool.login.Login;
import com.tool.printManager.PrintManager;
import com.tool.publish.PublishManager;
import com.tool.user.UserSession;
import com.tool.util.ExceptionUtil;
import com.tool.util.StringUtil;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileManager extends JFrame
{
    private       Logger                  logger                 = LoggerFactory.getLogger(FileManager.class);
    public static ThreadPoolExecutor      executors              = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    public        HashMap<String, JFrame> menuFrameMap           = new HashMap();
    public static UserSession             session;
    private       FileManagerTray         tray;
    private       Image                   image;
    private       boolean                 started;
    public        String                  TITLE_DOCUMENT_MANAGER = "Document Manager";
    public        String                  TITLE_PUBLISH_MANAGER  = "Publish Manager";
    public        String                  TITLE_SECURITY_CONTEXT = "Security Context";
    public        String                  TITLE_RECONNECTION     = "Reconnection";
    public        String                  TITLE_PRINT_MANAGER    = "Print Manager";
    ServerSocket serverSocket = null;

    public FileManager()
    {
    }

    public void init()
    {
        this.logger.info(StringUtil.replaceStr(AppConfig.getString("log.init"), new String[]{AppConfig.getString("fileManager")}));

        try
        {
            SqliteSession.init();
        }
        catch (SQLException var3)
        {
            this.logger.error(var3.getMessage());
            System.exit(0);
        }

        this.image = AppConfig.getImageIcon("iconSync.png").getImage();
        this.setTitle("File Manager");
        this.setSize(650, 450);
        this.setResizable(false);
        this.setIconImage(this.image);
        this.setDefaultCloseOperation(0);
        Dimension frameSize  = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                if (!(e instanceof FileManagerWindowEvent))
                {
                    FileManager.this.dispatchEvent(new FileManagerWindowEvent(e.getWindow(), 201, !FileManager.this.started));
                }
                else
                {
                    FileManagerWindowEvent event = (FileManagerWindowEvent) e;
                    if (event.isExit())
                    {
                        if (JOptionPane.showConfirmDialog(FileManager.this.getRootPane(), AppConfig.getString("confirm.program.exit"), AppConfig.getString("exit"), 2, 3) == 0)
                        {
                            FileManager.this.shutdown();
                            System.exit(0);
                        }
                    }
                    else
                    {
                        FileManager.this.setVisible(false);
                    }
                }
            }
        });
        this.add(new Login(this));
    }

    public void run()
    {
        try
        {
            /**
             * <pre>
             *     중복 실행 막기위해 특정 포트 사용 유무 체크
             * </pre>
             */
            this.serverSocket = new ServerSocket(18888);
        }
        catch (Exception var2)
        {
            this.logger.warn(AppConfig.getString("message.program.running"));
            JOptionPane.showMessageDialog(this, AppConfig.getString("message.program.running"), AppConfig.getString("execution.error"), 0);
            this.shutdown();
            System.exit(0);
        }

        this.init();
        this.setVisible(true);
    }

    public void start(UserSession session) throws Exception
    {
        this.logger.info(StringUtil.replaceStr(AppConfig.getString("log.start"), new String[]{AppConfig.getString("fileManager")}));
        FileManager.session = session;
        if (SystemTray.isSupported())
        {
            this.tray = new FileManagerTray(this, this.image, AppConfig.getString("fileManager"));
            JSONObject functionsObj = LoginHttpUtil.getFunctions(session);
            ExceptionUtil.ifResponseErrorThrow(functionsObj);
            JSONArray functions = (JSONArray) functionsObj.get("data");

            if (functions.contains(this.TITLE_PRINT_MANAGER))
            {
                this.tray.addMenuItem(this.TITLE_PRINT_MANAGER, (e) -> {
                    if (!this.menuFrameMap.containsKey(this.TITLE_PRINT_MANAGER))
                    {
                        this.menuFrameMap.put(this.TITLE_PRINT_MANAGER, new PrintManager(this, this.TITLE_PRINT_MANAGER));
                    }
                    else
                    {
                        this.menuFrameMap.get(this.TITLE_PRINT_MANAGER).setVisible(true);
                    }
                });
            }

            if (functions.contains(this.TITLE_DOCUMENT_MANAGER))
            {
                this.tray.addMenuItem(this.TITLE_DOCUMENT_MANAGER, (e) -> {
                    if (!this.menuFrameMap.containsKey(this.TITLE_DOCUMENT_MANAGER))
                    {
                        this.menuFrameMap.put(this.TITLE_DOCUMENT_MANAGER, new DocumentManager(this, this.TITLE_DOCUMENT_MANAGER));
                    }
                    else
                    {
                        this.menuFrameMap.get(this.TITLE_DOCUMENT_MANAGER).setVisible(true);
                    }
                });
            }

            if (functions.contains(this.TITLE_PUBLISH_MANAGER))
            {
                this.tray.addMenuItem(this.TITLE_PUBLISH_MANAGER, (e) -> {
                    if (!this.menuFrameMap.containsKey(this.TITLE_PUBLISH_MANAGER))
                    {
                        this.menuFrameMap.put(this.TITLE_PUBLISH_MANAGER, new PublishManager(this, this.TITLE_PUBLISH_MANAGER));
                    }
                    else
                    {
                        this.menuFrameMap.get(this.TITLE_PUBLISH_MANAGER).setVisible(true);
                    }
                });
            }


            this.tray.addMenuItem(this.TITLE_SECURITY_CONTEXT, (e) -> {
                this.setVisible(true);
            });

            this.tray.addMenuItem(this.TITLE_RECONNECTION, (e) -> {
                try
                {
                    JSONObject result = LoginHttpUtil.healthCheck(this.session);
                    if(result.containsKey("error"))
                    {
                        UserSession newSession = LoginHttpUtil.login(this.session.getId(), this.session.getPassword());
                        this.session.setCookieStore(newSession.getCookieStore());
                        JOptionPane.showMessageDialog(this, AppConfig.getString("message.session.reconnection"), AppConfig.getString("login"), 1);
                    }
                    else {
                        JOptionPane.showMessageDialog(this, AppConfig.getString("message.session.noreconnection"), AppConfig.getString("login"), 0);
                    }
                }
                catch (Exception ex)
                {
                    throw new RuntimeException(ex);
                }
            });

            this.tray.addMenuItem("Exit", (e) -> {
                this.dispatchEvent(new FileManagerWindowEvent(this, 201, true));
            });
        }

        this.setVisible(false);
        this.started = true;
    }

    public boolean isStarted()
    {
        return this.started;
    }

    public void shutdown()
    {
        (new Thread(() -> {
            executors.shutdown();

            if (session != null)
                LoginHttpUtil.invalidateSession(session);

            if (this.serverSocket != null)
            {
                try
                {
                    this.serverSocket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            this.logger.warn(AppConfig.getString("message.program.exit"));
        })).start();
    }

    public static UserSession getSession()
    {
        return session;
    }

    public static void setSession(UserSession session)
    {
        FileManager.session = session;
    }

    /**
     * <pre>
     *     File Manager main 메소드
     * </pre>
     *
     * @param args
     * @throws UnsupportedLookAndFeelException
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException
    {
        UIManager.setLookAndFeel(new FlatIntelliJLaf());
        (new FileManager()).run();
    }
}
