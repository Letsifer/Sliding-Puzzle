import game.Field
import java.util.*

fun main(arg: Array<String>) {
    val input = Scanner(System.`in`)
    val field = Field()
    while (!field.isFinish()) {
        try {
            println(field.getViewOfField())
            println("Choose actions from ${field.getPossibleActions().joinToString(", ")}")
            val value = input.next()
            if (value == "end") {
                return
            }
            field.step(value)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
        println("Finish")
}

