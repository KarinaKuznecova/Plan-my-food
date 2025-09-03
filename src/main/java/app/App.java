package app;

import app.graphics.Rectangle;
import app.graphics.RenderHandler;
import app.loading.LoadingService;
import app.repository.JsonProductsRepository;
import app.screens.*;
import app.screens.components.MenuComponent;
import app.service.MealService;
import app.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static app.screens.events.ChangeScreenButtonEvent.*;

public class App extends JFrame implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private final Canvas canvas = new Canvas();

    private LoadingService loadingService;

    private RenderHandler renderHandler;

    private AppScreen currentScreen;
    private MainScreen mainScreen;
    private ProductsScreen productsScreen;
    private NewProductScreen newProductScreen;
    private MealsScreen mealsScreen;

    private ProductService productService;
    private MealService mealService;

    public static void main(String[] args) {
        new App();
    }

    private App() {
        initializeServices();
        initializeScreens();
        startThread();
    }

    // Initialization methods

    private void initializeServices() {
        logger.info("Initializing services...");
        loadingService = new LoadingService();
        loadingService.loadUI(this);
        loadingService.getControllersLoadingService().loadListeners(this, canvas);

        renderHandler = new RenderHandler(this);

        productService = new ProductService(new JsonProductsRepository());
        mealService = new MealService();
    }

    private void initializeScreens() {
        logger.info("Initializing screens...");

        MenuComponent menuComponent = createMainMenu();
        mainScreen = new MainScreen(menuComponent);
        productsScreen = new ProductsScreen(this, menuComponent);
        newProductScreen = new NewProductScreen(this, menuComponent);
        mealsScreen = new MealsScreen(menuComponent);

        setCurrentScreen(getMainScreen());
    }

    private MenuComponent createMainMenu() {
        Rectangle mainMenuRectangle = new Rectangle(0, 0, getWidth(), 60);
        Map<String, String> buttonNames = new LinkedHashMap<>();
        buttonNames.put("Main", MAIN_SCREEN);
        buttonNames.put("Products", PRODUCTS_SCREEN);
        buttonNames.put("Meals", MEALS_SCREEN);
        buttonNames.put("Settings", SETTINGS_SCREEN);
        buttonNames.put("Quit", QUIT);
        return new MenuComponent(this, mainMenuRectangle, buttonNames, 150);
    }

    // main loop methods

    private void startThread() {
        logger.info("Starting main loop...");
        add(canvas);
        canvas.createBufferStrategy(3);
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        long lastTime = System.nanoTime(); //long 2^63
        double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second
        double changeInSeconds = 0;

        while (true) {
            long now = System.nanoTime();

            changeInSeconds += (now - lastTime) / nanoSecondConversion;
            while (changeInSeconds >= 1) {
                update();
                changeInSeconds--;
            }

            render();

            lastTime = now;
        }
    }

    private void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        Graphics2D graphics2D = (Graphics2D) graphics;
        super.paintComponents(graphics);

        currentScreen.render(this, getRenderHandler(), graphics2D);

        graphics2D.dispose();
        bufferStrategy.show();
    }

    private void update() {
        currentScreen.update(this);
    }

    public void leftClick(int xScreenRelated, int yScreenRelated, boolean doubleClick) {
        currentScreen.leftClick(this, xScreenRelated, yScreenRelated, doubleClick);
    }

    public void rightClick(int xScreenRelated, int yScreenRelated) {
        currentScreen.rightClick(this, xScreenRelated, yScreenRelated);
    }
    
    public void keyPressed(int keyCode, char keyChar) {
        currentScreen.keyPressed(this, keyCode, keyChar);
    }

    // Getters and Setters

    public AppScreen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(AppScreen currentScreen) {
        this.currentScreen = currentScreen;
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public ProductsScreen getProductsScreen() {
        return productsScreen;
    }

    public NewProductScreen getNewProductScreen() {
        return newProductScreen;
    }

    public MealsScreen getMealsScreen() {
        return mealsScreen;
    }

    public RenderHandler getRenderHandler() {
        return renderHandler;
    }

    public ProductService getProductService() {
        return productService;
    }

    public MealService getMealService() {
        return mealService;
    }
}
