package gameboard

import gameboard.drawable.BallFactory
import gameboard.drawable.Drawable
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.Timer
import kotlin.random.Random

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

class GameBoard : JPanel(), ActionListener, MouseListener {
    private val timer = Timer(16, this)
    private val drawableList = mutableListOf<Drawable>()
    private val ballFactory = BallFactory(drawableList)

    fun start() {
        timer.start()
        addMouseListener(this)
        ballFactory.create(this)
    }

    override fun paintComponent(graphics: Graphics?) {
        super.paintComponent(graphics)
        for (drawable in drawableList) {
            graphics?.let { drawable.draw(it) }
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        for (drawable in drawableList)
            drawable.next()
        repaint()
    }

    override fun mouseClicked(e: MouseEvent?) { }

    override fun mousePressed(e: MouseEvent?) {
        e!!
        val x = e.x
        val y = e.y

        if (e.button == MouseEvent.BUTTON1) {
            println("random!")
            ballFactory.randomBalls(x = x.toDouble(), y = y.toDouble(), panel = this)
        }
    }

    override fun mouseReleased(e: MouseEvent?) { }

    override fun mouseEntered(e: MouseEvent?) { }

    override fun mouseExited(e: MouseEvent?) { }
}