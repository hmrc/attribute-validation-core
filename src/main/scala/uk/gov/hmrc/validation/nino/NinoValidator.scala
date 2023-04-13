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

import uk.gov.hmrc.validation.core.{AttributeNormalizer, AttributeValidator}

import scala.util.Try

case class Nino(unwrap: String)

class NinoValidator extends AttributeValidator[Nino] {

  import AttributeValidator._

  private val ninoValidatorRegEx = """^([A-Za-z&&[^DFIQUdfiqu]][A-Za-z&&[^DFIQUOdfiquo]]\p{Digit}{6}[ABCDabcd])$""".r

  private val validationCheck: String => Option[String] = in => Option(in.take(2)).flatMap {
    case "BG" | "GB" | "KN" | "NK" | "NT" | "TN" | "ZZ" => None
    case i                                              => Some(i)
  }

  override def validate(it: Nino): AttributeValidator.ValidateResult[Nino] = {
    it.unwrap.trim match {
      case ninoValidatorRegEx(n) => validationCheck(n).toRight(InvalidAttribute).fold(
        _ => Left(InvalidAttribute(it)),
        _ => Right(ValidAttribute[Nino](it))
      )
      case x                     =>
        Left(InvalidAttribute(it))
    }
  }
}

object NinoValidator {
  object Implicits {
    implicit val ninoValidator: NinoValidator = new NinoValidator()
  }

  import Implicits.ninoValidator
  val validator = new uk.gov.hmrc.validation.core.Validator[Nino]()
}

class NinoNormalizer extends AttributeNormalizer[Nino] {

  import AttributeNormalizer._
  import AttributeValidator._

  override def normalize(it: ValidAttribute[Nino]): AttributeNormalizer.NormalizeResult[Nino] =
    Try(it.valid.unwrap.toUpperCase).fold(
      t => Left(NotNormalizedAttribute(it.valid)),
      n => Right(NormalizedAttribute(Nino(n)))
    )
}

object NinoNormalizer {
  object Implicits {
    implicit val ninoNormalizer: NinoNormalizer = new NinoNormalizer()
  }

  import Implicits.ninoNormalizer
  val normalizer = new uk.gov.hmrc.validation.core.Normalizer[Nino]()
}
