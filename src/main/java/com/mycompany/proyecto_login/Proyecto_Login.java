package com.mycompany.proyecto_login;

import static com.mycompany.proyecto_login.Rol.ADMIN;
import static com.mycompany.proyecto_login.Rol.CAJA;
import static com.mycompany.proyecto_login.Rol.OPERADOR;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.ListChangeListener;



import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;


public class Proyecto_Login extends Application {

    private UserStore userStore = new UserStore();
    private Usuario usuarioActual;

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
        btnLogin.disableProperty().bind(
        txtUsuario.textProperty().isEmpty().or(txtPass.textProperty().isEmpty()));
        Button btnCrear = new Button("Crear usuario");
        btnCrear.getStyleClass().add("button-secondary");

        HBox contBotones = new HBox(10, btnLogin);
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

        scene.widthProperty().addListener((obs, oldW, newW) -> {
            double ancho = newW.doubleValue() * 0.5;
            if (ancho < 360) ancho = 360;
            if (ancho > 600) ancho = 600;
            card.setMaxWidth(ancho);
        });

        stage.setMaximized(false);
        stage.setWidth(900);
        stage.setHeight(600);
        stage.centerOnScreen();

        btnLogin.setOnAction(e -> {
            String user = txtUsuario.getText().trim();
            String pass = txtPass.getText().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
                lblMensaje.setText("Debes ingresar usuario y contraseña.");
                return;
            }
            CasoLogin cas = userStore.validarLogin(user, pass);
            if (cas == null) {
                lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
                lblMensaje.setText("Usuario o contraseña incorrectos.");
            } else if (cas.getNum() == 1) {
                lblMensaje.setText("");
                usuarioActual = cas.getUsuario();
                mostrarPantallaRol(stage, usuarioActual);
            } else if (cas.getNum() == 2) {
                usuarioActual = cas.getUsuario();
                mostrarPantallaContra(stage);
            }
        });

        stage.setScene(scene);
    }

    private void mostrarPantallaContra(Stage stage) {
        Image img = new Image(getClass().getResourceAsStream("/imgs/logonavbar.png"));
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(200);
        imgView.setPreserveRatio(true);
        imgView.setSmooth(true);

        Label lblSub = new Label("Su usuario fue creado recientemente.\nConfigure su contraseña.");
        lblSub.getStyleClass().add("subtitulo");

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

        Label lblMensaje = new Label();
        lblMensaje.getStyleClass().add("mensaje");

        Button btnguardar = new Button("Guardar");
        btnguardar.getStyleClass().add("button-primary");

        Button btnVolver = new Button("Volver al login");
        btnVolver.getStyleClass().add("button-secondary");

        HBox contBotones = new HBox(10, btnguardar, btnVolver);
        contBotones.setAlignment(Pos.CENTER_RIGHT);

        VBox card = new VBox(15,
                imgView,
                lblSub,
                lblPass,
                txtPass,
                lblConf,
                txtConf,
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

        scene.widthProperty().addListener((obs, oldW, newW) -> {
            double ancho = newW.doubleValue() * 0.5;
            if (ancho < 360) ancho = 360;
            if (ancho > 600) ancho = 600;
            card.setMaxWidth(ancho);
        });

        stage.setMaximized(false);
        stage.setWidth(900);
        stage.setHeight(600);
        stage.centerOnScreen();

        btnguardar.setOnAction(e -> {

            String pass = txtPass.getText().trim();
            String conf = txtConf.getText().trim();

            if (pass.isEmpty() || conf.isEmpty()) {
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

            lblMensaje.getStyleClass().setAll("mensaje", "mensaje-ok");
            lblMensaje.setText("Contraseña actualizada correctamente. Ahora puedes iniciar sesión.");

            txtPass.clear();
            txtConf.clear();

            userStore.actualizarPassword(usuarioActual.getUsuario(), pass);
        });

        btnVolver.setOnAction(e -> mostrarPantallaLogin(stage));

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

        Label lblRol = new Label("Rol");
        lblRol.getStyleClass().add("label");

        ComboBox<Rol> cbRol = new ComboBox<>();
        cbRol.getItems().addAll(ADMIN, CAJA, OPERADOR);
        cbRol.getSelectionModel().select(OPERADOR);
        cbRol.getStyleClass().add("combo");

        Label lblMensaje = new Label();
        lblMensaje.getStyleClass().add("mensaje");

        Button btnGuardar = new Button("Guardar");
        btnGuardar.getStyleClass().add("button-primary");

        Button btnVolver = new Button("Volver al panel");
        btnVolver.getStyleClass().add("button-secondary");

        HBox contBotones = new HBox(10, btnGuardar, btnVolver);
        contBotones.setAlignment(Pos.CENTER_RIGHT);

        VBox card = new VBox(15,
                imgView,
                lblSub,
                lblDesc,
                lblUsuario,
                txtUsuario,
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

        Scene scene = new Scene(root, 1000, 700);
        aplicarCss(scene);

        scene.widthProperty().addListener((obs, oldW, newW) -> {
            double ancho = newW.doubleValue() * 0.5;
            if (ancho < 400) ancho = 400;
            if (ancho > 700) ancho = 700;
            card.setMaxWidth(ancho);
        });

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
            Rol rol = cbRol.getValue();

            if (user.isEmpty()) {
                lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
                lblMensaje.setText("Todos los campos son obligatorios.");
                return;
            }
            if (userStore.existeUsuario(user)) {
                lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
                lblMensaje.setText("El usuario ya existe. Elige otro.");
                return;
            }
            String pass = "++++";
            boolean ok = userStore.crearUsuario(user, pass, rol);
            if (ok) {
                lblMensaje.getStyleClass().setAll("mensaje", "mensaje-ok");
                lblMensaje.setText("Usuario creado correctamente.");
                txtUsuario.clear();
                cbRol.getSelectionModel().select(OPERADOR);
            } else {
                lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
                lblMensaje.setText("No se pudo crear el usuario.");
            }
        });

        btnVolver.setOnAction(e -> mostrarPantallaRol(stage, usuarioActual));
        stage.setScene(scene);
    }

    // -------------------------------------------------------------------------
    // SELECCIÓN DE ROL
    // -------------------------------------------------------------------------
    private void mostrarPantallaRol(Stage stage, Usuario usuario) {
        switch (usuario.getRol()) {
            case ADMIN -> mostrarAdmin(stage, usuario);
            case CAJA -> mostrarCaja(stage, usuario);
            case OPERADOR -> mostrarOperador(stage, usuario);
        }
    }

    // -------------------------------------------------------------------------
    // ADMIN
    // -------------------------------------------------------------------------
    private void mostrarAdmin(Stage stage, Usuario usuario) {

        // ==== LOGO ====
        Image logoImg = new Image(getClass().getResourceAsStream("/imgs/logonavbar.png"));
        ImageView logoView = new ImageView(logoImg);
        logoView.setFitWidth(200);
        logoView.setPreserveRatio(true);

        // ==== BOTÓN USUARIO (CON MENÚ) ====
        Label lblUsuario = new Label(usuario.getUsuario());
        lblUsuario.getStyleClass().add("user-label");

        Image userIcon = new Image(getClass().getResourceAsStream("/imgs/user_icon.png"));
        ImageView userIconView = new ImageView(userIcon);
        userIconView.setFitWidth(28);
        userIconView.setPreserveRatio(true);

        HBox userButton = new HBox(8, lblUsuario, userIconView);
        userButton.setAlignment(Pos.CENTER_RIGHT);
        userButton.getStyleClass().add("user-button");
        userButton.setPadding(new Insets(8, 12, 8, 12));

        // ==== MENÚ DEL USUARIO (ContextMenu) ====
        ContextMenu userMenu = new ContextMenu(); 

        // Item: Crear usuario
        Label lblCrear = new Label("Crear usuario");
        lblCrear.getStyleClass().add("user-menu-label");
        HBox crearBox = new HBox(lblCrear);
        crearBox.setAlignment(Pos.CENTER_LEFT);   
        crearBox.getStyleClass().add("user-menu-item");
        CustomMenuItem itemCrear = new CustomMenuItem(crearBox, true);

        // Item: Cerrar sesión
        Label lblCerrar = new Label("Cerrar sesión");
        lblCerrar.getStyleClass().add("user-menu-label");
        HBox cerrarBox = new HBox(lblCerrar);
        cerrarBox.setAlignment(Pos.CENTER_LEFT);  
        cerrarBox.getStyleClass().add("user-menu-item");
        CustomMenuItem itemCerrar = new CustomMenuItem(cerrarBox, true);


        crearBox.setOnMouseClicked(e -> {
            userMenu.hide();
            mostrarPantallaRegistro(stage);
        });

        cerrarBox.setOnMouseClicked(e -> {
            userMenu.hide();
            mostrarPantallaLogin(stage);
        });

        userMenu.getItems().addAll(itemCrear, itemCerrar);

        userButton.setOnMouseClicked(e -> {
            if (!userMenu.isShowing()) {
                userMenu.show(userButton, Side.BOTTOM, 0, 0);
            } else {
                userMenu.hide();
            }
        });

        // ==== TOP BAR ====
        BorderPane topBar = new BorderPane();
        topBar.setLeft(logoView);
        topBar.setRight(userButton);
        topBar.setPadding(new Insets(10, 20, 10, 20));
        topBar.getStyleClass().add("top-bar");

        // ==== NAVBAR (MENÚ) ====
        Button btnprincipal = new Button("Menu Principal");
        Button btncrud = new Button("CRUD");
        Button btninventario = new Button("Inventario");
        Button btnmov = new Button("Movimientos");
        Button btnAbono = new Button("Abonos");
 
        btnprincipal.getStyleClass().addAll("navbar-button", "navbar-button-selected");
        btncrud.getStyleClass().add("navbar-button");
        btninventario.getStyleClass().add("navbar-button");
        btnmov.getStyleClass().add("navbar-button");
        btnAbono.getStyleClass().add("navbar-button");

        HBox navbar = new HBox(20, btnprincipal, btncrud, btninventario, btnmov, btnAbono);
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(12, 20, 12, 20));
        navbar.getStyleClass().add("navbar");

        VBox topContainer = new VBox(topBar, navbar);

        // ==== CONTENEDOR CENTRAL ====
        VBox centerContent = new VBox(20);
        centerContent.setPadding(new Insets(30));

        VBox vistaPrincipal = crearVistaAdminPrincipal(usuario);
        VBox vistaCrud = crearVistaAdminCrud();
        VBox vistaInventario = crearVistaAdminInventario();
        VBox vistaMovimientos = crearVistaAdminMovimientos();
        VBox vistaAbonos = crearVistaAbonosYCompras();
        ScrollPane scrollAbonos = new ScrollPane(vistaAbonos);
        scrollAbonos.setFitToWidth(true);
        scrollAbonos.getStyleClass().add("scroll-pane");
        
        centerContent.getChildren().setAll(vistaPrincipal);

        // Acciones navbar
        btnprincipal.setOnAction(e -> {
            centerContent.getChildren().setAll(vistaPrincipal);
            actualizarSeleccionNavbar(btnprincipal, btnprincipal, btncrud, btninventario, btnmov, btnAbono);
        });

        btncrud.setOnAction(e -> {
            centerContent.getChildren().setAll(vistaCrud);
            actualizarSeleccionNavbar(btncrud, btnprincipal, btncrud, btninventario, btnmov, btnAbono);
        });

        btninventario.setOnAction(e -> {
            centerContent.getChildren().setAll(vistaInventario);
            actualizarSeleccionNavbar(btninventario, btnprincipal, btncrud, btninventario, btnmov, btnAbono);
        });

        btnmov.setOnAction(e -> {
            centerContent.getChildren().setAll(vistaMovimientos);
            actualizarSeleccionNavbar(btnmov, btnprincipal, btncrud, btninventario, btnmov, btnAbono);
        });
        
        btnAbono.setOnAction(e -> {
            centerContent.getChildren().setAll(scrollAbonos);
            actualizarSeleccionNavbar(btnAbono, btnprincipal, btncrud, btninventario, btnmov, btnAbono);
        });

        // ==== ROOT PRINCIPAL ====
        BorderPane root = new BorderPane();
        root.setTop(topContainer);
        root.setCenter(centerContent);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 1200, 800);
        aplicarCss(scene);
        stage.setScene(scene);
        stage.setMaximized(true);
    }

    // Marca un botón del navbar como seleccionado y limpia los demás
    private void actualizarSeleccionNavbar(Button seleccionado, Button... todos) {
        for (Button b : todos) {
            b.getStyleClass().remove("navbar-button-selected");
        }
        if (!seleccionado.getStyleClass().contains("navbar-button-selected")) {
            seleccionado.getStyleClass().add("navbar-button-selected");
        }
    }

    // ====== VISTA: MENU PRINCIPAL (ADMIN) ======
    private VBox crearVistaAdminPrincipal(Usuario usuario) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");

        Label lblBienvenida = new Label("Bienvenido, " + usuario.getUsuario());
        lblBienvenida.getStyleClass().add("subtitulo");

        Label lblInfo = new Label("Administra el restaurante La Oficina desde este panel de administrador.");
        lblInfo.getStyleClass().add("descripcion");
        lblInfo.setWrapText(true);

        card.getChildren().addAll(lblBienvenida, lblInfo);
        return card;
    }

    // ====== VISTA: CRUD ======
    private VBox crearVistaAdminCrud() {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");

        Label lblTitulo = new Label("Módulo CRUD");
        lblTitulo.getStyleClass().add("subtitulo");

        Label lblInfo = new Label("Aquí podrás gestionar (crear, actualizar y eliminar) registros del sistema.");
        lblInfo.getStyleClass().add("descripcion");
        lblInfo.setWrapText(true);

        card.getChildren().addAll(lblTitulo, lblInfo);
        return card;
    }

    // ====== VISTA: INVENTARIO ======
    private VBox crearVistaAdminInventario() {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");

        Label lblTitulo = new Label("Inventario");
        lblTitulo.getStyleClass().add("subtitulo");

        Label lblInfo = new Label("Visualiza y controla el inventario de productos del restaurante.");
        lblInfo.getStyleClass().add("descripcion");
        lblInfo.setWrapText(true);

        card.getChildren().addAll(lblTitulo, lblInfo);
        return card;
    }

    // ====== VISTA: MOVIMIENTOS ======
    private VBox crearVistaAdminMovimientos() {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");

        Label lblTitulo = new Label("Movimientos");
        lblTitulo.getStyleClass().add("subtitulo");

        Label lblInfo = new Label("Consulta los movimientos, ventas y cambios realizados en el sistema.");
        lblInfo.getStyleClass().add("descripcion");
        lblInfo.setWrapText(true);

        card.getChildren().addAll(lblTitulo, lblInfo);
        return card;
    }
    

    // -------------------------------------------------------------------------
    // CAJA
    // -------------------------------------------------------------------------
    private void mostrarCaja(Stage stage, Usuario usuario) {

        // ==== LOGO ====
        Image logoImg = new Image(getClass().getResourceAsStream("/imgs/logonavbar.png"));
        ImageView logoView = new ImageView(logoImg);
        logoView.setFitWidth(200);
        logoView.setPreserveRatio(true);

        // ==== BOTÓN USUARIO (CON MENÚ) ====
        Label lblUsuario = new Label(usuario.getUsuario());
        lblUsuario.getStyleClass().add("user-label");

        Image userIcon = new Image(getClass().getResourceAsStream("/imgs/user_icon.png"));
        ImageView userIconView = new ImageView(userIcon);
        userIconView.setFitWidth(28);
        userIconView.setPreserveRatio(true);

        HBox userButton = new HBox(8, lblUsuario, userIconView);
        userButton.setAlignment(Pos.CENTER_RIGHT);
        userButton.getStyleClass().add("user-button");
        userButton.setPadding(new Insets(8, 12, 8, 12));

        ContextMenu userMenu = new ContextMenu();

        Label lblCerrar = new Label("Cerrar sesión");
        lblCerrar.getStyleClass().add("user-menu-label");
        HBox cerrarBox = new HBox(lblCerrar);
        cerrarBox.getStyleClass().add("user-menu-item");
        CustomMenuItem itemCerrar = new CustomMenuItem(cerrarBox, true);

        cerrarBox.setOnMouseClicked(e -> {
            userMenu.hide();
            mostrarPantallaLogin(stage);
        });

        userMenu.getItems().add(itemCerrar);

        userButton.setOnMouseClicked(e -> {
            if (!userMenu.isShowing()) {
                userMenu.show(userButton, Side.BOTTOM, 0, 0);
            } else {
                userMenu.hide();
            }
        });

        // ==== TOP BAR ====
        BorderPane topBar = new BorderPane();
        topBar.setLeft(logoView);
        topBar.setRight(userButton);
        topBar.setPadding(new Insets(10, 20, 10, 20));
        topBar.getStyleClass().add("top-bar");

        // ==== NAVBAR (MENÚ) ====
        Button btnprincipal = new Button("Menú Principal");
        Button btnCobros = new Button("Cobros");
        Button btnHistorial = new Button("Historial");
        Button btnCorte = new Button("Corte de caja");

        btnprincipal.getStyleClass().addAll("navbar-button", "navbar-button-selected");
        btnCobros.getStyleClass().add("navbar-button");
        btnHistorial.getStyleClass().add("navbar-button");
        btnCorte.getStyleClass().add("navbar-button");

        HBox navbar = new HBox(20, btnprincipal, btnCobros, btnHistorial, btnCorte);
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(12, 20, 12, 20));
        navbar.getStyleClass().add("navbar");

        VBox topContainer = new VBox(topBar, navbar);

        // ==== CONTENEDOR CENTRAL ====
        VBox centerContent = new VBox(20);
        centerContent.setPadding(new Insets(30));

        VBox vistaPrincipal = crearVistaCajaPrincipal(usuario);
        VBox vistaCobros = crearVistaCajaCobros();
        VBox vistaHistorial = crearVistaCajaHistorial();
        VBox vistaCorte = crearVistaCajaCorte();

        centerContent.getChildren().setAll(vistaPrincipal);

        // Acciones navbar
        btnprincipal.setOnAction(e -> {
            centerContent.getChildren().setAll(vistaPrincipal);
            actualizarSeleccionNavbar(btnprincipal, btnprincipal, btnCobros, btnHistorial, btnCorte);
        });

        btnCobros.setOnAction(e -> {
            centerContent.getChildren().setAll(vistaCobros);
            actualizarSeleccionNavbar(btnCobros, btnprincipal, btnCobros, btnHistorial, btnCorte);
        });

        btnHistorial.setOnAction(e -> {
            centerContent.getChildren().setAll(vistaHistorial);
            actualizarSeleccionNavbar(btnHistorial, btnprincipal, btnCobros, btnHistorial, btnCorte);
        });

        btnCorte.setOnAction(e -> {
            centerContent.getChildren().setAll(vistaCorte);
            actualizarSeleccionNavbar(btnCorte, btnprincipal, btnCobros, btnHistorial, btnCorte);
        });

        // ==== ROOT ====
        BorderPane root = new BorderPane();
        root.setTop(topContainer);
        root.setCenter(centerContent);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 1200, 800);
        aplicarCss(scene);
        stage.setScene(scene);
        stage.setMaximized(true);
    }

    // ====== CAJA: MENÚ PRINCIPAL ======
    private VBox crearVistaCajaPrincipal(Usuario usuario) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");

        Label lblTitulo = new Label("Caja - Punto de venta");
        lblTitulo.getStyleClass().add("subtitulo");

        Label lblInfo = new Label("Bienvenido, " + usuario.getUsuario()
                + ". Desde aquí puedes controlar los cobros y el flujo de caja del día.");
        lblInfo.getStyleClass().add("descripcion");
        lblInfo.setWrapText(true);

        card.getChildren().addAll(lblTitulo, lblInfo);
        return card;
    }
    // ====== CAJA: COBROS ======
