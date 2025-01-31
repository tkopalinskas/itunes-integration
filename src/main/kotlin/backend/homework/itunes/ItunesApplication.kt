package backend.homework.itunes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
class ItunesApplication

fun main(args: Array<String>) {
	runApplication<ItunesApplication>(*args)
}
