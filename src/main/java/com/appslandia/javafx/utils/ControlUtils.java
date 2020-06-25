// The MIT License (MIT)
// Copyright © 2015 AppsLandia. All rights reserved.

// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:

// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package com.appslandia.javafx.utils;

import java.util.function.Consumer;

import com.appslandia.javafx.base.FXController;
import com.appslandia.javafx.base.LoaderImpl;
import com.appslandia.javafx.base.TabFirstOpen;
import com.appslandia.javafx.base.TabUserData;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class ControlUtils {

	public static Tab loadTab(String fxml, String text, FXController controller, Class<?> resourceClass) {
		LoaderImpl loader = new LoaderImpl().location(resourceClass.getResource(fxml));
		loader.setController(controller);
		Tab tab = (Tab) loader.loadControl();

		if (controller instanceof TabFirstOpen) {

			onTabFirstOpen(tab, t -> {
				((TabFirstOpen) controller).onTabFirstOpen(t);
			});
		}
		if (text != null) {
			tab.setText(text);
		}
		return tab;
	}

	public static void onTabFirstOpen(Tab tab, Consumer<Tab> handler) {
		tab.setOnSelectionChanged((e) -> {
			if (tab.isSelected()) {
				TabUserData userData = (TabUserData) tab.getUserData();

				if ((userData == null) || !userData.isInitialized()) {

					if (userData != null) {
						userData.setInitialized(true);
					} else {
						tab.setUserData(new TabUserData().setInitialized(true));
					}
					handler.accept(tab);
				}
			}
		});
	}

	public static <T> T loadControl(String fxml, FXController controller, Class<?> resourceClass) {
		LoaderImpl loader = new LoaderImpl().location(resourceClass.getResource(fxml));
		loader.setController(controller);
		return loader.loadControl();
	}

	public static void requestFocusAndLastCaret(TextInputControl textInput) {
		textInput.requestFocus();
		textInput.positionCaret(textInput.getText().length());
	}

	public static void bindEnter(Button... buttons) {
		for (Button btn : buttons) {
			btn.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER) {
					btn.fire();
				}
			});
		}
	}

	public static void bindEnter(Node source, Button action) {
		source.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				action.fire();
			}
		});
	}

	public static Window getWindow(Object obj) {
		if (obj instanceof Window) {
			return (Window) obj;
		}
		if (obj instanceof Node) {
			Scene s = ((Node) obj).getScene();
			return (s != null) ? s.getWindow() : null;
		}
		throw new IllegalArgumentException();
	}

	public static void setSizeAndPos(Stage window, double wFactor, double hFactor) {
		Rectangle2D ss = Screen.getPrimary().getVisualBounds();

		int width = (int) (ss.getWidth() * wFactor);
		int height = (int) (ss.getHeight() * hFactor);

		window.setWidth(width);
		window.setHeight(height);

		int posX = (int) ((ss.getWidth() - width) / 2);
		int posY = (int) ((ss.getHeight() - height) / 2);

		window.setX(posX);
		window.setY(posY);
	}
}