private VBox crearVistaCajaCobros() {
    VBox card = new VBox(15);
    card.getStyleClass().add("card");

    // Título y descripción
    Label lblTitulo = new Label("Cobros");
    lblTitulo.getStyleClass().add("subtitulo");

    Label lblInfo = new Label("Registra los pagos de los clientes, agrega productos a la cuenta y genera el cobro final.");
    lblInfo.getStyleClass().add("descripcion");
    lblInfo.setWrapText(true);

    // Tabla de productos en la cuenta
    TableView<ItemCuenta> tabla = new TableView<>();
    tabla.getStyleClass().add("tabla-cobros");
    tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<ItemCuenta, String> colProducto = new TableColumn<>("Producto");
    colProducto.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getDescripcion()));

    TableColumn<ItemCuenta, String> colCant = new TableColumn<>("Cant.");
    colCant.setMaxWidth(80);
    colCant.setCellValueFactory(data ->
            new SimpleStringProperty(String.valueOf(data.getValue().getCantidad())));

    TableColumn<ItemCuenta, String> colPrecio = new TableColumn<>("Precio");
    colPrecio.setMinWidth(90);
    colPrecio.setCellValueFactory(data ->
            new SimpleStringProperty(String.format("L %.2f", data.getValue().getPrecio())));

    TableColumn<ItemCuenta, String> colTotal = new TableColumn<>("Total");
    colTotal.setMinWidth(90);
    colTotal.setCellValueFactory(data ->
            new SimpleStringProperty(String.format("L %.2f", data.getValue().getSubtotal())));

    tabla.getColumns().addAll(colProducto, colCant, colPrecio, colTotal);

    // Datos de ejemplo
    ObservableList<ItemCuenta> datos = FXCollections.observableArrayList(
            new ItemCuenta("Hamburguesa clásica", 1, 180.00),
            new ItemCuenta("Papas fritas", 2, 60.00),
            new ItemCuenta("Refresco", 2, 40.00)
    );
    tabla.setItems(datos);

    // Totales (por ahora fijos)
    Label lblSubtotal = new Label("Subtotal:");
    lblSubtotal.getStyleClass().add("totales-label");
    Label lblSubtotalValor = new Label("L 380.00");
    lblSubtotalValor.getStyleClass().add("totales-valor");

    Label lblImpuesto = new Label("Impuesto (15%):");
    lblImpuesto.getStyleClass().add("totales-label");
    Label lblImpuestoValor = new Label("L 57.00");
    lblImpuestoValor.getStyleClass().add("totales-valor");

    Label lblTotal = new Label("Total a pagar:");
    lblTotal.getStyleClass().add("totales-label-strong");
    Label lblTotalValor = new Label("L 437.00");
    lblTotalValor.getStyleClass().add("totales-valor-strong");

    GridPane boxTotales = new GridPane();
    boxTotales.getStyleClass().add("totales-box");
    boxTotales.setHgap(10);
    boxTotales.setVgap(4);
    boxTotales.add(lblSubtotal, 0, 0);
    boxTotales.add(lblSubtotalValor, 1, 0);
    boxTotales.add(lblImpuesto, 0, 1);
    boxTotales.add(lblImpuestoValor, 1, 1);
    boxTotales.add(lblTotal, 0, 2);
    boxTotales.add(lblTotalValor, 1, 2);

    // Botones de acción
    Button btnAgregar = new Button("Agregar producto");
    btnAgregar.getStyleClass().add("button-secondary");

    Button btnEliminar = new Button("Eliminar línea");
    btnEliminar.getStyleClass().add("button-secondary");

    Button btnCancelar = new Button("Cancelar cuenta");
    btnCancelar.getStyleClass().add("button-secondary");

    Button btnCobrar = new Button("Cobrar");
    btnCobrar.getStyleClass().add("button-primary");

    HBox acciones = new HBox(10, btnAgregar, btnEliminar, btnCancelar, btnCobrar);
    acciones.setAlignment(Pos.CENTER_RIGHT);

    card.getChildren().addAll(lblTitulo, lblInfo, tabla, boxTotales, acciones);
    return card;
}

    // ====== CAJA: HISTORIAL ======
    private VBox crearVistaCajaHistorial() {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");

        Label lblTitulo = new Label("Historial de cobros");
        lblTitulo.getStyleClass().add("subtitulo");

        Label lblInfo = new Label("Consulta los cobros realizados en el día, filtros por fecha y método de pago.");
        lblInfo.getStyleClass().add("descripcion");
        lblInfo.setWrapText(true);

        card.getChildren().addAll(lblTitulo, lblInfo);
        return card;
    }

    // ====== CAJA: CORTE DE CAJA ======
    private VBox crearVistaCajaCorte() {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");

        Label lblTitulo = new Label("Corte de caja");
        lblTitulo.getStyleClass().add("subtitulo");

        Label lblInfo = new Label("Genera el corte de caja al final del turno: resumen de ingresos, egresos y saldo.");
        lblInfo.getStyleClass().add("descripcion");
        lblInfo.setWrapText(true);

        card.getChildren().addAll(lblTitulo, lblInfo);
        return card;
    }

    // -------------------------------------------------------------------------
