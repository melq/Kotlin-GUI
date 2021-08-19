package gameboard.drawable

import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class Ball(
    private val r: Double = 10.0,
    private var x: Double = 0.0,
    private var y: Double = 0.0,
    private var vx: Double = 15.0,
    private var vy: Double = -15.0,
    val color: Color = Color.DARK_GRAY,
    private val panel: JPanel
) : Drawable {
    private val e = 0.8
    private val g = 0.5

    override fun draw(graphics: Graphics) {
        val prevColor = graphics.color
        graphics.color = color
        graphics.fillOval(
            (x - r).toInt(),
            (y - r).toInt(),
            2 * r.toInt(),
            2 * r.toInt())
        graphics.color = prevColor
    }

    override fun next() {
        // 画面の幅と高さ取得
        val width  = panel.width
        val height = panel.height

        x += vx; y += vy

        /*--- 衝突判定 ---*/
        if (x < r) {
            x = r
            vx = -vx * e
        }

        if (x + r > width) {
            x = width - r
            vx = -vx * e
        }

        if (y < r) {
            y = r
            vy = -vy * e
        }

        if (y + r > height) {
            y = height - r
            vy = -vy * e
        }

        // 画面下向きの加速度
        vy += g
    }
}