package org.example

import kotlin.math.*

fun main() {
    println("=== ПРАКТИЧНА РОБОТА №3 ===")
    println("Виконала: Колодько Дар'я (Варіант 3)")
    println("--------------------------------------------------")

    // Вхідні дані згідно з Задачею 1 (Методичні вказівки)
    val Pc = 5.0      // Середньодобова потужність, МВт
    val sigma1 = 1.0  // Середньоквадратичне відхилення ДО вдосконалення
    val sigma2 = 0.25 // Середньоквадратичне відхилення ПІСЛЯ вдосконалення
    val B = 7.0       // Вартість електроенергії, грн/кВт*год

    println("--> Вхідні дані:")
    println("Потужність станції (Pc): $Pc МВт")
    println("Вартість електроенергії (B): $B грн/кВт*год")
    println("--------------------------------------------------")

    // Сценарій 1: Стара система прогнозування
    calculateProfit(Pc, sigma1, B, "СЦЕНАРІЙ 1: Базова система прогнозування (sigma=$sigma1)")

    println("--------------------------------------------------")

    // Сценарій 2: Вдосконалена система прогнозування
    calculateProfit(Pc, sigma2, B, "СЦЕНАРІЙ 2: Вдосконалена система (sigma=$sigma2)")
}

fun calculateProfit(Pc: Double, sigma: Double, B: Double, label: String) {
    println("\n>>> $label")

    // 1. Розрахунок частки енергії без небалансів (інтеграл імовірності)
    // Допустиме відхилення 5% від 5 МВт = 0.25 МВт
    val delta = 0.25

    // Використовуємо Error Function (erf) для розрахунку нормального розподілу
    // Формула: share = integral from (Pc-0.25) to (Pc+0.25)
    val share = erf(delta / (sigma * sqrt(2.0)))

    println("Частка енергії, що генерується без небалансів: ${String.format("%.1f", share * 100)}%")

    // 2. Розрахунок енергії (МВт*год) за добу (24 години)
    val W_total = Pc * 24
    val W_profit = W_total * share           // Енергія, продана за повною ціною
    val W_penalty = W_total * (1 - share)    // Енергія, за яку платиться штраф (небаланс)

    // 3. Фінансові показники (тис. грн)
    // Ціна B вказана за кВт*год, тому множимо на 1000 для МВт
    val Profit_gross = W_profit * B * 1000
    val Penalty_cost = W_penalty * B * 1000

    // Чистий дохід = Прибуток - Штраф
    val Result = Profit_gross - Penalty_cost

    println("Прибуток від продажу: ${String.format("%.1f", Profit_gross / 1000)} тис. грн")
    println("Втрати (штраф за небаланс): ${String.format("%.1f", Penalty_cost / 1000)} тис. грн")

    print("ФІНАНСОВИЙ РЕЗУЛЬТАТ: ")
    if (Result < 0) {
        println("ЗБИТОК ${String.format("%.1f", abs(Result) / 1000)} тис. грн")
    } else {
        println("ПРИБУТОК ${String.format("%.1f", Result / 1000)} тис. грн")
    }
}

// Математична функція для розрахунку інтегралу помилок (Gauss error function)
fun erf(x: Double): Double {
    val a1 =  0.254829592; val a2 = -0.284496736; val a3 =  1.421413741
    val a4 = -1.453152027; val a5 =  1.061405429; val p  =  0.3275911
    val sign = if (x < 0) -1.0 else 1.0
    val xAbs = abs(x)
    val t = 1.0 / (1.0 + p * xAbs)
    val y = 1.0 - (((((a5 * t + a4) * t) + a3) * t + a2) * t + a1) * t * exp(-xAbs * xAbs)
    return sign * y
}
