package cn.edu.sustech.cs209.chatting.client;

import client.ClientMain;
import client.ui.RegisterFrame;
import client.util.ClientUtil;
import cn.edu.sustech.cs209.chatting.client.UI.Register;
import cn.edu.sustech.cs209.chatting.common.Message;
import common.model.entity.Request;
import common.model.entity.Response;
import common.model.entity.ResponseStatus;
import common.model.entity.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import server.MainServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Controller implements Initializable {

    @FXML
    ListView<Message> chatContentList;

    String username;

    HashSet<String> loggedUsers = new HashSet<>();

    @FXML
    ListView<String> chatList;
    @FXML
    private TextArea inputArea;

// ...

    public void setTextInInputArea(String text) {
        inputArea.setText(text);
    }



    /*
    * 初始化方法，在应用程序启动时调用，用于创建登录对话框并让用户输入他们的用户名。
    * 当用户输入了有效的用户名后，该方法设置了实例变量username并创建了一个MessageCellFactory来设置聊天内容ListView的单元格样式。
    * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Dialog<String> dialog = new TextInputDialog();
        dialog.setTitle("Welcome");
        dialog.setHeaderText(null);
        dialog.setContentText("请输入任意字符开始\n");

        Optional<String> input = dialog.showAndWait();

        if (input.isPresent() && !input.get().isEmpty()) {
            /*
               TODO: Check if there is a user with the same name among the currently logged-in users,
                     if so, ask the user to change the username
             */

            String name = username;
            while (loggedUsers.contains(name)) {
                dialog.setContentText("Username " + name + " is already taken. Please choose another username:");
                input = dialog.showAndWait();
                if (!input.isPresent() || input.get().isEmpty()) {
                    System.out.println("Invalid username " + input + ", exiting");
                    Platform.exit();
                }
                name = input.get();
            }
            username = name;
            loggedUsers.add(username);
        } else {

            System.out.println("Invalid username " + input + ", exiting");
            Platform.exit();
        }

        chatList.getItems().add("你的FXML文件是一个XML格式的文本文件，" +
                "\n用于定义JavaFX用户界面的布局、控件及其属性。" +
                "\n它的根元素是一个VBox控件，其中包含一个MenuBar、" +
                "一个SplitPane和一个HBox。\n" +
                "\n" +
                "MenuBar控件定义了应用程序的菜单栏，其中有三个菜单，" +
                "\n分别为“Client”、“Server”和“Help”。" +
                "\n每个菜单下面有几个菜单项，对应着应用程序的不同功能，" +
                "如登录、注册、创建聊天室等。\n" +
                "\n" +
                "SplitPane控件被用来创建一个水平分割面板，其中左侧是一个ListView控件，" +
                "\n用于显示聊天室列表。" +
                "\n右侧是一个垂直分割面板，其中上半部分是一个ListView控件，用于显示聊天内容。" +
                "\n下半部分是一个HBox控件，其中包含一个TextArea和一个Button控件，用于输入和发送聊天内容。\n" +
                "\n" +
                "HBox控件是一个水平布局的控件，用于显示当前登录用户的信息和在线人数。" +
                "\n其中包含一个Label控件用于显示当前用户的用户名，一个Pane控件用于占位，" +
                "\n和一个Label控件用于显示当前在线人数。\n" +
                "\n" +
                "除了根元素之外，FXML文件中还使用了许多其他JavaFX控件，" +
                "\n如Label、ListView、SplitPane、TextArea、Button等。" +
                "\n每个控件都有自己的属性，如fx:id、prefHeight、prefWidth、onAction等，" +
                "\n可以用来设置控件的各种属性和事件监听器。" +
                "\n控件还可以嵌套使用，形成复杂的界面布局。");

        inputArea.setText("输入无效，请先查看Help");
        chatContentList.setCellFactory(new MessageCellFactory());
    }

    @FXML
    public void Continue(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText("未完待续\n" +
                "Contact\n  " +
                "QQ:2290477984");
        alert.showAndWait();
    }
    @FXML
    public void Readme() throws IOException {
        Stage stage = new Stage();
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 20, 20, 20));
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setText("这里是总控制台的使用说明。\n" +
                "这是一个用javafx + socket实现的一个聊天室\n" +
                "你可以通过点击页面上方导航栏的client打开客户端窗口\n" +
                "通过点击页面上方导航栏的server打开服务器窗口\n" +
                "你可以通过点击说明窗口左侧的导航栏进入具体的c-s的功能说明");

