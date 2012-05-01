package org.mariotaku.twidere.util;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AsyncTaskManager {

	private SparseArray<ManagedAsyncTask> mTasks = new SparseArray<ManagedAsyncTask>();

	public int add(ManagedAsyncTask task, boolean exec, Object... params) {
		int hashCode = task.hashCode();
		mTasks.put(hashCode, task);
		if (exec) {
			execute(hashCode);
		}
		return hashCode;
	}

	public boolean cancel(int hashCode) {
		return cancel(hashCode, true);
	}

	public boolean cancel(int hashCode, boolean mayInterruptIfRunning) {
		ManagedAsyncTask task = mTasks.get(hashCode);
		if (task != null) {
			task.cancel(mayInterruptIfRunning);
			mTasks.remove(hashCode);
			return true;
		}
		return false;
	}

	/**
	 * Cancel all tasks added, then clear all tasks.
	 */
	public void cancelAll() {
		for (int i = 0; i < mTasks.size(); i++) {
			cancel(mTasks.keyAt(i));
		}
		mTasks.clear();
	}

	public boolean execute(int hashCode, Object... params) {
		ManagedAsyncTask task = mTasks.get(hashCode);
		if (task != null) {
			task.execute(params);
			return true;
		}
		return false;
	}

	public List<ManagedAsyncTask<?>> getTaskList() {
		List<ManagedAsyncTask<?>> list = new ArrayList<ManagedAsyncTask<?>>();

		for (int i = 0; i < mTasks.size(); i++) {
			ManagedAsyncTask task = mTasks.valueAt(i);
			if (task != null) {
				list.add(task);
			}
		}
		return list;
	}

	public boolean hasActivatedTask() {
		return mTasks.size() > 0;
	}

	public boolean isExcuting(int hashCode) {
		ManagedAsyncTask task = mTasks.get(hashCode);
		if (task != null && !task.isCancelled()) return true;
		return false;
	}

	public void remove(int hashCode) {
		mTasks.remove(hashCode);
	}

	private static AsyncTaskManager sInstance;

	public static AsyncTaskManager getInstance() {
		if (sInstance == null) {
			sInstance = new AsyncTaskManager();
		}
		return sInstance;
	}
}
