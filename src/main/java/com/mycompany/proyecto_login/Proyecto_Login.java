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
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );
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

        Scene scene = new Scene(root, 900, 600);
        aplicarCss(scene);

        // Que el card se haga un poco más ancho con la ventana (sin deformarse)
        scene.widthProperty().addListener((obs, oldW, newW) -> {
            double ancho = newW.doubleValue() * 0.5; // 50% del ancho
            if (ancho < 360) ancho = 360;
            if (ancho > 600) ancho = 600;
            card.setMaxWidth(ancho);
        });

        // LÓGICA DE LOGIN
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
                mostrarPantallaPrincipal(stage, u);
            }
        });

        // Ir a registro
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
        card.setMaxWidth(480);

        StackPane root = new StackPane(card);
        root.getStyleClass().add("root");
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 900, 650);
        aplicarCss(scene);

        scene.widthProperty().addListener((obs, oldW, newW) -> {
            double ancho = newW.doubleValue() * 0.55;
            if (ancho < 380) ancho = 380;
            if (ancho > 680) ancho = 680;
            card.setMaxWidth(ancho);
        });

        // LÓGICA DE REGISTRO
        btnGuardar.setOnAction(e -> {
            String user = txtUsuario.getText().trim();
            String pass = txtPass.getText().trim();
            String conf = txtConf.getText().trim();
            Rol rol = cbRol.getValue();

            // Validaciones
            if (user.isEmpty() || pass.isEmpty() || conf.isEmpty()) {
                lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
                lblMensaje.setText("Todos los campos son obligatorios.");
                return;
            }

            if (user.length() < 3) {
                lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
                lblMensaje.setText("El usuario debe tener al menos 3 caracteres.");
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

            if (rol == null) {
                lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
                lblMensaje.setText("Debes seleccionar un rol.");
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

        // Volver
        btnVolver.setOnAction(e -> mostrarPantallaLogin(stage));

        stage.setScene(scene);
    }

    // -------------------------------------------------------------------------
    // PANTALLA PRINCIPAL (después de login)
    // -------------------------------------------------------------------------
    private void mostrarPantallaPrincipal(Stage stage, Usuario usuario) {

          Image img = new Image(getClass().getResourceAsStream("/imgs/logonavbar.png")); 
ImageView imgView = new ImageView(img);

imgView.setFitWidth(200);
imgView.setPreserveRatio(true);
imgView.setSmooth(true);

        Label lblBien = new Label(
                "Bienvenido, " + usuario.getUsuario() + " (" + usuario.getRol() + ")"
        );
        lblBien.getStyleClass().add("subtitulo");

        Label lblInfo = new Label(textoSegunRol(usuario.getRol()));
        lblInfo.getStyleClass().add("descripcion");
        lblInfo.setWrapText(true);

        Button btnCerrar = new Button("Cerrar sesión");
        btnCerrar.getStyleClass().add("button-secondary");

        VBox card = new VBox(20, imgView, lblBien, lblInfo, btnCerrar);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(40));
        card.setMaxWidth(520);

        StackPane root = new StackPane(card);
        root.getStyleClass().add("root");
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 900, 600);
        aplicarCss(scene);

        btnCerrar.setOnAction(e -> mostrarPantallaLogin(stage));

        stage.setScene(scene);
    }

    private String textoSegunRol(Rol rol) {
        return switch (rol) {
            case ADMIN   -> "Rol ADMIN: gestión de usuarios, cajas, reportes y configuración general del sistema.";
            case CAJA    -> "Rol CAJA: facturación, cobros, cierre de caja y control de ventas.";
            case OPERADOR-> "Rol OPERADOR: operaciones diarias del restaurante (pedidos, atención, etc.).";
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
