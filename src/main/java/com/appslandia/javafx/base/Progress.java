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

import java.util.concurrent.atomic.AtomicReference;

import com.appslandia.javafx.utils.Resources;

import javafx.application.Platform;
import javafx.stage.Window;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public abstract class Progress extends DialogImpl<Boolean> {

	protected final static AtomicReference<Progress> CURRENT = new AtomicReference<>();

	public static void remove() {
		CURRENT.updateAndGet((current) -> {
			if (current != null) {
				current.setResult(Boolean.TRUE);
				current.close();
			}
			return null;
		});
	}

	public static boolean hasCurrent() {
		return CURRENT.get() != null;
	}

	public static void postStatus(String message) {
		Progress dlg = CURRENT.get();
		if (dlg == null) {
			return;
		}
		if (Platform.isFxApplicationThread()) {
			if (dlg.getWindow() != null) {
				dlg.status(message);
			}
		} else {
			Platform.runLater(() -> {
				if (dlg.getWindow() != null) {
					dlg.status(message);
				}
			});
		}
	}

	public static void postStatusRes(String resKey) {
		postStatus(Resources.getString(resKey));
	}

	public static void postStatusRes(String resKey, Object... params) {
		postStatus(Resources.getString(resKey, params));
	}

	public static void postStatus(double workDone, double max, String message) {
		Progress dlg = CURRENT.get();
		if (dlg == null) {
			return;
		}
		if (Platform.isFxApplicationThread()) {
			if (dlg.getWindow() != null) {
				dlg.status(workDone, max, message);
			}
		} else {
			Platform.runLater(() -> {
				if (dlg.getWindow() != null) {
					dlg.status(workDone, max, message);
				}
			});
		}
	}

	public static void postStatusRes(double workDone, double max, String resKey) {
		postStatus(workDone, max, Resources.getString(resKey));
	}

	public static void postStatusRes(double workDone, double max, String resKey, Object... params) {
		postStatus(workDone, max, Resources.getString(resKey, params));
	}

	public static void postStatus(double workDone, double max) {
		Progress dlg = CURRENT.get();
		if (dlg == null) {
			return;
		}
		if (Platform.isFxApplicationThread()) {
			if (dlg.getWindow() != null) {
				dlg.status(workDone, max);
			}
		} else {
			Platform.runLater(() -> {
				if (dlg.getWindow() != null) {
					dlg.status(workDone, max);
				}
			});
		}
	}

	public Progress() {
		CURRENT.set(this);
	}

	public abstract Progress status(String message);

	public Progress statusRes(String resKey) {
		return status(Resources.getString(resKey));
	}

	public Progress statusRes(String resKey, Object... params) {
		return status(Resources.getString(resKey, params));
	}

	public abstract Progress status(double workDone, double max, String message);

	public Progress statusRes(double workDone, double max, String resKey) {
		return status(workDone, max, Resources.getString(resKey));
	}

	public Progress statusRes(double workDone, double max, String resKey, Object... params) {
		return status(workDone, max, Resources.getString(resKey, params));
	}

	public abstract Progress status(double workDone, double max);

	public Progress owner(Window owner) {
		initOwner(owner);
		return this;
	}

	public Progress showAsync() {
		show();
		return this;
	}
}
