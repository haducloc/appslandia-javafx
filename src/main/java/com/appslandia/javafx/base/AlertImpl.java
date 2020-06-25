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

import java.util.function.Consumer;

import com.appslandia.javafx.utils.ButtonUtils;
import com.appslandia.javafx.utils.ControlUtils;
import com.appslandia.javafx.utils.Resources;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class AlertImpl extends Alert {

	private ButtonType focus;

	public AlertImpl(AlertType alertType, ButtonType... buttons) {
		super(alertType, null, buttons);
		titleRes(Resources.APP_TITLE);

		for (ButtonType type : buttons) {
			Button btn = (Button) getDialogPane().lookupButton(type);
			btn.setDefaultButton(false);

			ButtonUtils.localize(btn, type);
			ControlUtils.bindEnter(btn);
		}
		setOnShown(this::onDialogShown);
	}

	protected void onDialogShown(DialogEvent e) {
		if (focus != null) {
			Button btn = (Button) getDialogPane().lookupButton(focus);
			btn.requestFocus();
		}
	}

	public AlertImpl title(String value) {
		setTitle(value);
		return this;
	}

	public AlertImpl titleRes(String value) {
		setTitle(Resources.getString(value));
		return this;
	}

	public AlertImpl titleRes(String value, Object... params) {
		setTitle(Resources.getString(value, params));
		return this;
	}

	public AlertImpl headerText(String value) {
		setHeaderText(value);
		return this;
	}

	public AlertImpl headerTextRes(String value) {
		setHeaderText(Resources.getString(value));
		return this;
	}

	public AlertImpl headerTextRes(String value, Object... params) {
		setHeaderText(Resources.getString(value, params));
		return this;
	}

	public AlertImpl contentText(String value) {
		setContentText(value);
		return this;
	}

	public AlertImpl contentTextRes(String value) {
		setContentText(Resources.getString(value));
		return this;
	}

	public AlertImpl contentTextRes(String value, Object... params) {
		setContentText(Resources.getString(value, params));
		return this;
	}

	public AlertImpl focus(ButtonType value) {
		this.focus = value;
		return this;
	}

	public AlertImpl result(Consumer<ButtonType> handler) {
		resultProperty().addListener((observable, oldValue, newValue) -> {
			handler.accept(newValue);
		});
		return this;
	}

	public AlertImpl modality(Modality value) {
		initModality(value);
		return this;
	}

	public AlertImpl owner(Window owner) {
		initOwner(owner);
		return this;
	}

	public AlertImpl action(ButtonType type, Consumer<AlertImpl> handler) {
		Button btn = (Button) getDialogPane().lookupButton(type);
		btn.addEventFilter(ActionEvent.ACTION, e -> {
			e.consume();
			handler.accept(AlertImpl.this);
		});
		return this;
	}

	public AlertImpl showAsync() {
		show();
		return this;
	}

	public Window getWindow() {
		Scene s = getDialogPane().getScene();
		return (s != null) ? s.getWindow() : null;
	}

	public static AlertImpl error(Window owner) {
		return new AlertImpl(AlertType.ERROR, ButtonType.OK).owner(owner);
	}

	public static AlertImpl info(Window owner) {
		return new AlertImpl(AlertType.INFORMATION, ButtonType.OK).owner(owner);
	}

	public static AlertImpl warn(Window owner) {
		return new AlertImpl(AlertType.WARNING, ButtonType.OK).owner(owner);
	}

	public static AlertImpl confirm(Window owner) {
		return new AlertImpl(AlertType.CONFIRMATION, ButtonType.YES, ButtonType.NO).owner(owner).focus(ButtonType.NO);
	}
}
