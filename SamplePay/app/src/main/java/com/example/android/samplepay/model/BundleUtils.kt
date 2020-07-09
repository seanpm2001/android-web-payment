/*
 * Copyright 2019 Google LLC
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

package com.example.android.samplepay.model

import android.os.Bundle
import org.json.JSONException

internal fun Bundle.getCertificateChain(key: String): List<ByteArray> {
    return getParcelableArray(key)?.map {
        (it as Bundle).getByteArray("certificate") ?: byteArrayOf()
    } ?: emptyList()
}

internal fun Bundle.getPaymentAmount(key: String): PaymentAmount? {
    val s = getString(key)
    return if (s != null) {
        try {
            PaymentAmount.parse(s)
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    } else {
        null
    }
}

internal fun Bundle.getShippingOptions(key: String): List<ShippingOption>? {
    return getParcelableArray(key)?.mapNotNull {
        ShippingOption.from(it as Bundle)
    }
}

internal fun Bundle.getMethodData(key: String): Map<String, String> {
    val b = getBundle(key) ?: return emptyMap()
    return b.keySet().map { it to b.getString(it, "[]") }.toMap()
}
