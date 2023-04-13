/*
 * Copyright 2023 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.validation.nino

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.validation.core.AttributeNormalizer._
import uk.gov.hmrc.validation.core.AttributeValidator._

class NinoValidatorTest extends AnyFlatSpec with Matchers {
  "NinoValidator" should "validate a good nino" in {
    val validatedNino = for {
      goodNino <- NinoValidator.validator.validate(Nino("Ab345678c"))
    } yield goodNino
    validatedNino shouldBe Right(ValidAttribute(Nino("Ab345678c")))
  }

  it should "not validate nino with invalid prefix" in {
    val badNinos = Seq(
      "DA123456A",
      "FB123456A",
      "IC123456A",
      "QE123456A",
      "UG123456A",
      "AD123456A",
      "BF123456A",
      "CI123456A",
      "EQ123456A",
      "GU123456A",
      "GO123456A",
      "BG123456A",
      "GB123456A",
      "KN123456A",
      "NK123456A",
      "NT123456A",
      "TN123456A",
      "ZZ123456A",
    )

    badNinos.map(n => NinoValidator.validator.validate(Nino(n))).forall(_.isInstanceOf[InvalidAttribute[_]]) shouldBe false
  }
}
