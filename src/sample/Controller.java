package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.SecretKey;
import java.io.File;
import java.util.List;

public class Controller {
    private Stage stage;
    private String keypath="";
    @FXML
    private Button loadButton;
    @FXML
    private Button buildButton;
    @FXML
    private Button decodeButton;
    @FXML
    private Button encodeButton;

    @FXML
    private TextArea miwen;
    @FXML
    private TextArea mingwen;
    @FXML
    private TextField mima;
    @FXML
    private TextField keyPath;
    public void init(Stage stage)
    {
        this.stage=stage;
    }
    @FXML
    protected void onMouseClisk(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            System.out.println("双击了条目");
            FileChooser fileChooser=new FileChooser();
            fileChooser.setTitle("select key file");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("any name", "*.*")
            );
            File file=
                    fileChooser.showOpenDialog(stage);
            if(file!=null)
            {
                keypath=file.getAbsolutePath();
                keyPath.setText(keypath);
            }
        }
    }
    @FXML
    protected void onLoad(ActionEvent event) {
            System.out.println("load");
            keypath=keyPath.getText();
            File file=new File(keypath);
            if(!file.exists())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("AESTool");
                alert.setHeaderText("警告");
                alert.setContentText("文件不存在");
                alert.showAndWait();
                return;
            }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AESTool");
        alert.setHeaderText("成功");
        alert.setContentText("密钥文件加载成功");
        alert.showAndWait();
    }
    @FXML
    protected void onBuild(ActionEvent event) {
        System.out.println("build");
        keypath=keyPath.getText();
        String mi=mima.getText();
        Boolean b=AESUtil.buildKeyFile(mi,keypath);
        if(b)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("AESTool");
            alert.setHeaderText("成功");
            alert.setContentText("密钥文件成功生成:"+keypath);
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("AESTool");
            alert.setHeaderText("错误");
            alert.setContentText("密钥文件生成失败");
            alert.showAndWait();
        }
    }
    @FXML
    protected void onEncode(ActionEvent event) {
        System.out.println("encode");
        miwen.setWrapText(true);
        mingwen.setWrapText(true);
        keypath=keyPath.getText();
        File file=new File(keypath);
        if(!file.exists())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("AESTool");
            alert.setHeaderText("警告");
            alert.setContentText("密钥文件不存在");
            alert.showAndWait();
            return;
        }
        miwen.setText(AESUtil.encode(mingwen.getText(),keypath));
    }
    @FXML
    protected void onDecode(ActionEvent event) {
        System.out.println("decode");
        miwen.setWrapText(true);
        mingwen.setWrapText(true);
        keypath=keyPath.getText();
        File file=new File(keypath);
        if(!file.exists())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("AESTool");
            alert.setHeaderText("警告");
            alert.setContentText("密钥文件不存在");
            alert.showAndWait();
            return;
        }
        mingwen.setText(AESUtil.decode(miwen.getText(),keypath));
    }
}
