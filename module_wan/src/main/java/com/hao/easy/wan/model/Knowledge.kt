package com.hao.easy.wan.model

import android.os.Parcel
import android.os.Parcelable

class Knowledge(
    var id: Int,
    var name: String?,
    var children: ArrayList<Knowledge>?
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.createTypedArrayList(CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
        writeTypedList(children)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Knowledge> = object : Parcelable.Creator<Knowledge> {
            override fun createFromParcel(source: Parcel): Knowledge = Knowledge(source)
            override fun newArray(size: Int): Array<Knowledge?> = arrayOfNulls(size)
        }
    }
}