// OPERADOR
// -------------------------------------------------------------------------
private void mostrarOperador(Stage stage, Usuario usuario) {

    // ==== LOGO ====
    Image logoImg = new Image(getClass().getResourceAsStream("/imgs/logonavbar.png"));
    ImageView logoView = new ImageView(logoImg);
    logoView.setFitWidth(200);
    logoView.setPreserveRatio(true);

    // ==== BOTÓN USUARIO (CON MENÚ) ====
    Label lblUsuario = new Label(usuario.getUsuario());
    lblUsuario.getStyleClass().add("user-label");

    Image userIcon = new Image(getClass().getResourceAsStream("/imgs/user_icon.png"));
    ImageView userIconView = new ImageView(userIcon);
    userIconView.setFitWidth(28);
    userIconView.setPreserveRatio(true);

    HBox userButton = new HBox(8, lblUsuario, userIconView);
    userButton.setAlignment(Pos.CENTER_RIGHT);
    userButton.getStyleClass().add("user-button");
    userButton.setPadding(new Insets(8, 12, 8, 12));

    ContextMenu userMenu = new ContextMenu();

    Label lblCerrar = new Label("Cerrar sesión");
    lblCerrar.getStyleClass().add("user-menu-label");
    HBox cerrarBox = new HBox(lblCerrar);
    cerrarBox.setAlignment(Pos.CENTER_LEFT);
    cerrarBox.getStyleClass().add("user-menu-item");
    CustomMenuItem itemCerrar = new CustomMenuItem(cerrarBox, true);

    cerrarBox.setOnMouseClicked(e -> {
        userMenu.hide();
        mostrarPantallaLogin(stage);
    });

    userMenu.getItems().add(itemCerrar);

    userButton.setOnMouseClicked(e -> {
        if (!userMenu.isShowing()) {
            userMenu.show(userButton, Side.BOTTOM, 0, 0);
        } else {
            userMenu.hide();
        }
    });

    // ==== TOP BAR ====
    BorderPane topBar = new BorderPane();
    topBar.setLeft(logoView);
    topBar.setRight(userButton);
    topBar.setPadding(new Insets(10, 20, 10, 20));
    topBar.getStyleClass().add("top-bar");

    // ==== NAVBAR ====
    Button btnprincipal = new Button("Menú Principal");
    Button btnComandas = new Button("Comandas");
    Button btnMesas = new Button("Mesas");
    Button btnCocina = new Button("Cocina");

    btnprincipal.getStyleClass().addAll("navbar-button", "navbar-button-selected");
    btnComandas.getStyleClass().add("navbar-button");
    btnMesas.getStyleClass().add("navbar-button");
    btnCocina.getStyleClass().add("navbar-button");

    HBox navbar = new HBox(20, btnprincipal, btnComandas, btnMesas, btnCocina);
    navbar.setAlignment(Pos.CENTER_LEFT);
    navbar.setPadding(new Insets(12, 20, 12, 20));
    navbar.getStyleClass().add("navbar");

    VBox topContainer = new VBox(topBar, navbar);

    // ==== CONTENIDO CENTRAL
    VBox centerContent = new VBox(20);
    centerContent.setPadding(new Insets(30));

    VBox vistaPrincipal = crearVistaOperadorPrincipal(usuario);
    VBox vistaComandas = crearVistaOperadorComandas();
    VBox vistaMesas = crearVistaOperadorMesas();
    VBox vistaCocina = crearVistaOperadorCocina();

    // Vista inicial
    centerContent.getChildren().setAll(vistaPrincipal);

    // Acciones navbar
    btnprincipal.setOnAction(e -> {
        centerContent.getChildren().setAll(vistaPrincipal);
        actualizarSeleccionNavbar(btnprincipal, btnprincipal, btnComandas, btnMesas, btnCocina);
    });

    btnComandas.setOnAction(e -> {
        centerContent.getChildren().setAll(vistaComandas);
        actualizarSeleccionNavbar(btnComandas, btnprincipal, btnComandas, btnMesas, btnCocina);
    });

    btnMesas.setOnAction(e -> {
        centerContent.getChildren().setAll(vistaMesas);
        actualizarSeleccionNavbar(btnMesas, btnprincipal, btnComandas, btnMesas, btnCocina);
    });

    btnCocina.setOnAction(e -> {
        centerContent.getChildren().setAll(vistaCocina);
        actualizarSeleccionNavbar(btnCocina, btnprincipal, btnComandas, btnMesas, btnCocina);
    });

    // ==== ROOT ====
    BorderPane root = new BorderPane();
    root.setTop(topContainer);
    root.setCenter(centerContent);
    root.setPadding(new Insets(20));

    Scene scene = new Scene(root, 1200, 800);
    aplicarCss(scene);
    stage.setScene(scene);
    stage.setMaximized(true);
}
// ====== OPERADOR: MENÚ PRINCIPAL ======
private VBox crearVistaOperadorPrincipal(Usuario usuario) {
    VBox card = new VBox(10);
    card.getStyleClass().add("card");

    Label lblTitulo = new Label("Operador - Gestión de servicio");
    lblTitulo.getStyleClass().add("subtitulo");

    Label lblInfo = new Label("Bienvenido, " + usuario.getUsuario()
            + ". Desde aquí puedes coordinar mesas, comandas y pedidos hacia cocina.");
    lblInfo.getStyleClass().add("descripcion");
    lblInfo.setWrapText(true);

    card.getChildren().addAll(lblTitulo, lblInfo);
    return card;
}

