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

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class ButtonUtils {

	public static ButtonType create(String textRes) {
		return new ButtonType(Resources.getString(textRes), ButtonData.OK_DONE);
	}

	public static ButtonType create(String textRes, ButtonData buttonData) {
		return new ButtonType(Resources.getString(textRes), buttonData);
	}

	public static void localize(Button button, ButtonType type) {
		if (type == ButtonType.OK) {
			button.setText(Resources.getString("action.ok"));
			return;
		}
		if (type == ButtonType.CANCEL) {
			button.setText(Resources.getString("action.cancel"));
			return;
		}
		if (type == ButtonType.YES) {
			button.setText(Resources.getString("action.yes"));
			return;
		}
		if (type == ButtonType.NO) {
			button.setText(Resources.getString("action.no"));
			return;
		}
		if (type == ButtonType.CLOSE) {
			button.setText(Resources.getString("action.close"));
			return;
		}
		if (type == ButtonType.APPLY) {
			button.setText(Resources.getString("action.apply"));
			return;
		}
		if (type == ButtonType.FINISH) {
			button.setText(Resources.getString("action.finish"));
			return;
		}
		if (type == ButtonType.NEXT) {
			button.setText(Resources.getString("action.next"));
			return;
		}
		if (type == ButtonType.PREVIOUS) {
			button.setText(Resources.getString("action.previous"));
			return;
		}
	}
}
