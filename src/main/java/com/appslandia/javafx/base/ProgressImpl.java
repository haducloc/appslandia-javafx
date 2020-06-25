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

import com.appslandia.javafx.utils.Resources;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.StageStyle;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class ProgressImpl extends Progress {

	protected final Label statusLabel;
	protected final ProgressIndicator indicator;

	public ProgressImpl() {
		this(false);
	}

	public ProgressImpl(boolean progressBar) {
		initStyle(StageStyle.UTILITY);

		statusLabel = new Label(Resources.getString("text.please_wait"));
		if (progressBar) {
			indicator = new ProgressBar(ProgressBar.INDETERMINATE_PROGRESS);
			indicator.setMaxWidth(Double.POSITIVE_INFINITY);
		} else {
			indicator = new ProgressIndicator(ProgressBar.INDETERMINATE_PROGRESS);
		}

		Node root = null;
		if (progressBar) {
			root = new VBoxBuilder().spacing(10).fillWidth(true).alignment(Pos.TOP_LEFT).children(statusLabel, indicator).getControl();
		} else {
			root = new HBoxBuilder().spacing(10).alignment(Pos.CENTER_LEFT).children(indicator, statusLabel).getControl();
		}
		contentNode(root);
	}

	@Override
	public Progress status(String message) {
		statusLabel.setText(message);
		getWindow().sizeToScene();
		return this;
	}

	@Override
	public Progress status(double workDone, double max) {
		indicator.setProgress(workDone / max);
		getWindow().sizeToScene();
		return this;
	}

	@Override
	public Progress status(double workDone, double max, String message) {
		statusLabel.setText(message);
		indicator.setProgress(workDone / max);
		getWindow().sizeToScene();
		return this;
	}
}
