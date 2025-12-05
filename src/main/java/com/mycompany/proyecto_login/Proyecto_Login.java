package com.mycompany.proyecto_login;

import static com.mycompany.proyecto_login.Rol.ADMIN;
import static com.mycompany.proyecto_login.Rol.CAJA;
import static com.mycompany.proyecto_login.Rol.OPERADOR;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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

    Scene scene = new Scene(root, 900, 600);  // tam inicial
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
        } else if (cas.getNum() == 1){
            lblMensaje.setText("");
             usuarioActual = cas.getUsuario();
            mostrarPantallaRol(stage,usuarioActual);
        }
        else if(cas.getNum() == 2){
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

    Label lblSub = new Label("Su usuario fue creado recientemente. \nConfigure su contraseña.");
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

    Button btnguardar= new Button("Guardar");
    btnguardar.getStyleClass().add("button-primary");
    
    Button btnVolver = new Button("Volver al login");
    btnVolver.getStyleClass().add("button-secondary");

    HBox contBotones = new HBox(10, btnguardar,btnVolver);
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

    Scene scene = new Scene(root, 900, 600);  // tam inicial
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
    
    btnVolver.setOnAction(e -> {
mostrarPantallaLogin(stage);
            });
    

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
    cbRol.getItems().addAll(Rol.ADMIN, Rol.CAJA, Rol.OPERADOR);
    cbRol.getSelectionModel().select(Rol.OPERADOR);
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
            cbRol.getSelectionModel().select(Rol.OPERADOR);
        } else {
            lblMensaje.getStyleClass().setAll("mensaje", "mensaje-error");
            lblMensaje.setText("No se pudo crear el usuario.");
        }
    });

   btnVolver.setOnAction(e -> mostrarPantallaRol(stage, usuarioActual));
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

    Label lblCrear = new Label("Crear usuario");
    lblCrear.setStyle("-fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 4 12 4 12;");
    HBox crearBox = new HBox(lblCrear);

    Label lblCerrar = new Label("Cerrar sesión");
    lblCerrar.setStyle("-fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 4 12 4 12;");
    HBox cerrarBox = new HBox(lblCerrar);

    crearBox.setOnMouseClicked(e -> {
        userMenu.hide();
        mostrarPantallaRegistro(stage);
    });

    cerrarBox.setOnMouseClicked(e -> {
        userMenu.hide();
        mostrarPantallaLogin(stage);
    });

    userMenu.getItems().addAll(
            new CustomMenuItem(crearBox, true),
            new CustomMenuItem(cerrarBox, true)
    );

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

    // ==== CONTENEDOR CENTRAL (donde cambiaremos el contenido) ====
    VBox centerContent = new VBox(20);
    centerContent.setPadding(new Insets(30));

    // Vistas para cada opción del menú
    VBox vistaPrincipal = crearVistaAdminPrincipal(usuario);
    VBox vistaCrud = crearVistaAdminCrud();
    VBox vistaInventario = crearVistaAdminInventario();
    VBox vistaMovimientos = crearVistaAdminMovimientos();

    // Vista inicial
    centerContent.getChildren().setAll(vistaPrincipal);

    // ==== ACCIONES DE LOS BOTONES DEL NAVBAR ====
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
// Marca un boton del navbar como seleccionado y limpia los demas
private void actualizarSeleccionNavbar(Button seleccionado, Button... todos) {
    for (Button b : todos) {
        b.getStyleClass().remove("navbar-button-selected");
    }
    if (!seleccionado.getStyleClass().contains("navbar-button-selected")) {
        seleccionado.getStyleClass().add("navbar-button-selected");
    }
}
// ====== VISTA: MENU PRINCIPAL ======
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
    lblCerrar.setStyle("-fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 4 12 4 12;");
    HBox cerrarBox = new HBox(lblCerrar);

    cerrarBox.setOnMouseClicked(e -> {
        userMenu.hide();
        mostrarPantallaLogin(stage);
    });

    userMenu.getItems().add(new CustomMenuItem(cerrarBox, true));

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

    // ==== CONTENIDO CENTRAL ====
    VBox centerContent = new VBox(20);
    centerContent.setPadding(new Insets(30));

    // Card principal
    VBox card = new VBox(10);
    card.getStyleClass().add("card");

    Label lblTitulo = new Label("Caja - Punto de venta");
    lblTitulo.getStyleClass().add("subtitulo");

    Label lblInfo = new Label("Registra pagos, genera tickets y controla el flujo de efectivo del día.");
    lblInfo.getStyleClass().add("descripcion");
    lblInfo.setWrapText(true);

    card.getChildren().addAll(lblTitulo, lblInfo);

    centerContent.getChildren().add(card);

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
    lblCerrar.setStyle("-fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 4 12 4 12;");
    HBox cerrarBox = new HBox(lblCerrar);

    cerrarBox.setOnMouseClicked(e -> {
        userMenu.hide();
        mostrarPantallaLogin(stage);
    });

    userMenu.getItems().add(new CustomMenuItem(cerrarBox, true));

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

    // ==== CONTENIDO CENTRAL ====
    VBox centerContent = new VBox(20);
    centerContent.setPadding(new Insets(30));

    VBox card = new VBox(10);
    card.getStyleClass().add("card");

    Label lblTitulo = new Label("Operador - Gestión de servicio");
    lblTitulo.getStyleClass().add("subtitulo");

    Label lblInfo = new Label("Gestiona las mesas, las comandas y el flujo de pedidos hacia cocina.");
    lblInfo.getStyleClass().add("descripcion");
    lblInfo.setWrapText(true);

    card.getChildren().addAll(lblTitulo, lblInfo);
    centerContent.getChildren().add(card);

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


public static void main(String[] args) {
    launch(args);
}

}
