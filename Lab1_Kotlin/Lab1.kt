fun main() {
    println("=== ЛАБОРАТОРНА РОБОТА №1 ===")
    println("Варіант 3")
    
    val Hp = 3.8; val Cp = 62.4; val Sp = 3.6; val Np = 1.1
    val Op = 4.3; val Wp = 6.0; val Ap = 18.8
    
    val Krs = 100.0 / (100.0 - Wp)
    val Krg = 100.0 / (100.0 - Wp - Ap)
    
    val Hc = Hp * Krs; val Cc = Cp * Krs; val Sc = Sp * Krs; val Oc = Op * Krs; val Ac = Ap * Krs
    val Hg = Hp * Krg; val Cg = Cp * Krg; val Sg = Sp * Krg; val Og = Op * Krg
    val Qrn = (339 * Cp + 1030 * Hp - 108.8 * (Op - Sp) - 25 * Wp) / 1000.0

    println("Коеф. сухої маси: %.2f".format(Krs))
    println("Коеф. горючої маси: %.2f".format(Krg))
    println("Склад сухої: H=%.2f, C=%.2f, S=%.2f, O=%.2f, A=%.2f".format(Hc, Cc, Sc, Oc, Ac))
    println("Склад горючої: H=%.2f, C=%.2f, S=%.2f, O=%.2f".format(Hg, Cg, Sg, Og))
    println("Нижча теплота згоряння: %.2f МДж/кг".format(Qrn))

    println("\n--- МАЗУТ ---")
    val Cg_m = 85.5; val Hg_m = 11.2; val Og_m = 0.8; val Sg_m = 2.5; val Wp_m = 2.0; val Ad_m = 0.15; val Vg_m = 333.3; val Qdaf_m = 40.40
    val Ap_m = Ad_m * (100 - Wp_m) / 100.0
    val factor = (100 - Wp_m - Ap_m) / 100.0
    val Cp_m = Cg_m * factor; val Hp_m = Hg_m * factor; val Sp_m = Sg_m * factor; val Op_m = Og_m * ((100 - Wp_m - Ad_m) / 100.0); val Vp_m = Vg_m * (100 - Wp_m) / 100.0
    val Qp_m = Qdaf_m * ((100 - Wp_m - Ad_m) / 100.0) - 0.025 * Wp_m

    println("Мазут робоча маса: C=%.2f, H=%.2f, S=%.2f, O=%.2f, A=%.2f, V=%.2f".format(Cp_m, Hp_m, Sp_m, Op_m, Ap_m, Vp_m))
    println("Теплота мазуту: %.2f МДж/кг".format(Qp_m))
}