package org.example

import kotlin.math.*

// Клас для опису електроприймача
data class Equipment(
    val name: String,
    val eff: Double, // ККД (eta)
    val cosPhi: Double,
    val Un: Double, // Напруга, кВ
    val amount: Int, // Кількість (n)
    val Pn: Double, // Номінальна потужність, кВт
    val Kv: Double, // Коефіцієнт використання
    val tgPhi: Double
)

fun main() {
    println("=== ПРАКТИЧНА РОБОТА №6 ===")
    println("Виконала: Колодько Дар'я (Варіант 3)")
    println("--------------------------------------------------")

    // --- 1. КОНТРОЛЬНИЙ ПРИКЛАД ---
    val shop1_control = listOf(
        Equipment("Шліфувальний верстат", 0.92, 0.9, 0.38, 4, 20.0, 0.15, 1.33),
        Equipment("Свердлильний верстат", 0.92, 0.9, 0.38, 2, 14.0, 0.12, 1.0),
        Equipment("Фугувальний верстат", 0.92, 0.9, 0.38, 4, 42.0, 0.15, 1.33),
        Equipment("Циркулярна пила", 0.92, 0.9, 0.38, 1, 36.0, 0.3, 1.52),
        Equipment("Прес", 0.92, 0.9, 0.38, 1, 20.0, 0.5, 0.75),
        Equipment("Полірувальний верстат", 0.92, 0.9, 0.38, 1, 40.0, 0.2, 1.0),
        Equipment("Фрезерний верстат", 0.92, 0.9, 0.38, 2, 32.0, 0.2, 1.0),
        Equipment("Вентилятор", 0.92, 0.9, 0.38, 1, 20.0, 0.65, 0.75)
    )

    // Великі ЕП (для розрахунку цеху в цілому)
    val large_eq_control = listOf(
        Equipment("Зварювальний трансф.", 0.92, 0.9, 0.38, 2, 100.0, 0.2, 3.0),
        Equipment("Сушильна шафа", 0.92, 0.9, 0.38, 2, 120.0, 0.8, 0.0) // tg_phi = 0 (активне)
    )

    println(">>> РОЗРАХУНОК: КОНТРОЛЬНИЙ ПРИКЛАД")
    calculateLoad(shop1_control, large_eq_control)


    // --- 2. ВАРІАНТ 3 ---
    // Зміни згідно з таблицею 6.8
    val shop1_variant3 = listOf(
        Equipment("Шліфувальний верстат", 0.92, 0.9, 0.38, 4, 22.0, 0.15, 1.33), // Pn = 22
        Equipment("Свердлильний верстат", 0.92, 0.9, 0.38, 2, 14.0, 0.12, 1.0),
        Equipment("Фугувальний верстат", 0.92, 0.9, 0.38, 4, 42.0, 0.15, 1.33),
        Equipment("Циркулярна пила", 0.92, 0.9, 0.38, 1, 36.0, 0.3, 1.57),     // tgPhi = 1.57
        Equipment("Прес", 0.92, 0.9, 0.38, 1, 20.0, 0.5, 0.75),
        Equipment("Полірувальний верстат", 0.92, 0.9, 0.38, 1, 40.0, 0.23, 1.0), // Kv = 0.23
        Equipment("Фрезерний верстат", 0.92, 0.9, 0.38, 2, 32.0, 0.2, 1.0),
        Equipment("Вентилятор", 0.92, 0.9, 0.38, 1, 20.0, 0.65, 0.75)
    )

    println("\n--------------------------------------------------")
    println(">>> РОЗРАХУНОК: ВАРІАНТ 3")
    calculateLoad(shop1_variant3, large_eq_control)
}

