package cn.edu.sustech.cs209.chatting.client.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Server extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // 创建标题为 "Starting Server" 的场景
        primaryStage.setTitle("Starting Server");

        // 创建一个水平的 Box，包含 "Port" 文本、"Close" 按钮、"Send All" 文本、可输入文本框和 "Send" 按钮
        HBox hbox = new HBox();
        Label portLabel = new Label("Port");
        Button closeButton = new Button("Close");
        Label sendAllLabel = new Label("Send All");
        TextField textField = new TextField();
        Button sendButton = new Button("Send");
        hbox.getChildren().addAll(portLabel, closeButton, sendAllLabel, textField, sendButton);

        // 创建两个导航按钮
        Button button1 = new Button("Table 1");
        Button button2 = new Button("Table 2");

        // 创建表格
        TableView table1 = new TableView();
        table1.setEditable(true);
        TableColumn column1 = new TableColumn("Column 1");
        TableColumn column2 = new TableColumn("Column 2");
        TableColumn column3 = new TableColumn("Column 3");
        table1.getColumns().addAll(column1, column2, column3);

        TableView table2 = new TableView();
        table2.setEditable(true);
        TableColumn column4 = new TableColumn("Column 1");
        TableColumn column5 = new TableColumn("Column 2");
        TableColumn column6 = new TableColumn("Column 3");
        table2.getColumns().addAll(column4, column5, column6);

        // 创建一个垂直的 Box，包含水平 Box 和两个表格
        VBox vbox = new VBox();
        vbox.getChildren().addAll(hbox, table1, table2);

        // 把垂直 Box 放在场景的中心
        root.setCenter(vbox);

        // 把导航按钮放在场景的底部
        root.setBottom(new HBox(button1, button2));

        // 设置场景大小并展示
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
