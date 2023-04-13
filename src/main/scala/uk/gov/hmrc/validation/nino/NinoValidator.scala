/*
 * Copyright 2023 HM Revenue & Customs
 *
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

  override def validate(it: Nino): Either[InvalidAttribute[Nino], ValidAttribute[Nino]] = {
    it.unwrap.trim match {
      case ninoValidatorRegEx(n) => validationCheck(n).fold(
        Left(InvalidAttribute(it)): Either[InvalidAttribute[Nino], ValidAttribute[Nino]])(x =>
        Right(ValidAttribute[Nino](it))
      )
      case x                     =>
        Left[InvalidAttribute[Nino], ValidAttribute[Nino]](InvalidAttribute(it))
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

  override def normalize(it: ValidAttribute[Nino]): Either[NotNormalizedAttribute[Nino], NormalizedAttribute[Nino]] =
    Try(it.valid.unwrap.toUpperCase).fold(
      t => Left[NotNormalizedAttribute[Nino], NormalizedAttribute[Nino]](NotNormalizedAttribute(it.valid)),
      n => Right[NotNormalizedAttribute[Nino], NormalizedAttribute[Nino]](NormalizedAttribute(Nino(n)))
    )
}

object NinoNormalizer {
  object Implicits {
    implicit val ninoNormalizer: NinoNormalizer = new NinoNormalizer()
  }

  import Implicits.ninoNormalizer
  val normalizer = new uk.gov.hmrc.validation.core.Normalizer[Nino]()
}
