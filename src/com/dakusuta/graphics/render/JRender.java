package com.dakusuta.graphics.render;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class JRender extends Application {
	public static double width = 0;
	public static double height = 0;
	public static GraphicsContext gc;
	private Stage primaryStage;
	private Canvas renderCanvas;
	private long updateFrequency = 0;
	private boolean showFPS = false;
	private String title = "Untitled";
	private Color background = Color.WHITE;
	protected double mouseX = 0;
	protected double mouseY = 0;
	public static double PI = Math.PI;
	public static double TAU = PI * 2;
	public static int[] pixels;

	private AnimationTimer timer = new AnimationTimer() {
		private long lastTime = System.nanoTime();
		int counter = 0;

		@Override
		public void handle(long now) {
			if (now - lastTime > updateFrequency) {
				try {
					update();
				} catch (Exception e) {
					e.printStackTrace();
					timer.stop();
				}
				lastTime = System.nanoTime();
			}

			if (showFPS) {
				long time = (now - lastTime) / 1000;
				if (time >= 60) {
					primaryStage.setTitle(title + " : " + (counter / (time / 60)) + "FPS");
					counter = 0;
					lastTime = System.nanoTime();
				}
			}
			try {
				render();
			} catch (Exception e) {
				e.printStackTrace();
				timer.stop();
			}
			counter++;
		}
	};

	protected void noLoop() {
		timer.stop();
	}

	protected void loop() {
		timer.start();
	}

	protected void onMouseClicked(MouseEvent e) {

	};

	@Override
	public void start(Stage primaryStage) throws Exception {
		preload();
		this.primaryStage = primaryStage;
		VBox hBox = new VBox();

		primaryStage.setOnCloseRequest(e -> {
			timer.stop();
		});
		Scene scene = new Scene(hBox);
		primaryStage.setScene(scene);
		primaryStage.setTitle(title);
		createCanvas(width, height);
		renderCanvas.setOnMouseMoved(e -> {
			mouseX = e.getX();
			mouseY = e.getY();
		});

		renderCanvas.setOnMouseClicked(this::onMouseClicked);
		gc = renderCanvas.getGraphicsContext2D();
		hBox.getChildren().add(renderCanvas);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		setup();
		primaryStage.show();
		timer.start();
	}

	protected void preload() {
	}

	protected void setup() {

	}

	protected void render() {

	}

	protected void update() {

	}

	private Canvas createCanvas(double d, double e) {
		renderCanvas = new Canvas(d, e);
		return renderCanvas;
	}

	protected void background(int gray) {
		background = Color.grayRgb(gray);
		Paint fill = gc.getFill();
		gc.setFill(background);
		gc.fillRect(0, 0, renderCanvas.getWidth(), renderCanvas.getHeight());
		gc.setFill(fill);
	}

	protected void background(int gray, double alpha) {
		alpha = (alpha <= 0) ? 0 : alpha / 255.0;
		background = Color.grayRgb(gray, alpha);
		Paint fill = gc.getFill();
		gc.setFill(background);
		gc.fillRect(0, 0, renderCanvas.getWidth(), renderCanvas.getHeight());
		gc.setFill(fill);
	}

	protected void setFullScreen() {
		if (primaryStage.isFullScreen()) {
			primaryStage.setFullScreen(false);
			return;
		}
		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreen(true);
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
		width = primaryScreenBounds.getWidth();
		height = primaryScreenBounds.getHeight();
		renderCanvas.setWidth(width);
		renderCanvas.setHeight(height);
	}

	protected void setSize(double width, double height) {
		JRender.width = width;
		JRender.height = height;
		primaryStage.setWidth(width);
		primaryStage.setHeight(height);
		renderCanvas.setWidth(width);
		renderCanvas.setHeight(height);
	}

	protected void setTitle(String title) {
		this.title = title;
		primaryStage.setTitle(title);
	}

	protected String getTitle() {
		return title;
	}

	public static double sin(double value) {
		return Math.sin(value);
	}

	public static double cos(double value) {
		return Math.cos(value);
	}

	public static double tan(double value) {
		return Math.tan(value);
	}

	public static double asin(double value) {
		return Math.asin(value);
	}

	public static double acos(double value) {
		return Math.acos(value);
	}

	public static double atan(double value) {
		return Math.atan(value);
	}

	public static double atan2(double x, double y) {
		return Math.atan2(x, y);
	}

}
