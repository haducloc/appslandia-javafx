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

import java.io.File;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class FXClipboard {

	final ClipboardContent content = new ClipboardContent();

	public FXClipboard store() {
		Clipboard.getSystemClipboard().setContent(this.content);
		return this;
	}

	public ClipboardContent getContent() {
		return this.content;
	}

	public FXClipboard put(DataFormat format, Object data) {
		this.content.put(format, data);
		return this;
	}

	public FXClipboard putString(String s) {
		this.content.putString(s);
		return this;
	}

	public FXClipboard putUrl(String url) {
		this.content.putUrl(url);
		return this;
	}

	public FXClipboard putHtml(String html) {
		this.content.putHtml(html);
		return this;
	}

	public FXClipboard putRtf(String rtf) {
		this.content.putRtf(rtf);
		return this;
	}

	public FXClipboard putImage(Image i) {
		this.content.putImage(i);
		return this;
	}

	public FXClipboard putFiles(List<File> files) {
		this.content.putFiles(files);
		return this;
	}

	public static Object getContent(DataFormat format) {
		return Clipboard.getSystemClipboard().getContent(format);
	}

	public static String getString() {
		return Clipboard.getSystemClipboard().getString();
	}

	public static String getUrl() {
		return Clipboard.getSystemClipboard().getUrl();
	}

	public static String getHtml() {
		return Clipboard.getSystemClipboard().getHtml();
	}

	public static String getRtf() {
		return Clipboard.getSystemClipboard().getRtf();
	}

	public static Image getImage() {
		return Clipboard.getSystemClipboard().getImage();
	}

	public static List<File> getFiles() {
		return Clipboard.getSystemClipboard().getFiles();
	}
}
