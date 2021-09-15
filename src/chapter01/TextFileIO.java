package chapter01;
import javafx.stage.FileChooser;
import java.io.*;
import java.util.Scanner;
public class TextFileIO {
    private PrintWriter pw;
    private Scanner sc;
    public TextFileIO(){

    }
    //内容添加到文件中，文件通过对话框来确定
    public void append(String msg){
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showSaveDialog(null);
        //用户放弃操作则返回
        if(file==null) return;
    //以追加模式utf-8的编码模式写到文件中
        try {
            pw=new PrintWriter( new OutputStreamWriter( new FileOutputStream(file,true),"utf-8"));
            pw.println(msg);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            pw.close();
        }
    }
    public String load() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        //用户放弃操作则返回
        if(file == null) return  null;
        StringBuilder sb = new StringBuilder();
        try{
            //读和写的编码要注意保持一致
            sc = new Scanner(file,"utf-8");
            while (sc.hasNext()) {
                //补上行读取的行末尾回车
                sb.append(sc.nextLine()+'\n');
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            sc.close();
        }
        return sb.toString();
    }
}
