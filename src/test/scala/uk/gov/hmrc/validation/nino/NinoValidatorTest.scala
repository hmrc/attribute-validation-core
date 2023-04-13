/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
