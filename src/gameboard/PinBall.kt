package gameboard

import gameboard.drawable.Ball
import gameboard.drawable.BallFactory
import gameboard.drawable.Drawable
import gameboard.drawable.Position
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
    private val timer = Timer(30, this)
    private val drawableList = mutableListOf<Drawable>()
    private val ballFactory = BallFactory(drawableList)

    private var isLeftPressed = false; private var pressCount = 0
    private val pressedPosition = Position(0, 0)
    private val currentPosition = Position(0, 0)
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
                if (pressedPosition == currentPosition) {
                    ball.r *= 1.05
                }
                    g.drawLine(
                        pressedPosition.x,
                        pressedPosition.y,
                        currentPosition.x,
                        currentPosition.y)
                ball.draw(g)
            }
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        if (isLeftPressed) {
            if (pressCount % 5 == 0)
                ballFactory.randomBalls(
                x = currentPosition.x.toDouble(),
                y = currentPosition.y.toDouble(),
                panel = this)
            pressCount++
        }
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
        pressedPosition.x = e!!.x
        pressedPosition.y = e.y

        when (e.button) {
            MouseEvent.BUTTON1 -> {
                isLeftPressed = true
            }
            MouseEvent.BUTTON2 -> {
                drawableList.clear()
            }
            MouseEvent.BUTTON3 -> {
                holdingBall = ballFactory.createDrawable(this) as Ball
                holdingBall!!.setPosition(pressedPosition.x.toDouble(), pressedPosition.y.toDouble())
            }
        }
    }

    override fun mouseReleased(e: MouseEvent?) {
        when (e!!.button) {
            MouseEvent.BUTTON1 -> {
                isLeftPressed = false
                pressCount = 0
            }
            MouseEvent.BUTTON3 -> {
                holdingBall?.vx = 0.2 * (pressedPosition.x - e.x)
                holdingBall?.vy = 0.2 * (pressedPosition.y - e.y)
                holdingBall?.let { ballFactory.registerDrawable(it) }
                holdingBall = null
            }
        }
    }
    override fun mouseEntered(e: MouseEvent?) { }
    override fun mouseExited(e: MouseEvent?) { }

    override fun mouseDragged(e: MouseEvent?) {
        currentPosition.x = e!!.x
        currentPosition.y = e.y
    }

    override fun mouseMoved(e: MouseEvent?) {
        currentPosition.x = e!!.x
        currentPosition.y = e.y
    }
}