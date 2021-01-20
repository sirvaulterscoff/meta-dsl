package com.otr.fssp.mim.decl

import com.otr.fssp.mim.model.ОписаниеФункции
import com.otr.fssp.mim.model.Функция

object ФункцииСистемы {
    val Функция1 = object : ОписаниеФункции {
        override val code = "Функция_1_1"
        override val наименование = "Функция такая-то"
        override val описание = "Описание этой самой функции"
    }
}