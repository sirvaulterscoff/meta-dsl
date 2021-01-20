package com.otr.fssp.mim.model

import kotlin.reflect.KProperty

class ПЗ(
    val документ: Документ,
    val разделы: List<Раздел>
)

abstract class Раздел

data class Раздел1_1(
    val функции: List<Функция>
) : Раздел()

data class Раздел1_2(
    val назначение: FormattedText
) : Раздел()

data class Раздел1_3(
    val акты: List<АктНПА>
) : Раздел()

data class Раздел1_4(
    val функции: List<Функция>
) : Раздел()

data class Раздел1_5(
    val процессы: List<СведенияОПроцессеДеятельности>
) : Раздел()

data class Раздел1_6(
    val родительскиеДокументы: List<Документ>
) : Раздел()

data class Раздел1_7(
    val справочники: List<СведенияОСправочнике>
) : Раздел()

data class Раздел1_8(
    val документы: List<СведенияОДокументе>
) : Раздел()

data class Раздел1_11(
    val регистры: List<СведенияОДокументе>
) : Раздел()

data class Раздел2_3(
    val поля: List<ГруппаПолей>
) : Раздел()

data class АктНПА(
    val акт: Акт,
    val разделы: List<String> = emptyList()
)

interface ПроцессДеятельности : CodeReferencedSubject
interface ОписаниеПроцессаДеятельности : ПроцессДеятельности {
    val наименованиеПроцесса: String
    val наименованиеГосФункции: String
}

data class СведенияОПроцессеДеятельности(
    val процесс: ПроцессДеятельности,
    override val наследование: ПризнакНаследования = ПризнакНаследования.НЕТ
) : ИмеетНаследование

data class СведенияОДокументе(
    val документ: Документ,
    override val наследование: ПризнакНаследования = ПризнакНаследования.НЕТ
) : ИмеетНаследование

interface FieldType<T> : FieldTypeRef<T> {
    val name: String
    val description: String?
    val base: String
    val format: Array<out FieldFormat>
}

data class ГруппаПолей(
    val наименование: String,
    val описание: String?,
    val поля: Any
)

interface Field<T> {
    val name: String
    val description: String?
    val type: KProperty<FieldTypeRef<T>>
    val required: Boolean
    val filling: FieldFilling?
    val sort: Boolean
    val copied: Boolean
    val inherited: ПризнакНаследования
}

interface FieldFilling
interface FieldFormat
interface FieldTypeRef<T>
interface FieldNameRef<T> : CodeReferencedSubject
interface CodeReferencedSubject {
    val code: String
}

interface Функция : CodeReferencedSubject
interface ОписаниеФункции : Функция {
    val наименование: String
    val описание: String
}

interface Акт : CodeReferencedSubject
interface ОписаниеАкта : Акт {
    val полноеНаименование: FormattedText
}

interface Документ : CodeReferencedSubject

fun объявитьТипДокумента(код: String): Документ {
    return object : Документ {
        override val code = код
    }
}

fun объявитьНПА(код: String, полноеНаименование: FormattedText): ОписаниеАкта {
    return object : ОписаниеАкта {
        override val code = код
        override val полноеНаименование = полноеНаименование
    }
}

fun объявитьПроцессДеятельности(
    код: String,
    наименованиеПроцесса: String,
    наименованиеГосФункции: String
): ОписаниеПроцессаДеятельности {
    return object : ОписаниеПроцессаДеятельности {
        override val наименованиеПроцесса = наименованиеПроцесса
        override val наименованиеГосФункции = наименованиеГосФункции
        override val code = код
    }
}

fun <T> описатьПоле(
    имя: String,
    описание: String,
    тип: KProperty<FieldTypeRef<T>>,
    обязательность: Boolean = false,
    заполнение: FieldFilling? = null,
    сортировка: Boolean = false,
    копировать: Boolean = false,
    признакНаследования: ПризнакНаследования = ПризнакНаследования.НЕТ

): Field<T> {
    return object : Field<T> {
        override val name = имя
        override val description = описание
        override val type = тип
        override val required = обязательность
        override val filling = заполнение
        override val sort = сортировка
        override val copied = копировать
        override val inherited = признакНаследования
    }
}

fun <T> объявитьТип(
    наименование: String,
    описание: String?, описаниеБазовогоТипа: String,
    vararg формат: FieldFormat
): FieldType<T> {
    return object : FieldType<T> {
        override val name = наименование
        override val description = описание
        override val base = описаниеБазовогоТипа
        override val format = формат
    }
}

interface ИмеетНаследование {
    val наследование: ПризнакНаследования
}

enum class ПризнакНаследования {
    НЕТ, Наследуется, Исключено
}

typealias  FormattedText = String
typealias Справочник = Документ
typealias СведенияОСправочнике = СведенияОДокументе