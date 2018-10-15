package game

import java.util.*

class Field {
    private val SWAPS = 100
    private val EMPTY_CELL_VALUE = 0
    private val size : Int
    private val field : Array<IntArray>
    private var currentZeroI : Int
    private var currentZeroJ : Int

    constructor() : this(4)

    constructor(size : Int) {
        this.size = size
        field = Array(size) { i -> fillLine(i)}
        currentZeroI = size - 1; currentZeroJ = size - 1
        field[currentZeroI][currentZeroJ] = EMPTY_CELL_VALUE
        for (n in 0 until SWAPS) {
            randomSwap()
        }
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (field[i][j] == EMPTY_CELL_VALUE) {
                    currentZeroI = i; currentZeroJ = j
                }
            }
        }
    }

    fun getPossibleActions() : List<String> {
        return Action.values()
                .filter { action -> action.canBeUsed(this) }
                .map { action -> action.text }
    }

    enum class Action(val text: String) {
        U("Up") {
            override fun step(field: Field) {
                field.swap(field.currentZeroI, field.currentZeroJ, field.currentZeroI - 1, field.currentZeroJ)
                field.currentZeroI--
            }

            override fun canBeUsed(field : Field): Boolean {
                return field.currentZeroI > 0
            }
        },
        D("Down") {
            override fun step(field: Field) {
                field.swap(field.currentZeroI, field.currentZeroJ, field.currentZeroI + 1, field.currentZeroJ)
                field.currentZeroI++
            }

            override fun canBeUsed(field: Field): Boolean {
                return field.currentZeroI < field.size - 1
            }
        },
        L("Left") {
            override fun step(field: Field) {
                field.swap(field.currentZeroI, field.currentZeroJ, field.currentZeroI, field.currentZeroJ - 1)
                field.currentZeroJ--
            }

            override fun canBeUsed(field: Field): Boolean {
                return field.currentZeroJ > 0
            }
        },
        R("Right") {
            override fun step(field: Field) {
                field.swap(field.currentZeroI, field.currentZeroJ, field.currentZeroI, field.currentZeroJ + 1)
                field.currentZeroJ++
            }
            override fun canBeUsed(field: Field): Boolean {
                return field.currentZeroJ < field.size - 1
            }
        };

        abstract fun canBeUsed(field : Field) : Boolean
        abstract fun step(field: Field)
    }

    fun step(step : String) {
        val action = Action.valueOf(step)
        if (!action.canBeUsed(this)) {
            throw IllegalArgumentException("Invalid action $step")
        }
        action.step(this)
    }

    fun getViewOfField() : String {
        val builder = StringBuilder()
        for (i in 0 until size) {
            for (j in 0 until size) {
                builder.append(" ${getStringValueOfField(i, j)}")
            }
            builder.append("\n")
        }
        return builder.toString()
    }

    fun isFinish() : Boolean {
        return currentZeroI == size - 1 && currentZeroJ == size - 1
    }

    /**
     * Fill the line of field by values: i*size + j.
     */
    private fun fillLine(i : Int) : IntArray {
        val line = IntArray(this.size)
        for (j in 0 until this.size) {
            line[j] = i * this.size + j + 1
        }
        return line
    }

    private fun getStringValueOfField(i : Int, j : Int) : String {
        val value = field[i][j]
        if (value == EMPTY_CELL_VALUE) return "[]"
        return "%2d".format(value)
    }

    private fun randomSwap() {
        val randomize = Random()
        val i1 = randomize.nextInt(this.size)
        val j1 = randomize.nextInt(this.size)
        val i2 = randomize.nextInt(this.size)
        val j2 = randomize.nextInt(this.size)
        if (i1 == i2 && i2 == j2) {
            return
        }
        swap(i1, j1, i2, j2)
    }

    private fun swap(i1 : Int, j1 : Int, i2: Int, j2: Int) {
        val temp = field[i1][j1]
        field[i1][j1] = field[i2][j2]
        field[i2][j2] = temp
    }

}