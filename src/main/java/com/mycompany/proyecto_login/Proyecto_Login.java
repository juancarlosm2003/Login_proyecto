package com.mycompany.proyecto_login;

import static com.mycompany.proyecto_login.Rol.ADMIN;
import static com.mycompany.proyecto_login.Rol.CAJA;
import static com.mycompany.proyecto_login.Rol.OPERADOR;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Proyecto_Login extends Application {

    private UserStore userStore = new UserStore();

    @Override
    public void start(Stage stage) {
        stage.setTitle("La Oficina - Sistema");
        mostrarPantallaLogin(stage);
        stage.show();
    }

    private void aplicarCss(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    }

    // -------------------------------------------------------------------------
    // LOGIN
    // -------------------------------------------------------------------------
 private void mostrarPantallaLogin(Stage stage) {
    Image img = new Image(getClass().getResourceAsStream("/imgs/logonavbar.png"));
    ImageView imgView = new ImageView(img);
    imgView.setFitWidth(200);
    imgView.setPreserveRatio(true);
    imgView.setSmooth(true);

    Label lblSub = new Label("Iniciar sesión");
    lblSub.getStyleClass().add("subtitulo");

    Label lblDesc = new Label("Accede al panel del restaurante");
    lblDesc.getStyleClass().add("descripcion");

    Label lblUsuario = new Label("Usuario");
    lblUsuario.getStyleClass().add("label");

    TextField txtUsuario = new TextField();
    txtUsuario.setPromptText("Ingresa tu usuario");
    txtUsuario.getStyleClass().add("input");

    Label lblPass = new Label("Contraseña");
    lblPass.getStyleClass().add("label");

    PasswordField txtPass = new PasswordField();
    txtPass.setPromptText("Ingresa tu contraseña");
    txtPass.getStyleClass().add("input");

    Label lblMensaje = new Label();
    lblMensaje.getStyleClass().add("mensaje");

    Button btnLogin = new Button("Ingresar");
    btnLogin.getStyleClass().add("button-primary");

    Button btnCrear = new Button("Crear usuario");
    btnCrear.getStyleClass().add("button-secondary");

    HBox contBotones = new HBox(10, btnLogin, btnCrear);
    contBotones.setAlignment(Pos.CENTER_RIGHT);

    VBox card = new VBox(15,
            imgView,
            lblSub,
            lblDesc,
            lblUsuario,
            txtUsuario,
            lblPass,
            txtPass,
            lblMensaje,
            contBotones
    );
    card.getStyleClass().add("card");
    card.setPadding(new Insets(40));
    card.setMaxWidth(420);

    StackPane root = new StackPane(card);
    root.getStyleClass().add("root");
    StackPane.setAlignment(card, Pos.CENTER);
    root.setPadding(new Insets(30));

    Scene scene = new Scene(root, 900, 600);  // tamaño inicial
    aplicarCss(scene);

    // Listener para que el card se redimensione pero siempre centrado
    scene.widthProperty().addListener((obs, oldW, newW) -> {
        double ancho = newW.doubleValue() * 0.5;
        if (ancho < 360) ancho = 360;
        if (ancho > 600) ancho = 600;
        card.setMaxWidth(ancho);
    });

    // Cada vez que mostramos login, aseguramos tamaño no maximizado
    stage.setMaximized(false);
    stage.setWidth(900);
    stage.setHeight(600);
    stage.centerOnScreen();  // <<-- esto es clave para que quede centrado

    btnLogin.setOnAction(e -> {
        String user = txtUsuario.getText().trim();
        String pass = txtPass.getText().trim();
        if (user.isEmpty() || pass.isEmpty()) {
            lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
            lblMensaje.setText("Debes ingresar usuario y contraseña.");
            return;
        }
        Usuario u = userStore.validarLogin(user, pass);
        if (u == null) {
            lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
            lblMensaje.setText("Usuario o contraseña incorrectos.");
        } else {
            lblMensaje.setText("");
            mostrarPantallaRol(stage, u);
        }
    });

    btnCrear.setOnAction(e -> mostrarPantallaRegistro(stage));
    stage.setScene(scene);
}

private void mostrarPantallaRegistro(Stage stage) {
    Image img = new Image(getClass().getResourceAsStream("/imgs/logonavbar.png"));
    ImageView imgView = new ImageView(img);
    imgView.setFitWidth(200);
    imgView.setPreserveRatio(true);
    imgView.setSmooth(true);

    Label lblSub = new Label("Crear usuario");
    lblSub.getStyleClass().add("subtitulo");

    Label lblDesc = new Label("Registra un nuevo usuario para el sistema");
    lblDesc.getStyleClass().add("descripcion");

    Label lblUsuario = new Label("Usuario");
    lblUsuario.getStyleClass().add("label");

    TextField txtUsuario = new TextField();
    txtUsuario.setPromptText("Ejemplo: juan.caja");
    txtUsuario.getStyleClass().add("input");

    Label lblPass = new Label("Contraseña");
    lblPass.getStyleClass().add("label");

    PasswordField txtPass = new PasswordField();
    txtPass.setPromptText("Mínimo 4 caracteres");
    txtPass.getStyleClass().add("input");

    Label lblConf = new Label("Confirmar contraseña");
    lblConf.getStyleClass().add("label");

    PasswordField txtConf = new PasswordField();
    txtConf.setPromptText("Repite la contraseña");
    txtConf.getStyleClass().add("input");

    Label lblRol = new Label("Rol");
    lblRol.getStyleClass().add("label");

    ComboBox<Rol> cbRol = new ComboBox<>();
    cbRol.getItems().addAll(Rol.ADMIN, Rol.CAJA, Rol.OPERADOR);
    cbRol.getSelectionModel().select(Rol.OPERADOR);
    cbRol.getStyleClass().add("combo");

    Label lblMensaje = new Label();
    lblMensaje.getStyleClass().add("mensaje");

    Button btnGuardar = new Button("Guardar");
    btnGuardar.getStyleClass().add("button-primary");

    Button btnVolver = new Button("Volver al login");
    btnVolver.getStyleClass().add("button-secondary");

    HBox contBotones = new HBox(10, btnGuardar, btnVolver);
    contBotones.setAlignment(Pos.CENTER_RIGHT);

    VBox card = new VBox(15,
            imgView,
            lblSub,
            lblDesc,
            lblUsuario,
            txtUsuario,
            lblPass,
            txtPass,
            lblConf,
            txtConf,
            lblRol,
            cbRol,
            lblMensaje,
            contBotones
    );
    card.getStyleClass().add("card");
    card.setPadding(new Insets(40));
    card.setMinWidth(400);
    card.setMaxWidth(Double.MAX_VALUE);

    StackPane root = new StackPane(card);
    root.getStyleClass().add("root");
    StackPane.setAlignment(card, Pos.CENTER);
    root.setPadding(new Insets(30));

    Scene scene = new Scene(root, 1000, 700); // tamaño inicial más grande
    aplicarCss(scene);

    // Listener para ancho del card
    scene.widthProperty().addListener((obs, oldW, newW) -> {
        double ancho = newW.doubleValue() * 0.5;
        if (ancho < 400) ancho = 400;
        if (ancho > 700) ancho = 700;
        card.setMaxWidth(ancho);
    });

    // Listener para alto del card
    scene.heightProperty().addListener((obs, oldH, newH) -> {
        double alto = newH.doubleValue() * 0.8;
        if (alto < 500) alto = 500;
        if (alto > 900) alto = 900;
        card.setMaxHeight(alto);
    });

    stage.setMaximized(false);
    stage.setWidth(1000);
    stage.setHeight(700);
    stage.centerOnScreen();

    btnGuardar.setOnAction(e -> {
        String user = txtUsuario.getText().trim();
        String pass = txtPass.getText().trim();
        String conf = txtConf.getText().trim();
        Rol rol = cbRol.getValue();

        if (user.isEmpty() || pass.isEmpty() || conf.isEmpty()) {
            lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }
        if (pass.length() < 4) {
            lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
            lblMensaje.setText("La contraseña debe tener al menos 4 caracteres.");
            return;
        }
        if (!pass.equals(conf)) {
            lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
            lblMensaje.setText("Las contraseñas no coinciden.");
            return;
        }
        if (userStore.existeUsuario(user)) {
            lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
            lblMensaje.setText("El usuario ya existe. Elige otro.");
            return;
        }
        boolean ok = userStore.crearUsuario(user, pass, rol);
        if (ok) {
            lblMensaje.getStyleClass().setAll("mensaje", "mensaje-ok");
            lblMensaje.setText("Usuario creado correctamente. Ahora puedes iniciar sesión.");
            txtUsuario.clear();
            txtPass.clear();
            txtConf.clear();
            cbRol.getSelectionModel().select(Rol.OPERADOR);
        } else {
            lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
            lblMensaje.setText("No se pudo crear el usuario.");
        }
    });

    btnVolver.setOnAction(e -> mostrarPantallaLogin(stage));
    stage.setScene(scene);
}




private void mostrarPantallaRol(Stage stage, Usuario usuario) {
    switch (usuario.getRol()) {
        case ADMIN -> mostrarAdmin(stage, usuario);
        case CAJA -> mostrarCaja(stage, usuario);
        case OPERADOR -> mostrarOperador(stage, usuario);
    }
}

private void mostrarAdmin(Stage stage, Usuario usuario) {
    Label lbl = new Label("Bienvenido ADMIN");
        lbl.getStyleClass().add("subtitulo");

        Label info = new Label("pepeee");
        info.getStyleClass().add("descripcion");
        info.setWrapText(true);

        Button btnCerrar = new Button("Cerrar sesión");
        btnCerrar.getStyleClass().add("button-secondary");
        btnCerrar.setOnAction(e -> mostrarPantallaLogin(stage));

        VBox content = new VBox(20, lbl, info, btnCerrar);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane(content);
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root);
        aplicarCss(scene);

        stage.setScene(scene);
        stage.setMaximized(true); 
}

