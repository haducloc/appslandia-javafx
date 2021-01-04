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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Set;

import com.appslandia.common.utils.AssertUtils;
import com.appslandia.common.utils.StringFormat;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class Resources {

	private static final ResourceBundle LBundle = ResourceBundle.getBundle("com/appslandia/javafx/utils/Resources");

	private static volatile ResourceBundle bundle;
	private static final Object MUTEX = new Object();

	public static final String APP_TITLE = "app.title";

	public static ResourceBundle getBundle() {
		ResourceBundle obj = bundle;
		if (obj == null) {
			synchronized (MUTEX) {
				if ((obj = bundle) == null) {
					ResourceBundle b = ResourceBundle.getBundle("application");
					bundle = obj = new ResourceBundleImpl(b, LBundle);
				}
			}
		}
		return obj;
	}

	public static void setBundle(ResourceBundle bundle) {
		AssertUtils.assertNull(Resources.bundle);
		Resources.bundle = new ResourceBundleImpl(bundle, LBundle);
	}

	public static String getString(String key) {
		return getBundle().getString(key);
	}

	public static String getString(String key, Object... params) {
		return StringFormat.format(getBundle().getString(key), params);
	}

	static class ResourceBundleImpl extends ResourceBundle {
		final Map<String, Object> lookup;

		public ResourceBundleImpl(ResourceBundle bundle, ResourceBundle parent) {
			lookup = toMap(bundle);
			this.parent = AssertUtils.assertNotNull(parent);
		}

		@Override
		public Object handleGetObject(String key) {
			AssertUtils.assertNotNull(key);
			return lookup.get(key);
		}

		@Override
		public Enumeration<String> getKeys() {
			return new ResourceBundleEnumeration(lookup.keySet(), parent.getKeys());
		}

		@Override
		protected Set<String> handleKeySet() {
			return lookup.keySet();
		}

		static Map<String, Object> toMap(ResourceBundle bundle) {
			Map<String, Object> m = new HashMap<>();
			Enumeration<String> keys = bundle.getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				m.put(key, bundle.getObject(key));
			}
			return m;
		}
	}

	static class ResourceBundleEnumeration implements Enumeration<String> {
		final Set<String> keySet;
		final Iterator<String> keys;
		final Enumeration<String> parentKeys;
		private String next = null;

		public ResourceBundleEnumeration(Set<String> keySet, Enumeration<String> parentKeys) {
			this.keySet = keySet;
			this.keys = keySet.iterator();
			this.parentKeys = parentKeys;
		}

		@Override
		public boolean hasMoreElements() {
			if (this.next == null) {
				if (this.keys.hasNext()) {
					this.next = this.keys.next();
				} else {
					while (this.next == null && this.parentKeys.hasMoreElements()) {
						this.next = this.parentKeys.nextElement();
						if (this.keySet.contains(this.next)) {
							this.next = null;
						}
					}
				}
			}
			return this.next != null;
		}

		@Override
		public String nextElement() {
			if (this.hasMoreElements()) {
				String key = this.next;
				this.next = null;
				return key;
			} else {
				throw new NoSuchElementException();
			}
		}
	}
}
