package org.example

fun main() {
    println("=== ПРАКТИЧНА РОБОТА №2 ===")
    println("Виконав: Студент (Варіант 3)")
    println("--------------------------------------------------")

    // ВХІДНІ ДАНІ З МЕТОДИЧКИ
    val Qr_coal = 20.47
    val Ar_coal = 25.20
    val Avin_coal = 0.80
    val Gvin_coal = 1.5
    val n_zu = 0.985
    val k_tvS_coal = 0.0

    val Qr_oil_calc = 39.48
    val Ar_oil = 0.15 * (100 - 2.0) / 100.0
    val Avin_oil = 1.0
    val Gvin_oil = 0.0
    val n_zu_oil = 0.985

    // КІЛЬКІСТЬ ПАЛИВА (ТВІЙ ВАРІАНТ 3)
    val B_coal = 759834.56
    val B_oil = 99672.62
    val B_gas = 115923.14 * 1000

    println("--> Вхідні дані (Варіант 3):")
    println("Вугілля: $B_coal т")
    println("Мазут: $B_oil т")
    println("Газ: $B_gas м3")
    println("--------------------------------------------------")

    // 1. ВУГІЛЛЯ
    val k_tv_coal = (1000000 / Qr_coal) * (Avin_coal * Ar_coal / (100 - Gvin_coal)) * (1 - n_zu) + k_tvS_coal
    val E_coal = 0.000001 * k_tv_coal * Qr_coal * B_coal

    println("\n1. ВУГІЛЛЯ:")
    println("Показник емісії твердих частинок: " + String.format("%.2f", k_tv_coal) + " г/ГДж")
    println("Валовий викид: " + String.format("%.2f", E_coal) + " т")

    // 2. МАЗУТ
    val k_tv_oil = (1000000 / Qr_oil_calc) * (Avin_oil * Ar_oil / (100 - Gvin_oil)) * (1 - n_zu_oil)
    val E_oil = 0.000001 * k_tv_oil * Qr_oil_calc * B_oil

    println("\n2. МАЗУТ:")
    println("Показник емісії твердих частинок: " + String.format("%.2f", k_tv_oil) + " г/ГДж")
    println("Валовий викид: " + String.format("%.2f", E_oil) + " т")

    // 3. ГАЗ
    val E_gas = 0.0
    println("\n3. ПРИРОДНИЙ ГАЗ:")
    println("Показник емісії твердих частинок: 0.00 г/ГДж")
    println("Валовий викид: 0.00 т")
    
    println("--------------------------------------------------")
    println("ЗАГАЛЬНИЙ ВИКИД: " + String.format("%.2f", (E_coal + E_oil + E_gas)) + " т")
}