private void mostrarCaja(Stage stage, Usuario usuario) {
    Label lbl = new Label("Bienvenido CAJITA");
        lbl.getStyleClass().add("subtitulo");

        Label info = new Label("pepeee");
        info.getStyleClass().add("descripcion");
        info.setWrapText(true);

        Button btnCerrar = new Button("Cerrar sesión");
        btnCerrar.getStyleClass().add("button-secondary");
        btnCerrar.setOnAction(e -> mostrarPantallaLogin(stage));

        VBox content = new VBox(20, lbl, info, btnCerrar);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane(content);
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root);
        aplicarCss(scene);

        stage.setScene(scene);
        stage.setMaximized(true); 
}

private void mostrarOperador(Stage stage, Usuario usuario) {
     Label lbl = new Label("Bienvenido OPERADOE");
        lbl.getStyleClass().add("subtitulo");

        Label info = new Label("pepeee");
        info.getStyleClass().add("descripcion");
        info.setWrapText(true);

        Button btnCerrar = new Button("Cerrar sesión");
        btnCerrar.getStyleClass().add("button-secondary");
        btnCerrar.setOnAction(e -> mostrarPantallaLogin(stage));

        VBox content = new VBox(20, lbl, info, btnCerrar);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane(content);
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root);
        aplicarCss(scene);

        stage.setScene(scene);
        stage.setMaximized(true); 
}

public static void main(String[] args) {
    launch(args);
}

}
