package gameboard.drawable

import java.awt.Graphics

interface Drawable {
    fun draw(graphics: Graphics)
    fun next()
}