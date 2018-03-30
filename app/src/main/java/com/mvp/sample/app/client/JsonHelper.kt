package com.mvp.sample.app.client

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken


/**
 * 作者：秦川小将
 * 时间：2018/3/30
 * 描述：解析器
 */
class JsonHelper {

    companion object {
        /**
         * json to any
         */
        fun <T> fromJsonAny(json: String, clazz: Class<T>): T? {
            return Gson().fromJson(json, clazz)
        }

        /**
         * json to Array
         */
        fun <T> fromJsonArray(json: String, clazz: Class<T>): ArrayList<Any> {
            val mType = object : TypeToken<ArrayList<JsonObject>>() {}.type
            val mJsonArray: ArrayList<JsonObject> = Gson().fromJson(json, mType)
            val mArray = arrayListOf<Any>()
            for (it in mJsonArray) {
                mArray.add(Gson().fromJson(it, clazz)!!)
            }
            return mArray
        }
    }
}