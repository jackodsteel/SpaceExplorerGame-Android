package nz.co.jacksteel.spaceexplorer.util

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import nz.co.jacksteel.spaceexplorer.model.CrewType
import nz.co.jacksteel.spaceexplorer.model.FoodItem
import nz.co.jacksteel.spaceexplorer.model.MedicalItem
import okio.Okio
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.reflect.KClass


object Json {

    private val moshi = Moshi.Builder()
        .add(RuntimeJsonAdapterFactory.of(CrewType::class.java, "clazz"))
        .add(RuntimeJsonAdapterFactory.of(FoodItem::class.java, "clazz"))
        .add(RuntimeJsonAdapterFactory.of(MedicalItem::class.java, "clazz"))
        .add(KotlinJsonAdapterFactory())
        .build()

    fun <T : Any> from(string: String, clazz: KClass<T>) : T {
        val jsonAdapter = moshi.adapter(clazz.java)
        return jsonAdapter.fromJson(string) ?: throw JsonDataException("Got null value")
    }

    fun <T : Any> from(src: FileInputStream, clazz: KClass<T>) : T {
        val jsonAdapter = moshi.adapter(clazz.java)
        val buffer = Okio.buffer(Okio.source(src))
        return jsonAdapter.fromJson(buffer) ?: throw JsonDataException("Got null value")
    }

    fun <T : Any> to(obj: T) : String {
        val jsonAdapter = moshi.adapter(obj.javaClass)
        return jsonAdapter.toJson(obj)
    }

    fun <T : Any> write(obj: T, file: FileOutputStream) {
        val jsonAdapter = moshi.adapter(obj.javaClass)
        val buffer = Okio.buffer(Okio.sink(file))
        jsonAdapter.toJson(buffer, obj)
    }



}