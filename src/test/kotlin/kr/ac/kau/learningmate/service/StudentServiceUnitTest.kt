package kr.ac.kau.learningmate.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kr.ac.kau.learningmate.domain.Student
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kr.ac.kau.learningmate.repository.StudentRepository

@ExtendWith(MockKExtension::class)
class StudentServiceUnitTest {

    @MockK
    lateinit var studentRepository: StudentRepository

    @InjectMockKs
    lateinit var studentService: StudentService

    @BeforeEach
    fun setup() {
    }

    @Test
    fun `findAllStudents should return a list of students`() {
        // Given
        val students = listOf(
            Student(id = 1, name = "John Doe", studentId = 1),
            Student(id = 2, name = "Jane Doe", studentId = 2),
        )

        every { studentRepository.findAll() } returns students

        // When
        val result = studentService.findAllStudents()

        // Then
        assertEquals(students, result)
    }
}