//        content
        Accordion accordion = new Accordion();

        TitledPane pane1 = new TitledPane();
        pane1.setText("Client");
        Text text1 = new Text("You should open server first.\n" +
                "");
        pane1.setContent(text1);

        TitledPane pane2 = new TitledPane();
        pane2.setText("Server");
        Text text2 = new Text("Explanation for Server");
        pane2.setContent(text2);

        TitledPane pane3 = new TitledPane();
        pane3.setText("Other");
        Text text3 = new Text("Continue.....");
        pane3.setContent(text3);

        accordion.getPanes().addAll(pane1, pane2,pane3);

        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 20, 20, 20));
        box.getChildren().addAll(accordion, textArea);

        stage.setScene(new Scene(box));
        stage.showAndWait();

    }
    @FXML
    public void TestRegister() throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Register Form");

        // 创建表单布局
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // 创建表单控件
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 1);
        TextField usernameTextField = new TextField();
        grid.add(usernameTextField, 1, 1);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordTextField = new PasswordField();
        grid.add(passwordTextField, 1, 2);

        Label confirmPasswordLabel = new Label("Confirm Password:");
        grid.add(confirmPasswordLabel, 0, 3);
        PasswordField confirmPasswordTextField = new PasswordField();
        grid.add(confirmPasswordTextField, 1, 3);

        // 添加“性别”标签和选择器
        final String[] genders = {"Male", "Female", "Other"};

        Label genderLabel = new Label("Gender:");
        GridPane.setConstraints(genderLabel, 0, 4);
        ChoiceBox<String> genderChoiceBox = new ChoiceBox<>();
        genderChoiceBox.getItems().addAll(genders);
        genderChoiceBox.setValue(genders[0]);
        GridPane.setConstraints(genderChoiceBox, 1, 4);

        Button registerButton = new Button("Register");
        grid.add(registerButton, 1, 5);


        // 处理注册按钮的点击事件
        registerButton.setOnAction(event -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            String confirmPassword = confirmPasswordTextField.getText();
            String gender = genderChoiceBox.getValue();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all the fields.");
                alert.showAndWait();
            } else if (!password.equals(confirmPassword)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Passwords do not match.");
                alert.showAndWait();
            } else {
                // 如果前端正确注册，进行以下操作：
                System.out.println(username);
                System.out.println(password);
                System.out.println(confirmPassword);
                System.out.println(gender);

                User user = new User(password,username, 'm', 0);
                System.out.println("user: "+user.getPassword());

                try {
                    Controller.this.register(user);
                } catch (IOException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                ((Stage)primaryStage.getScene().getWindow()).close();
            }
        });

        Scene scene = new Scene(grid, 400, 275);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    @FXML
    public void TestServer() throws IOException {
        Stage primaryStage = new Stage();
        BorderPane root = new BorderPane();

        // 创建标题为 "Starting Server" 的场景
        primaryStage.setTitle("Starting Server");

        // 创建一个水平的 Box，包含 "Port" 文本、"Close" 按钮、"Send All" 文本、可输入文本框和 "Send" 按钮
        HBox hbox = new HBox();
        hbox.setSpacing(10);
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
    @FXML
    public void createClient() throws IOException {
        ClientMain.use();

    }


    @FXML
    public void registerClient(){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Register Form");

        // 创建表单布局
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // 创建表单控件
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 1);
        TextField usernameTextField = new TextField();
        grid.add(usernameTextField, 1, 1);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordTextField = new PasswordField();
        grid.add(passwordTextField, 1, 2);

        Label confirmPasswordLabel = new Label("Confirm Password:");
        grid.add(confirmPasswordLabel, 0, 3);
        PasswordField confirmPasswordTextField = new PasswordField();
        grid.add(confirmPasswordTextField, 1, 3);

        // 添加“性别”标签和选择器
        final String[] genders = {"Male", "Female", "Other"};

        Label genderLabel = new Label("Gender:");
        GridPane.setConstraints(genderLabel, 0, 4);
        ChoiceBox<String> genderChoiceBox = new ChoiceBox<>();
        genderChoiceBox.getItems().addAll(genders);
        genderChoiceBox.setValue(genders[0]);
        GridPane.setConstraints(genderChoiceBox, 1, 4);

        Button registerButton = new Button("Register");
        grid.add(registerButton, 1, 5);

        // 处理注册按钮的点击事件
        registerButton.setOnAction(event -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            String confirmPassword = confirmPasswordTextField.getText();
            String gender = genderChoiceBox.getValue();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all the fields.");
                alert.showAndWait();
            } else if (!password.equals(confirmPassword)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Passwords do not match.");
                alert.showAndWait();
            } else {
                // 如果前端正确注册，进行以下操作：
                System.out.println(username);
                System.out.println(password);
                System.out.println(confirmPassword);
                System.out.println(gender);

                User user = new User(password,username, 'm', 0);
                System.out.println("user: "+user.getPassword());

                try {
                    Controller.this.register(user);
                } catch (IOException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                ((Stage)primaryStage.getScene().getWindow()).close();
            }
        });

        Scene scene = new Scene(grid, 400, 275);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    //注册方法
    private void register(User user) throws IOException, ClassNotFoundException{
        Request request = new Request();
        request.setAction("userRegister");
        request.setAttribute("user", user);

        System.out.println(request);
        //获取响应
        Response response = ClientUtil.sendTextRequest(request);

        ResponseStatus status = response.getStatus();

        if (status == ResponseStatus.OK) {
            User user2 = (User) response.getData("user");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("您的Account为："+ username);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Register Error.");
            alert.showAndWait();
        }
    }
    @FXML
    public void createServer() throws IOException {
        MainServer.useser();
    }
    @FXML
    public void createPrivateChat() throws IOException {
        AtomicReference<String> user = new AtomicReference<>();

        Stage stage = new Stage();
        ComboBox<String> userSel = new ComboBox<>();

        // FIXME: get the user list from server, the current user's name should be filtered out
        userSel.getItems().addAll("Item 1", "Item 2", "Item 3");

        Button okBtn = new Button("OK");
        okBtn.setOnAction(e -> {
            user.set(userSel.getSelectionModel().getSelectedItem());
            stage.close();
        });

        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 20, 20, 20));
        box.getChildren().addAll(userSel, okBtn);
        stage.setScene(new Scene(box));
        stage.showAndWait();

        // TODO: if the current user already chatted with the selected user, just open the chat with that user
        // TODO: otherwise, create a new chat item in the left panel, the title should be the selected user's name
        // 如果当前用户已经与所选用户聊过天，则打开该用户的聊天
        for (Message message : chatContentList.getItems()) {
            System.out.println("here");
            if (message.getSentBy().equals(user.get())) {
                // TODO: 打开该用户的聊天


            }
        }


    }

    /**
     * A new dialog should contain a multi-select list, showing all user's name.
     * You can select several users that will be joined in the group chat, including yourself.
     * <p>
     * The naming rule for group chats is similar to WeChat:
     * If there are > 3 users: display the first three usernames, sorted in lexicographic order, then use ellipsis with the number of users, for example:
     * UserA, UserB, UserC... (10)
     * If there are <= 3 users: do not display the ellipsis, for example:
     * UserA, UserB (2)
     */
    @FXML
    public void createGroupChat() {

    }

    /**
     * Sends the message to the <b>currently selected</b> chat.
     * <p>
     * Blank messages are not allowed.
     * After sending the message, you should clear the text input field.
     */
    @FXML
    public void doSendMessage() {
        // TODO
    }

    /**
     * You may change the cell factory if you changed the design of {@code Message} model.
     * Hint: you may also define a cell factory for the chats displayed in the left panel, or simply override the toString method.
     */
    private class MessageCellFactory implements Callback<ListView<Message>, ListCell<Message>> {
        @Override
        public ListCell<Message> call(ListView<Message> param) {
            return new ListCell<Message>() {

                @Override
                public void updateItem(Message msg, boolean empty) {
                    super.updateItem(msg, empty);
                    if (empty || Objects.isNull(msg)) {
                        setText(null);
                        setGraphic(null);
                        return;
                    }

                    HBox wrapper = new HBox();
                    Label nameLabel = new Label(msg.getSentBy());
                    Label msgLabel = new Label(msg.getData());

                    nameLabel.setPrefSize(50, 20);
                    nameLabel.setWrapText(true);
                    nameLabel.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

                    if (username.equals(msg.getSentBy())) {
                        wrapper.setAlignment(Pos.TOP_RIGHT);
                        wrapper.getChildren().addAll(msgLabel, nameLabel);
                        msgLabel.setPadding(new Insets(0, 20, 0, 0));
                    } else {
                        wrapper.setAlignment(Pos.TOP_LEFT);
                        wrapper.getChildren().addAll(nameLabel, msgLabel);
                        msgLabel.setPadding(new Insets(0, 0, 0, 20));
                    }

                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(wrapper);
                }
            };
        }
    }

}
