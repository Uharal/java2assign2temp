package cn.edu.sustech.cs209.chatting.client.UI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Register extends Application {

    @Override
    public void start(Stage primaryStage) {
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

        Button registerButton = new Button("Register");
        grid.add(registerButton, 1, 4);

        // 处理注册按钮的点击事件
        registerButton.setOnAction(event -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            String confirmPassword = confirmPasswordTextField.getText();
            if (!password.equals(confirmPassword)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Passwords do not match!");
                alert.showAndWait();
            } else {
                // 在此处添加注册功能的代码
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Registration successful!");
                alert.showAndWait();
            }
        });

        Scene scene = new Scene(grid, 400, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
