package com.tangguna.imageswitcher.library.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class BaseArrayAdapter<T, H> extends BaseAdapter {

	/**
	 * Contains the list of objects that represent the data_picker_dialog of this
	 * ArrayAdapter. The content of this list is referred to as "the array" in
	 * the documentation.
	 */
	private List<T> mObjects;

	/**
	 * Lock used to modify the content of {@link #mObjects}. Any write operation
	 * performed on the array should be synchronized on this lock. This lock is
	 * also used by the filter (see {@link #getFilter()} to make a synchronized
	 * copy of the original array of data_picker_dialog.
	 */
	private final Object mLock = new Object();

	/**
	 * The resource indicating what views to inflate to display the content of
	 * this array adapter.
	 */
	private int mResource;

	/**
	 * Indicates whether or not {@link #notifyDataSetChanged()} must be called
	 * whenever {@link #mObjects} is modified.
	 */
	private boolean mNotifyOnChange = true;

	private Context mContext;

	private ArrayList<T> mOriginalValues;
	private ArrayFilter mFilter;

	private LayoutInflater mInflater;

	public BaseArrayAdapter(Context context, int resource) {
		super();
		this.mResource = resource;
		this.mContext = context;
		this.mObjects = new ArrayList<T>();
	}

	public BaseArrayAdapter(Context context, int resource, List<T> objects) {
		super();
		this.mObjects = objects;
		this.mResource = resource;
		this.mContext = context;
	}

	/**
	 * Adds the specified object at the end of the array.
	 * 
	 * @param object
	 *            The object to add at the end of the array.
	 */
	public void add(T object) {
		if (mOriginalValues != null) {
			synchronized (mLock) {
				mOriginalValues.add(object);
				if (mNotifyOnChange)
					notifyDataSetChanged();
			}
		} else {
			mObjects.add(object);
			if (mNotifyOnChange)
				notifyDataSetChanged();
		}
	}

	/**
	 * 获取所有数据
	 * 
	 * @return
	 */
	public List<T> getObjects() {
		return mObjects;
	}

	/**
	 * 添加集合
	 * 
	 * @param objects
	 */
	public void addAll(Collection<T> objects) {
		if (mOriginalValues != null) {
			synchronized (mLock) {
				mOriginalValues.addAll(objects);
				if (mNotifyOnChange)
					notifyDataSetChanged();
			}
		} else {
			mObjects.addAll(objects);
			if (mNotifyOnChange)
				notifyDataSetChanged();
		}
	}

	/**
	 * Inserts the specified object at the specified index in the array.
	 * 
	 * @param object
	 *            The object to insert into the array.
	 * @param index
	 *            The index at which the object must be inserted.
	 */
	public void insert(T object, int index) {
		if (mOriginalValues != null) {
			synchronized (mLock) {
				mOriginalValues.add(index, object);
				if (mNotifyOnChange)
					notifyDataSetChanged();
			}
		} else {
			mObjects.add(index, object);
			if (mNotifyOnChange)
				notifyDataSetChanged();
		}
	}

	/**
	 * Removes the specified object from the array.
	 * 
	 * @param object
	 *            The object to remove.
	 */
	public void remove(T object) {
		if (mOriginalValues != null) {
			synchronized (mLock) {
				mOriginalValues.remove(object);
			}
		} else {
			mObjects.remove(object);
		}
		if (mNotifyOnChange)
			notifyDataSetChanged();
	}

	/**
	 * Removes the specified object from the array.
	 * 
	 * @param index
	 *            The object to remove.
	 * @return
	 */
	public T remove(int index) {
		T t;
		if (mOriginalValues != null) {
			synchronized (mLock) {
				t = mOriginalValues.remove(index);
			}
		} else {
			t = mObjects.remove(index);
		}
		if (mNotifyOnChange)
			notifyDataSetChanged();
		return t;
	}

	/**
	 * Remove all elements from the list.
	 */
	public void clear() {
		if (mOriginalValues != null) {
			synchronized (mLock) {
				mOriginalValues.clear();
			}
		} else {
			mObjects.clear();
		}
		if (mNotifyOnChange)
			notifyDataSetChanged();
	}

	/**
	 * Sorts the content of this adapter using the specified comparator.
	 * 
	 * @param comparator
	 *            The comparator used to sort the objects contained in this
	 *            adapter.
	 */
	public void sort(Comparator<? super T> comparator) {
		Collections.sort(mObjects, comparator);
		if (mNotifyOnChange)
			notifyDataSetChanged();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		mNotifyOnChange = true;
	}

	/**
	 * Control whether methods that change the list ({@link #add},
	 * {@link #insert}, {@link #remove}, {@link #clear}) automatically call
	 * {@link #notifyDataSetChanged}. If set to false, caller must manually call
	 * notifyDataSetChanged() to have the changes reflected in the attached
	 * view.
	 * 
	 * The default is true, and calling notifyDataSetChanged() resets the flag
	 * to true.
	 * 
	 * @param notifyOnChange
	 *            if true, modifications to the list will automatically call
	 *            {@link #notifyDataSetChanged}
	 */
	public void setNotifyOnChange(boolean notifyOnChange) {
		mNotifyOnChange = notifyOnChange;
	}

	/**
	 * Returns the context associated with this array adapter. The context is
	 * used to create views from the resource passed to the constructor.
	 * 
	 * @return The Context associated with this adapter.
	 */
	public Context getContext() {
		return mContext;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getCount() {
		return mObjects != null ? mObjects.size() : 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public T getItem(int position) {
		return mObjects.get(position);
	}

	/**
	 * Returns the position of the specified item in the array.
	 * 
	 * @param item
	 *            The item to retrieve the position of.
	 * 
	 * @return The position of the specified item.
	 */
	public int getPosition(T item) {
		return mObjects.indexOf(item);
	}

	/**
	 * {@inheritDoc}
	 */
	public long getItemId(int position) {
		return position;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
		H holder;
		if (rowView == null) {
			rowView = getInfateView();
			holder = createHolder();
			initHolder(position, rowView, holder);
			rowView.setTag(holder);
		} else {
			holder = (H) rowView.getTag();
		}
		T item = getItem(position);
		initParamsHolder(position, holder, item);
		bundleValue(position, holder, item);
		return rowView;
	}

	protected View getInfateView() {
		return this.getLayoutInflater().inflate(mResource, null);
	}

	/**
	 * 创建ViewHolder
	 * 
	 * @return
	 */
	protected abstract H createHolder();

	/**
	 * 初始化ViewHolder值
	 * 
	 * @param position
	 *            当前初始化位置
	 * @param v
	 *            显示item
	 * @param holder
	 *            createHolder()方法创建的ViewHolder对象
	 */
	protected abstract void initHolder(int position, View v, H holder);

	/**
	 * 初始化Holder中控件的默认显示属性
	 * 
	 * @param position
	 *            显示位置
	 * @param holder
	 *            显示控件集合
	 * @param item
	 *            当前对象
	 */
	protected abstract void initParamsHolder(int position, H holder, T item);

	/**
	 * 绑定Holder中控件的值
	 * 
	 * @param position
	 * @param holder
	 * @param item
	 */
	protected abstract void bundleValue(int position, H holder, T item);

	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new ArrayFilter();
		}
		return mFilter;
	}

	/**
	 * 获取布局加载器
	 * 
	 * @return
	 */
	public LayoutInflater getLayoutInflater() {
		if (this.mInflater == null) {
			this.mInflater = LayoutInflater.from(getContext());
		}
		return this.mInflater;
	}

	/**
	 * <p>
	 * An array filter constrains the content of the array adapter with a
	 * prefix. Each item that does not start with the supplied prefix is removed
	 * from the list.
	 * </p>
	 */
	private class ArrayFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();

			if (mOriginalValues == null) {
				synchronized (mLock) {
					mOriginalValues = new ArrayList<T>(mObjects);
				}
			}

			if (prefix == null || prefix.length() == 0) {
				synchronized (mLock) {
					ArrayList<T> list = new ArrayList<T>(mOriginalValues);
					results.values = list;
					results.count = list.size();
				}
			} else {
				String prefixString = prefix.toString().toLowerCase();

				final ArrayList<T> values = mOriginalValues;
				final int count = values.size();

				final ArrayList<T> newValues = new ArrayList<T>(count);

				for (int i = 0; i < count; i++) {
					final T value = values.get(i);
					final String valueText = value.toString().toLowerCase();

					// First match against the whole, non-splitted value
					if (valueText.startsWith(prefixString)) {
						newValues.add(value);
					} else {
						final String[] words = valueText.split(" ");
						final int wordCount = words.length;

						for (int k = 0; k < wordCount; k++) {
							if (words[k].startsWith(prefixString)) {
								newValues.add(value);
								break;
							}
						}
					}
				}

				results.values = newValues;
				results.count = newValues.size();
			}

			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// TODO Auto-generated method stub

		}
	}
}
