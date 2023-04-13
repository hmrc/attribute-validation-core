/*
 * Copyright 2023 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.validation.nino

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.validation.core.AttributeNormalizer._

class NinoNormalizerTest extends AnyFlatSpec with Matchers {
  "NinoNormalizer" should "normalize a good nino" in {
    val normalizedNino = for {
      goodNino <- NinoValidator.validator.validate(Nino("Ab345678c"))
      normGoodNino <- NinoNormalizer.normalizer.normalize(goodNino)
    } yield normGoodNino
    normalizedNino shouldBe Right(NormalizedAttribute(Nino("AB345678C")))
  }
}
