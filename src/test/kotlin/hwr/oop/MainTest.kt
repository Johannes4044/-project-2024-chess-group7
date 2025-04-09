package hwr.oop

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat

class MainTest : AnnotationSpec() {

  @Test
  fun `main prints hello world to stdout`() {
    val output = captureStandardOut {
      main()
    }.trim()
    assertThat(output).isEqualTo("Hello World!")
  }

}