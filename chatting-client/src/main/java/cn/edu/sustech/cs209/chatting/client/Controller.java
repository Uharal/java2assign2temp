package cn.edu.sustech.cs209.chatting.client;

import client.ClientMain;
import cn.edu.sustech.cs209.chatting.common.Message;
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
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import server.MainServer;

import javax.swing.*;
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
    public void createClient() throws IOException {
        ClientMain.use();
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
        final JFrame frame = new JFrame();
        JButton button = new JButton("打开");
        frame.setBounds(300, 200, 500, 500);
        frame.getContentPane().add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFrameShaker dialog1 = new JFrameShaker(frame);
                dialog1.startShake();
            }
        });
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
