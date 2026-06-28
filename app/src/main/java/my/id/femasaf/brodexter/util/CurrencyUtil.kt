package my.id.femasaf.brodexter.util

import java.text.NumberFormat
import java.util.*

fun Long.toRupiah(): String {
    val localeID = Locale("in", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
    return numberFormat.format(this).replace(",00", "").replace("Rp", "Rp ")
}
