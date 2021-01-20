package com.otr.fssp.mim.decl

import com.otr.fssp.mim.model.Field
import com.otr.fssp.mim.model.FieldFormat
import com.otr.fssp.mim.model.Документ

object Форматы {
}

class ФорматПоШаблону(
    val шаблон: String
) : FieldFormat

class ПолеСвязанногоОъекта(
    val типДокумента: Документ,
    val выводимоеПоле: Field<*>
) : FieldFormat