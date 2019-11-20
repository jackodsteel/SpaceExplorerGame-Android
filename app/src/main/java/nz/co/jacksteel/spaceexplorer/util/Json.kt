package nz.co.jacksteel.spaceexplorer.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.reflect.KClass

object Json {

    private val delegate: IJson = JacksonJson

    fun <T : Any> from(string: String, clazz: KClass<T>): T = delegate.from(string, clazz)

    fun <T : Any> from(src: FileInputStream, clazz: KClass<T>): T = delegate.from(src, clazz)

    fun <T : Any> to(obj: T): String = delegate.to(obj)

    fun <T : Any> write(obj: T, file: FileOutputStream) = delegate.write(obj, file)

}

interface IJson {

    fun <T : Any> from(string: String, clazz: KClass<T>): T

    fun <T : Any> from(src: FileInputStream, clazz: KClass<T>): T

    fun <T : Any> to(obj: T): String

    fun <T : Any> write(obj: T, file: FileOutputStream)

}

object JacksonJson : IJson {

    private val jacksonMapper = jacksonObjectMapper().enableDefaultTyping(ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS)

    override fun <T : Any> from(string: String, clazz: KClass<T>): T {
        return jacksonMapper.readValue(string, clazz.java)
    }

    override fun <T : Any> from(src: FileInputStream, clazz: KClass<T>): T {
        return jacksonMapper.readValue(src, clazz.java)
    }

    override fun <T : Any> to(obj: T): String {
        return jacksonMapper.writeValueAsString(obj)
    }

    override fun <T : Any> write(obj: T, file: FileOutputStream) {
        return jacksonMapper.writeValue(file, obj)
    }

}

/* Dependency has been removed
object MoshiJson: IJson {

        private val moshi = Moshi.Builder()
            .add(RuntimeJsonAdapterFactory.of(CrewType::class.java, "clazz").registerSubtype(CrewType.BigCrewman::class.java, "BigCrewman"))
            .add(RuntimeJsonAdapterFactory.of(FoodItem::class.java, "clazz"))
            .add(RuntimeJsonAdapterFactory.of(MedicalItem::class.java, "clazz"))
            .add(KotlinJsonAdapterFactory())
            .build()

    override fun <T : Any> from(string: String, clazz: KClass<T>) : T {
                val jsonAdapter = moshi.adapter(clazz.java)
                return jsonAdapter.fromJson(string) ?: throw JsonDataException("Got null value")
    }

    override fun <T : Any> from(src: FileInputStream, clazz: KClass<T>) : T {
                val jsonAdapter = moshi.adapter(clazz.java)
                val buffer = Okio.buffer(Okio.source(src))
                return jsonAdapter.fromJson(buffer) ?: throw JsonDataException("Got null value")
    }

    override fun <T : Any> to(obj: T) : String {
                val jsonAdapter = moshi.adapter(obj.javaClass)
                return jsonAdapter.toJson(obj)
    }

    override fun <T : Any> write(obj: T, file: FileOutputStream) {
                val jsonAdapter = moshi.adapter(obj.javaClass)
                val buffer = Okio.buffer(Okio.sink(file))
                jsonAdapter.toJson(buffer, obj)
    }

}
*/