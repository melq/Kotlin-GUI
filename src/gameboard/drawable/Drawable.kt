package gameboard.drawable

import java.awt.Graphics

interface Drawable {
    var isDead: Boolean

    fun draw(graphics: Graphics)
    fun next()
}