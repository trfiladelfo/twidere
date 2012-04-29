package org.mariotaku.twidere.app;

import org.mariotaku.twidere.Constants;
import org.mariotaku.twidere.R;
import org.mariotaku.twidere.util.AsyncTaskManager;
import org.mariotaku.twidere.util.CommonUtils;
import org.mariotaku.twidere.util.LazyImageLoader;
import org.mariotaku.twidere.util.ServiceInterface;

import android.app.Application;

public class TwidereApplication extends Application implements Constants {

	private LazyImageLoader mListProfileImageLoader;
	private CommonUtils mCommonUtils;
	private ServiceInterface mServiceInterface;
	private AsyncTaskManager mAsyncTaskManager = new AsyncTaskManager();

	public AsyncTaskManager getAsyncTaskManager() {
		return mAsyncTaskManager;
	}

	public CommonUtils getCommonUtils() {
		return mCommonUtils;
	}

	public LazyImageLoader getListProfileImageLoader() {
		return mListProfileImageLoader;
	}

	public ServiceInterface getServiceInterface() {
		return mServiceInterface;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mListProfileImageLoader = new LazyImageLoader(this, R.drawable.ic_profile_image_default,
				getResources().getDimensionPixelSize(R.dimen.profile_image_size));
		mCommonUtils = new CommonUtils(this);
		mServiceInterface = new ServiceInterface(this);
		CommonUtils.limitDatabases(this);
	}

	@Override
	public void onLowMemory() {
		mListProfileImageLoader.clearMemoryCache();
		super.onLowMemory();
	}

}
