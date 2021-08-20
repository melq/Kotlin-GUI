package gameboard.drawable

import javax.swing.JPanel

abstract class Factory {
    fun create(panel: JPanel): Drawable {
        val drawable = createDrawable(panel)
        registerDrawable(drawable)
        return drawable
    }

    abstract fun createDrawable(panel: JPanel): Drawable
    abstract fun registerDrawable(drawable: Drawable)
}