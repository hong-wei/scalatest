/*
 * Copyright 2001-2013 Artima, Inc.
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
package org.scalatest

import SharedHelpers._
import FailureMessages.decorateToStringValue
import org.scalactic.{Every, One, Many}
import Matchers._
import LoneElement._

class EveryLoneElementSpec extends FunSpec {

  def wasNotGreaterThan(left: Any, right: Any): String =
    decorateToStringValue(left) + " was not greater than " + (right)

  def notLoneElement(left: Any, size: Int): String =
    "Expected " + decorateToStringValue(left) + " to contain exactly 1 element, but it has size " + size

  describe("The loneElement syntax") {

    describe("when used with List") {
      it("should work with xs.loneElement and passed when should syntax is used and xs only contains one element and the one element passed the check") {
        One(10).loneElement should be > 9
      }

      it("should work with xs.loneElement and passed when assert syntax is used and xs only contains one element and the one element passed the check") {
        assert(One(10).loneElement > 9)
      }

      it("should throw TestFailedException with correct stack depth and message when should syntax is used and xs.loneElement contains one element but it failed the check") {
        val e = intercept[exceptions.TestFailedException] {
          One(8).loneElement should be > 9
        }
        e.failedCodeFileName should be (Some("EveryLoneElementSpec.scala"))
        e.failedCodeLineNumber should be (Some(thisLineNumber - 3))
        e.message should be (Some(wasNotGreaterThan(8, 9)))
      }

      it("should throw TestFailedException with correct stack depth and message when assert syntax is used and xs.loneElement contains one element but it failed the check") {
        val e = intercept[exceptions.TestFailedException] {
          assert(One(8).loneElement > 9)
        }
        assert(e.failedCodeFileName == Some("EveryLoneElementSpec.scala"))
        assert(e.failedCodeLineNumber == Some(thisLineNumber - 3))
        assert(e.message == Some(wasNotGreaterThan(8, 9)))
      }

      it("should throw TestFailedException with correct stack depth and message when should syntax is used and xs contains > 1 elements and xs.loneElement is called") {
        val xs = Many(10, 12)
        val e = intercept[exceptions.TestFailedException] {
          xs.loneElement should be > 9
        }
        e.failedCodeFileName should be (Some("EveryLoneElementSpec.scala"))
        e.failedCodeLineNumber should be (Some(thisLineNumber - 3))
        e.message should be (Some(notLoneElement(xs, 2)))
      }

      it("should throw TestFailedException with correct stack depth and message when assert syntax is used and xs contains > 1 elements and xs.loneElement is called") {
        val xs = Many(10, 12)
        val e = intercept[exceptions.TestFailedException] {
          assert(xs.loneElement > 9)
        }
        assert(e.failedCodeFileName === Some("EveryLoneElementSpec.scala"))
        assert(e.failedCodeLineNumber === Some(thisLineNumber - 3))
        assert(e.message === Some(notLoneElement(xs, 2)))
      }
    }
  }
}
