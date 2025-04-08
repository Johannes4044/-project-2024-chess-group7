package hwr.oop

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

// TODO Delete this placeholder test.
class SchachBrettTest : AnnotationSpec() {
  @Test
  fun `example returns hello world`() {
    val example = KotlinExample()
    val result = example.sayHello()
    assertThat(result).isEqualTo("Hello World!") }
}