// ====== OPERADOR: COMANDAS ======
private VBox crearVistaOperadorComandas() {
    VBox card = new VBox(10);
    card.getStyleClass().add("card");

    Label lblTitulo = new Label("Comandas");
    lblTitulo.getStyleClass().add("subtitulo");

    Label lblInfo = new Label("Visualiza las comandas abiertas, envía pedidos a cocina y marca órdenes como servidas.");
    lblInfo.getStyleClass().add("descripcion");
    lblInfo.setWrapText(true);

    card.getChildren().addAll(lblTitulo, lblInfo);
    return card;
}

// ====== OPERADOR: MESAS ======
private VBox crearVistaOperadorMesas() {
    VBox card = new VBox(10);
    card.getStyleClass().add("card");

    Label lblTitulo = new Label("Mesas");
    lblTitulo.getStyleClass().add("subtitulo");

    Label lblInfo = new Label("Administra el estado de las mesas: libre, ocupada, en espera de cuenta, reservada, etc.");
    lblInfo.getStyleClass().add("descripcion");
    lblInfo.setWrapText(true);

    card.getChildren().addAll(lblTitulo, lblInfo);
    return card;
}

// ====== OPERADOR: COCINA ======
private VBox crearVistaOperadorCocina() {
    VBox card = new VBox(10);
    card.getStyleClass().add("card");

    Label lblTitulo = new Label("Cocina");
    lblTitulo.getStyleClass().add("subtitulo");

    Label lblInfo = new Label("Consulta los pedidos pendientes en cocina y su estado de preparación.");
    lblInfo.getStyleClass().add("descripcion");
    lblInfo.setWrapText(true);

    card.getChildren().addAll(lblTitulo, lblInfo);
    return card;
}

