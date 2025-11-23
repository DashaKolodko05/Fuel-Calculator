package org.example

fun main() {
    println("=== ПРАКТИЧНА РОБОТА №2 ===")
    println("Виконала: Колодько Дар'я (Варіант 3)")
    println("--------------------------------------------------")

    // --- КОНСТАНТИ (Характеристики палива) ---
    val Qr_coal = 20.47; val Ar_coal = 25.20; val Avin_coal = 0.80; val Gvin_coal = 1.5; val n_zu = 0.985
    val Qr_oil = 39.48; val Ar_oil = 0.15 * (100 - 2.0) / 100.0; val Avin_oil = 1.0; val n_zu_oil = 0.985

    // ФУНКЦІЯ РОЗРАХУНКУ (Щоб не писати одне й те саме двічі)
    fun calculateEmissions(B_coal: Double, B_oil: Double, label: String) {
        println("\n>>> РОЗРАХУНОК: $label")

        // 1. Вугілля
        val k_coal = (1000000 / Qr_coal) * (Avin_coal * Ar_coal / (100 - Gvin_coal)) * (1 - n_zu)
        val E_coal = 0.000001 * k_coal * Qr_coal * B_coal
        println("ВУГІЛЛЯ: k = ${String.format("%.2f", k_coal)} г/ГДж; E = ${String.format("%.2f", E_coal)} т")

        // 2. Мазут
        val k_oil = (1000000 / Qr_oil) * (Avin_oil * Ar_oil / (100 - 0.0)) * (1 - n_zu_oil)
        val E_oil = 0.000001 * k_oil * Qr_oil * B_oil
        println("МАЗУТ:   k = ${String.format("%.2f", k_oil)} г/ГДж; E = ${String.format("%.2f", E_oil)} т")

        // 3. Газ (завжди 0)
        println("ГАЗ:     Емісія твердих частинок відсутня (0 т)")
    }

    // --- 1. КОНТРОЛЬНИЙ ПРИКЛАД (Дані з тексту методички) ---
    val B_coal_test = 1096363.0
    val B_oil_test = 70945.0
    calculateEmissions(B_coal_test, B_oil_test, "КОНТРОЛЬНИЙ ПРИКЛАД")

    println("--------------------------------------------------")

    // --- 2. ВАРІАНТ 3 (Дані з таблиці) ---
    val B_coal_var = 759834.56
    val B_oil_var = 99672.62
    calculateEmissions(B_coal_var, B_oil_var, "ВАРІАНТ 3")
}
