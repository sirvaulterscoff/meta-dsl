package com.otr.fssp.mim.executor

import com.otr.fssp.mim.decl.ТипыДанных
import com.otr.fssp.mim.model.*
import kotlin.reflect.KProperty

class ModelExecutor(val pz: ПЗ) {

    companion object {
        fun ОписаниеПЗ(block: () -> ПЗ) {
            ModelExecutor(block())
                .transform()
        }
    }

    private fun transform() {
        val v = PrintToConsoleModelVisitor()
        pz.разделы
            .forEach {
                when(it) {
                    is Раздел1_1 -> v.visitChapter1_1(it)
                    is Раздел1_2 -> v.visitChapter1_2(it)
                    is Раздел1_3 -> v.visitChapter1_3(it)
                    is Раздел1_4 -> v.visitChapter1_4(it)
                    is Раздел1_5 -> v.visitChapter1_5(it)
                    is Раздел1_6 -> v.visitChapter1_6(it)
                    is Раздел1_7 -> v.visitChapter1_7(it)
                    is Раздел1_8 -> v.visitChapter1_8(it)
                    is Раздел1_11 -> v.visitChapter1_11(it)
                    is Раздел2_3 -> v.visitChapter2_3(it)
                }
            }
    }
}

interface ModelVisitor {
    fun visitChapter1_1(chapter: Раздел1_1)
    fun visitChapter1_2(chapter: Раздел1_2)
    fun visitChapter1_3(chapter: Раздел1_3)
    fun visitChapter1_4(chapter: Раздел1_4)
    fun visitChapter1_5(chapter: Раздел1_5)
    fun visitChapter1_6(chapter: Раздел1_6)
    fun visitChapter1_7(chapter: Раздел1_7)
    fun visitChapter1_8(chapter: Раздел1_8)
    fun visitChapter1_11(chapter: Раздел1_11)
    fun visitChapter2_3(chapter: Раздел2_3)
}

class PrintToConsoleModelVisitor : ModelVisitor {
    override fun visitChapter1_1(chapter: Раздел1_1) {
        println("Раздел 1.1 Функция, в рамках которой реализуется информационный объект")
        chapter.функции.forEach {
            println("\t Функция ${it.code}")
        }
    }

    override fun visitChapter1_2(chapter: Раздел1_2) {
        println("Раздел 1.2 Назначение информационного объекта")
        println(chapter.назначение)
    }

    override fun visitChapter1_3(chapter: Раздел1_3) {
        println("Раздел 1.3 Основные нормативные правовые акты и иные документы\n")
        chapter.акты.forEach {
            println("\t" + it.акт.code + " " + it.разделы.joinToString(", "))
        }
    }

    override fun visitChapter1_4(chapter: Раздел1_4) {
        println("Раздел 1.4 Модули и функции")
        chapter.функции.forEach {
            println("\t" + it.code)
        }

    }

    override fun visitChapter1_5(chapter: Раздел1_5) {
        println("Раздел 1.5 Процессы деятельности")
        chapter.процессы.forEach {
            println(it.процесс.code + " наследуется " + it.наследование)
        }

    }

    override fun visitChapter1_6(chapter: Раздел1_6) {
        println("Раздел 1.6 Сведения о родительском документе")
        chapter.родительскиеДокументы.forEach {
            println("\t" + it.code)
        }

    }

    override fun visitChapter1_7(chapter: Раздел1_7) {
        println("Раздел 1.7 Сведения о справочниках, используемых при формировании дела")
        chapter.справочники.forEach {
            println("\t ${it.документ.code}  наследование ${it.наследование}")
        }
    }

    override fun visitChapter1_8(chapter: Раздел1_8) {
        println("Раздел 1.8 Сведения об информационных объектах, являющихся основанием для формирования дела")
        chapter.документы.forEach {
            println("\t ${it.документ.code} наследование ${it.наследование}")
        }

    }

    override fun visitChapter1_11(chapter: Раздел1_11) {
        println("Раздел 1.11 Регистры учета")
        chapter.регистры.forEach {
            println("\t ${it.документ.code} наследование ${it.наследование}")
        }
    }

    override fun visitChapter2_3(chapter: Раздел2_3) {
        println("Раздел 2.1 Требования к прикладным типам данных")
        println("Описание прикладных типов данных, используемых для описания реквизитного состава дела, приведено в таблице 14.")
        println("Раздел 2.3 Требования к реквизитному составу")
        chapter.поля.forEach {
            println("\t Группа ${it.наименование}")
            it.поля::class
                .members
                .filter {  it is KProperty }
//                .filter { it.returnType is Field<*>  }
                .map { it as KProperty<Field<*>> }
                .forEach {
                    prop ->
                    val value = prop.call(it.поля)
                    val type = value.type.call()
                    println("\t\t ${prop.name} ${value.name}")
                    println("\t\t\t ${value.description} | обязательное=${value.required} тип=${value.type.name}")
                }
        }

    }
}