// ===== TABLAS DE ABONO Y COMPRAS ===========

private VBox crearVistaCompras() {
    VBox card = new VBox(15);
    card.getStyleClass().add("card"); 

    Label lblTitulo = new Label("Gestión de Compras");
    lblTitulo.getStyleClass().add("subtitulo");

    // Crear TableView
    TableView<Compra> tablaCompras = new TableView<>();
    tablaCompras.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    // Columnas que coinciden con tu tabla SQL
    TableColumn<Compra, Number> colId = new TableColumn<>("ID");
    colId.setMaxWidth(60);
    colId.setCellValueFactory(data -> data.getValue().idCompraProperty());

    TableColumn<Compra, LocalDateTime> colFecha = new TableColumn<>("Fecha");
    colFecha.setMinWidth(150);
    colFecha.setCellValueFactory(data -> data.getValue().fechaProperty());
    // Formato personalizado para fecha
    colFecha.setCellFactory(col -> new TableCell<Compra, LocalDateTime>() {
        @Override
        protected void updateItem(LocalDateTime item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                setText(item.format(formatter));
            }
        }
    });

    TableColumn<Compra, String> colEstado = new TableColumn<>("Estado");
    colEstado.setMaxWidth(100);
    colEstado.setCellValueFactory(data -> data.getValue().estadoProperty());

    TableColumn<Compra, String> colMetodo = new TableColumn<>("Método Pago");
    colMetodo.setMinWidth(120);
    colMetodo.setCellValueFactory(data -> data.getValue().metodoPagoProperty());

    TableColumn<Compra, Number> colSubtotal = new TableColumn<>("Subtotal");
    colSubtotal.setCellValueFactory(data -> data.getValue().subtotalProperty());
    colSubtotal.setCellFactory(col -> new TableCell<Compra, Number>() {
        @Override
        protected void updateItem(Number item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty || item == null ? null : String.format("L %.2f", item.doubleValue()));
        }
    });

    TableColumn<Compra, Number> colImpuestos = new TableColumn<>("Impuestos");
    colImpuestos.setCellValueFactory(data -> data.getValue().impuestosProperty());
    colImpuestos.setCellFactory(col -> new TableCell<Compra, Number>() {
        @Override
        protected void updateItem(Number item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty || item == null ? null : String.format("L %.2f", item.doubleValue()));
        }
    });

    TableColumn<Compra, Number> colTotal = new TableColumn<>("Total");
    colTotal.setCellValueFactory(data -> data.getValue().totalProperty());
    colTotal.setCellFactory(col -> new TableCell<Compra, Number>() {
        @Override
        protected void updateItem(Number item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty || item == null ? null : String.format("L %.2f", item.doubleValue()));
        }
    });

    // Agregar todas las columnas
    tablaCompras.getColumns().addAll(colId, colFecha, colEstado, colMetodo, 
                                      colSubtotal, colImpuestos, colTotal);

    // Datos de ejemplo (luego conectarás con tu base de datos)
    ObservableList<Compra> datosCompras = FXCollections.observableArrayList();
    tablaCompras.setItems(datosCompras);

    // Botones de acción
    Button btnNueva = new Button("Nueva Compra");
    btnNueva.getStyleClass().add("button-primary");
    
    Button btnEditar = new Button("Editar");
    btnEditar.getStyleClass().add("button-secondary");
    
    Button btnEliminar = new Button("Eliminar");
    btnEliminar.getStyleClass().add("button-secondary");

    Button btnVerAbonos = new Button("Ver Abonos");
    btnVerAbonos.getStyleClass().add("button-secondary");

    HBox acciones = new HBox(10, btnNueva, btnEditar, btnEliminar, btnVerAbonos);
    acciones.setAlignment(Pos.CENTER_RIGHT);

    // Deshabilitar botones si no hay selección
    btnEditar.disableProperty().bind(
        tablaCompras.getSelectionModel().selectedItemProperty().isNull()
    );
    btnEliminar.disableProperty().bind(
        tablaCompras.getSelectionModel().selectedItemProperty().isNull()
    );
    btnVerAbonos.disableProperty().bind(
        tablaCompras.getSelectionModel().selectedItemProperty().isNull()
    );

    card.getChildren().addAll(lblTitulo, tablaCompras, acciones);
    return card;
}

