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

import java.util.function.BiFunction;
import java.util.function.Consumer;

import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.Printer.MarginType;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.transform.Scale;
import javafx.stage.Window;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class PrinterUtils {

	public static void printPage(Node pageNode, String jobName, Window owner, Consumer<JobSettings> settingsInit, BiFunction<Printer, PageOrientation, PageLayout> layoutInit) {
		Printer printer = Printer.getDefaultPrinter();
		if (printer == null) {
			return;
		}
		PrinterJob job = PrinterJob.createPrinterJob(printer);
		if (job == null) {
			return;
		}
		if (!job.showPrintDialog(owner)) {
			return;
		}
		JobSettings settings = job.getJobSettings();
		settings.setCopies(1);
		settings.setJobName(jobName);

		double nbWidth = pageNode.getBoundsInParent().getWidth();
		double nbHeight = pageNode.getBoundsInParent().getHeight();

		PageOrientation orient = (nbWidth > nbHeight) ? PageOrientation.LANDSCAPE : PageOrientation.PORTRAIT;
		PageLayout layout = (layoutInit != null) ? layoutInit.apply(printer, orient) : printer.createPageLayout(Paper.NA_LETTER, orient, MarginType.DEFAULT);
		settings.setPageLayout(layout);

		if (settingsInit != null) {
			settingsInit.accept(settings);
		}

		double scaleX = (nbWidth != 0.0) ? (layout.getPrintableWidth() / nbWidth) : 1.0;
		double scaleY = (nbHeight != 0.0) ? layout.getPrintableHeight() / nbHeight : 1.0;

		double scaleMin = Math.min(scaleX, scaleY);
		Scale scale = (scaleMin < 1.0) ? new Scale(scaleMin, scaleMin) : null;

		if (scale != null) {
			pageNode.getTransforms().add(scale);
		}
		if (job.printPage(layout, pageNode)) {
			job.endJob();
		}
		if (scale != null) {
			pageNode.getTransforms().remove(scale);
		}
	}
}
