package chapter01;

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

public class SimpleFX extends Application {
    private  Button btnExit = new Button("退出");
    private  Button btnSend = new Button("发送");
    private  Button btnOpen = new Button("加载");
    private  Button btnSave = new Button("保存");
    //待发送信息的文本框
    private  TextField tfSend = new TextField();
    //显示信息的文本区域
    private  TextArea taDisplay = new TextArea();
    private  TextFileIO textFileIO = new TextFileIO();

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
        hBox.getChildren().addAll(btnSend, btnSave, btnOpen, btnExit);
        mainPane.setBottom(hBox);
        Scene scene = new Scene(mainPane, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        btnExit.setOnAction(event->{System.exit(0);});
        btnSend.setOnAction(event->{
            String msg= tfSend.getText();
            taDisplay.appendText(msg+'\n');
            tfSend.clear();
        });
        btnSave.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(null);
            textFileIO.append(
                    LocalDateTime.now().withNano(0)+" "+taDisplay.getText()
            );
        });
        btnOpen.setOnAction(event -> {
            String msg = textFileIO.load();
            if(msg != null){
                taDisplay.clear();
                taDisplay.setText("msg");
            }
        });
    }
}