private VBox crearVistaAbonos(int idCompra) {
    VBox card = new VBox(15);
    card.getStyleClass().add("card");

    Label lblTitulo = new Label("Abonos de la Compra #" + idCompra);
    lblTitulo.getStyleClass().add("subtitulo");

    // TableView para Abonos
    TableView<Abono> tablaAbonos = new TableView<>();
    tablaAbonos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<Abono, Number> colId = new TableColumn<>("# Abono");
    colId.setMaxWidth(80);
    colId.setCellValueFactory(data -> data.getValue().idAbonoProperty());

    TableColumn<Abono, Number> colIdCompra = new TableColumn<>("# Compra");
    colIdCompra.setMaxWidth(80);
    colIdCompra.setCellValueFactory(data -> data.getValue().idCompraProperty());

    TableColumn<Abono, LocalDateTime> colFecha = new TableColumn<>("Fecha");
    colFecha.setMinWidth(150);
    colFecha.setCellValueFactory(data -> data.getValue().fechaProperty());
    colFecha.setCellFactory(col -> new TableCell<Abono, LocalDateTime>() {
        @Override
        protected void updateItem(LocalDateTime item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                setText(item.format(formatter));
            }
        }
    });

    TableColumn<Abono, Number> colMonto = new TableColumn<>("Monto");
    colMonto.setMinWidth(100);
    colMonto.setCellValueFactory(data -> data.getValue().montoProperty());
    colMonto.setCellFactory(col -> new TableCell<Abono, Number>() {
        @Override
        protected void updateItem(Number item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty || item == null ? null : String.format("L %.2f", item.doubleValue()));
        }
    });

    TableColumn<Abono, String> colMetodo = new TableColumn<>("Método Pago");
    colMetodo.setMinWidth(110);
    colMetodo.setCellValueFactory(data -> data.getValue().metodoPagoProperty());

    TableColumn<Abono, String> colComentario = new TableColumn<>("Comentario");
    colComentario.setMinWidth(200);
    colComentario.setCellValueFactory(data -> data.getValue().comentarioProperty());

    tablaAbonos.getColumns().addAll(colId, colIdCompra, colFecha, 
                                      colMonto, colMetodo, colComentario);

    ObservableList<Abono> datosAbonos = FXCollections.observableArrayList();
    tablaAbonos.setItems(datosAbonos);

    // Total de abonos
    Label lblTotalAbonos = new Label("Total Abonado:");
    lblTotalAbonos.getStyleClass().add("totales-label-strong");
    
    Label lblTotalValor = new Label("L 0.00");
    lblTotalValor.getStyleClass().add("totales-valor-strong");

    HBox boxTotal = new HBox(10, lblTotalAbonos, lblTotalValor);
    boxTotal.setAlignment(Pos.CENTER_RIGHT);
    boxTotal.setPadding(new Insets(10, 0, 0, 0));

    // Botones
    Button btnNuevoAbono = new Button("Nuevo Abono");
    btnNuevoAbono.getStyleClass().add("button-primary");
    
    Button btnEliminar = new Button("Eliminar Abono");
    btnEliminar.getStyleClass().add("button-secondary");

    HBox acciones = new HBox(10, btnNuevoAbono, btnEliminar);
    acciones.setAlignment(Pos.CENTER_RIGHT);

    btnEliminar.disableProperty().bind(
        tablaAbonos.getSelectionModel().selectedItemProperty().isNull()
    );

    card.getChildren().addAll(lblTitulo, tablaAbonos, boxTotal, acciones);
    return card;
}

