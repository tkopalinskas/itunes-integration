package backend.homework.itunes.configuration

import backend.homework.itunes.job.AlbumUpdateJob
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class ShedulerConfig(
    val albumUpdateJob: AlbumUpdateJob,
    ) {

    @Scheduled(cron = "0 0 0 * * *")
    fun updateAlbums() {
        albumUpdateJob.run()
    }
}