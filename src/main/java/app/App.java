package app;

import app.graphics.Rectangle;
import app.graphics.RenderHandler;
import app.loading.LoadingService;
import app.screens.AppScreen;
import app.screens.MainScreen;
import app.screens.MealsScreen;
import app.screens.ProductsScreen;
import app.screens.components.MenuComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class App extends JFrame implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private final Canvas canvas = new Canvas();

    private LoadingService loadingService;

    private RenderHandler renderHandler;

    private AppScreen currentScreen;
    private MainScreen mainScreen;
    private ProductsScreen productsScreen;
    private MealsScreen mealsScreen;

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
    }

    private void initializeScreens() {
        logger.info("Initializing screens...");

        MenuComponent menuComponent = new MenuComponent(new Rectangle(0, 0, getWidth(), 80));
        mainScreen = new MainScreen(menuComponent);
        productsScreen = new ProductsScreen(menuComponent);
        mealsScreen = new MealsScreen(menuComponent);

        setCurrentScreen(getMainScreen());
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

    public MealsScreen getMealsScreen() {
        return mealsScreen;
    }

    public RenderHandler getRenderHandler() {
        return renderHandler;
    }
}