private VBox crearVistaAbonosYCompras() {
    VBox contenedorPrincipal = new VBox(20);
    contenedorPrincipal.setPadding(new Insets(0));

    // ========== CARD DE INTRODUCCIÓN ==========
    VBox cardIntro = new VBox(10);
    cardIntro.getStyleClass().add("card");

    Label lblTitulo = new Label("Historial de Compra y Abonos");
    lblTitulo.getStyleClass().add("subtitulo");

    Label lblInfo = new Label("Consulta el historial de compras y de abonos en el sistema.");
    lblInfo.getStyleClass().add("descripcion");
    lblInfo.setWrapText(true);

    cardIntro.getChildren().addAll(lblTitulo, lblInfo);

    // ========== PARTE 1: MASTER (Tabla de Compras) ==========
    VBox cardCompras = new VBox(15);
    cardCompras.getStyleClass().add("card");

    Label lblTituloCompras = new Label("Gestión de Compras");
    lblTituloCompras.getStyleClass().add("subtitulo");

    Label lblInfoCompras = new Label("Selecciona una compra para ver sus abonos asociados");
    lblInfoCompras.getStyleClass().add("descripcion");
    lblInfoCompras.setWrapText(true);

    // Tabla de Compras (MASTER)
    TableView<Compra> tablaCompras = new TableView<>();
    tablaCompras.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    tablaCompras.setPrefHeight(300);
    tablaCompras.setMinHeight(200);

    TableColumn<Compra, Number> colIdCompra = new TableColumn<>("ID");
    colIdCompra.setMaxWidth(60);
    colIdCompra.setCellValueFactory(data -> data.getValue().idCompraProperty());

    TableColumn<Compra, String> colFecha = new TableColumn<>("Fecha");
    colFecha.setMinWidth(150);
    colFecha.setCellValueFactory(data -> {
        LocalDateTime fecha = data.getValue().getFecha();
        if (fecha != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return new SimpleStringProperty(fecha.format(formatter));
        }
        return new SimpleStringProperty("");
    });

    TableColumn<Compra, String> colEstado = new TableColumn<>("Estado");
    colEstado.setMaxWidth(150);
    colEstado.setCellValueFactory(data -> data.getValue().estadoProperty());

    TableColumn<Compra, String> colMetodo = new TableColumn<>("Método Pago");
    colMetodo.setMinWidth(120);
    colMetodo.setCellValueFactory(data -> data.getValue().metodoPagoProperty());

    TableColumn<Compra, String> colSubtotal = new TableColumn<>("Subtotal");
    colSubtotal.setCellValueFactory(data -> {
        double valor = data.getValue().getSubtotal();
        return new SimpleStringProperty(String.format("L %.2f", valor));
    });

    TableColumn<Compra, String> colImpuestos = new TableColumn<>("Impuestos");
    colImpuestos.setCellValueFactory(data -> {
        double valor = data.getValue().getImpuestos();
        return new SimpleStringProperty(String.format("L %.2f", valor));
    });

    TableColumn<Compra, String> colTotal = new TableColumn<>("Total");
    colTotal.setCellValueFactory(data -> {
        double valor = data.getValue().getTotal();
        return new SimpleStringProperty(String.format("L %.2f", valor));
    });

    tablaCompras.getColumns().addAll(colIdCompra, colFecha, colEstado, 
                                      colMetodo, colSubtotal, colImpuestos, colTotal);

    // Datos de ejemplo para Compras
    ObservableList<Compra> datosCompras = FXCollections.observableArrayList();
    datosCompras.add(new Compra(1, LocalDateTime.of(2025, 12, 5, 10, 30), "Pendiente", 
                   "Crédito 30 días", 285.00, 42.75, 327.75));
    datosCompras.add(new Compra(2, LocalDateTime.of(2025, 12, 4, 14, 15), "Parcialmente Pagada", 
                   "Efectivo", 1500.00, 225.00, 1725.00));
    datosCompras.add(new Compra(3, LocalDateTime.of(2025, 12, 3, 9, 0), "Pagada", 
                   "Transferencia", 800.00, 120.00, 920.00));
    
    tablaCompras.setItems(datosCompras);

    cardCompras.getChildren().addAll(lblTituloCompras, lblInfoCompras, tablaCompras);

    VBox cardAbonos = new VBox(15);
    cardAbonos.getStyleClass().add("card");

    Label lblTituloAbonos = new Label("Abonos de la Compra");
    lblTituloAbonos.getStyleClass().add("subtitulo");

    Label lblInfoAbonos = new Label("Selecciona una compra arriba para ver sus abonos");
    lblInfoAbonos.getStyleClass().add("descripcion");
    lblInfoAbonos.setWrapText(true);

    // Tabla de Abonos (DETAIL)
    TableView<Abono> tablaAbonos = new TableView<>();
    tablaAbonos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    tablaAbonos.setPrefHeight(250);
    tablaAbonos.setMinHeight(200);
    tablaAbonos.setPlaceholder(new Label("Selecciona una compra para ver sus abonos"));

    TableColumn<Abono, Number> colIdAbono = new TableColumn<>("# Abono");
    colIdAbono.setMaxWidth(80);
    colIdAbono.setCellValueFactory(data -> data.getValue().idAbonoProperty());

    TableColumn<Abono, Number> colIdCompraAbono = new TableColumn<>("# Compra");
    colIdCompraAbono.setMaxWidth(80);
    colIdCompraAbono.setCellValueFactory(data -> data.getValue().idCompraProperty());

    TableColumn<Abono, String> colFechaAbono = new TableColumn<>("Fecha");
    colFechaAbono.setMinWidth(150);
    colFechaAbono.setCellValueFactory(data -> {
        LocalDateTime fecha = data.getValue().getFecha();
        if (fecha != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return new SimpleStringProperty(fecha.format(formatter));
        }
        return new SimpleStringProperty("");
    });

    TableColumn<Abono, String> colMonto = new TableColumn<>("Monto");
    colMonto.setMinWidth(100);
    colMonto.setCellValueFactory(data -> {
        double valor = data.getValue().getMonto();
        return new SimpleStringProperty(String.format("L %.2f", valor));
    });

    TableColumn<Abono, String> colMetodoAbono = new TableColumn<>("Método Pago");
    colMetodoAbono.setMinWidth(110);
    colMetodoAbono.setCellValueFactory(data -> data.getValue().metodoPagoProperty());

    TableColumn<Abono, String> colComentario = new TableColumn<>("Comentario");
    colComentario.setMinWidth(200);
    colComentario.setCellValueFactory(data -> data.getValue().comentarioProperty());

    tablaAbonos.getColumns().addAll(colIdAbono, colIdCompraAbono, colFechaAbono, 
                                      colMonto, colMetodoAbono, colComentario);

    ObservableList<Abono> datosAbonos = FXCollections.observableArrayList();
    tablaAbonos.setItems(datosAbonos);

    // ========== LABELS DE TOTALES ==========
    Label lblTotalAbonadoTexto = new Label("Total Abonado:");
    lblTotalAbonadoTexto.getStyleClass().add("totales-label-strong");
    
    Label lblTotalAbonado = new Label("L 0.00");
    lblTotalAbonado.getStyleClass().add("totales-valor-strong");

    Label lblPendienteTexto = new Label("Pendiente de Pago:");
    lblPendienteTexto.getStyleClass().add("totales-label");
    
    Label lblPendiente = new Label("L 0.00");
    lblPendiente.getStyleClass().add("totales-valor");

    tablaCompras.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblTituloAbonos.setText("Abonos de la Compra #" + newValue.getIdCompra());
                lblInfoAbonos.setText("Total de la compra: L " + 
                    String.format("%.2f", newValue.getTotal()) + 
                    " | Estado: " + newValue.getEstado());
                
                datosAbonos.clear();
                
                if (newValue.getIdCompra() == 1) {
                    // Compra 1: Sin abonos
                } else if (newValue.getIdCompra() == 2) {
                    datosAbonos.add(new Abono(1, 2, LocalDateTime.of(2025, 12, 4, 15, 0), 
                                  500.00, "Efectivo", "Primer abono"));
                    datosAbonos.add(new Abono(2, 2, LocalDateTime.of(2025, 12, 5, 10, 0), 
                                  300.00, "Efectivo", "Segundo abono"));
                } else if (newValue.getIdCompra() == 3) {
                    datosAbonos.add(new Abono(3, 3, LocalDateTime.of(2025, 12, 3, 11, 30), 
                                  920.00, "Transferencia", "Pago total"));
                }
                
                double totalAbonado = datosAbonos.stream()
                    .mapToDouble(Abono::getMonto)
                    .sum();
                double pendiente = newValue.getTotal() - totalAbonado;
                
                lblTotalAbonado.setText(String.format("L %.2f", totalAbonado));
                lblPendiente.setText(String.format("L %.2f", pendiente));
                
            } else {
                lblTituloAbonos.setText("Abonos de la Compra");
                lblInfoAbonos.setText("Selecciona una compra arriba para ver sus abonos");
                datosAbonos.clear();
                lblTotalAbonado.setText("L 0.00");
                lblPendiente.setText("L 0.00");
            }
        }
    );

    datosAbonos.addListener((ListChangeListener<Abono>) change -> {
        Compra compraSeleccionada = tablaCompras.getSelectionModel().getSelectedItem();
        if (compraSeleccionada != null) {
            double totalAbonado = datosAbonos.stream()
                .mapToDouble(Abono::getMonto)
                .sum();
            double pendiente = compraSeleccionada.getTotal() - totalAbonado;
            
            lblTotalAbonado.setText(String.format("L %.2f", totalAbonado));
            lblPendiente.setText(String.format("L %.2f", pendiente));
        }
    });

    // ========== GRID DE TOTALES ==========
    GridPane boxTotales = new GridPane();
    boxTotales.getStyleClass().add("totales-box");
    boxTotales.setHgap(10);
    boxTotales.setVgap(4);
    boxTotales.add(lblTotalAbonadoTexto, 0, 0);
    boxTotales.add(lblTotalAbonado, 1, 0);
    boxTotales.add(lblPendienteTexto, 0, 1);
    boxTotales.add(lblPendiente, 1, 1);

    cardAbonos.getChildren().addAll(lblTituloAbonos, lblInfoAbonos, 
                                     tablaAbonos, boxTotales);

    contenedorPrincipal.getChildren().addAll(cardIntro, cardCompras, cardAbonos);
    
    return contenedorPrincipal;
}


    // -------------------------------------------------------------------------
    // MAIN
    // -------------------------------------------------------------------------
    public static void main(String[] args) {
        launch(args);
    }
}
