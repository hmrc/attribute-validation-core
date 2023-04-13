/*
 * Copyright 2023 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.validation.core

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.validation.nino.{Nino, NinoNormalizer, NinoValidator}

import scala.util.Try

class NinoSpec extends AnyFlatSpec with Matchers {

  import AttributeValidator._
  import AttributeNormalizer._


  //val badNino01 = ninoValidator.validate(Nino("12345678"))
  //val badNino02 = ninoValidator.validate(Nino("aB345678"))
  //val badNino03 = ninoValidator.validate(Nino("Ab34567c"))

  val res = for {
    goodNino01 <- NinoValidator.validator.validate(Nino("Ab345678c"))
    normGoodNino01 <- NinoNormalizer.normalizer.normalize(goodNino01)
  } yield normGoodNino01
  res shouldBe Right(NormalizedAttribute(Nino("AB345678C")))
}
