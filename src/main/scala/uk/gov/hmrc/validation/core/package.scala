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

package uk.gov.hmrc.validation

package object core {

  trait AttributeValidator[T] {

    import AttributeValidator._

    def validate(it: T): ValidateResult[T]
  }

  object AttributeValidator {

    type ValidateResult[T] = Either[InvalidAttribute[T], ValidAttribute[T]]

    case class ValidAttribute[T](valid: T)

    case class InvalidAttribute[T](invalid: T)
  }

  trait AttributeNormalizer[T] {

    import AttributeValidator._
    import AttributeNormalizer._

    def normalize(it: ValidAttribute[T]): NormalizeResult[T]
  }

  object AttributeNormalizer {

    type NormalizeResult[T] = Either[NotNormalizedAttribute[T], NormalizedAttribute[T]]

    case class NormalizedAttribute[T](normalized: T)

    case class NotNormalizedAttribute[T](unnormlized: T)
  }

  final class Validator[T: AttributeValidator] {

    import AttributeValidator._

    def validate(it: T): ValidateResult[T] =
      implicitly[AttributeValidator[T]].validate(it)
  }

  final class Normalizer[T: AttributeNormalizer] {

    import AttributeValidator._
    import AttributeNormalizer._

    def normalize(it: ValidAttribute[T]): NormalizeResult[T] =
      implicitly[AttributeNormalizer[T]].normalize(it)
  }
}
