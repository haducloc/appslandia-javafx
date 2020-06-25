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

package com.appslandia.javafx.base;

import java.lang.ref.WeakReference;

import com.appslandia.common.utils.AssertUtils;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public abstract class FXApplication extends Application {

	private static WeakReference<Stage> windowRef;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			doStart(primaryStage);
			initUncaughtExceptionHandler(primaryStage);

			windowRef = new WeakReference<>(primaryStage);
		} catch (Exception ex) {
			new ExceptionDialog(ex).showAsync();
		}
	}

	protected abstract void doStart(Stage primaryStage) throws Exception;

	protected void initUncaughtExceptionHandler(Stage primaryStage) {
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			Platform.runLater(() -> {
				new ExceptionDialog(e).owner(primaryStage).showAsync();
			});
			logUncaughtException(e);
		});
	}

	protected void logUncaughtException(Throwable e) {
	}

	public static Stage getWindow() {
		AssertUtils.assertNotNull(windowRef);
		return windowRef.get();
	}
}
