package gameboard

import gameboard.drawable.Ball
import gameboard.drawable.BallFactory
import gameboard.drawable.Drawable
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.*
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

class GameBoard : JPanel(), ActionListener, MouseListener, MouseMotionListener {
    private val timer = Timer(16, this)
    private val drawableList = mutableListOf<Drawable>()
    private val ballFactory = BallFactory(drawableList)

    private var pressedX = 0; private var mouseX = 0
    private var pressedY = 0; private var mouseY = 0
    private var holdingBall: Ball? = null

    fun start() {
        timer.start()
        addMouseListener(this)
        addMouseMotionListener(this)
        ballFactory.create(this)
    }

    override fun paintComponent(graphics: Graphics?) {
        super.paintComponent(graphics)
        graphics?.let { g ->
            for (drawable in drawableList) {
                drawable.draw(g)
            }
            holdingBall?.let { ball ->
                ball.draw(g)
                g.drawLine(pressedX, pressedY, mouseX, mouseY)
            }
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        var i = 0
        while (i < drawableList.size) {
            drawableList[i].next()
            if (drawableList[i].isDead) {
                drawableList.removeAt(i)
                i--
            }
            i++
        }
        repaint()
    }

    override fun mouseClicked(e: MouseEvent?) { }

    override fun mousePressed(e: MouseEvent?) {
        pressedX = e!!.x
        pressedY = e.y

        when (e.button) {
            MouseEvent.BUTTON1 -> {
                ballFactory.randomBalls(x = pressedX.toDouble(), y = pressedY.toDouble(), panel = this)
            }
            MouseEvent.BUTTON3 -> {
                holdingBall = ballFactory.createDrawable(this) as Ball
                holdingBall!!.setDimension(pressedX.toDouble(), pressedY.toDouble())
            }
        }
    }

    override fun mouseReleased(e: MouseEvent?) {
        when (e!!.button) {
            MouseEvent.BUTTON3 -> {
                holdingBall?.vx = 0.2 * (pressedX - e.x)
                holdingBall?.vy = 0.2 * (pressedY - e.y)
                holdingBall?.let { drawableList.add(it) }
                holdingBall = null
            }
        }
    }
    override fun mouseEntered(e: MouseEvent?) { }
    override fun mouseExited(e: MouseEvent?) { }

    override fun mouseDragged(e: MouseEvent?) {
        mouseX = e!!.x
        mouseY = e.y
    }

    override fun mouseMoved(e: MouseEvent?) {
        mouseX = e!!.x
        mouseY = e.y
    }
}