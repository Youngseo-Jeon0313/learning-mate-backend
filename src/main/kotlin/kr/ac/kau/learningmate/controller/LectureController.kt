package kr.ac.kau.learningmate.controller

import kr.ac.kau.learningmate.controller.dto.LectureDto
import kr.ac.kau.learningmate.service.LectureService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/lecture")
class LectureController(private val lectureService: LectureService) {

    @GetMapping("/{id}")
    fun getLecture(
        @PathVariable id: Long,
    ): LectureDto.Response {
        val userId = 1L
        return lectureService.getLecture(id, userId)
    }
}
