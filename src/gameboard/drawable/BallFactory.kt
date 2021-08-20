package gameboard.drawable

import javax.swing.JPanel

class BallFactory(private val drawableList: MutableList<Drawable>) : Factory() {
    override fun createDrawable(panel: JPanel): Drawable {
        return Ball(panel = panel)
    }

    override fun registerDrawable(drawable: Drawable) {
        drawableList.add(drawable)
    }
}