package gameboard

import gameboard.drawable.Ball
import gameboard.drawable.Drawable
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.Timer

class PinBall {
    fun startGame() {
        val window = JFrame("PinBall")
        window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        window.size = Dimension(650, 400)

        val gameBoard = GameBoard()

        window.add(gameBoard)
        window.isVisible = true

        gameBoard.start()
    }
}

class GameBoard : JPanel(), ActionListener {
    private val timer = Timer(16, this)
    private val drawableList = mutableListOf<Drawable>(Ball(panel = this))

    fun start() {
        timer.start()
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        println("draw: $drawableList")
        for (drawable in drawableList) {
            g?.let { drawable.draw(it) }
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        for (drawable in drawableList)
            drawable.next()
        repaint()
    }
}