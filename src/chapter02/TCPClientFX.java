package chapter02;
import chapter01.TextFileIO;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import java.time.LocalDateTime;
import java.io.File;
public class TCPClientFX extends Application{
    private  Button btnExit = new Button("退出");
    private  Button btnSend = new Button("发送");
    private  Button btnConnect = new Button("连接");
    //待发送信息的文本框
    private  TextField tfSend = new TextField();
    private  TextField tfIP = new TextField();
    private  TextField tfPort = new TextField();
    //显示信息的文本区域
    private  TextArea taDisplay = new TextArea();
    private TextFileIO textFileIO = new TextFileIO();
    private TCPClient tcpClient;

    public void start(Stage primaryStage) {
        BorderPane mainPane = new BorderPane();
        //内容显示区域
        VBox vBox = new VBox();
        vBox.setSpacing(10);//各控件之间的间隔
        //VBox面板中的内容距离四周的留空区域
        vBox.setPadding(new Insets(10, 20, 10, 20));
        vBox.getChildren().addAll(new Label("信息显示区:"),taDisplay,new Label("信息输入区:"),tfSend);
        //设置显示信息区的文本区域可以纵向自动扩充范围
        VBox.setVgrow(taDisplay,Priority.ALWAYS);
        mainPane.setCenter(vBox);
        //底部按钮区域
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10, 20, 10, 20));
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.getChildren().addAll(btnSend,btnExit);
        mainPane.setBottom(hBox);
        HBox topBox = new HBox();
        topBox.setSpacing(10);
        topBox.setPadding(new Insets(10, 20, 10, 20));
        topBox.setAlignment(Pos.CENTER);
        mainPane.setTop(topBox);
        topBox.getChildren().addAll(new Label("ip地址："),tfIP,new Label("端口："),tfPort,btnConnect);
        Scene scene = new Scene(mainPane, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        btnExit.setOnAction(event->{System.exit(0);});
        btnSend.setOnAction(event->{
            String msg= tfSend.getText();
            taDisplay.appendText(msg+'\n');
            tfSend.clear();
        });
        btnConnect.setOnAction(event -> {
            String ip = tfIP.getText().trim();
            String port = tfPort.getText().trim();

            try {
                //tcpClient不是局部变量，是本程序定义的一个TCPClient类型的成员变量
                tcpClient = new TCPClient(ip,port);
                //成功连接服务器，接收服务器发来的第一条欢迎信息
                String firstMsg = tcpClient.receive();
                taDisplay.appendText(firstMsg + "\n");
            } catch (Exception e) {
                taDisplay.appendText("服务器连接失败！" + e.getMessage() + "\n");
            }
        });
        btnExit.setOnAction(event -> {
            if(tcpClient != null){
                //向服务器发送关闭连接的约定信息
                tcpClient.send("bye");
                tcpClient.close();
            }
            System.exit(0);
        });
        btnSend.setOnAction(event -> {
            String sendMsg = tfSend.getText();
            tcpClient.send(sendMsg);//向服务器发送一串字符
            taDisplay.appendText("客户端发送：" + sendMsg + "\n");
            String receiveMsg = tcpClient.receive();//从服务器接收一行字符
            taDisplay.appendText(receiveMsg + "\n");
        });
    }
}
