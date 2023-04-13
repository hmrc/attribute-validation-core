/*
 * Copyright 2023 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.validation

package object core {

  trait AttributeValidator[T] {

    import AttributeValidator._

    def validate(it: T): Either[InvalidAttribute[T], ValidAttribute[T]]
  }

  object AttributeValidator {
    case class ValidAttribute[T] (valid: T)

    case class InvalidAttribute[T] (invalid: T)
  }

  trait AttributeNormalizer[T] {

    import AttributeValidator._
    import AttributeNormalizer._

    def normalize(it: ValidAttribute[T]): Either[NotNormalizedAttribute[T], NormalizedAttribute[T]]
  }

  object AttributeNormalizer {
    case class NormalizedAttribute[T] (normalized: T)

    case class NotNormalizedAttribute[T](unnormlized: T)
  }

  final class Validator[T: AttributeValidator] {

    import AttributeValidator._

    def validate(it: T): Either[InvalidAttribute[T], ValidAttribute[T]] = {
      implicitly[AttributeValidator[T]].validate(it)
    }
  }

  final class Normalizer[T: AttributeNormalizer] {

    import AttributeValidator._
    import AttributeNormalizer._

    def normalize(it: ValidAttribute[T]): Either[NotNormalizedAttribute[T], NormalizedAttribute[T]] = {
      implicitly[AttributeNormalizer[T]].normalize(it)
    }
  }
}
