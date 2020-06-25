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

package com.appslandia.javafx.tasks;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import com.appslandia.common.threading.DaemonThreadFactory;
import com.appslandia.common.utils.AssertUtils;
import com.appslandia.javafx.base.Progress;
import com.appslandia.javafx.base.ProgressImpl;

import javafx.concurrent.Task;
import javafx.stage.Window;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public abstract class AsyncTask extends Task<Object> {

	protected final WeakReference<TaskCallbacks> callbacksRef;
	protected final int taskId;

	protected Object[] args;
	protected boolean progress;

	public AsyncTask(TaskCallbacks callbacks, int taskId) {
		this.callbacksRef = new WeakReference<>(callbacks);
		this.taskId = taskId;
	}

	public AsyncTask args(Object... args) {
		this.args = args;
		return this;
	}

	@Override
	protected void succeeded() {
		super.succeeded();
		if (this.progress) {
			removeProgress();
		}
		TaskCallbacks callbacks = callbacksRef.get();
		if (callbacks != null) {
			callbacks.onTaskCompleted(taskId, args, getValue());
		}
	}

	@Override
	protected void failed() {
		super.failed();
		if (this.progress) {
			removeProgress();
		}
		TaskCallbacks callbacks = callbacksRef.get();
		if (callbacks != null) {
			callbacks.onTaskFailed(taskId, args, getException());
		}
	}

	@Override
	protected void cancelled() {
		super.cancelled();
		if (this.progress) {
			removeProgress();
		}
		TaskCallbacks callbacks = callbacksRef.get();
		if (callbacks != null) {
			callbacks.onTaskCancelled(taskId, args);
		}
	}

	public AsyncTask start() {
		TaskCallbacks callbacks = callbacksRef.get();
		AssertUtils.assertNotNull(callbacks);
		getExecutor().execute(this);
		return this;
	}

	public AsyncTask startProgress(Window owner) {
		this.progress = true;
		TaskCallbacks callbacks = callbacksRef.get();
		AssertUtils.assertNotNull(callbacks);

		showProgress(owner);
		getExecutor().execute(this);
		return this;
	}

	protected void showProgress(Window owner) {
		new ProgressImpl().owner(owner).showAsync();
	}

	protected void removeProgress() {
		Progress.remove();
	}

	private static volatile ExecutorService __instance;
	private static final Object MUTEX = new Object();

	public static ExecutorService getExecutor() {
		ExecutorService obj = __instance;
		if (obj == null) {
			synchronized (MUTEX) {
				if ((obj = __instance) == null) {
					__instance = obj = initExecutorService();
				}
			}
		}
		return obj;
	}

	public static void setExecutor(ExecutorService executor) {
		AssertUtils.assertNull(__instance);
		__instance = executor;
	}

	private static Supplier<ExecutorService> __provider;

	public static void setProvider(Supplier<ExecutorService> provider) {
		AssertUtils.assertNull(__instance);
		__provider = provider;
	}

	private static ExecutorService initExecutorService() {
		if (__provider != null) {
			return __provider.get();
		}
		return Executors.newFixedThreadPool(5, new DaemonThreadFactory());
	}
}
