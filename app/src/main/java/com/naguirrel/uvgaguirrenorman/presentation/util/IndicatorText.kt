package com.naguirrel.uvgaguirrenorman.presentation.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun indicadorTexto(fromLocal: Boolean, lastUpdate: Long?): String? {
    return if (!fromLocal) {
        "Viendo data más reciente"
    } else if (fromLocal && lastUpdate != null) {
        val fecha = Instant.ofEpochMilli(lastUpdate).atZone(ZoneId.systemDefault())
        val formatted = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(fecha)
        "Viendo data del $formatted"
    } else {
        null
    }
}
