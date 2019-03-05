package com.hao.easy.wan.model

import android.os.Parcel
import android.os.Parcelable
import com.hao.easy.base.adapter.BaseItem

data class Knowledge(var name: String,
                     var children: ArrayList<Knowledge>) : BaseItem(), Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString(), ArrayList()) {
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Knowledge> {
        override fun createFromParcel(parcel: Parcel): Knowledge {
            return Knowledge(parcel)
        }

        override fun newArray(size: Int): Array<Knowledge?> {
            return arrayOfNulls(size)
        }
    }
}