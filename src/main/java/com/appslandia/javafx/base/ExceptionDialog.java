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

import com.appslandia.common.utils.ExceptionUtils;
import com.appslandia.javafx.utils.ButtonUtils;
import com.appslandia.javafx.utils.Resources;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class ExceptionDialog extends DialogImpl<ButtonType> {

	public ExceptionDialog(Throwable exception) {
		initButton(ButtonUtils.create("action.ok"), true, () -> {
			close();
		});

		Label contentLabel = new Label(ExceptionUtils.buildMessage(exception));
		contentNode(contentLabel);

		Label stLabel = new Label(Resources.getString("label.exception_stacktrace"));
		TextArea exTextArea = new TextArea(ExceptionUtils.toStackTrace(exception));

		exTextArea.setEditable(false);
		exTextArea.setWrapText(true);

		exTextArea.setMaxWidth(Double.MAX_VALUE);
		exTextArea.setMaxHeight(Double.MAX_VALUE);

		GridPane.setVgrow(exTextArea, Priority.ALWAYS);
		GridPane.setHgrow(exTextArea, Priority.ALWAYS);

		GridPane root = new GridPane();
		root.setVgap(8);
		root.setMaxWidth(Double.MAX_VALUE);

		root.add(stLabel, 0, 0);
		root.add(exTextArea, 0, 1);
		expandableContentNode(root);
	}
}
