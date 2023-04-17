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

package uk.gov.hmrc.validation.phonenumber

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat
import uk.gov.hmrc.validation.core.{AttributeNormalizer, AttributeValidator}

import scala.util.Try

case class TelephoneNumber(unwrap: String)

class TelephoneNumberValidator extends AttributeValidator[TelephoneNumber] {

  import AttributeValidator._

  override def validate(it: TelephoneNumber): AttributeValidator.ValidateResult[TelephoneNumber] = {
    val phoneNumberUtil = PhoneNumberUtil.getInstance()
    Try(phoneNumberUtil.parse(it.unwrap, "GB")).fold(
      e => Left(InvalidAttribute[TelephoneNumber](it)),
      pn => Right(ValidAttribute[TelephoneNumber](it))
    )
  }
}

object TelephoneNumberValidator {
  object Implicits {
    implicit val telephoneNumberValidator: TelephoneNumberValidator = new TelephoneNumberValidator()
  }

  import Implicits.telephoneNumberValidator

  val validator = new uk.gov.hmrc.validation.core.Validator[TelephoneNumber]()
}

class TelephoneNumberNormalizer extends AttributeNormalizer[TelephoneNumber] {

  import AttributeNormalizer._

  override def normalize(it: AttributeValidator.ValidAttribute[TelephoneNumber]): AttributeNormalizer.NormalizeResult[TelephoneNumber] = {
    val phoneNumberUtil = PhoneNumberUtil.getInstance()
    Try(phoneNumberUtil.parse(it.valid.unwrap, "GB")).fold(
      _ => Left(NotNormalizedAttribute[TelephoneNumber](it.valid)),
      pn => Right(NormalizedAttribute[TelephoneNumber](
        TelephoneNumber(phoneNumberUtil.format(pn, PhoneNumberFormat.E164)))
      )
    )
  }
}

object TelephoneNumberNormalizer {
  object Implicits {
    implicit val telephoneNumberNormalizer: TelephoneNumberNormalizer = new TelephoneNumberNormalizer()
  }

  import Implicits.telephoneNumberNormalizer

  val normalizer = new uk.gov.hmrc.validation.core.Normalizer[TelephoneNumber]()
}
