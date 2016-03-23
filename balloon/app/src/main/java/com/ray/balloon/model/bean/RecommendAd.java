package com.ray.balloon.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 轮播广告
 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
 *
 * @author ZhuFan
 * @Date: 2015-6-2
 */
public class RecommendAd implements Parcelable{
	public long id;
	public String imgsrc;
	public String title;
	public String createdate;
	public long productid;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.imgsrc);
		dest.writeString(this.title);
		dest.writeString(this.createdate);
		dest.writeLong(this.productid);
	}


	protected RecommendAd(Parcel in) {
		this.id = in.readLong();
		this.imgsrc = in.readString();
		this.title = in.readString();
		this.createdate = in.readString();
		this.productid = in.readLong();
	}

	public static final Creator<RecommendAd> CREATOR = new Creator<RecommendAd>() {
		@Override
		public RecommendAd createFromParcel(Parcel source) {
			return new RecommendAd(source);
		}

		@Override
		public RecommendAd[] newArray(int size) {
			return new RecommendAd[size];
		}
	};
}
