package gameboard.drawable

import java.awt.Color
import javax.swing.JPanel
import kotlin.random.Random

class BallFactory(private val drawableList: MutableList<Drawable>) : Factory() {
    private val random = Random(1)

    override fun createDrawable(panel: JPanel): Drawable {
        return Ball(
            panel = panel,
            color = Color(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)))
    }

    override fun registerDrawable(drawable: Drawable) {
        drawableList.add(drawable)
    }

    fun randomBalls(r: Double = 10.0,
                    x: Double = 0.0,
                    y: Double = 0.0,
                    panel: JPanel,
                    n: Int = 10) {
        for (i in 0 .. n) {
            registerDrawable(
                Ball(r, x, y,
                    (random.nextInt(30) - 15).toDouble(),
                    (random.nextInt(15) - 15).toDouble(),
                    600,
                    Color(
                        random.nextInt(256),
                        random.nextInt(256),
                        random.nextInt(256)),
                    panel))
        }
    }
}