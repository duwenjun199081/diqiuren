package com.earthman.app.nim.contact;

import android.content.Context;

import com.earthman.app.nim.contact.activity.UserProfileActivity;
import com.earthman.app.nim.uikit.NimUIKit;
import com.earthman.app.nim.uikit.contact.ContactEventListener;

/**
 * UIKit联系人列表定制展示类
 * <p/>
 * Created by huangjun on 2015/9/11.
 */
public class ContactHelper {

	public static void init() {
		setContactEventListener();
	}

	private static void setContactEventListener() {
		NimUIKit.setContactEventListener(new ContactEventListener() {
			@Override
			public void onItemClick(Context context, String account) {
				UserProfileActivity.start(context, account);
			}

			@Override
			public void onItemLongClick(Context context, String account) {

			}

			@Override
			public void onAvatarClick(Context context, String account) {
				UserProfileActivity.start(context, account);
			}
		});
	}

}
