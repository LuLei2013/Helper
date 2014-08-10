package com.lu.lei;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.sikuli.api.DesktopScreenLocation;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.robot.desktop.DesktopScreen;
import org.sikuli.api.visual.DesktopCanvas;

public class SikuliImpl {
	private Mouse mouse;
	private ScreenRegion sr;
	private DesktopCanvas canvas;

	public SikuliImpl() {
		sr = new DesktopScreenRegion();
		sr.setScore(0.85);
		sr.setScreen(new DesktopScreen(0));
		mouse = new DesktopMouse();
		canvas = new DesktopCanvas();
	}

	public void wait(int sec) {
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public List<ScreenRegion> findAll(Target target) {
		return sr.findAll(target);
	}

	public void click(int x, int y) {
		mouse.click(new DesktopScreenLocation(x, y));
	}

	public void clearCanvas() {
		canvas.clear();
	}

	public void addBox(ScreenRegion region) {
		canvas.addBox(region).display(2);
	}

	public ScreenRegion find(String imgPath) {
		return sr.wait(new ImageTarget(new File(imgPath)), 15000);
	}
}
