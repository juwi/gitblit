/*
 * Copyright 2011 gitblit.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gitblit.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.gitblit.IStoredSettings;

/**
 * Table model of IStoredSettings.
 * 
 * @author James Moger
 * 
 */
public class SettingsModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	IStoredSettings settings;

	List<String> keys;

	enum Columns {
		Name, Value;

		@Override
		public String toString() {
			return name().replace('_', ' ');
		}
	}

	public SettingsModel() {
		this(null);
	}

	public SettingsModel(IStoredSettings settings) {
		setSettings(settings);
	}

	public void setSettings(IStoredSettings settings) {
		this.settings = settings;
		if (settings == null) {
			keys = new ArrayList<String>();
		} else {
			keys = new ArrayList<String>(settings.getAllKeys(null));
			Collections.sort(keys);
		}
	}

	@Override
	public int getRowCount() {
		return keys.size();
	}

	@Override
	public int getColumnCount() {
		return Columns.values().length;
	}

	@Override
	public String getColumnName(int column) {
		Columns col = Columns.values()[column];
		switch (col) {
		case Name:
			return Translation.get("gb.name");
		}
		return "";
	}

	/**
	 * Returns <code>Object.class</code> regardless of <code>columnIndex</code>.
	 * 
	 * @param columnIndex
	 *            the column being queried
	 * @return the Object.class
	 */
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String key = keys.get(rowIndex);
		Columns col = Columns.values()[columnIndex];
		switch (col) {
		case Name:
			return key;
		case Value:
			return settings.getString(key, "");
		}
		return null;
	}
}