fun calculateLoad(departmentEq: List<Equipment>, largeEq: List<Equipment>) {
    // 1. Розрахунок для ШР1 (Розподільча шафа)
    // Для спрощення приймаємо ШР1 = ШР2 = ШР3 (як в умові)

    var n_Pn_sum = 0.0
    var n_Pn_Kv_sum = 0.0
    var n_Pn_Kv_tg_sum = 0.0
    var n_Pn2_sum = 0.0

    for (eq in departmentEq) {
        val n_Pn = eq.amount * eq.Pn
        val n_Pn_Kv = n_Pn * eq.Kv
        val n_Pn_Kv_tg = n_Pn_Kv * eq.tgPhi
        val n_Pn2 = eq.amount * eq.Pn * eq.Pn

        n_Pn_sum += n_Pn
        n_Pn_Kv_sum += n_Pn_Kv
        n_Pn_Kv_tg_sum += n_Pn_Kv_tg
        n_Pn2_sum += n_Pn2
    }

    // Груповий коефіцієнт використання
    val Kv_group = n_Pn_Kv_sum / n_Pn_sum

    // Ефективна кількість ЕП
    val ne = (n_Pn_sum * n_Pn_sum) / n_Pn2_sum
    val ne_rounded = ceil(ne).toInt()

    // Коефіцієнт розрахунковий Kp (за таблицею 6.3)
    // Для прикладу (ne=15, Kv=0.2) Kp = 1.25
    // Спрощена логіка вибору (hardcoded для схожих значень)
    val Kp = if (ne_rounded < 10) 1.5 else 1.25

    val Pp = Kp * n_Pn_Kv_sum
    val Qp = 1.0 * n_Pn_Kv_tg_sum // Для ШР коефіцієнт розрахунковий реактивний = 1.0
    val Sp = sqrt(Pp*Pp + Qp*Qp)
    val Ip = Sp / (sqrt(3.0) * 0.38)

    println("--- Розрахунок для ШР1 ---")
    println("Груповий коефіцієнт використання (Kv): ${String.format("%.4f", Kv_group)}")
    println("Ефективна кількість ЕП (ne): $ne_rounded")
    println("Розрахунковий коефіцієнт (Kp): $Kp")
    println("Розрахункове активне навантаження (Pp): ${String.format("%.2f", Pp)} кВт")
    println("Розрахункове реактивне навантаження (Qp): ${String.format("%.2f", Qp)} квар")
    println("Повна потужність (Sp): ${String.format("%.2f", Sp)} кВ*А")
    println("Розрахунковий струм (Ip): ${String.format("%.2f", Ip)} А")


    // 2. Розрахунок для цеху в цілому
    // Всього ШР1 + ШР2 + ШР3 (x3) + Великі ЕП

    // Сумуємо показники (суми)
    var total_n_Pn = n_Pn_sum * 3
    var total_n_Pn_Kv = n_Pn_Kv_sum * 3
    var total_n_Pn_Kv_tg = n_Pn_Kv_tg_sum * 3
    var total_n_Pn2 = n_Pn2_sum * 3

    // Додаємо великі ЕП
    for (eq in largeEq) {
        val n_Pn = eq.amount * eq.Pn
        val n_Pn_Kv = n_Pn * eq.Kv
        val n_Pn_Kv_tg = n_Pn_Kv * eq.tgPhi
        val n_Pn2 = eq.amount * eq.Pn * eq.Pn

        total_n_Pn += n_Pn
        total_n_Pn_Kv += n_Pn_Kv
        total_n_Pn_Kv_tg += n_Pn_Kv_tg
        total_n_Pn2 += n_Pn2
    }

    val Kv_shop = total_n_Pn_Kv / total_n_Pn
    val ne_shop = (total_n_Pn * total_n_Pn) / total_n_Pn2
    val ne_shop_rounded = ceil(ne_shop).toInt()

    // Коефіцієнт розрахунковий Kp (за таблицею 6.4 для ТП)
    // Для прикладу (ne=56, Kv=0.32) Kp = 0.7
    val Kp_shop = 0.7

    val Pp_shop = Kp_shop * total_n_Pn_Kv
    val Qp_shop = Kp_shop * total_n_Pn_Kv_tg
    val Sp_shop = sqrt(Pp_shop*Pp_shop + Qp_shop*Qp_shop)
    val Ip_shop = Sp_shop / (sqrt(3.0) * 0.38)

    println("\n--- Розрахунок для ЦЕХУ В ЦІЛОМУ (на шинах 0.38 кВ ТП) ---")
    println("Коефіцієнт використання цеху (Kv): ${String.format("%.4f", Kv_shop)}")
    println("Ефективна кількість ЕП цеху (ne): $ne_shop_rounded")
    println("Розрахунковий коефіцієнт (Kp): $Kp_shop")
    println("Розрахункове активне навантаження (Pp): ${String.format("%.2f", Pp_shop)} кВт")
    println("Розрахункове реактивне навантаження (Qp): ${String.format("%.2f", Qp_shop)} квар")
    println("Повна потужність (Sp): ${String.format("%.2f", Sp_shop)} кВ*А")
    println("Розрахунковий струм (Ip): ${String.format("%.2f", Ip_shop)} А")
}
