package com.mycompany.proyecto_login;

import static com.mycompany.proyecto_login.Rol.ADMIN;
import static com.mycompany.proyecto_login.Rol.CAJA;
import static com.mycompany.proyecto_login.Rol.OPERADOR;

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
        ContextMenu userMenu = new ContextMenu(); // usa estilos .context-menu

        // Item: Crear usuario
        Label lblCrear = new Label("Crear usuario");
        lblCrear.getStyleClass().add("user-menu-label");
        HBox crearBox = new HBox(lblCrear);
        crearBox.setAlignment(Pos.CENTER_LEFT);   // <- importante
        crearBox.getStyleClass().add("user-menu-item");
        CustomMenuItem itemCrear = new CustomMenuItem(crearBox, true);

        // Item: Cerrar sesión
        Label lblCerrar = new Label("Cerrar sesión");
        lblCerrar.getStyleClass().add("user-menu-label");
        HBox cerrarBox = new HBox(lblCerrar);
        cerrarBox.setAlignment(Pos.CENTER_LEFT);  // <- importante
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

        btnprincipal.getStyleClass().addAll("navbar-button", "navbar-button-selected");
        btncrud.getStyleClass().add("navbar-button");
        btninventario.getStyleClass().add("navbar-button");
        btnmov.getStyleClass().add("navbar-button");

        HBox navbar = new HBox(20, btnprincipal, btncrud, btninventario, btnmov);
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

        centerContent.getChildren().setAll(vistaPrincipal);

        // Acciones navbar
        btnprincipal.setOnAction(e -> {
            centerContent.getChildren().setAll(vistaPrincipal);
            actualizarSeleccionNavbar(btnprincipal, btnprincipal, btncrud, btninventario, btnmov);
        });

        btncrud.setOnAction(e -> {
            centerContent.getChildren().setAll(vistaCrud);
            actualizarSeleccionNavbar(btncrud, btnprincipal, btncrud, btninventario, btnmov);
        });

        btninventario.setOnAction(e -> {
            centerContent.getChildren().setAll(vistaInventario);
            actualizarSeleccionNavbar(btninventario, btnprincipal, btncrud, btninventario, btnmov);
        });

        btnmov.setOnAction(e -> {
            centerContent.getChildren().setAll(vistaMovimientos);
            actualizarSeleccionNavbar(btnmov, btnprincipal, btncrud, btninventario, btnmov);
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

    // Totales (por ahora fijos; luego podemos calcularlos)
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

    // ==== CONTENIDO CENTRAL (cambia según el botón) ====
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
    // -------------------------------------------------------------------------
    // MAIN
    // -------------------------------------------------------------------------
    public static void main(String[] args) {
        launch(args);
    }
}
